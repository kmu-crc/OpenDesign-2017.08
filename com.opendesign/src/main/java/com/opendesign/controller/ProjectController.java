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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opendesign.service.CommonService;
import com.opendesign.service.ProductService;
import com.opendesign.service.ProjectService;
import com.opendesign.utils.CmnConst.FileUploadDomain;
import com.opendesign.utils.CmnConst.ProjectProgressStatus;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.Day;
import com.opendesign.utils.StringUtil;
import com.opendesign.utils.ThumbnailManager;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.MainItemVO;
import com.opendesign.vo.ProjectGroupReqVO;
import com.opendesign.vo.ProjectGroupVO;
import com.opendesign.vo.ProjectSubjectVO;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.ProjectWorkVO;
import com.opendesign.vo.SearchGroupVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;
import com.opendesign.websocket.SocketHandler;
import com.wdfall.spring.JsonModelAndView;

/**
 * <pre>
 * 디자인 프로젝트의 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController {

	/** 조회type 주제 */
	private static final String TYPE_SUBJECT = "S";
	/** 조회type 작품 */
	private static final String TYPE_WORK = "W";

	/**
	 * 로그(log4j) 인스턴스
	 */
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 디자인 프로젝트 서비스 인스턴스
	 */
	@Autowired
	ProjectService service;

	/**
	 * 공통 서비스 인스턴스
	 */
	@Autowired
	CommonService commonService;

	/**
	 * 웹소켓 서비스 인스턴스
	 */
	@Autowired
	SocketHandler websocketHandler;
	
	/**
	 * 디자인(작품) 서비스 인스턴스
	 */
	@Autowired
	ProductService productService;

	/**
	 * <pre>
	 * 디자인 프로젝트 페이지 조회(이동)
	 * ID#OD03-01-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/project.do")
	public ModelAndView project(HttpServletRequest request) {
		List<ProjectGroupVO> myProjectGroupList = null;
		List<ProjectVO> myProjectList = null;

		/*
		 * 로그인이 되어있으면 나의 프로젝트와 나의 그룹을 조회
		 */
		UserVO user = CmnUtil.getLoginUser(request);
		if (user != null && StringUtil.isNotEmpty(user.getSeq())) {
			int memberSeq = Integer.parseInt(user.getSeq());
			myProjectGroupList = service.selectMyProjectGroupList(memberSeq);
			myProjectList = service.selectMyProjectList(memberSeq);
		}

		if (myProjectGroupList == null) {
			myProjectGroupList = new ArrayList<ProjectGroupVO>();
		}

		if (myProjectList == null) {
			myProjectList = new ArrayList<ProjectVO>();
		}

		ModelAndView view = new ModelAndView("project/project");
		view.addObject("myPGroupList", myProjectGroupList);
		view.addObject("myProjectList", myProjectList);

		return view;
	}

	/**
	 * 디자인 프로젝트 목록 데이터 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectList.ajax")
	public @ResponseBody Map<String, Object> ajaxProjectList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		/*
		 * 페이징 처리
		 */
		String schPPage = request.getParameter("schPPage");
		String schCPage = request.getParameter("schCPage");
		String progressStatus = request.getParameter("schProgressStatus");
		String schSort = request.getParameter("schSort");

		int pageIndex = getPageIndex(schPPage, schCPage, progressStatus);
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
		/* 그룹은 selectGroupDetail.ajax에서 조회 */

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page_count", (pageIndex - 1) * limitCount);
		paramMap.put("limit_count", limitCount);
		paramMap.put("schCate", request.getParameter("schCate"));
		paramMap.put("schSort", schSort);

		
		UserVO user = CmnUtil.getLoginUser(request);
		if (user != null && StringUtil.isNotEmpty(user.getSeq())) {
			int memberSeq = Integer.parseInt(user.getSeq());
			paramMap.put("memberSeq", memberSeq);
		}
		
		paramMap.put("schProgressStatus", progressStatus);
		int totalCount = service.selectProjectCount(paramMap);
		int allCount = totalCount; //완료된 프로젝트 없앰. allCount를 진행중인것으로 값변환.

		List<ProjectVO> list = service.selectProjectList(paramMap);
		
		MainItemVO item = new MainItemVO();
		
		if(!StringUtils.isEmpty(request.getParameter("schCate"))) {
			item = productService.settingCategoryNm(request.getParameter("schCate"));
		}
		
		resultMap.put("all_count", allCount);
		resultMap.put("total_count", totalCount);
		resultMap.put("item", item);
		resultMap.put("list", list);

		return resultMap;
	}
	
	
	/**
	 * 그룹 탭 데이터 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectGroupList.ajax")
	public ModelAndView selectGroupList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {
		String schSort = request.getParameter("sortType");
		searchVO.setSchOrderType(schSort);
		Map<String, Object> resultMap = service.selectGroupList(searchVO);

		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 그룹 상세 조회
	 * 
	 * @param searchVO
	 *  schMyGroup
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectGroupDetail.ajax")
	public ModelAndView selectGroupDetail(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page_count", 0);
		paramMap.put("limit_count", Integer.MAX_VALUE); //다 가져옴.
		
		UserVO user = CmnUtil.getLoginUser(request);
		if (user != null && StringUtil.isNotEmpty(user.getSeq())) {
			int memberSeq = Integer.parseInt(user.getSeq());
			paramMap.put("memberSeq", memberSeq);
		}
		
		//=== schMyGroup 변경: 로그인 없이도 검색할수 있게 
		String schMyGroup = request.getParameter("schMyGroup");
		if (StringUtil.isNotEmpty(schMyGroup)) {
			try {
				paramMap.put("schMyGroup", Integer.parseInt(schMyGroup));
			} catch (Exception e) {
			}
		}
		paramMap.put("schProgressStatus", ProjectProgressStatus.PROGRESS);
		
		int allCount = service.selectProjectCount(paramMap);
		List<ProjectVO> list = service.selectProjectList(paramMap);
		
		resultMap.put("all_count", allCount);
		resultMap.put("list", list);

		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 페이징 퍼래미터 처리
	 * @param schPPage
	 * @param schCPage
	 * @param progressStatus
	 * @return
	 */
	private int getPageIndex(String schPPage, String schCPage, String progressStatus) {
		int pPage = 1;
		try {
			pPage = Integer.parseInt(schPPage);
		} catch (Exception e) {
		}

		int cPage = 1;
		try {
			cPage = Integer.parseInt(schCPage);
		} catch (Exception e) {
		}

		int pageIndex = pPage;
		if ("C".equals(progressStatus)) {
			pageIndex = cPage;

		}

		return pageIndex;

	}

	/**
	 * <pre>
	 * 디자인 프로젝트 등록 페이지 조회(이동) 
	 * ID#OD03-01-02
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectRegi.do")
	public ModelAndView projectRegi(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("project/project_regi");

		view.addObject("cateList", commonService.selectCategoryListDepth1());
		return view;
	}

	/**
	 * 회원 찾기 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findMember.ajax")
	public ModelAndView findMember(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String schWord = request.getParameter("schWord");

		result.put("result", service.selectFindMemberList(schWord));
		return new JsonModelAndView(result);
	}

	/**
	 * 디자인 프로젝트 등록
	 * 
	 * @param projectVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerProject.ajax")
	public ModelAndView registerProject(@ModelAttribute("project") ProjectVO projectVO,
			MultipartHttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);
		}
		projectVO.setOwnerSeq(Integer.parseInt(loginUser.getSeq()));

		String fileUploadDbPath = CmnUtil.handleFileUpload(request, "fileUrlFile", FileUploadDomain.PROJECT);
		/*
		 * 작업 목록에 보여지는 Thumbnail과 
		 * 디자인 프로젝트 목록 에서 보여지는 Thumbnail 저장 
		 */
		String fileUploadDir = CmnUtil.getFileUploadDir(request, FileUploadDomain.PROJECT);
		String fileName = File.separator + FilenameUtils.getName(fileUploadDbPath);
		ThumbnailManager.saveThumbDesignWorkMedium(fileUploadDir + fileName );
		ThumbnailManager.saveThumbProjectWorkMedium(fileUploadDir + fileName);
		
		String oriFileName = CmnUtil.handleFileUploadGetOriFileName(request, "fileUrlFile");

		projectVO.setFileName(oriFileName);
		projectVO.setFileUrl(fileUploadDbPath);
		String currentDate = Day.getCurrentTimestamp().substring(0, 12);
		projectVO.setRegisterTime(currentDate);
		projectVO.setUpdateTime(currentDate);
		projectVO.setProgressStatus("P");

		String[] categoryCodes = request.getParameterValues("categoryCodes");
		String[] emails = request.getParameterValues("emails");

		int projectSeq = service.insertProject(projectVO, categoryCodes, emails);
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return new JsonModelAndView(resultMap);

	}

	/**
	 * 디자인 프로젝트 수정
	 * 
	 * @param projectVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProject.ajax")
	public ModelAndView ajaxUpdateProject(@ModelAttribute("project") ProjectVO projectVO,
			MultipartHttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);
		}
		projectVO.setOwnerSeq(Integer.parseInt(loginUser.getSeq()));

		String fileUploadDbPath = CmnUtil.handleFileUpload(request, "fileUrlFile", FileUploadDomain.PROJECT);
		String oriFileName = CmnUtil.handleFileUploadGetOriFileName(request, "fileUrlFile");

		
		if (StringUtil.isNotEmpty(fileUploadDbPath)) {
			/*
			 * 작업 목록에 보여지는 Thumbnail과 
			 * 디자인 프로젝트 목록 에서 보여지는 Thumbnail 저장 
			 */
			String fileUploadDir = CmnUtil.getFileUploadDir(request, FileUploadDomain.PROJECT);
			String fileName = File.separator + FilenameUtils.getName(fileUploadDbPath);
			ThumbnailManager.saveThumbDesignWorkMedium(fileUploadDir + fileName );
			ThumbnailManager.saveThumbProjectWorkMedium(fileUploadDir + fileName);
			
			projectVO.setFileUrl(fileUploadDbPath);
			projectVO.setFileName(oriFileName);
		}

		String currentDate = Day.getCurrentTimestamp().substring(0, 12);
		projectVO.setUpdateTime(currentDate);

		String[] categoryCodes = request.getParameterValues("categoryCodes");
		String[] emails = request.getParameterValues("emails");

		int projectSeq = service.updateProject(projectVO, categoryCodes, emails);
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return new JsonModelAndView(resultMap);

	}
	
	/**
	 * 디자인 프로젝트 삭제
	 * 
	 * @param projectVO:
	 * 		seq
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteProject.ajax")
	public ModelAndView deleteProject(@ModelAttribute("project") ProjectVO projectVO,
			HttpServletRequest request) throws Exception {

		UserVO loginUser = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.deleteProject(projectVO, loginUser);
	
		return new JsonModelAndView(resultMap);

	}
	

	// =========================== 그룹 ===========================

	/**
	 * <pre>
	 * 디자인 프로젝트 그룹 등록 및 관리 페이지 조회(이동)
	 * ID#OD03-02-05
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectGroup.do")
	public ModelAndView projectGroup(HttpServletRequest request) {
		return new ModelAndView("/project/project_group");
	}

	/**
	 * 디자인 프로젝트 그룹 생성 및 관리 목록 데이터 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/groupList.ajax")
	public ModelAndView groupList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}
		resultMap.put("list", service.selectMyProjectGroupList(Integer.parseInt(loginUser.getSeq())));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹내의 프로젝트 목록 데이터 조회
	 * 
	 * @param groupVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/groupProjectList.ajax")
	public ModelAndView groupProjectList(ProjectGroupVO groupVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}
		resultMap.put("list", service.selectMyGroupProjectList(Integer.parseInt(loginUser.getSeq()), groupVO));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹 등록
	 * 
	 * @param groupVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertGroup.ajax")
	public ModelAndView insertGroup(ProjectGroupVO groupVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}
		resultMap.put("data", service.insertMyGroup(Integer.parseInt(loginUser.getSeq()), groupVO));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹 삭제 
	 * 
	 * @param groupVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteGroup.ajax")
	public ModelAndView deleteGroup(ProjectGroupVO groupVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}
		service.deleteMyGroup(Integer.parseInt(loginUser.getSeq()), groupVO);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹 팝업에서 프로젝트 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchGroupProject.ajax")
	@Deprecated
	public ModelAndView searchGroupProject(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}

		String schProjectName = request.getParameter("schProjectName");
		if (!StringUtil.isNotEmpty(schProjectName)) {
			resultMap.put("list", new ArrayList<ProjectVO>());
			return new JsonModelAndView(resultMap);

		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schProjectName", schProjectName);
		int pageIndex = 1;
		int limitCount = 100;
		paramMap.put("page_count", (pageIndex - 1) * limitCount);
		paramMap.put("limit_count", limitCount);

		int memberSeq = Integer.parseInt(loginUser.getSeq());
		paramMap.put("memberSeq", memberSeq);

		List<ProjectVO> list = service.selectProjectList(paramMap);
		resultMap.put("list", list);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹 팝업에서 프로젝트를 그룹에 등록
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addGroupProject.ajax")
	@Deprecated
	public ModelAndView addGroupProject(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}

		String groupSeq = request.getParameter("projectGroupSeq");
		String[] projectSeqs = request.getParameterValues("projectSeq[]");
		if (!StringUtil.isNotEmpty(groupSeq) || (projectSeqs == null || projectSeqs.length <= 0)) {
			resultMap.put("result", "200");
			return new JsonModelAndView(resultMap);

		}

		service.insertGroupProject(loginUser.getSeq(), groupSeq, projectSeqs);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹 팝업에서 프로젝트를 그룹에서 제외
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteGroupProject.ajax")
	public ModelAndView deleteGroupProject(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);

		}

		String groupSeq = request.getParameter("groupSeq");
		String projectSeq = request.getParameter("projectSeq");

		if (!StringUtil.isNotEmpty(groupSeq) || !StringUtil.isNotEmpty(projectSeq)) {
			resultMap.put("result", "200");
			return new JsonModelAndView(resultMap);

		}

		service.deleteGroupProject(loginUser.getSeq(), groupSeq, projectSeq);

		return new JsonModelAndView(resultMap);
	}

	// =========================== 그룹new ===========================
	/**
	 * 그룹에 신청한 프로젝트 목록 조회
	 * 
	 * @param reqVO
	 *            schProjectSeq
	 * @return
	 */
	@RequestMapping(value = "/selectProjectGroupRequestInfo.ajax")
	public ModelAndView selectProjectGroupRequestInfo(@ModelAttribute SearchGroupVO reqVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectProjectGroupRequestInfo(reqVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 그룹 목록 조회 (이름으로 정렬)
	 * </pre>
	 * 
	 * @param reqVO
	 *            schGroupName
	 * @return
	 */
	@RequestMapping(value = "/selectProjectGroupListByName.ajax")
	public ModelAndView selectProjectGroupListByName(@ModelAttribute SearchGroupVO reqVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectProjectGroupListByName(reqVO);

		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 프로젝트를 그룹에 신청
	 * 
	 * @param reqVO
	 *            projectSeq,projectGroupSeq
	 * @return
	 */
	@RequestMapping(value = "/insertProjectGroupRequest.ajax")
	public ModelAndView insertProjectGroupRequest(@ModelAttribute ProjectGroupReqVO reqVO, HttpServletRequest request) {

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.insertProjectGroupRequest(reqVO, loginUserVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트를 그룹에 신청한것을 취소
	 * 
	 * @param reqVO
	 *            projectSeq,projectGroupSeq
	 * @return
	 */
	@RequestMapping(value = "/updateProjectGroupRequestCancel.ajax")
	public ModelAndView updateProjectGroupRequestCancel(@ModelAttribute ProjectGroupReqVO reqVO, HttpServletRequest request) {

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.updateProjectGroupRequestCancel(reqVO, loginUserVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹에 신청한 프로젝트 대기 목록 조회
	 * 
	 * @param reqVO
	 *            schGroupSeq
	 * @return
	 */
	@RequestMapping(value = "/selectProjectGroupRequestWaitingList.ajax")
	public ModelAndView selectProjectGroupRequestWaitingList(@ModelAttribute SearchGroupVO reqVO,
			HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectProjectGroupRequestWaitingList(reqVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹에 신청한 프로젝트를 승인
	 * 
	 * @param reqVO
	 * 		projectSeq, projectGroupSeq
	 * @return
	 */
	@RequestMapping(value = "/updateProjectGroupRequestApprove.ajax")
	public ModelAndView updateProjectGroupRequestApprove(@ModelAttribute ProjectGroupReqVO reqVO,
			HttpServletRequest request) {

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.updateProjectGroupRequestApprove(reqVO, loginUserVO);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 그룹에 신청한 프로젝트를 제외
	 * 
	 * @param reqVO
	 * 		projectSeq, projectGroupSeq
	 * @return
	 */
	@RequestMapping(value = "/updateProjectGroupRequestReject.ajax")
	public ModelAndView updateProjectGroupRequestReject(@ModelAttribute ProjectGroupReqVO reqVO,
			HttpServletRequest request) {

		UserVO loginUserVO = CmnUtil.getLoginUser(request);
		Map<String, Object> resultMap = service.updateProjectGroupRequestReject(reqVO, loginUserVO);

		return new JsonModelAndView(resultMap);
	}

	// =========================== ]]그룹new ===========================

	// =========================== ]]그룹 =====================================

	/**
	 * <pre>
	 * 디자인 프로젝트 관리 페이지 조회(이동)
	 * ID#OD03-01-03
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectManage.do")
	public ModelAndView projectManage(HttpServletRequest request) {
		UserVO loginUser = (UserVO) CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			return new ModelAndView("redirect:/project/project.do");
		}

		ModelAndView view = new ModelAndView("project/project_manage");
		view.addObject("projectList", service.selectMyProjectList(Integer.parseInt(loginUser.getSeq())));
		view.addObject("cateList", commonService.selectCategoryListDepth1());
		return view;
	}

	/**
	 * 디자인 프로젝트 관리 페이지에서 프로젝트 상세 조회
	 * 
	 * @param projectVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectDetail.ajax")
	public ModelAndView ajaxProjectDetail(@ModelAttribute("project") ProjectVO projectVO, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO loginUser = CmnUtil.getLoginUser(request);
		if (loginUser == null || !StringUtil.isNotEmpty(loginUser.getSeq())) {
			resultMap.put("result", "100");
			return new JsonModelAndView(resultMap);
		}
		projectVO.setOwnerSeq(Integer.parseInt(loginUser.getSeq()));

		ProjectVO project = service.selectMyProject(projectVO.getOwnerSeq(), projectVO.getSeq());
		if (project == null || project.getSeq() <= 0) {
			resultMap.put("result", "404");
			return new JsonModelAndView(resultMap);
		}
		List<CategoryVO> cateList = service.selectProjectCategoryList(project.getSeq());
		project.setCategoryList(cateList); 

		resultMap.put("project", project);
		resultMap.put("cate_list", cateList);
		resultMap.put("member_list", service.selectProjectMemberList(project.getSeq()));

		return new JsonModelAndView(resultMap);

	}

	// =========================== 주제, 작품 =========================== 

	/**
	 * <pre>
	 * 프로젝트 상세 페이지 조회(이동) 
	 * ID#OD03-02-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openProjectDetail.do")
	public ModelAndView openProjectDetail(@ModelAttribute("subject") ProjectSubjectVO subjectVO,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String projectSeq = request.getParameter("projectSeq");
		request.setAttribute("projectSeq", projectSeq); // @test
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectSeq", projectSeq);
		// 프로젝트 상세 정보 가져오기
		UserVO user = CmnUtil.getLoginUser(request);
		if (user != null && StringUtil.isNotEmpty(user.getSeq())) {
			int memberSeq = Integer.parseInt(user.getSeq());
			paramMap.put("memberSeq", memberSeq);
		}

		ProjectVO projectVO = service.selectProjectInfo(paramMap);
		request.setAttribute("projectVO", projectVO);
		
		// 좋아요 정보 : like 했는지 조회
		if (user != null) {
			resultMap.put("liked", commonService.selectProjectLiked(user.getSeq(), projectSeq));
		}else{
			resultMap.put("liked", false);			
		}

		return new ModelAndView("/project/project_list", resultMap);
	}

	/**
	 * 프로젝트 상세 페이지에서 주제,작품 목록 데이터 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectProjectDetail.ajax")
	public ModelAndView selectProjectDetail(@ModelAttribute("subject") ProjectSubjectVO subjectVO,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String projectSeq = subjectVO.getProjectSeq();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectSeq", projectSeq);
		
		
		if( CmnUtil.isUserLogin(request) ) {
			paramMap.put("logonUserSeq", CmnUtil.getLoginUser(request).getSeq() );
		}
		
		List<ProjectSubjectVO> subjectList = service.selectProjectSubjectWholeList(paramMap);
		resultMap.put("subjectList", subjectList);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 주제 등록
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/insertProjectSubject.ajax")
	public ModelAndView insertProjectSubject(@ModelAttribute("subject") ProjectSubjectVO subjectVO,
			HttpServletRequest request) throws JsonProcessingException {

		Map<String, Object> resultMap = service.insertProjectSubject(subjectVO, request);

		// === websocket 처리
		websocketHandler.notifyProjectChanged(subjectVO.getProjectSeq());

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 주제 수정
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/updateProjectSubject.ajax")
	public ModelAndView updateProjectSubject(@ModelAttribute("subject") ProjectSubjectVO subjectVO,
			HttpServletRequest request) throws JsonProcessingException {

		Map<String, Object> resultMap = service.updateProjectSubject(subjectVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 주제 삭제 체크
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkDeleteProjectSubject.ajax")
	public ModelAndView checkDeleteProjectSubject(@ModelAttribute("subject") ProjectSubjectVO subjectVO,
			HttpServletRequest request) {

		Map<String, Object> resultMap = service.checkDeleteProjectSubject(subjectVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 주제 삭제
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/deleteProjectSubject.ajax")
	public ModelAndView deleteProjectSubject(@ModelAttribute("subject") ProjectSubjectVO subjectVO,
			HttpServletRequest request) throws JsonProcessingException {

		ProjectVO proj = service.selectProjectByTypeAndSeq(TYPE_SUBJECT, subjectVO.getSeq());

		Map<String, Object> resultMap = service.deleteProjectSubject(subjectVO, request);

		// === websocket 처리
		websocketHandler.notifyProjectChanged(String.valueOf(proj.getSeq()));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 등록
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/insertProjectWork.ajax")
	public ModelAndView insertProjectWork(@ModelAttribute("work") ProjectWorkVO workVO,
			MultipartHttpServletRequest request) throws IOException {

		Map<String, Object> resultMap = service.insertProjectWork(workVO, request);

		// === websocket 처리
		ProjectVO proj = service.selectProjectByTypeAndSeq(TYPE_WORK, workVO.getSeq());
		websocketHandler.notifyProjectChanged(String.valueOf(proj.getSeq()));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 상세 조회
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectProjectWorkDetail.ajax")
	public ModelAndView selectProjectWorkDetail(@ModelAttribute("work") ProjectWorkVO workVO,
			HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectProjectWorkDetail(workVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 수정 페이지 조회(이동)
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/openUpdateProjectWork.ajax")
	public ModelAndView openUpdateProjectWork(@ModelAttribute("work") ProjectWorkVO workVO, HttpServletRequest request)
			throws JsonProcessingException {

		Map<String, Object> resultMap = service.openUpdateProjectWork(workVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 수정
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateProjectWork.ajax")
	public ModelAndView updateProjectWork(@ModelAttribute("work") ProjectWorkVO workVO,
			MultipartHttpServletRequest request) throws IOException {

		Map<String, Object> resultMap = service.updateProjectWork(workVO, request);

		// === websocket 처리
		ProjectVO proj = service.selectProjectByTypeAndSeq(TYPE_WORK, workVO.getSeq());
		websocketHandler.notifyProjectChanged(String.valueOf(proj.getSeq()));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 삭제
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/deleteProjectWork.ajax")
	public ModelAndView deleteProjectWork(@ModelAttribute("work") ProjectWorkVO workVO, HttpServletRequest request)
			throws JsonProcessingException {

		ProjectVO proj = service.selectProjectByTypeAndSeq(TYPE_WORK, workVO.getSeq());

		Map<String, Object> resultMap = service.deleteProjectWork(workVO, request);

		// === websocket 처리
		websocketHandler.notifyProjectChanged(String.valueOf(proj.getSeq()));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 퍼가기
	 * 
	 * @param workVO:
	 *            seq: 퍼갈 workSeq
	 * @param workVO:
	 *            fromVerSeq: 퍼갈 버전seq
	 * @param workVO:
	 *            toSubjectSeq: 목적 주제 seq
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/shareProjectWork.ajax")
	public ModelAndView shareProjectWork(@ModelAttribute("work") ProjectWorkVO workVO, HttpServletRequest request)
			throws JsonProcessingException {

		Map<String, Object> resultMap = service.shareProjectWork(workVO, request);

		// === websocket 처리
		ProjectVO proj = service.selectProjectByTypeAndSeq(TYPE_WORK, workVO.getSeq());
		websocketHandler.notifyProjectChanged(String.valueOf(proj.getSeq()));

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 프로젝트 작품 퍼가기 팝업에서 주제 목록 조회
	 * 
	 * @param workVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectShareProjectSubjectList.ajax")
	public ModelAndView selectShareProjectSubjectList(@ModelAttribute("work") ProjectWorkVO workVO,
			HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectShareProjectSubjectList(workVO, request);

		return new JsonModelAndView(resultMap);
	}

	// =========================== ]]주제, 작품 ===========================

}
