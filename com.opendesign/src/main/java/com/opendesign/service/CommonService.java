/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opendesign.dao.CommonDAO;
import com.opendesign.dao.UserDAO;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.vo.AlarmVO;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.ItemCmmtVO;
import com.opendesign.vo.ItemLikeVO;
import com.opendesign.vo.ItemViewCntVO;
import com.opendesign.vo.ItemWorkVO;
import com.opendesign.vo.MessageVO;
import com.opendesign.vo.ProjectLikeVO;
import com.opendesign.vo.MessageVO.MessageMode;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;
import com.opendesign.websocket.SocketHandler;

/**
 * 
 * <pre>
 * 공통 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class CommonService {

	/**
	 * 로그(log4j) 인스턴스
	 */
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 공통  DAO 인스턴스
	 */
	@Autowired
	CommonDAO dao;

	/**
	 * 회원 DAO 인스턴스
	 */
	@Autowired
	UserDAO userDao;

	/**
	 * 웹소켓 서비스 인스턴스
	 */
	@Autowired
	SocketHandler websocketHandler;

	/**
	 * 카테고리 대분류 조회
	 * 
	 * @return
	 */
	public List<CategoryVO> selectCategoryListDepth1() {
		return this.selectCategoryListDepth1(null);
	}
	
	/**
	 * 카테고리 대분류 조회
	 * 
	 * @param excludeCodes 포함하지 않을 카테고리
	 * @return
	 */
	public List<CategoryVO> selectCategoryListDepth1(String excludeCodes) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("depth", CategoryVO.DEPTH_1);
		pMap.put("excludeCodes", excludeCodes);
		return dao.selectCategoryList(pMap);
	}
	
	/**
	 * 카테고리 중분류 조회
	 * 
	 * @return
	 */
	public List<CategoryVO> selectCategoryListDepth2() {
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("depth", CategoryVO.DEPTH_2);
		return dao.selectCategoryList(pMap);
	}
	
	/**
	 * 카테고리 소분류 조회
	 * 
	 * @return
	 */
	public List<CategoryVO> selectCategoryListDepth3() {
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("depth", CategoryVO.DEPTH_3);
		return dao.selectCategoryList(pMap);
	}

	/**
	 * 카테고리 중/소분류 조회
	 * 
	 * @param parentCateCode 부모 카테고리
	 * @return
	 */
	public List<CategoryVO> selectCategoryListDepthSub(String parentCateCode) {
		if (StringUtils.isEmpty(parentCateCode)) {
			return new ArrayList<CategoryVO>();
		}
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("parentCateCode", parentCateCode);
		if (parentCateCode.length() == 3) { // 중분류
			pMap.put("depth", CategoryVO.DEPTH_2);
		} else if (parentCateCode.length() == 6) { // 소분류
			pMap.put("depth", CategoryVO.DEPTH_3);
		}
		return dao.selectCategoryList(pMap);
	}

	// ========================= 좋아요 =========================================

	// --------------------------------- 작품 ---------------------------------------------------- //
	/**
	 * 작품 좋아요/취소 처리
	 * 
	 * @param itemVO:
	 *            itemType, itemSeq
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> likeItemWork(ItemLikeVO itemVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO userVO = CmnUtil.getLoginUser(request);

		boolean isliked = this.selectItemWorkLiked(userVO.getSeq(), itemVO.getItemSeq(), itemVO.getItemType());
		if (!isliked) {
			itemVO.setMemberSeq(userVO.getSeq());
			CmnUtil.setCmnDate(itemVO);
			dao.likeItemWork(itemVO);

			/*
			 * 웹소켓 처리
			 */
			// ===== 알림 추가 ====
			AlarmVO alarmVO = insertAlarmForLike(itemVO, userVO);
			websocketHandler.notifyAlarmChanged(alarmVO.getMemberSeq());
			// ===== ]]알림 추가 ====

		} else {
			// 이미 좋아요 했음 (취소 처리)
			dao.unlikeItemWork(userVO.getSeq(), itemVO.getItemSeq(), itemVO.getItemType());
			LOGGER.info("already done like.");
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 좋아요에 관련된 알림정보 등록
	 * 
	 * @param itemVO
	 * @param userVO
	 * @return
	 */
	private AlarmVO insertAlarmForLike(ItemLikeVO itemVO, UserVO userVO) {
		ItemWorkVO itemWorkVO = dao.selectItemWorkBySeq(itemVO.getItemSeq(), itemVO.getItemType());
		String alarmContents = String.format("나의 게시물 \"%s\"에 좋아요를 남겼습니다.", itemWorkVO.getItemTitle());
		// 
		//
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setMemberSeq(itemWorkVO.getMemberSeq());
		alarmVO.setContents(alarmContents);
		alarmVO.setActionUri("");
		alarmVO.setActorSeq(userVO.getSeq());
		CmnUtil.setCmnDate(alarmVO);
		dao.insertAlarm(alarmVO);
		return alarmVO;
	}

	/**
	 * 작품의 좋아요 여부 조회
	 * 
	 * @param memberSeq
	 * @param itemSeq
	 * @param itemType
	 * @return
	 */
	public boolean selectItemWorkLiked(String memberSeq, String itemSeq, String itemType) {

		int cnt = dao.selectItemWorkLiked(memberSeq, itemSeq, itemType);

		return (cnt > 0) ? true : false;
	}
	
	
	// --------------------------------- 프로젝트 ---------------------------------------------------- //
	/**
	 * 프로젝트 좋아요/취소 처리
	 * 
	 * @param SubjectVO:
	 *            subjectSeq
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> likeProejct(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ProjectLikeVO projectVO = new ProjectLikeVO();

		UserVO userVO = CmnUtil.getLoginUser(request);
		String projectSeq = request.getParameter("projectSeq");
		boolean isliked = this.selectProjectLiked(userVO.getSeq(), projectSeq);
		if (!isliked) {
			projectVO.setMemberSeq(userVO.getSeq());
			projectVO.setProjectSeq(projectSeq);
			CmnUtil.setCmnDate(projectVO);
			dao.likeProject(projectVO);
		} else {
			// 이미 좋아요 했음 (취소 처리)
			dao.unlikeProject(userVO.getSeq(), projectSeq);
			LOGGER.info("already done like.");
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}
	
	/**
	 * 프로젝트 좋아요 여부 조회
	 * 
	 * @param memberSeq
	 * @param subjectSeq
	 * @return
	 */
	public boolean selectProjectLiked(String memberSeq, String itemSeq) {

		int cnt = dao.selectProjectLiked(memberSeq, itemSeq);

		return (cnt > 0) ? true : false;
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
	@Transactional
	public Map<String, Object> insertItemCmmt(ItemCmmtVO cmmtVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 줄바꿈 처리
		CmnUtil.handleHtmlEnterRN2BR(cmmtVO, "contents");

		UserVO userVO = CmnUtil.getLoginUser(request);
		cmmtVO.setMemberSeq(userVO.getSeq());
		CmnUtil.setCmnDate(cmmtVO);

		dao.insertItemCmmt(cmmtVO);

		// ===== 알림 추가 ====
		AlarmVO alarmVO = insertAlarmForCmmt(cmmtVO, userVO);
		websocketHandler.notifyAlarmChanged(alarmVO.getMemberSeq());
		// ===== ]]알림 추가 ====

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 댓글에 관련된 알림정보 등록 
	 * 
	 * @param cmmtVO
	 * @param userVO
	 * @return
	 */
	private AlarmVO insertAlarmForCmmt(ItemCmmtVO cmmtVO, UserVO userVO) {
		ItemCmmtVO itemWorkVO = dao.selectItemCmmtBySeq(cmmtVO.getItemSeq(), cmmtVO.getItemCmmtType());
		String contentFormat = "나의 게시물 \"%s\"에 댓글을 남겼습니다.";
		String alarmContents = String.format(contentFormat, itemWorkVO.getItemTitle());
		// 
		//
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setMemberSeq(itemWorkVO.getMemberSeq());
		alarmVO.setContents(alarmContents);
		alarmVO.setActionUri("");
		alarmVO.setActorSeq(userVO.getSeq());
		CmnUtil.setCmnDate(alarmVO);
		dao.insertAlarm(alarmVO);
		return alarmVO;
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * @param cmmtVO
	 * @param loginUserVO
	 * @return
	 */
	public Map<String, Object> deleteItemCmmt(ItemCmmtVO cmmtVO, UserVO loginUserVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NEED_LOGIN);
			return resultMap;
		}
		
		dao.deleteItemCmmt(cmmtVO);
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS); 
		return resultMap;
	}

	/**
	 * 댓글 조회
	 * 
	 * @param itemSeq
	 * @param itemCmmtType
	 * @return
	 */
	@Deprecated
	public List<ItemCmmtVO> selectItemCmmtList(String itemSeq, String itemCmmtType) {
		return dao.selectItemCmmtList(itemSeq, itemCmmtType);
	}

	/**
	 * 댓글 조회
	 * 
	 * @param searchVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectItemCmmtPagingList(SearchVO searchVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO loginUserVO = CmnUtil.getLoginUser(request);

		int allCount = dao.selectItemCmmtAllCount(searchVO);
		List<ItemCmmtVO> list = dao.selectItemCmmtPagingList(searchVO);
		
		// 삭제할수 있는지 체크:
		if(!CmnUtil.isEmpty(list) && loginUserVO != null && !StringUtils.isEmpty(loginUserVO.getSeq())) {
			for(ItemCmmtVO item : list) {
				if(loginUserVO.getSeq().equals(item.getMemberSeq())) {
					item.setCurUserAuthYN(true); 
				}
			}
		}

		resultMap.put("all_count", allCount);
		resultMap.put("list", list);
		return resultMap;
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
	@Transactional
	public Map<String, Object> updateItemViewCnt(ItemViewCntVO viewCntVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO userVO = CmnUtil.getLoginUser(request);

		dao.updateItemViewCnt(viewCntVO);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	// ========================= ]]조회수 =========================================

	// ========================= 알림 =========================================
	/**
	 * 최신알림 개수 조회
	 * 
	 * @param alarmVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectLatestAlarmCount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO userVO = CmnUtil.getLoginUser(request);
		if (userVO == null || StringUtils.isEmpty(userVO.getSeq())) {
			return resultMap;
		}

		int count = dao.selectLatestAlarmCount(userVO.getSeq());

		resultMap.put(RstConst.P_NAME, count);
		return resultMap;
	}

	/**
	 * 최신알림 목록 조회
	 * @param searchVO 
	 * @param loginUserVO 
	 * 
	 * @param alarmVO
	 * @return
	 */
	public Map<String, Object> selectLatestAlarmList(SearchVO searchVO, UserVO loginUserVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			return resultMap;
		}

		searchVO.setSchSeq(loginUserVO.getSeq());
		
		int allCount = dao.selectLatestAlarmAllCount(searchVO);
		List<AlarmVO> resultList = dao.selectLatestAlarmList(searchVO);

		resultMap.put("all_count", allCount);
		resultMap.put("list", resultList);
		return resultMap;
	}

	/**
	 * 최신알림 이미 읽었음으로 수정
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateLatestAlarmRead(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO userVO = CmnUtil.getLoginUser(request);
		if (userVO == null || StringUtils.isEmpty(userVO.getSeq())) {
			return resultMap;
		}

		// 현제시간전에 보내온것을 모두 읽었음으로 표기
		dao.updateLatestAlarmRead(userVO.getSeq());

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	// =========================]] 알림 =========================================

	// ========================= 메시지 =========================================

	/**
	 * 최신메시지 개수 조회
	 * 
	 * @param msgVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectLatestMessageCount(MessageVO msgVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO userVO = CmnUtil.getLoginUser(request);
		if (userVO == null || StringUtils.isEmpty(userVO.getSeq())) {
			return resultMap;
		}

		msgVO.setSchLoginUserSeq(userVO.getSeq());

		int count = dao.selectLatestMessageCount(msgVO);

		resultMap.put(RstConst.P_NAME, count);
		return resultMap;
	}

	/**
	 * <pre>
	 * 최신 메시지 대상 목록 조회 
	 * </pre>
	 * 
	 * @param msgVO:
	 *            schMode, schWord, schNewUserSeq
	 * @param request
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectLatestMessageRoomList(MessageVO msgVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			return resultMap;
		}

		msgVO.setSchLoginUserSeq(loginUserVO.getSeq());

		// 
		List<MessageVO> msgRoomList = new ArrayList<MessageVO>();
		if (MessageMode.SEARCH.equals(msgVO.getSchMode())) {
			// 검색조건따라 조회: schWord
			msgRoomList = dao.selectLatestMessageRoomListBySchWord(msgVO);
		} else {
			// 새 회원 조회: schNewUserSeq
			msgRoomList = dao.selectLatestMessageRoomListByNewUserSeq(msgVO);
			// 회원 기록이 없을때 하나 만들어준다.
			if (CmnUtil.isEmpty(msgRoomList)) {
				UserVO newUserVO = userDao.selectUserBySeq(msgVO.getSchNewUserSeq());
				MessageVO newMsgVO = new MessageVO();
				newMsgVO.setSendSeq(newUserVO.getSeq());
				newMsgVO.setSendName(newUserVO.getEmail());
				newMsgVO.setSendImageUrl(newUserVO.getImageUrl());
				newMsgVO.setRecieveSeq(loginUserVO.getSeq());
				newMsgVO.setRecieveName(loginUserVO.getUname());
				newMsgVO.setRecieveImageUrl(loginUserVO.getImageUrl());
				newMsgVO.setSchLoginUserSeq(loginUserVO.getSeq());
				CmnUtil.setCmnDate(newMsgVO);
				msgRoomList.add(newMsgVO);
			}
		}

		resultMap.put(RstConst.P_NAME, msgRoomList);
		return resultMap;
	}

	/**
	 * 최신 메시지 내용 목록 조회
	 * 
	 * @param msgVO:
	 *            schSelectedUserSeq
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectLatestMessageContentList(MessageVO msgVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			return resultMap;
		}

		msgVO.setSchLoginUserSeq(loginUserVO.getSeq());

		// 읽었음으로 set한다.
		updateLatestMessageRead(msgVO, request);

		List<MessageVO> msgList = dao.selectLatestMessageContentList(msgVO);

		resultMap.put(RstConst.P_NAME, msgList);
		return resultMap;
	}

	/**
	 * 메시지 내용 이미 읽었음으로 수정
	 * 
	 * @param msgVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateLatestMessageRead(MessageVO msgVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			return resultMap;
		}

		msgVO.setSchLoginUserSeq(loginUserVO.getSeq());
		CmnUtil.setCmnDate(msgVO);
		dao.updateLatestMessageRead(msgVO);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 메시지 등록
	 * 
	 * @param msgVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> sendUserMessage(MessageVO msgVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			return resultMap;
		}

		msgVO.setSchLoginUserSeq(loginUserVO.getSeq());

		msgVO.setSendSeq(loginUserVO.getSeq());
		CmnUtil.setCmnDate(msgVO);
		dao.insertMessage(msgVO);

		// ===== 메시지 변경 통지 ====
		websocketHandler.notifyMsgChanged(msgVO.getRecieveSeq());
		websocketHandler.notifyMsgChanged(msgVO.getSendSeq());
		// ===== ]]메시지 변경 통지 ====

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	// =========================]] 메시지 =========================================
}
