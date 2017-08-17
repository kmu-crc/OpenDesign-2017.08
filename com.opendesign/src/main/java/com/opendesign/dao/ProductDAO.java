/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.DesignPreviewImageVO;
import com.opendesign.vo.DesignWorkFileVO;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.MainItemVO;

/**
 * <pre>
 * 디자인(작품) DAO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Repository
public class ProductDAO {
	
	private static final String SQL_NS = "ProductSQL.";
	
	@Autowired
    SqlSession sqlSession;
	
	/**
	 * 디자인(작품) 총개수 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public int selectProductCount(Map<String, Object> paramMap) {
		return (Integer) sqlSession.selectOne( SQL_NS + "selectProductCount", paramMap);
	}

	/**
	 * 디자인(작품) 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<DesignWorkVO> selectProductList(Map<String, Object> paramMap) {
		return sqlSession.selectList( SQL_NS + "selectProductList", paramMap);
	}
	
	/**
	 * 디자인(작품)과 완료된 프로젝트 총개수 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public int selectProductWithProjCount(Map<String, Object> paramMap) {
		return (Integer) sqlSession.selectOne( SQL_NS + "selectProductWithProjCount", paramMap);
	}

	/**
	 * 디자인(작품)과 완료된 프로젝트 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MainItemVO> selectProductWithProjList(Map<String, Object> paramMap) {
		return sqlSession.selectList( SQL_NS + "selectProductWithProjList", paramMap);
	}
	
	/**
	 * 나의 디자인(작품) 조회
	 * 
	 * @param memberSeq
	 * @param designWorkSeq
	 * @return
	 */
	public DesignWorkVO selectMyProduct(int memberSeq, int designWorkSeq) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seq", designWorkSeq);
		paramMap.put("memberSeq", memberSeq);
		
		return sqlSession.selectOne( SQL_NS + "selectMyProduct", paramMap);
		
	}
	
	/**
	 * 디자인(작품) 카테고리 조회
	 * 
	 * @param designWorkSeq
	 * @return
	 */
	public List<CategoryVO> selectProductCategoryList(int designWorkSeq) {
		return sqlSession.selectList( SQL_NS + "selectProductCategoryByDesignWorkSeq", designWorkSeq);
	}
	
	/**
	 * 디자인(작품) 등록
	 * 
	 * @param designWork
	 * @return
	 */
	public DesignWorkVO insertProduct(DesignWorkVO designWork) {
		sqlSession.insert(SQL_NS + "insertProduct", designWork);
		return designWork;
	}

	/**
	 * 디자인(작품) 수정
	 * 
	 * @param designWork
	 * @return
	 */
	public int updateProduct(DesignWorkVO designWork) {
		return sqlSession.update(SQL_NS + "updateProduct", designWork);
	}

	/**
	 * 디자인(작품) 카테고리 등록
	 * 
	 * @param designSeq
	 * @param categorys
	 * @param registerTime
	 */
	public void insertProductCategory(int designSeq, String[] categorys, String registerTime) {
		sqlSession.delete(SQL_NS + "deleteProductCategory", designSeq);
		if (categorys != null && categorys.length > 0) {
			for (String aCateGory : categorys) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("designWorkSeq", designSeq);
				paramMap.put("categoryCode", aCateGory);
				paramMap.put("registerTime", registerTime);

				sqlSession.insert(SQL_NS + "insertProductCategory", paramMap);
			}
		}
	}

	/**
	 * 디자인(작품) 작품파일 등록
	 * 
	 * @param productFile
	 */
	public void insertProductFile(DesignPreviewImageVO productFile) {
		sqlSession.insert(SQL_NS + "insertProductFile", productFile);
		
	}

	/**
	 * 디자인(작품) 오픈 소스 등록
	 * 
	 * @param openSource
	 */
	public void insertOpenSourceFile(DesignWorkFileVO openSource) {
		sqlSession.insert(SQL_NS + "insertOpenSourceFile", openSource);
		
	}

	/**
	 * 디자인(작품) 작품파일 삭제
	 * 
	 * @param seqs
	 */
	public void deleteProductFiles(String[] seqs) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seqs", seqs);
		
		sqlSession.insert(SQL_NS + "deleteProductFiles", paramMap);
	}

	/**
	 * 디자인(작품) 오픈 소스 삭제
	 * 
	 * @param seqs
	 */
	public void deleteOpenSourceFiles(String[] seqs) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seqs", seqs);
		
		sqlSession.insert(SQL_NS + "deleteOpenSourceFiles", paramMap);
		
	}

	/**
	 * 디자인(작품) 카테고리 삭제
	 * 
	 * @param product
	 * @return
	 */
	public int deleteProductCategoryForProduct(DesignWorkVO product) {
		return sqlSession.delete(SQL_NS + "deleteProductCategoryForProduct", product);
	}

	/**
	 * 디자인(작품) 작품파일 삭제
	 * 
	 * @param product
	 * @return
	 */
	public int deleteProductFilesForProduct(DesignWorkVO product) {
		return sqlSession.delete(SQL_NS + "deleteProductFilesForProduct", product);
		
	}

	/**
	 * 디자인(작품) 좋아요 삭제
	 * 
	 * @param product
	 * @return
	 */
	public int deleteProductLikeForProduct(DesignWorkVO product) {
		return sqlSession.delete(SQL_NS + "deleteProductLikeForProduct", product);
		
	}

	/**
	 * 디자인(작품) 오픈 소스 삭제
	 * 
	 * @param product
	 * @return
	 */
	public int deleteOpenSourceFilesForProduct(DesignWorkVO product) {
		return sqlSession.delete(SQL_NS + "deleteOpenSourceFilesForProduct", product);
		
	}
	
	/**
	 * 디자인(작품) 댓글 삭제
	 * 
	 * @param product
	 * @return
	 */
	public int deleteProductCommentForProduct(DesignWorkVO product) {
		return sqlSession.delete(SQL_NS + "deleteProductCommentForProduct", product);
		
	}
	
	/**
	 * 디자인(작품) 삭제
	 * 
	 * @param product
	 * @return
	 */
	public int deleteProduct(DesignWorkVO product){
		return sqlSession.update(SQL_NS + "deleteProduct", product);
	}

	/**
	 * 이달의 Best 작품 목록 조회
	 * @param paramMap
	 * @return
	 */
	public List<DesignWorkVO> selectBestProductList(Map<String, Object> paramMap) {
		return sqlSession.selectList(SQL_NS + "selectBestProductList", paramMap);
	}
	
	/**
	 * 카테고리 이름 조회
	 * @param category
	 * @return
	 */
	public String selectCategoryName(String category) {
		return sqlSession.selectOne(SQL_NS + "selectCategoryName", category);
	}

}
