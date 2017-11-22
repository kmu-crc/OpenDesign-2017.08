/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.opendesign.dao.UserDAO;
import com.opendesign.utils.CmnConst.FileUploadDomain;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnConst.SessionKey;
import com.opendesign.utils.CmnUtil;
import com.opendesign.vo.MemberCategoryVO;
import com.opendesign.vo.MyUserPointVO;
import com.opendesign.vo.MyUserVO;
import com.opendesign.vo.ProjectGroupVO;
import com.opendesign.vo.SidoVO;
import com.opendesign.vo.UserVO;

/**
 * 
 * <pre>
 * 회원의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class UserService {

	/**
	 * 회원가입시 주는 point
	 */
	public static final String DEFAULT_POINT = "10000";

	/**
	 * 로그(log4j) 인스턴스
	 */
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 회원 DAO 인스턴스
	 */
	@Autowired
	UserDAO dao;

	/**
	 * <pre>
	 * 회원가입 절차 1: 이메일/패스월드 입력 
	 * </pre>
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> register(UserVO userVO, HttpServletRequest request) {
		//LOGGER.debug(userVO);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 이메일 중복체크:
		int emailCnt = checkEmailDup(userVO);
		if (emailCnt > 0) {
			resultMap.put(RstConst.P_NAME, RstConst.V_EMAIL_DUP); // 이메일 중복
			return resultMap;
		}

		// 추가: session저장
		request.getSession().setAttribute(SessionKey.SESSION_REG_USER, userVO);
		//
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 이메일 중복 개수 조회
	 * 
	 * @param userVO
	 * @return
	 */
	public int checkEmailDup(UserVO userVO) {
		return dao.checkEmailDup(userVO);
	}

	/**
	 * 회원가입 절차 2: 회원가입 프로세스
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> register2(UserVO userVO, MultipartHttpServletRequest request) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// === 1. session의 user (이메일, passwd) 꺼내온다.
		UserVO regUser = (UserVO) request.getSession().getAttribute(SessionKey.SESSION_REG_USER);
		boolean isValidSessionUser = (regUser != null && StringUtils.isNotEmpty(regUser.getEmail())
				&& StringUtils.isNotEmpty(regUser.getPasswd()));

		/* 페이스북 accesstoken 값이 있다면 패스 */
		if (!StringUtils.isEmpty(regUser.getFbAccessToken())) {
			isValidSessionUser = true;
		}

		if (!isValidSessionUser) {
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
			return resultMap;
		}
		userVO.setEmail(regUser.getEmail());
		// 비밀번암호화
		userVO.setPasswd(CmnUtil.encryptPassword(regUser.getPasswd()));
		userVO.setFbAccessToken(regUser.getFbAccessToken());
		
		userVO.setSidoSeq(regUser.getSidoSeq());
		
		//거주지역 set
		String sidoVal = (String)request.getParameter("sidoVal");
		int sidoSeq = Integer.parseInt(sidoVal);
		
		userVO.setSidoSeq(sidoSeq);
		
		LOGGER.debug(userVO);

		
		// 1.1 이메일 중복체크:
		int emailCnt = dao.checkEmailDup(userVO);
		if (emailCnt > 0) {
			resultMap.put(RstConst.P_NAME, RstConst.V_EMAIL_DUP); // 이메일 중복
			return resultMap;
		}
		// 1.2 닉네임의 중복 체크:
		int unameCnt = dao.checkUnameDup(userVO);
		if (unameCnt > 0) {
			resultMap.put(RstConst.P_NAME, RstConst.V_UNAME_DUP); // 닉네임의 중복 체크
			return resultMap;
		}

		// === 2. 특수 param 처리
		// 2.1 memberType
		String memberType = handleMemberType(userVO.getMemberTypeCheck());
		userVO.setMemberType(memberType);

		// 2.2 file 처리
		// 
		String fileUploadDbPath = CmnUtil.handleFileUpload(request, "imageUrlFile", FileUploadDomain.USER_PROFILE);
		userVO.setImageUrl(fileUploadDbPath);

		// === 3. db 처리
		CmnUtil.setCmnDate(userVO);
		userVO.setPoint(DEFAULT_POINT); // default point
		dao.insertUser(userVO);

		// 3.1 회원 카테고리 처리
		String[] memberCateCode = userVO.getMemberCateCode();
		if (!CmnUtil.isEmpty(memberCateCode)) {
			for (String code : memberCateCode) {
				MemberCategoryVO mcVO = new MemberCategoryVO();
				mcVO.setCategoryCode(code);
				mcVO.setMemberSeq(userVO.getSeq());
				CmnUtil.setCmnDate(mcVO);
				dao.insertMemberCategory(mcVO);
			}
		}
		
		// 4 성공일때 session에 저장
		request.getSession().setAttribute(SessionKey.SESSION_LOGIN_USER, userVO);

		//
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * memberTypeCheck -> memberType
	 * 
	 * @param memberTypeCheck
	 * @return
	 */
	private String handleMemberType(String[] memberTypeCheck) {
		String memberType = "00"; // 일반
		if (memberTypeCheck != null) {
			if (memberTypeCheck.length == 2) {
				memberType = "11"; // 디자이너/제작자
			} else if (memberTypeCheck.length == 0) {
				memberType = "00"; // 일반
			} else if (memberTypeCheck.length == 1) {
				if ("d".equals(memberTypeCheck[0])) {
					memberType = "10"; // 디자이너
				} else if ("p".equals(memberTypeCheck[0])) {
					memberType = "01"; // 제작자
				}
			}
		}
		return memberType;
	}

	/**
	 * 로그인 프로세스
	 * 
	 * @param request
	 * @param userVO
	 * 
	 * @return
	 */
	public Map<String, Object> loginProcess(UserVO userVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 비밀번호 암호화
		userVO.setPasswd(CmnUtil.encryptPassword(userVO.getPasswd()));
		int cnt = dao.loginCheck(userVO);
		if (cnt == 0) {
			resultMap.put(RstConst.P_NAME, "200"); // 존재하지 않음
			return resultMap;
		}

		// 성공일때 session에 저장
		UserVO loginUser = dao.selectUserByEmail(userVO.getEmail());
		request.getSession().setAttribute(SessionKey.SESSION_LOGIN_USER, loginUser);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 페이스북 로그인 프로세스
	 * 
	 * @param request
	 * @param userVO
	 * 
	 * @return
	 */
	public Map<String, Object> loginFBProcess(UserVO userVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 성공일때 session에 저장
		UserVO loginUser = dao.selectUserByEmail(userVO.getEmail());
		request.getSession().setAttribute(SessionKey.SESSION_LOGIN_USER, loginUser);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 로그아웃 프로세스
	 * 
	 * @param vo
	 * @param request
	 */
	public void logoutProcess(UserVO vo, HttpServletRequest request) {
		request.getSession().setAttribute(SessionKey.SESSION_LOGIN_USER, null);
	}

	/**
	 * 회원 카테고리 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<MemberCategoryVO> selectUCategoryByseq(int memberSeq) {
		return dao.selectUCategoryByseq(memberSeq);
	}

	/**
	 * 회원의 작품 조회
	 * @param memberSeq
	 * @return
	 */
	public List<MyUserVO> selectWorkByseq(int memberSeq) {
		return dao.selectWorkByseq(memberSeq);
	}

	/**
	 * 회원의 작품 개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectWorkCount(int memberSeq) {
		return dao.selectCntWork(memberSeq);
	}

	/**
	 * 회원의 좋아요 개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectLikeCount(int memberSeq) {
		return dao.selectCntLike(memberSeq);
	}

	/**
	 * 회원의 메시지 개수 조회
	 * @param memberSeq
	 * @return
	 */
	public int selectTalkCount(int memberSeq) {
		return dao.selectCntTalk(memberSeq);
	}

	/**
	 * 회원의 포인트 조회
	 * @param memberSeq
	 * @return
	 */
	public int selectMypointCnt(int memberSeq) {
		return dao.selectMypointCnt(memberSeq);
	}

	/**
	 * 회원의 작품 개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectMyProductCnt(int memberSeq) {
		return dao.selectMyProductCnt(memberSeq);
	}

	/**
	 * <pre>
	 * 마이페이지: 나의 프로젝트 목록 조회
	 * 내가 포함된 프로젝트 목록을 조회한다.
	 * </pre>
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<MyUserVO> selectMyProjectList(String memberSeq) {

		List<MyUserVO> projectList = dao.selectMyProjectList(memberSeq);

		return projectList;
	}
	
	/**
	 * <pre>
	 * 마이페이지: 나의 그룹 현황 조회 
	 * 내가 포함된 그룹 목록을 조회한다.
	 * </pre>
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<ProjectGroupVO> selectMyGroupList(String memberSeq) {

		List<ProjectGroupVO> projectList = dao.selectMyGroupList(memberSeq);

		return projectList;
	}

	/**
	 * 마이페이지: 최근등록한 제작물 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMyWorkList(String memberSeq) {

		// 1. 작품 조회
		List<MyUserVO> workList = dao.selectMyWorkList(memberSeq);

		// 2. 작품 좋아요 수 조회
		if (!CmnUtil.isEmpty(workList)) {
			for (MyUserVO workLike : workList) {
				String cntL = dao.selectMyWorkLike(workLike.getWseq());
				workLike.setCntLike(cntL);
				;
			}
		}

		return workList;
	}

	/**
	 * 마이페이지: 나의 관심 디자인(작품) 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMylikeList(String memberSeq) {

		// 1. 작품 조회
		List<MyUserVO> likeList = dao.selectMylikeList(memberSeq);

		// 2. 작품 좋아요 수 조회
		if (!CmnUtil.isEmpty(likeList)) {
			for (MyUserVO workLike : likeList) {
				String cntL = dao.selectMyWorkLike(workLike.getWseq());
				workLike.setCntLike(cntL);
				;
			}
		}

		return likeList;
	}
	
	/**
	 * 2017.11.22
	 * 마이페이지: 나의 관심 프로젝트 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMyProjectlikeList(String memberSeq) {

		// 1. 작품 조회
		List<MyUserVO> likeList = dao.selectMyProjectlikeList(memberSeq);

		

		return likeList;
	}

	/**
	 * 마이페이지: 내가 구입한 디자인(작품) 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMyorderList(String memberSeq) {

		// 1. 작품 조회
		List<MyUserVO> orderList = dao.selectMyorderList(memberSeq);

		return orderList;
	}

	/**
	 * 마이페이지: 나의 포인트 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserPointVO> selectMyPointList(MyUserPointVO paramVO, HttpServletRequest request) {

		// Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberSeq = paramVO.getWseq();
		String fromDate = paramVO.getFromDate();
		fromDate = fromDate.replace("-", "");
		fromDate = fromDate + "0000";
		String toDate = paramVO.getToDate();
		toDate = toDate.replace("-", "");
		toDate = toDate + "2359";

		List<MyUserPointVO> pointList = dao.selectMyPointList(memberSeq, fromDate, toDate);
		// MyUserVO sI;

		// 마이포인트 조회 번호
		if (!CmnUtil.isEmpty(pointList)) {
			int index = pointList.size();
			for (MyUserPointVO pointCnt : pointList) {
				int cntL = index--;
				pointCnt.setCntI(cntL);
				;
			}
		}

		return pointList;

	}

	/**
	 * 마이페이지: 나의 회원정보 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<UserVO> selectMyInfoList(String memberSeq) {

		List<UserVO> myinfoList = dao.selectMyInfoList(memberSeq);

		int memseq = Integer.parseInt(memberSeq);
		List<MemberCategoryVO> myCtgList = dao.selectUCategoryByseq(memseq);
		
		String memberType = (String) dao.selectMymemberType(memberSeq).toString();

		//시/도 목록 가져오기.
		myinfoList.get(0).setSidoList(dao.selectSidoList());
		
		
		//회원 시/도 명 가져오기.
		int sidoSeq = myinfoList.get(0).getSidoSeq();
		myinfoList.get(0).setSido(dao.selectMemberSido(sidoSeq));
		
		for (UserVO item : myinfoList) {
			item.setCateNameList(myCtgList);
			if ("11".equals(memberType)) {
				item.setChkDesigner(true);
				item.setChkProDucer(true);
			} else if ("10".equals(memberType)) {
				item.setChkDesigner(true);
				item.setChkProDucer(false);
			} else if ("01".equals(memberType)) {
				item.setChkDesigner(false);
				item.setChkProDucer(true);
			} else {
				item.setChkDesigner(false);
				item.setChkProDucer(false);
			}
		}

		return myinfoList;
	}

	/**
	 * 회원정보 수정
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> memupdate(UserVO userVO, MultipartHttpServletRequest request) throws Exception {
		LOGGER.debug(userVO);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 1.1 이메일 중복체크:
		//int emailCnt = dao.checkEmailDup2(userVO);

		//if (emailCnt > 0) {
		//  resultMap.put(RstConst.P_NAME, RstConst.V_EMAIL_DUP); // 이메일 중복
		//	return resultMap;
		//}
		
		//현재비밀번호 체크
		if(!StringUtils.isEmpty(userVO.getPasswdOld())) {
			userVO.setPasswdOld(CmnUtil.encryptPassword(userVO.getPasswdOld()));
			
			int cnt = dao.passwdCheck(userVO);
			if (cnt <= 0) {
				resultMap.put(RstConst.P_NAME, "200"); // 존재하지 않음
				return resultMap;
			}
		}
		
		// === 2. 특수 param 처리
		// 2.1 memberType
		String memberType = handleMemberType(userVO.getMemberTypeCheck());
		userVO.setMemberType(memberType);

		// LOGGER.debug(userVO);
		// 2.2 file 처리
		// 
		String fileUploadDbPath = CmnUtil.handleFileUpload(request, "imageUrlFile", FileUploadDomain.USER_PROFILE);
		userVO.setImageUrl(fileUploadDbPath);

		// === 3. db 처리
		CmnUtil.setCmnDate(userVO);
		
		// 비밀번암호화
		if(!StringUtils.isEmpty(userVO.getPasswd())) {
			userVO.setPasswd(CmnUtil.encryptPassword(userVO.getPasswd()));
		}
		
		dao.updateUser(userVO);

		// 3.1 회원 카테고리 처리

		dao.deleteMemberCategory(userVO);

		String[] memberCateCode = userVO.getMemberCateCode();
		if (!CmnUtil.isEmpty(memberCateCode)) {
			for (String code : memberCateCode) {
				MemberCategoryVO mcVO = new MemberCategoryVO();
				mcVO.setCategoryCode(code);
				mcVO.setMemberSeq(userVO.getSeq());
				CmnUtil.setCmnDate(mcVO);
				dao.insertMemberCategory(mcVO);
			}
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 이메일로 회원 조회
	 * 
	 * @param userVO
	 * 
	 * @return
	 */
	public UserVO selectUserByEmail(String email) {

		return dao.selectUserByEmail(email);
	}
	
	/**
	 * 이메일로 seq 조회
	 * 
	 * @param List
	 * 
	 * @return
	 */
	public List<String> selectMemberSeqsFromEmails(Set<String> allEmails) {

		return dao.selectMemberSeqsFromEmails(allEmails);
	}

	/**
	 * 회원 패스월드를 새로 생성 및 수정
	 * 
	 * @param seq
	 * @return
	 */
	public String updateUserNewPassword(String userSeq) {
		String newPassword = generateNewPasswod();
		String encryptPassword = CmnUtil.encryptPassword(newPassword);
		dao.updateUserPassword(userSeq, encryptPassword);
		return newPassword;
	}

	/**
	 * 새 패스월드 생성
	 * 
	 * @return
	 */
	private String generateNewPasswod() {
		StringBuffer result = new StringBuffer();
		String charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random passRandom = new Random();
		for (int i = 0; i < 6; i++) {
			int pos = passRandom.nextInt(charPool.length());
			result.append(charPool.charAt(pos));
		}
		return result.toString();
	}
	
	/**
	 * 시/도 목록
	 * 
	 * @return
	 */
	public Map<String, Object> selectSidoList() {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<SidoVO> sidoList = dao.selectSidoList();
		
		resultMap.put("sidoList", sidoList);
		
		return resultMap;
	}
}
