/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.CommonService;
import com.opendesign.service.DesignerService;
import com.opendesign.service.ProductService;
import com.opendesign.utils.CmnConst.FileUploadDomain;
import com.opendesign.utils.CmnConst.MemberDiv;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.Day;
import com.opendesign.utils.StringUtil;
import com.opendesign.utils.ThumbnailManager;
import com.opendesign.vo.DesignPreviewImageVO;
import com.opendesign.vo.DesignWorkFileVO;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.DesignerVO;
import com.opendesign.vo.MainItemVO;
import com.opendesign.vo.UserVO;
import com.wdfall.spring.JsonModelAndView;

/**
 * <pre>
 * 디자인(작품)의 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

	/**
	 * 파일 사이즈 제한:100MB
	 */
	private static final long LIMIT_FILE_SIZE = 100000000L; 
	/**
	 * 등록 플래그 
	 */
	private static final int SAVE_TYPE_INSERT = 10000; 
	/**
	 * 수정 플래그
	 */
	private static final int SAVE_TYPE_UPDATE = 20000; 

	/**
	 * 디자인(작품) 서비스 인스턴스
	 */
	@Autowired
	ProductService service;

	/**
	 * 디자이너/제작자 서비스 인스턴스
	 */
	@Autowired
	DesignerService designerService;

	/**
	 * 공통 서비스 인스턴스
	 */
	@Autowired
	CommonService commonService;

	/* ================ 작품list(완료된 프로젝트와 분리) ========================  */
	/**
	 * 2017.10.27
	 * seoyeon.yeom
	 * 
	 * <pre>
	 * 디자인(작품) 페이지 조회(이동) 
	 * ID#OD02-01-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product.do")
	public ModelAndView product(HttpServletRequest request) {

		return new ModelAndView("product/product");
	}

	/**
	 * 디자인(작품) 목록 데이터 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productList.ajax")
	public @ResponseBody Map<String, Object> ajaxProductList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		/*
		 * 페이징 퍼래미터 처리
		 */
		int pageIndex = 1;
		try {
			pageIndex = Integer.parseInt(request.getParameter("schPage"));
		} catch (Exception e) {
		}

		if (pageIndex <= 0) {
			pageIndex = 1;
		}

		String schLimitCount = request.getParameter("schLimitCount");
		int limitCount = 50;
		if (StringUtil.isNotEmpty(schLimitCount)) {
			try {
				limitCount = Integer.parseInt(schLimitCount);
			} catch (Exception e) {
			}
		}

		/*
		 * 조회
		 */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page_count", (pageIndex - 1) * limitCount);
		paramMap.put("limit_count", limitCount);
		paramMap.put("schCate", request.getParameter("schCate"));
		paramMap.put("schSort", request.getParameter("schSort"));

		if( CmnUtil.isUserLogin(request) ) {
			paramMap.put("memberSeq", CmnUtil.getLoginUser(request).getSeq() );
		}
		
		int allCount = service.selectProductCount(paramMap);

		List<DesignWorkVO> list = service.selectProductList(paramMap);
		
		MainItemVO item = new MainItemVO();
		
		if(!StringUtils.isEmpty(request.getParameter("schCate"))) {
			item = settingCategoryNm(request.getParameter("schCate"));
		}
		
		
		resultMap.put("all_count", allCount);
		resultMap.put("list", list);
		resultMap.put("item", item);

		return resultMap;
	}
	
	/* ================ 작품list(완료된 프로젝트와 분리)========================  */
	
	
	/**
	 * 디자인(작품) 등록
	 * 
	 * @param product
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerProduct.ajax")
	public ModelAndView registerProject(@ModelAttribute("product") DesignWorkVO product,
			MultipartHttpServletRequest request) throws Exception {

		return saveProduct(product, request, SAVE_TYPE_INSERT);

	}

	/**
	 * 디자인(작품) 수정
	 * 
	 * @param product
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProduct.ajax")
	public ModelAndView updateProduct(@ModelAttribute("product") DesignWorkVO product,
			MultipartHttpServletRequest request) throws Exception {

		return saveProduct(product, request, SAVE_TYPE_UPDATE);

	}

	/**
	 * 디자인(작품) 등록/수정
	 * 
	 * @param product
	 * @param request
	 * @param saveType
	 * @return
	 * @throws Exception
	 */
	private JsonModelAndView saveProduct(DesignWorkVO product, MultipartHttpServletRequest request, int saveType)
			throws Exception {

		/*
		 * 등록인지 수정인지 판단하는 플래그 
		 */
		boolean isUpdate = saveType == SAVE_TYPE_UPDATE;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);
		}

		/* 수정 시 회원 권한 체크 */
		if (isUpdate) {
			DesignWorkVO prevProduct = designerService.getDesignWork(product.getSeq());
			if (!loginUser.getSeq().equals(prevProduct.getMemberSeq())) {
				resultMap.put("result", "101");
				return new JsonModelAndView(resultMap);
			}
		}

		/*
		 * 이미지 체크
		 */
		MultipartFile fileUrlFile = request.getFile("fileUrlFile");
		if (fileUrlFile != null) {
			String fileName = fileUrlFile.getOriginalFilename().toLowerCase();
			if (!(fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
				resultMap.put("result", "202");
				return new JsonModelAndView(resultMap);
			}

		} else {
			/* 등록 시만 체크 한다. */
			if (!isUpdate) {
				resultMap.put("result", "201");
				return new JsonModelAndView(resultMap);
			}
		}

		/*
		 * license
		 */
		String license01 = request.getParameter("license01");
		String license02 = request.getParameter("license02");
		String license03 = request.getParameter("license03");
		String license = license01 + license02 + license03;
		product.setLicense(license);

		
		/* 
		 * 포인트 입력값이 없으면 "0"으로 처리 
		 */
		String point = request.getParameter("point");
		point = String.valueOf(CmnUtil.getIntValue(point)); //null--> 0 처리
		product.setPoint(point);

		/*
		 * 파일 체크
		 */
		List<MultipartFile> productFileList = new ArrayList<MultipartFile>();
		List<MultipartFile> openSourceFileList = new ArrayList<MultipartFile>();

		Iterator<String> iterator = request.getFileNames();
		while (iterator.hasNext()) {
			String fileNameKey = iterator.next();

			MultipartFile reqFile = request.getFile(fileNameKey);
			if (reqFile != null) {
				boolean existProuductFile = fileNameKey.startsWith("productFile");
				boolean existOpenSourceFile = fileNameKey.startsWith("openSourceFile");
				if (existProuductFile || existOpenSourceFile) {
					long fileSize = reqFile.getSize();
					if (fileSize > LIMIT_FILE_SIZE) {
						System.out.print("no");
						resultMap.put("result", "203");
						resultMap.put("fileName", reqFile.getOriginalFilename());
						return new JsonModelAndView(resultMap);

					}

					if (existProuductFile) {
						productFileList.add(reqFile);
					}

					if (existOpenSourceFile) {
						openSourceFileList.add(reqFile);
					}
				}
			}
		}
		product.setMemberSeq(loginUser.getSeq());

		/*
		 * 썸네일 처리
		 */
		String fileUploadDir = CmnUtil.getFileUploadDir(request, FileUploadDomain.PRODUCT);
		File thumbFile = null;
		if (fileUrlFile != null) {
			String saveFileName = UUID.randomUUID().toString();
			thumbFile = CmnUtil.saveFile(fileUrlFile, fileUploadDir, saveFileName);
			
			String fileUploadDbPath = CmnUtil.getFileUploadDbPath(request, thumbFile);
			product.setThumbUri(fileUploadDbPath);
		}

		/*
		 * 디자인(작품) 처리
		 */
		List<DesignPreviewImageVO> productList = new ArrayList<DesignPreviewImageVO>();
		List<String> productFilePaths = new ArrayList<>();
		
		for (MultipartFile aFile : productFileList) {
			String saveFileName = UUID.randomUUID().toString();
			File file = CmnUtil.saveFile(aFile, fileUploadDir, saveFileName);
			
			productFilePaths.add(file.getAbsolutePath());
			
			String fileUploadDbPath = CmnUtil.getFileUploadDbPath(request, file);

			DesignPreviewImageVO productFile = new DesignPreviewImageVO();
			productFile.setFilename(aFile.getOriginalFilename());
			productFile.setFileUri(fileUploadDbPath);

			productList.add(productFile);

		}
		product.setImageList(productList);

		/*
		 * 오픈 소스 처리
		 */
		List<DesignWorkFileVO> openSourceList = new ArrayList<DesignWorkFileVO>();
		for (MultipartFile aFile : openSourceFileList) {
			String saveFileName = UUID.randomUUID().toString();
			File file = CmnUtil.saveFile(aFile, fileUploadDir, saveFileName);
			String fileUploadDbPath = CmnUtil.getFileUploadDbPath(request, file);
			
			//openSourceFile은 파일이름을 client에서 입력해서 가져옴.
			String filenameOpenSourceFile = StringUtils
					.stripToEmpty(request.getParameter("filename_" + aFile.getName()));

			DesignWorkFileVO openSourceFile = new DesignWorkFileVO();
			openSourceFile.setFilename(filenameOpenSourceFile);
			openSourceFile.setFileUri(fileUploadDbPath);

			openSourceList.add(openSourceFile);

		}
		product.setFileList(openSourceList);

		/*
		 * 디자이너/제작자 가 올린 작업에 대한 이미지 리사이즈 처리
		 * 썸네일 파일 및 작업 이미지들 리사이즈 된 복사복처리
		 */
		String thumbFilePath = "";
		if( thumbFile != null ) {
			thumbFilePath = thumbFile.getAbsolutePath();
		}
		ThumbnailManager.resizeNClone4DesignWork(thumbFilePath, productFilePaths);
		
		/*
		 * 태그 처리
		 */
		String tag = request.getParameter("tag");
		if (StringUtil.isNotEmpty(tag)) {
			String[] tags = tag.split(",");

			int addIndex = 0;
			StringBuffer tagBuffer = new StringBuffer();
			for (String aTag : tags) {
				if (StringUtil.isNotEmpty(aTag)) {
					aTag = aTag.trim();
					tagBuffer.append(aTag);
					tagBuffer.append("|");
					addIndex++;
				}

				if (addIndex >= 5) {
					break;
				}
			}

			if (addIndex > 0) {
				tagBuffer.insert(0, "|");

				tag = tagBuffer.toString();
			}
		}
		product.setTags(tag);

		String currentDate = Day.getCurrentTimestamp().substring(0, 12);
		product.setRegisterTime(currentDate);
		product.setUpdateTime(currentDate);

		String[] categoryCodes = request.getParameterValues("categoryCodes");
		
		/*
		 * 서비스 실행
		 */
		if (isUpdate) {
			String[] removeProductSeqs = request.getParameterValues("removeProductSeq");
			String[] removeOpenSourceSeqs = request.getParameterValues("removeOpenSourceSeq");

			int projectSeq = service.updateProduct(product, categoryCodes, removeProductSeqs, removeOpenSourceSeqs);

		} else {
			int projectSeq = service.insertProduct(product, categoryCodes);

		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return new JsonModelAndView(resultMap);

	}

	/**
	 * 디자인(작품) 삭제
	 * 
	 * @param product
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteProduct.ajax")
	public ModelAndView deleteProduct(@ModelAttribute("product") DesignWorkVO product, HttpServletRequest request)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);
		}

		DesignWorkVO prevProduct = designerService.getDesignWork(product.getSeq());
		if (!loginUser.getSeq().equals(prevProduct.getMemberSeq())) {
			resultMap.put("result", "101");
			return new JsonModelAndView(resultMap);
		}

		int projectSeq = service.deleteProduct(product);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return new JsonModelAndView(resultMap);

	}


	/**
	 * <pre>
	 * 디자인(작품) 상세 페이지 조회(이동) 
	 * ID#OD04-02-01
	 * </pre>
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productModify.do")
	public ModelAndView productModify(@ModelAttribute DesignWorkVO dsWorkVO, HttpServletRequest request) {

		Map<String, Object> resultMap = designerService.productView(dsWorkVO, request);
		resultMap.put("cateList", commonService.selectCategoryListDepth1());

		return new ModelAndView("/product/product_modify", resultMap);
	}

	/**
	 * <pre>
	 * 디자인(작품) 등록 페이지 조회(이동)
	 * ID#OD02-01-02
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productRegi.do")
	public ModelAndView productRegi(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("product/product_regi");

		view.addObject("cateList", commonService.selectCategoryListDepth1());
		return view;
	}

	// ===================== 작품상세 ===========================
	
	/**
	 * <pre>
	 * 디자이너 포트폴리오 페이지 조회(이동) 
	 * ID#OD04-01-02
	 * </pre>
	 * 
	 * @param desiVO:
	 *            seq
	 * @return
	 */
	@RequestMapping(value = "/portfolio.do")
	public ModelAndView portfolio(@ModelAttribute DesignerVO desiVO, HttpServletRequest reqeust) {

		Map<String, Object> resultMap = designerService.selectDesingerDetail(desiVO, reqeust);
		resultMap.put("schMemberDiv", MemberDiv.PRODUCT); // 회원구분
		return new ModelAndView("/designer/portfolio", resultMap);
	}

	/**
	 * <pre>
	 * 디자인(작품) 상세 페이지 조회(이동)
	 * ID#OD04-02-01
	 * </pre>
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productView.do")
	public ModelAndView productView(@ModelAttribute DesignWorkVO dsWorkVO, HttpServletRequest request) {

		Map<String, Object> resultMap = designerService.productView(dsWorkVO, request);
		resultMap.put("schMemberDiv", MemberDiv.PRODUCT); // 회원구분

		return new ModelAndView("/product/product_view", resultMap);
	}

	/**
	 * <pre>
	 * 디자인(작품) 구매 페이지 조회(이동)
	 * ID#OD04-02-02
	 * </pre>
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productPurchase.do")
	public ModelAndView productPurchaseView(@ModelAttribute DesignWorkVO dsWorkVO, HttpServletRequest request) {
		
		UserVO user = CmnUtil.getLoginUser(request);
		
		Map<String, Object> resultMap = designerService.productPurchaseView(dsWorkVO, request);
		resultMap.put("schMemberDiv", MemberDiv.PRODUCT); // 회원구분

		
		/*
		 * 로그인 한 회원와 작품을 등록한 회원가 같은지, 다르다면 구매를 했는지 체크.
		 */
		String userSeq = "";
		String workSeq = "";
		
		if(user != null) {
			userSeq = user.getSeq();
		}
		if(dsWorkVO != null) {
			workSeq = dsWorkVO.getSeq();
		}
		
		DesignWorkVO workVO = designerService.getDesignWork(workSeq);
		
		String productUserSeq = workVO.getMemberSeq();
		
		boolean hasPurchasedWork = designerService.hasPurchasedWork(workSeq, userSeq);
		
		if(!userSeq.equals(productUserSeq)) {
			if(!hasPurchasedWork) {
				return new ModelAndView("/product/product_purchase", resultMap);
			} else {
				return new ModelAndView("/product/product_view", resultMap);
			}
		} else {
			return new ModelAndView("/product/product_view", resultMap);
		}
		
		
	}

	// ===================== ]]작품상세 ===========================

	
	/**
	 * 카테고리 이름 조회
	 * @param category
	 * @return
	 */
	public MainItemVO settingCategoryNm(String category) {
		
		return service.settingCategoryNm(category);
	}
}
