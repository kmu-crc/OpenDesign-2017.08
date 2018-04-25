/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opendesign.dao.DesignerDAO;
import com.opendesign.dao.ProductDAO;
import com.opendesign.utils.StringUtil;
import com.opendesign.vo.DesignPreviewImageVO;
import com.opendesign.vo.DesignWorkFileVO;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.MainItemVO;

/**
 * 
 * <pre>
 * 디자인(작품)의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class ProductService {
	
	/**
	 * 디자인(작품) DAO 인스턴스 
	 */
	@Autowired
	ProductDAO dao;
	
	/**
	 * 디자이너/제작자 DAO 인스턴스
	 */
	@Autowired
	DesignerDAO designerDao;
	
	/**
	 * 디자인(작품) 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<DesignWorkVO> selectProductList(Map<String, Object> paramMap) {
		return dao.selectProductList(paramMap);
	}

	/**
	 * 디자인(작품) 총개수 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public int selectProductCount(Map<String, Object> paramMap) {
		return dao.selectProductCount(paramMap);
	}
	
	/**
	 * 디자인(작품)과 완료된 프로젝트 총개수 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public int selectProductWithProjCount(Map<String, Object> paramMap) {
		return dao.selectProductWithProjCount(paramMap); 
	}

	/**
	 * 디자인(작품)과 완료된 프로젝트 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MainItemVO> selectProductWithProjList(Map<String, Object> paramMap) {
		return dao.selectProductWithProjList(paramMap);
	}
	
	/**
	 * 나의 디자인(작품) 조회
	 * 
	 * @param memberSeq
	 * @param designWorkSeq
	 * @return
	 */
	@Deprecated
	public DesignWorkVO selectMyProduct(int memberSeq, int designWorkSeq) {
		DesignWorkVO designWork = dao.selectMyProduct(memberSeq, designWorkSeq);
		if( designWork != null && StringUtil.isNotEmpty(designWork.getSeq())  ){
			designWork.setCateList(dao.selectProductCategoryList(designWorkSeq));
			
			String strDesignWorkSeq = designWork.getSeq();
			designWork.setImageList(designerDao.selectDesignPreviewImageList(strDesignWorkSeq));
			designWork.setFileList(designerDao.selectDesignWorkFileList(strDesignWorkSeq));
			
		}
		
		return designWork;
	}
	
	/**
	 * 디자인(작품) 등록
	 * 
	 * @param product
	 * @param categorys
	 * @return
	 */
	@Transactional
	public int insertProduct(DesignWorkVO product, String[] categorys) {
		/*
		 * 디자인(작품)
		 */
		DesignWorkVO iProduct = dao.insertProduct(product);

		/*
		 * 카테고리 
		 */
		int productSeq = Integer.parseInt(iProduct.getSeq());
		String registerTime = iProduct.getRegisterTime();
		dao.insertProductCategory(productSeq, categorys, registerTime);
		
		/*
		 * 작품 
		 */
		String designWorkSeq = Integer.toString(productSeq);
		insertProductFiles(designWorkSeq, iProduct.getImageList());
		
		/*
		 * 오픈 소스 파일 
		 */
		List<DesignWorkFileVO> openSourceList = iProduct.getFileList();
		insertOpenSourceFiles(designWorkSeq, registerTime, openSourceList);
		
		return productSeq;
	}

	/**
	 * 디자인(작품) 수정
	 * 
	 * @param product
	 * @param categoryCodes
	 * @param removeProductSeqs
	 * @param removeOpenSourceSeqs
	 * @return
	 */
	@Transactional
	public int updateProduct(DesignWorkVO product, String[] categoryCodes,
			String[] removeProductSeqs, String[] removeOpenSourceSeqs) {
		/*
		 * 디자인(작품)
		 */
		int result = dao.updateProduct(product);

		/*
		 * 카테고리 
		 */
		int productSeq = Integer.parseInt(product.getSeq());
		String updateTime = product.getUpdateTime();
		dao.insertProductCategory(productSeq, categoryCodes, updateTime);
		
		/*
		 * 삭제할 작품 
		 */
		if( removeProductSeqs != null && removeProductSeqs.length > 0 ){
			dao.deleteProductFiles(removeProductSeqs);
		}
		
		/*
		 * 삭제할 오픈 소스 파일
		 */
		if( removeOpenSourceSeqs != null && removeOpenSourceSeqs.length > 0 ){
			dao.deleteOpenSourceFiles(removeOpenSourceSeqs);
		}
		
		/*
		 * 작품 파일
		 */
		String designWorkSeq = Integer.toString(productSeq);
		insertProductFiles(designWorkSeq, product.getImageList());
		
		/*
		 * 오픈 소스 파일 
		 */
		insertOpenSourceFiles(designWorkSeq, updateTime, product.getFileList());

		return result;
		
	}
	
	/**
	 * 디자인(작품)의 작품 파일 등록
	 * 
	 * @param designWorkSeq
	 * @param productList
	 */
	private void insertProductFiles(String designWorkSeq, List<DesignPreviewImageVO> productList){
		for (DesignPreviewImageVO aProduct : productList) {
			aProduct.setDesignWorkSeq(designWorkSeq);
			
			dao.insertProductFile(aProduct);
			
		}
	}
	
	/**
	 * 디자인(작품)의 오픈 소스 파일 등록
	 * 
	 * @param designWorkSeq
	 * @param registerTime
	 * @param openSourceList
	 */
	private void insertOpenSourceFiles(String designWorkSeq, String registerTime, List<DesignWorkFileVO> openSourceList){
		for (DesignWorkFileVO aOpen : openSourceList) {
			aOpen.setDesignWorkSeq(designWorkSeq);
			aOpen.setRegisterTime(registerTime);
			
			dao.insertOpenSourceFile(aOpen);
			
		}
	}

	/**
	 * <pre>
	 * 디자인(작품) 삭제
	 * 삭제 flag로 처리
	 * </pre>
	 * 
	 * @param product
	 * @return
	 */
	@Transactional
	public int deleteProduct(DesignWorkVO product) {
		// 삭제flag 처리:
		//dao.deleteProductCategoryForProduct(product);
		//dao.deleteProductFilesForProduct(product);
		//dao.deleteOpenSourceFilesForProduct(product);
		//dao.deleteProductLikeForProduct(product);
		//dao.deleteProductCommentForProduct(product);
		
		return dao.deleteProduct(product);
	}

	
	/**
	 * 이달의 Best 작품 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<DesignWorkVO> selectBestProductList(Map<String, Object> paramMap) {
		return dao.selectBestProductList(paramMap);
	}
	
	/**
	 * 카테고리 이름 조회
	 * 
	 * @param category
	 * @return
	 */
	private String selectCategoryName(String category) {
		return dao.selectCategoryName(category);
	}
	
	/**
	 * 카테고리 이름 조회
	 * @param category
	 * @return
	 */
	public MainItemVO settingCategoryNm(String category) {
		int categoryLength = category.length();
		MainItemVO item = new MainItemVO();
		String subCategory = "";
		
		/*
		 * depth 1
		 */
		if(categoryLength >= 3) {
			subCategory = category.substring(0, 3);
			
			item.setFirstCategoryNm(this.selectCategoryName(subCategory));
			
			/*
			 * depth 2
			 */
			if(categoryLength >= 6) {
				subCategory = category.substring(0, 6);
				
				item.setSecondCategoryNm(this.selectCategoryName(subCategory));
				
				/*
				 * depth 3
				 */
				if(categoryLength >= 9) {
					subCategory = category.substring(0, 9);
					
					item.setThirdCategoryNm(this.selectCategoryName(subCategory));
				}
			}
		}
		
		return item;
	}
	
	
	
}
