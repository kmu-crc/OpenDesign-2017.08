/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.DesignerService;
import com.opendesign.service.MainService;
import com.opendesign.service.ProductService;
import com.opendesign.service.ProjectService;
import com.opendesign.service.UserService;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.ControllerUtil;
import com.opendesign.utils.StringUtil;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.DesignerVO;
import com.opendesign.vo.MemberCategoryVO;
import com.opendesign.vo.MyUserVO;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;
import com.wdfall.spring.JsonModelAndView;

/**
 * 
 * <pre>
 * 메인 페이지의 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Controller
public class MainController {

	/**
	 * 로그(log4j) 인스턴스
	 */
	@SuppressWarnings("unused")
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 메인 페이지 전용 서비스 인스턴스
	 */
	@Autowired
	MainService service;

	/**
	 * 디자이너/제작자 서비스 인스턴스
	 */
	@Autowired
	DesignerService designerService;
	
	/**
	 * 디자인(작품) 서비스 인스턴스
	 */
	@Autowired
	ProductService productService;
	
	/**
	 * 유저 서비스 인스턴스
	 */
	@Autowired
	UserService userService;
	
	/**
	 * 프로젝트 서비스 인스턴스
	 */
	@Autowired
	ProjectService projectService;
	
	/**
	 * 메인 페이지 조회(이동)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/main.do")
	public ModelAndView main(HttpServletRequest request) {
		return new ModelAndView("main");
	}

	/**
	 * <pre>
	 * 메인 페이지에서 사용하는 목록 데이터를 가져온다.
	 * 
	 * 데이터 상세에는 '베스트 디자이너/제작자' 와
	 * 베스트 디자인(작품) 리스트를 조회 한다.
	 * </pre>
	 * 
	 * @param schPage
	 * @param schLimitCount
	 *            default 16
	 * @return all_count
	 * @return list
	 */
	@RequestMapping(value = "/selectMainList.ajax")
	public ModelAndView selectMainList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {

		Map<String, Object> paramMap = ControllerUtil.createParamMap(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();//service.selectMainList(searchVO, request);

		/*
		 * 베스트 디자이너 리스트 조회
		 */
		List<DesignerVO> designerList = designerService.selectBestDesignerList(paramMap);
		resultMap.put("designerList", designerList);
		
		/*
		 * 베스트 디자인(작품) 리스트 조회 
		 */
		List<DesignWorkVO> productList = productService.selectBestProductList(paramMap);
		resultMap.put("productList", productList);
		
		
		/*
		 * 로그인 되어 있다면, 디자이너 / 디자인(작품별) 좋아요 여부 세팅
		 */
		if( CmnUtil.isUserLogin(request) ) {
			
			UserVO user = CmnUtil.getLoginUser(request);
			
			for( DesignerVO designer : designerList ) {
				paramMap.put("logonUserSeq", user.getSeq());
				paramMap.put("designerSeq", designer.getSeq());
				designer.setCurUserLikedYN( designerService.isLogonUserLikesDesigner(paramMap) );
			}
			
			for( DesignWorkVO product : productList ) {
				paramMap.put("logonUserSeq", user.getSeq());
				paramMap.put("designWorkSeq", product.getSeq());
				product.setCurUserLikedYN( designerService.isLogonUserLikesDesignWork(paramMap) );
			}
			
		}
		
		
		return new JsonModelAndView(resultMap);
	}
	
	
	/**
	 * 메인 페이지에서 로그인 했을 때, 개인 정보를 가져온다.
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value = "/selectMainMyInfo.ajax")
	public ModelAndView selectMainMyInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = (UserVO) CmnUtil.getLoginUser(request);

		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			return new ModelAndView("redirect:/main.do");
		}
		
		int memberSeq = Integer.parseInt(loginUser.getSeq());
		String memseq = Integer.toString(memberSeq);
		
		// 회원 기본 정보 가져오기
		List<UserVO> myInfoList = userService.selectMyInfoList(memseq);
		//int cntWork = userService.selectWorkCount(memberSeq);
		//int cntLike = userService.selectLikeCount(memberSeq);
		
		// 디자인 정보 가져오기
		List<MyUserVO> designList = userService.selectMyWorkList(memseq);
		if (designList == null) {
			designList = new ArrayList<MyUserVO>();
		}
		
		// 프로젝트 정보 가져오기
		List<ProjectVO> projectList = projectService.selectMyProjectList(memberSeq);
		if (projectList == null) {
			projectList = new ArrayList<ProjectVO>();
		}
		
		resultMap.put("memberSeq", memberSeq);
		resultMap.put("info", myInfoList);
		resultMap.put("myDesignList", designList);
		resultMap.put("myProjectList", projectList);
		
		return new JsonModelAndView(resultMap);
	}


}
