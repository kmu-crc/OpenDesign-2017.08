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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.CommonService;
import com.opendesign.service.UserService;
import com.opendesign.utils.CmnConst.CateExclude;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.ItemCmmtVO;
import com.opendesign.vo.ItemLikeVO;
import com.opendesign.vo.ItemViewCntVO;
import com.opendesign.vo.MessageVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;
import com.wdfall.spring.JsonModelAndView;

/**
 *
 * <pre>
 * 공통으로 쓰는 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 26.
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {

	/**
	 * 로그(log4j) 인스턴스
	 */
	@SuppressWarnings("unused")
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 회원 서비스 인스턴스
	 */
	@Autowired
	UserService userService;

	/**
	 * 공통 서비스 인스턴스
	 */
	@Autowired
	CommonService service;


	/**
	 * 카테고리 대분류 조회
	 * 
	 * @param request:
	 *            excludeCodes
	 * @return
	 */
	@RequestMapping(value = "/selectCategoryListDepth1.do")
	public ModelAndView selectCategoryListDepth1(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// exclude할 code
		String excludeCodes = request.getParameter(CateExclude.P_NAME);
		List<CategoryVO> categoryListDepth1 = service.selectCategoryListDepth1(excludeCodes);
		resultMap.put(RstConst.P_NAME, categoryListDepth1);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 카테고리 중/소 분류 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectCategoryListDepthSub.do")
	public ModelAndView selectCategoryListDepthSub(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//
		String parentCateCode = request.getParameter("parentCateCode");
		List<CategoryVO> categoryList = service.selectCategoryListDepthSub(parentCateCode);
		resultMap.put(RstConst.P_NAME, categoryList);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 파일 다운로드
	 * 
	 * @param fileUrl
	 * @param dispName
	 *            보여줄 파일명
	 * @return
	 */
	@RequestMapping(value = "/fileDownload.do")
	public ModelAndView fileDownload(HttpServletRequest request) {
		return new ModelAndView("/common/fileDownload");
	}

	// ========================= 좋아요 =========================================

	/**
	 * "좋아요" 하기
	 * 
	 * @param itemVO:
	 *            itemType, itemSeq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/likeItemWork.ajax")
	public ModelAndView likeItemWork(@ModelAttribute ItemLikeVO itemVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.likeItemWork(itemVO, request);

		return new JsonModelAndView(resultMap);
	}

	// ========================= ]]좋아요 =========================================

	// ========================= 댓글 =========================================

	/**
	 * 댓글 등록
	 * 
	 * @param cmmtVO:
	 *            contents, itemSeq, itemCmmtType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertItemCmmt.ajax")
	public ModelAndView insertProjectWorkCmmt(@ModelAttribute ItemCmmtVO cmmtVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.insertItemCmmt(cmmtVO, request);

		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * @param cmmtVO:
	 *            contents, itemSeq, itemCmmtType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteItemCmmt.ajax")
	public ModelAndView deleteItemCmmt(@ModelAttribute ItemCmmtVO cmmtVO, HttpServletRequest request) {

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.deleteItemCmmt(cmmtVO, loginUserVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 댓글 조회
	 * 
	 * @param searchVO:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectItemCmmtPagingList.ajax")
	public ModelAndView selectItemCmmtPagingList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectItemCmmtPagingList(searchVO, request);

		return new JsonModelAndView(resultMap);
	}

	// ========================= ]]댓글 =========================================

	// ========================= 조회수 =========================================
	/**
	 * 디자인(작품) 조회수 증가
	 * 
	 * @param viewCntVO:
	 *            itemSeq, itemViewCntType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateItemViewCnt.ajax")
	public ModelAndView updateItemViewCnt(@ModelAttribute ItemViewCntVO viewCntVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.updateItemViewCnt(viewCntVO, request);

		return new JsonModelAndView(resultMap);
	}

	// ========================= ]]조회수 =========================================

	// ========================= 알림 =========================================
	/**
	 * 최신알림 개수 조회
	 * 
	 * @param searchVO:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectLatestAlarmCount.ajax")
	public ModelAndView selectLatestAlarmCount(HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectLatestAlarmCount(request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 최신알림 목록 조회
	 * 
	 * @param searchVO:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectLatestAlarmList.ajax")
	public ModelAndView selectLatestAlarmList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.selectLatestAlarmList(searchVO, loginUserVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 최신알림 이미 읽었음으로 수정
	 * 
	 * @param searchVO:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateLatestAlarmRead.ajax")
	public ModelAndView updateLatestAlarmRead(HttpServletRequest request) {

		Map<String, Object> resultMap = service.updateLatestAlarmRead(request);

		return new JsonModelAndView(resultMap);
	}

	// ========================= ]]알림 =========================================

	// ========================= 메시지 =========================================
	/**
	 * 최신메시지 개수 조회
	 * 
	 * @param msgVO:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectLatestMessageCount.ajax")
	public ModelAndView selectLatestMessageCount(@ModelAttribute MessageVO msgVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectLatestMessageCount(msgVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 최신 메시지 대상 목록 조회
	 * </pre>
	 * 
	 * @param msgVO:
	 *            schMode, schWord, schNewUserSeq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectLatestMessageRoomList.ajax")
	public ModelAndView selectLatestMessageRoomList(@ModelAttribute MessageVO msgVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectLatestMessageRoomList(msgVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 최신 메시지 내용 목록 조회
	 * 
	 * @param msgVO:
	 *            schSelectedUserSeq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectLatestMessageContentList.ajax")
	public ModelAndView selectLatestMessageContentList(@ModelAttribute MessageVO msgVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectLatestMessageContentList(msgVO, request);

		return new JsonModelAndView(resultMap);
	}


	/**
	 * 메시지 등록
	 * 
	 * @param msgVO:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendUserMessage.ajax")
	public ModelAndView sendUserMessage(@ModelAttribute MessageVO msgVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.sendUserMessage(msgVO, request);

		return new JsonModelAndView(resultMap);
	}

	// ========================= ]]메시지 =========================================

}
