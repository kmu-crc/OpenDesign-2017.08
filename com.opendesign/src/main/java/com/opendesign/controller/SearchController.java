/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.DesignerService;
import com.opendesign.service.ProductService;
import com.opendesign.service.ProjectService;
import com.opendesign.utils.CmnConst.MemberDiv;
import com.opendesign.utils.ControllerUtil;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.SearchVO;
import com.wdfall.spring.JsonModelAndView;

/**
 * <pre>
 * 통합검색 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
	
	/**
	 * 디자인(작품) 서비스 인스턴스
	 */
	@Autowired
	ProductService productService;
	
	/**
	 * 디자인 플로젝트 서비스 인스턴스
	 */
	@Autowired
	ProjectService projectService;
	
	/**
	 * 디자이너/제작자 서비스 인스턴스
	 */
	@Autowired
	DesignerService designerService;

	/**
	 * 통합 검색 페이지 조회(이동)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search.do")
	public ModelAndView product(HttpServletRequest request) {

		return new ModelAndView("common/search");
	}
	
	/**
	 * 디자인(작품) 목록 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productList.ajax")
	public @ResponseBody Map<String, Object> ajaxProductList(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = createParameters(5, request);

		int allCount = productService.selectProductCount(paramMap);
		
		List<DesignWorkVO> list = productService.selectProductList(paramMap);
		resultMap.put("all_count", allCount);
		resultMap.put("list", list);

		return resultMap;
		
	}
	
	
	/**
	 * 디자인 프로젝트 목록 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectList.ajax")
	public @ResponseBody Map<String, Object> ajaxProjectList(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = createParameters(5, request);
		
		
		int totalCount = projectService.selectProjectCount(paramMap);
		List<ProjectVO> list = projectService.selectProjectList(paramMap);
		
		resultMap.put("all_count", totalCount);
		resultMap.put("list", list);

		return resultMap;
	}
	
	
	/**
	 * 디자이너 목록 조회
	 * 
	 * @param schPage
	 * @param schCate
	 * @param schOrderType
	 *            default '최신순'
	 * @param schLimitCount
	 *            default 16
	 * @return all_count
	 * @return list
	 */
	@RequestMapping(value = "/designerList.ajax")
	public ModelAndView designerList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {
		
		Map<String, Object> paramMap = createParameters(4, request);
		
		//디자이너로 세팅
		searchVO.setSchMemberDiv(MemberDiv.DESIGNER);
		searchVO.setSchPage( ( (String)paramMap.get("pageIndex") ) );
		searchVO.setSchLimitCount((Integer)paramMap.get("limit_count"));
		
		Map<String, Object> resultMap = designerService.selectDesignerList(searchVO, request);

		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 제작자 목록 데이터
	 * 
	 * @param schPage
	 * @param schCate
	 * @param schOrderType
	 *            default '최신순'
	 * @param schLimitCount
	 *            default 12
	 * @return all_count
	 * @return list
	 */
	@RequestMapping(value = "/producerList.ajax")
	public ModelAndView producerList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {
		
		Map<String, Object> paramMap = createParameters(4, request);
		
		//제작자로 세팅
		searchVO.setSchMemberDiv(MemberDiv.PRODUCER);
		searchVO.setSchPage( ( (String)paramMap.get("pageIndex") ) );
		searchVO.setSchLimitCount((Integer)paramMap.get("limit_count"));
		Map<String, Object> resultMap = designerService.selectDesignerList(searchVO, request);

		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 검색 파라미터 세팅
	 * 
	 * @param limitCount
	 * @param request
	 * @return
	 */
	private Map<String, Object> createParameters(int limitCount, HttpServletRequest request) {
		
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));

		Map<String, Object> paramMap = ControllerUtil.createParamMap(request);
		paramMap.put("page_count", (pageIndex - 1) * limitCount);
		paramMap.put("limit_count", limitCount);
		
		return paramMap;
	}
	
	
	
}
