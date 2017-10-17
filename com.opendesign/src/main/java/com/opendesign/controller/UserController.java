/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.CommonService;
import com.opendesign.service.MailService;
import com.opendesign.service.UserService;
import com.opendesign.utils.CmnConst;
import com.opendesign.utils.CmnConst.CateExclude;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.ControllerUtil;
import com.opendesign.utils.StringUtil;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.MemberCategoryVO;
import com.opendesign.vo.MyUserPointVO;
import com.opendesign.vo.MyUserVO;
import com.opendesign.vo.ProjectGroupVO;
import com.opendesign.vo.UserVO;
import com.wdfall.spring.JsonModelAndView;

import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;

/**
 * 
 * <pre>
 * 회원의 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Controller
public class UserController {

	/**
	 * 로그(log4j) 인스턴스
	 */
	@SuppressWarnings("unused")
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 회원 서비스 인스턴스
	 */
	@Autowired
	UserService service;

	/**
	 * 공통 서비스 인스턴스
	 */
	@Autowired
	CommonService commonService;

	/**
	 * 메일 서비스 인스턴스
	 */
	@Autowired
	MailService mailService;

	/**
	 * <pre>
	 * index.do 
	 * ID#OD 01-01-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("redirect:/main.do");
	}

	/**
	 * 회원가입 절차 1: 이메일/패스월드 입력 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register.ajax")
	public ModelAndView register(@ModelAttribute("user") UserVO userVO, HttpServletRequest request) {
		//
		Map<String, Object> resultMap = service.register(userVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 회원가입 절차 2: 상세 페이지 조회(이동)
	 * ID#OD01-01-04
	 * </pre>
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/openRegister2.do")
	public ModelAndView openRegister2(HttpServletRequest request) throws Exception {
		//
		Map<String, Object> resultMap = new HashMap<String, Object>();

		return new ModelAndView("/common/modal_register2", resultMap);
	}

	/**
	 * 회원가입 절차 2: 회원가입 프로세스
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register2.ajax")
	public ModelAndView register2(@ModelAttribute("user") UserVO userVO, MultipartHttpServletRequest request)
			throws Exception {
		//
		Map<String, Object> resultMap = service.register2(userVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 헤더 카테고리 조회
	 * ID#OD02-01-0
	 * </pre>
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openHeaderNaverCate.do")
	public ModelAndView openHeaderNaverCate(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 디자이너/제작자 목록 페이지인지 판단
		String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
		String excludeCodes = "";
		if (CateExclude.URL_DESI_PROD.contains(requestUri)) {
			//excludeCodes = CateExclude.V_DESI_PROD;
		}
		
		//
		List<CategoryVO> cateListDepth1 = commonService.selectCategoryListDepth1(excludeCodes);
		List<CategoryVO> cateListDepth2 = commonService.selectCategoryListDepth2();
		List<CategoryVO> cateListDepth3 = commonService.selectCategoryListDepth3();
		
		resultMap.put("cateListDepth1", cateListDepth1);
		resultMap.put("cateListDepth2", cateListDepth2);
		resultMap.put("cateListDepth3", cateListDepth3);
		
		return new ModelAndView("/common/header_naver_cate", resultMap);
	}
	
	/**
	 * <pre>
	 * 헤더 서브 카테고리 조회
	 * </pre>
	 * 
	 * @param schCate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openHeaderNaverCateSub.do")
	public ModelAndView openHeaderNaverCateSub(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String schCate = (String)request.getParameter("schCate");
		String codeDepth1 = CmnUtil.getCodeDepth1(schCate);
		String codeDepth2 = CmnUtil.getCodeDepth2(schCate);
		//
		List<CategoryVO> cateListDepth2 = commonService.selectCategoryListDepthSub(codeDepth1);
		List<CategoryVO> cateListDepth3 = commonService.selectCategoryListDepthSub(codeDepth2);
		resultMap.put("cateListDepth2", cateListDepth2);
		resultMap.put("cateListDepth3", cateListDepth3);

		return new ModelAndView("/common/header_naver_cate_sub", resultMap);
	}
	

	/**
	 * <pre>
	 * 헤더 내부 페이지: 
	 * 통합검색,로그인/로그아웃,마이페이지,메시지,알림 등 공통부분 
	 * ID#OD00-01-01
	 * </pre>
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openHeaderInner.do")
	public ModelAndView openHeaderInner(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//

		return new ModelAndView("/common/header_inner", resultMap);
	}

	/**
	 * 로그인 프로세스
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login.ajax")
	public ModelAndView login(@ModelAttribute("user") UserVO vo, HttpServletRequest request) {

		Map<String, Object> resultMap = service.loginProcess(vo, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 로그인 여부 체크
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkedLogin.ajax")
	public ModelAndView checkedLogin(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", CmnUtil.isUserLogin(request));
		return new JsonModelAndView(resultMap);

	}

	/**
	 * <pre>
	 * 로그아웃 프로세스
	 * ID#OD00-01-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout.do")
	public ModelAndView logout(@ModelAttribute("user") UserVO vo, HttpServletRequest request) {

		service.logoutProcess(vo, request);

		return new ModelAndView("redirect:/main.do");
	}

	/**
	 * <pre>
	 * 마이페이지 페이지 조회(이동)
	 * 작품수,좋아요수 등 데이터 조회
	 * ID#OD01-02-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/myPage.do")
	public ModelAndView myPage(HttpServletRequest request) {

		UserVO loginUser = (UserVO) CmnUtil.getLoginUser(request);

		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			return new ModelAndView("redirect:/main.do");
		}

		int memberSeq = Integer.parseInt(loginUser.getSeq());

		String memseq = Integer.toString(memberSeq);
		List<UserVO> myInfoList = service.selectMyInfoList(memseq);

		/*
		 * String UserName = loginUser.getUname(); String Comment =
		 * loginUser.getComments(); String ImageUrl = loginUser.getImageUrl();
		 * String MemType = loginUser.getMemberType();
		 */
		// String MyPoint = loginUser.getPoint();

		int cntWork = service.selectWorkCount(memberSeq);
		int cntLike = service.selectLikeCount(memberSeq);
		int cntTalk = service.selectTalkCount(memberSeq);
		int MyPoint = service.selectMypointCnt(memberSeq);
		int chkLayer = service.selectMyProductCnt(memberSeq);

		/*
		 * request.setAttribute("UserName", UserName);
		 * request.setAttribute("Comment", Comment);
		 * request.setAttribute("ImageUrl", ImageUrl);
		 * request.setAttribute("MemType", MemType);
		 */
		request.setAttribute("MyPoint", MyPoint);
		request.setAttribute("cntWork", cntWork);
		request.setAttribute("cntLike", cntLike);
		request.setAttribute("cntTalk", cntTalk);
		request.setAttribute("memberSeq", memberSeq);
		request.setAttribute("chkLayer", chkLayer);

		List<MemberCategoryVO> cateList = null;
		cateList = service.selectUCategoryByseq(memberSeq);
		if (cateList == null) {
			cateList = new ArrayList<MemberCategoryVO>();
		}

		/*
		 * List<MyUserVO> projectList = null; projectList =
		 * service.selectProjectByseq(memberSeq); if (projectList == null) {
		 * projectList = new ArrayList<MyUserVO>(); }
		 */

		List<MyUserVO> workSeq = null;
		workSeq = service.selectWorkByseq(memberSeq);
		if (workSeq == null) {
			workSeq = new ArrayList<MyUserVO>();
		}

		ModelAndView view = new ModelAndView("user/mypage");
		view.addObject("cateList", cateList);
		// view.addObject("projectList", projectList);
		view.addObject("workSeq", workSeq);
		view.addObject("myInfoList", myInfoList);
		return view;

	}

	/**
	 * <pre>
	 * 마이페이지: 나의 프로젝트 목록 조회
	 * 내가 포함된 프로젝트 목록을 조회한다.
	 * </pre>
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectProjectList.ajax")
	public ModelAndView selectProjectList(@ModelAttribute("work") MyUserVO seqVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String memberSeq = seqVO.getMemberSeq();
		List<MyUserVO> projectList = service.selectMyProjectList(memberSeq);
		resultMap.put("projectList", projectList);

		return new JsonModelAndView(resultMap);

	}
	
	/**
	 * <pre>
	 * 마이페이지: 나의 그룹 현황 조회 
	 * 내가 포함된 그룹 목록을 조회한다.
	 * </pre>
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectMyGroupList.ajax")
	public ModelAndView selectMyGroupList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO loginUser = CmnUtil.getLoginUser(request);
		if(loginUser == null || StringUtils.isEmpty(loginUser.getSeq())) {
			return new JsonModelAndView(resultMap);
		}
		
		List<ProjectGroupVO> resultList = service.selectMyGroupList(loginUser.getSeq());
		
		resultMap.put(RstConst.P_NAME, resultList);
		return new JsonModelAndView(resultMap);
	}

	/**
	 * 마이페이지: 최근등록한 제작물 조회
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectProjectWork.ajax")
	public ModelAndView selectProjectWork(@ModelAttribute("work") MyUserVO seqVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberSeq = seqVO.getMemberSeq();
		List<MyUserVO> workList = service.selectMyWorkList(memberSeq);
		resultMap.put("workList", workList);

		return new JsonModelAndView(resultMap);

	}

	/**
	 * 마이페이지: 나의 관심 디자인(작품) 조회
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectProjectLike.ajax")
	public ModelAndView selectProjectLike(@ModelAttribute("work") MyUserVO seqVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberSeq = seqVO.getMemberSeq();
		List<MyUserVO> likeList = service.selectMylikeList(memberSeq);
		resultMap.put("likeList", likeList);

		return new JsonModelAndView(resultMap);

	}

	/**
	 * 마이페이지: 내가 구입한 디자인(작품) 조회
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectProjectOrder.ajax")
	public ModelAndView selectProjectOrder(@ModelAttribute("work") MyUserVO seqVO, HttpServletRequest request) {
		// ====================
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberSeq = seqVO.getMemberSeq();
		List<MyUserVO> orderList = service.selectMyorderList(memberSeq);
		resultMap.put("orderList", orderList);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 마이페이지: 나의 포인트 조회
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectMyPoint.ajax")
	public ModelAndView selectMyPoint(@ModelAttribute("work") MyUserPointVO paramVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MyUserPointVO> pointList = service.selectMyPointList(paramVO, request);
		resultMap.put("pointList", pointList);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 마이페이지: 나의 회원정보 조회
	 * 
	 * @param MyUserVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectMyInfoList.ajax")
	public ModelAndView selectMyInfoList(@ModelAttribute("work") MyUserVO seqVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String memberSeq = seqVO.getMemberSeq();

		List<UserVO> infoList = service.selectMyInfoList(memberSeq);
		resultMap.put("infoList", infoList);

		return new JsonModelAndView(resultMap);

	}

	/**
	 * 회원정보 수정
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memupdate.ajax")
	public ModelAndView memupdate(@ModelAttribute("user") UserVO userVO, MultipartHttpServletRequest request)
			throws Exception {

		Map<String, Object> resultMap = service.memupdate(userVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 이메일 중복 체크
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hasEmailDuplication.ajax")
	public ModelAndView checkEmailDuplication(@ModelAttribute("user") UserVO userVO, HttpServletRequest request)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		int duplicationCount = service.checkEmailDup(userVO);

		resultMap.put("result", duplicationCount > 0);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 페이스북 인증을 통해서 들어온 회원 로그인 처리
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginFB.ajax")
	public ModelAndView loginFromFB(@ModelAttribute("user") UserVO vo, HttpServletRequest request) {

		Map<String, Object> resultMap = service.loginFBProcess(vo, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 자동가입 방지 보안 문자 이미지 조회
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/requestCaptCha.do")
	public void getCaptCha(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Captcha captcha = new Captcha.Builder(320, 43).addText().addBackground().addNoise().addBorder().build();

		request.getSession().setAttribute(CmnConst.CaptCha.SESSION_KEY, captcha.getAnswer()); 
		CaptchaServletUtil.writeImage(response, captcha.getImage());

	}

	/**
	 * 비밀번호 찾기 프로세스
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPassword.ajax")
	public JsonModelAndView findPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);

		String expectedAnswer = (String) request.getSession().getAttribute(CmnConst.CaptCha.SESSION_KEY);
		String userAnswer = request.getParameter("captchaAnswer");
		/*
		 * 보안 코드 인증 실패
		 */
		if (!userAnswer.equals(expectedAnswer)) {
			resultMap.put(RstConst.P_NAME, "-1");
			return new JsonModelAndView(resultMap);
		}

		try {
			/*
			 * 메일 템플릿 모델
			 */
			Map model = ControllerUtil.createParamMap(request);

			String email = (String) model.get("mail.target");
			UserVO user = service.selectUserByEmail(email);

			if (user != null && StringUtils.isNotEmpty(user.getPasswd())) {
				String userPassword = user.getPasswd();
				// === 회원 password를 새로 생성 및 변경
				String newUserPassword = service.updateUserNewPassword(user.getSeq());
				/*
				 * 메일 서비스 동기방식으로 처리
				 */
				model.put("userPassword", newUserPassword);
				mailService.sendSimpleMail(model);
			} else {
				resultMap.put(RstConst.P_NAME, "-2");
			}

		} catch (Exception e) {
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
		}

		return new JsonModelAndView(resultMap);

	}

	/**
	 * 시/도 목록 조회
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectSidoList.ajax")
	public ModelAndView selectSidoList(HttpServletRequest request) {
		
		Map<String, Object> resultMap = service.selectSidoList();
		
		return new JsonModelAndView(resultMap);
	}
	
	
	/**
	 * 메시지 보내는 사람 seq 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findMemberforMsg.ajax")
	public ModelAndView findMemberforMsg(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String schEmail = request.getParameter("schEmail");
		Set<String> schWord = new HashSet<String>(Arrays.asList(schEmail)); 

		result.put("result", service.selectMemberSeqsFromEmails(schWord));
		return new JsonModelAndView(result);
	}
}
