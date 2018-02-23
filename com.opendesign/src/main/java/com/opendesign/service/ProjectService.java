/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.opendesign.dao.CommonDAO;
import com.opendesign.dao.ProjectDAO;
import com.opendesign.dao.UserDAO;
import com.opendesign.utils.CmnConst.CheckRule;
import com.opendesign.utils.CmnConst.FileUploadDomain;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.Day;
import com.opendesign.utils.ThumbnailManager;
import com.opendesign.vo.AlarmVO;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.ItemLikeVO.ItemType;
import com.opendesign.vo.ProjectGroupReqVO;
import com.opendesign.vo.ProjectGroupReqVO.ProjectGroupReqStatus;
import com.opendesign.vo.ItemLikeVO;
import com.opendesign.vo.ItemWorkVO;
import com.opendesign.vo.ProjectGroupVO;
import com.opendesign.vo.ProjectMemberVO;
import com.opendesign.vo.ProjectSubjectVO;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.ProjectWorkMemberVO;
import com.opendesign.vo.ProjectWorkVO;
import com.opendesign.vo.ProjectWorkVerVO;
import com.opendesign.vo.SearchGroupVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;
import com.opendesign.websocket.SocketHandler;

/**
 * 
 * <pre>
 * 디자인 프로젝트의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class ProjectService {

	/**
	 *  시작 버전 
	 */
	private static final String VERSION_START = "1.0";

	/**
	 * 로그(log4j) 인스턴스
	 */
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 프로젝트 DAO 인스턴스
	 */
	@Autowired
	ProjectDAO dao;

	/**
	 * 회원 DAO 인스턴스
	 */
	@Autowired
	UserDAO userDao;

	/**
	 * 공통 서비스 인스턴스
	 */
	@Autowired
	CommonService commonService;

	/**
	 * 공통 DAO 인스턴스
	 */
	@Autowired
	CommonDAO commonDao;

	/**
	 * 웹소켓 서비스 인스턴스
	 */
	@Autowired
	SocketHandler websocketHandler;

	/**
	 * 디자인 프로젝트 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<ProjectVO> selectProjectList(Map<String, Object> paramMap) {
		List<ProjectVO> projectList = dao.selectProjectList(paramMap);

		for (ProjectVO vo : projectList) {
			vo.setCategoryList(dao.selectProjectCategoryList(vo.getSeq()));
		}

		return projectList;
	}
	
	/**
	 * 나의 프로젝트 목록 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<ProjectVO> selectMyProjectList(int memberSeq) {
		return dao.selectMyProjectList(memberSeq);
	}

	/**
	 * 디자인 프로젝트 총개수 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public int selectProjectCount(Map<String, Object> paramMap) {
		return dao.selectProjectCount(paramMap);
	}

	/**
	 * 나의 그룹 목록 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<ProjectGroupVO> selectMyProjectGroupList(int memberSeq) {
		return dao.selectMyProjectGroupList(memberSeq);
	}
	

	/**
	 * 디자인 프로젝트 등록
	 * 
	 * @param project
	 * @param categorys
	 * @param emails
	 * @return
	 */
	@Transactional
	public int insertProject(ProjectVO project, String[] categorys, String[] emails) {
		ProjectVO iProject = dao.insertProject(project);

		int projectSeq = iProject.getSeq();
		String registerTime = iProject.getRegisterTime();
		dao.insertProjectCategory(projectSeq, categorys, registerTime);
		dao.insertProjectMember(projectSeq, emails, registerTime);

		return projectSeq;
	}

	/**
	 * 디자인 프로젝트 수정
	 * 
	 * @param project
	 * @param categorys
	 * @param emails
	 * @return
	 */
	@Transactional
	public int updateProject(ProjectVO project, String[] categorys, String[] emails) {
		int result = dao.updateProject(project);

		int projectSeq = project.getSeq();
		String updateTime = project.getUpdateTime();
		dao.insertProjectCategory(projectSeq, categorys, updateTime);
		dao.insertProjectMember(projectSeq, emails, updateTime);

		return result;
	}
	
	/**
	 * 디자인 프로젝트 삭제
	 * 
	 * @param projectVO
	 * 		seq
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteProject(ProjectVO projectVO, UserVO loginUser) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (loginUser == null || StringUtils.isEmpty(loginUser.getSeq())) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NEED_LOGIN);
			return resultMap;
		}
		projectVO.setOwnerSeq(Integer.parseInt(loginUser.getSeq()));

		dao.deleteProject(projectVO);
	
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 회원 검색
	 * 
	 * @param schWord
	 * @return
	 */
	public List<ProjectMemberVO> selectFindMemberList(String schWord) {
		return dao.selectFindMember(schWord);
	}

	/**
	 * 나의 프로젝트 목록 조회
	 * 
	 * @param memberSeq
	 * @param projectSeq
	 * @return
	 */
	public ProjectVO selectMyProject(int memberSeq, int projectSeq) {
		return dao.selectMyProject(memberSeq, projectSeq);
	}

	/**
	 * 디자인 프로젝트 상세정보 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public ProjectVO selectProjectInfo(Map<String, Object> paramMap) {
		ProjectVO project = dao.selectProjectInfo(paramMap);
		
		if( project != null ) {
			project.setCategoryList(dao.selectProjectCategoryList(project.getSeq()));
		}

		return project;
	}

	/**
	 * 디자인 프로젝트 카테고리 목록 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<CategoryVO> selectProjectCategoryList(int projectSeq) {
		return dao.selectProjectCategoryList(projectSeq);
	}

	/**
	 * 디자인 프로젝트 멤버 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<ProjectMemberVO> selectProjectMemberList(int projectSeq) {
		return dao.selectProjectMemberList(projectSeq);
	}

	/**
	 * 프로젝트 주제 등록
	 * 
	 * @param subjectVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> insertProjectSubject(ProjectSubjectVO subjectVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO user = CmnUtil.getLoginUser(request);
		subjectVO.setMemberSeq(user.getSeq());
		CmnUtil.setCmnDate(subjectVO);
		dao.insertProjectSubject(subjectVO);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 프로젝트 주제 수정
	 * 
	 * @param subjectVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateProjectSubject(ProjectSubjectVO subjectVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO user = CmnUtil.getLoginUser(request);
		subjectVO.setMemberSeq(user.getSeq());
		CmnUtil.setCmnDate(subjectVO);
		dao.updateProjectSubject(subjectVO);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 프로젝트 주제 삭제 여부 체크
	 * 
	 * @param subjectVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> checkDeleteProjectSubject(ProjectSubjectVO subjectVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserVO user = CmnUtil.getLoginUser(request);
		subjectVO.setMemberSeq(user.getSeq());
		int cnt = dao.checkDeleteProjectSubject(subjectVO);

		if (cnt > 0) {
			// 작품이 있다.
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
		} else {
			resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		}
		return resultMap;
	}

	/**
	 * 프로젝트 주제 삭제
	 * 
	 * @param subjectVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteProjectSubject(ProjectSubjectVO subjectVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		dao.deleteProjectSubject(subjectVO);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 프로젝트 작품 등록
	 * 
	 * @param workVO
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Map<String, Object> insertProjectWork(ProjectWorkVO workVO, MultipartHttpServletRequest request)
			throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 00. 체크:
		MultipartFile ckFile = request.getFile("fileUriFile");
		if(ckFile != null) {
			if (ckFile.getSize() > CheckRule.LIMIT_FILE_SIZE) {
				resultMap.put(RstConst.P_NAME, RstConst.V_FILE_SIZE);
				resultMap.put("fileName", ckFile.getOriginalFilename());
				return resultMap;
			}
		}
		
		// 줄바꿈처리
		CmnUtil.handleHtmlEnterRN2BR(workVO, "contents");
		
		//0.
		// === 썸네일 이미지 처리
		String fileUploadDbPathThumb = CmnUtil.handleFileUpload(request, "fileUriFileThumb", FileUploadDomain.PROJECT_WORK_FILE);
		workVO.setThumbUri(fileUploadDbPathThumb);
		/*
		 * 디자인 프로젝트 주제 목록에서 보여지는 Thumbnail 과
		 */
		String fileUploadDirThumb = CmnUtil.getFileUploadDir(request, FileUploadDomain.PROJECT_WORK_FILE);
		String fileNameThumb = File.separator + FilenameUtils.getName(fileUploadDbPathThumb);
		ThumbnailManager.saveThumbProjectWorkSmall(fileUploadDirThumb +  fileNameThumb);
		
		// === 1.work
		// projectSubjectSeq 있어야 한다.
		UserVO user = CmnUtil.getLoginUser(request);
		workVO.setMemberSeq(user.getSeq());
		CmnUtil.setCmnDate(workVO);
		dao.insertProjectWork(workVO);

		// === 2.workVer
		// === 이미지 처리
		String fileUploadDbPath = CmnUtil.handleFileUpload(request, "fileUriFile", FileUploadDomain.PROJECT_WORK_FILE);

		/*
		 * 디자인 프로젝트 주제 목록에서 보여지는 Thumbnail 과
		 * 디자인 프로젝트 작업 상세 에서 보여지는 Thumbnail 저장 
		 */
			
		String fileUploadDir = CmnUtil.getFileUploadDir(request, FileUploadDomain.PROJECT_WORK_FILE);
		String fileName = File.separator + FilenameUtils.getName(fileUploadDbPath);
		if(CmnUtil.isImageFile(fileName)) {
			ThumbnailManager.saveThumbProjectWorkSmall(fileUploadDir +  fileName);
			ThumbnailManager.saveThumbProjectWorkLarge(fileUploadDir + fileName);
		}
		
		String oriFileName = CmnUtil.handleFileUploadGetOriFileName(request, "fileUriFile");
		ProjectWorkVerVO verVO = new ProjectWorkVerVO();
		verVO.setProjectWorkSeq(workVO.getSeq());
		verVO.setFileUri(fileUploadDbPath);
		verVO.setFilename(oriFileName);
		verVO.setVer(VERSION_START);
		CmnUtil.setCmnDate(verVO);
		dao.insertProjectWorkVer(verVO);

		// 2.1 작품 마지막 ver 변경
		workVO.setLastVer(VERSION_START);
		dao.updateProjectWorkLastVer(workVO);
		
		// 포함된 프로젝트의 업데이트 날짜 변경
		dao.updateProjectDate(workVO);
		
		// 포함된 프로젝트 seq 찾아서 알람 설정
		setProjectFromWork(workVO, user);
		

		// === 3. workMember
		// 참여자: (생성회원도 포함시킨다).
		Set<String> allEmails = new HashSet<String>();
		allEmails.add(user.getEmail());
		if (!CmnUtil.isEmpty(workVO.getWorkMemberEmails())) {
			for (String item : workVO.getWorkMemberEmails()) {
				allEmails.add(item);
			}
		}
		List<String> seqs = userDao.selectMemberSeqsFromEmails(allEmails);
		for (String seq : seqs) {
			insertWorkMember(workVO.getSeq(), seq);
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 프로젝트 작품 멤버 등록
	 * 
	 * @param workSeq
	 * @param memberSeq
	 */
	private void insertWorkMember(String workSeq, String memberSeq) {
		ProjectWorkMemberVO wmVO = new ProjectWorkMemberVO();
		wmVO.setMemberSeq(memberSeq);
		wmVO.setProjectWorkSeq(workSeq);
		CmnUtil.setCmnDate(wmVO);
		dao.insertProjectWorkMember(wmVO);
	}

	/**
	 * <pre>
	 * 프로젝트 작품 수정/삭제 할수 있는 회원인지 판단
	 * 생성회원 & 프로젝트 팀장만 가능하다.
	 * </pre>
	 * 
	 * @param workVO
	 * @param request
	 * @return
	 */
	public boolean selectIsProjectWorkAuthUser(String workSeq, HttpServletRequest request) {
		boolean result = false;

		UserVO userVO = CmnUtil.getLoginUser(request);
		ProjectWorkVO workVO = dao.selectProjectWork(workSeq);
		ProjectVO projectVO = dao.setProjectFromWork(workVO);
		if (userVO != null && workVO != null) {
			String memberSeq = StringUtils.stripToEmpty(workVO.getMemberSeq()); // 생성회원
			String ownerSeq = StringUtils.stripToEmpty(Integer.toString(projectVO.getOwnerSeq())); // 프로젝트 팀장
			if (memberSeq.equals(userVO.getSeq()) || ownerSeq.equals(userVO.getSeq())) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 프로젝트 작품 상세정보 조회
	 * 
	 * @param workVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectProjectWorkDetail(ProjectWorkVO workVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ProjectWorkVO resultVO = selectProjectWorkWhole(workVO.getSeq());
		UserVO userVo = CmnUtil.getLoginUser(request);
		
		if (userVo != null) {
			// === auth 판단
			boolean curUserAuthYN = this.selectIsProjectWorkAuthUser(workVO.getSeq(), request);
			resultVO.setCurUserAuthYN(curUserAuthYN);

			// === like 했는지 조회
			boolean curUserLikedYN = commonService.selectItemWorkLiked(userVo.getSeq(), workVO.getSeq(),
					ItemType.PROJECT_WORK);
			resultVO.setCurUserLikedYN(curUserLikedYN);
		}

		resultMap.put(RstConst.P_NAME, resultVO);
		return resultMap;
	}

	/**
	 * 프로젝트 작품 조회: 관련된 상세정보도 함께 조회
	 * 
	 * @param seq
	 * @return
	 */
	private ProjectWorkVO selectProjectWorkWhole(String seq) {
		ProjectWorkVO resultVO = dao.selectProjectWork(seq);
		List<ProjectWorkVerVO> verList = dao.selectProjectWorkVerList(seq);
		resultVO.setProjectWorkVerList(verList);
		List<ProjectWorkMemberVO> memberList = dao.selectProjectWorkMemberList(seq);
		resultVO.setProjectWorkMemberList(memberList);
		return resultVO;
	}

	/**
	 * 프로젝트 작품 수정 페이지
	 * 
	 * @param workVO
	 * @param request
	 * @return
	 */
	public Map<String, Object> openUpdateProjectWork(ProjectWorkVO workVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ProjectWorkVO resultVO = selectProjectWorkWhole(workVO.getSeq());
		
		// 줄바꿈처리
		CmnUtil.handleHtmlEnterBR2RN(resultVO, "contents");

		resultMap.put(RstConst.P_NAME, resultVO);
		return resultMap;
	}

	/**
	 * <pre>
	 * 프로젝트 작품 수정
	 * 1. 매번 이미지  수정할때마다 버전 0.1씩 증가한다
	 * </pre>
	 * 
	 * @param workVO
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Map<String, Object> updateProjectWork(ProjectWorkVO workVO, MultipartHttpServletRequest request)
			throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 00. 체크:
		MultipartFile ckFile = request.getFile("fileUriFile");
		if(ckFile != null) {
			if (ckFile.getSize() > CheckRule.LIMIT_FILE_SIZE) {
				resultMap.put(RstConst.P_NAME, RstConst.V_FILE_SIZE);
				resultMap.put("fileName", ckFile.getOriginalFilename());
				return resultMap;
			}
		}
		
		// 줄바꿈처리
		CmnUtil.handleHtmlEnterRN2BR(workVO, "contents");
		
		//0.
		// === 썸네일 이미지 처리
		String fileUploadDbPathThumb = CmnUtil.handleFileUpload(request, "fileUriFileThumb", FileUploadDomain.PROJECT_WORK_FILE);
		workVO.setThumbUri(fileUploadDbPathThumb);
		
		if(!StringUtils.isEmpty(fileUploadDbPathThumb)) {
			/*
			 * 디자인 프로젝트 주제 목록에서 보여지는 Thumbnail 과
			 */
			String fileUploadDirThumb = CmnUtil.getFileUploadDir(request, FileUploadDomain.PROJECT_WORK_FILE);
			String fileNameThumb = File.separator + FilenameUtils.getName(fileUploadDbPathThumb);
			ThumbnailManager.saveThumbProjectWorkSmall(fileUploadDirThumb +  fileNameThumb);
		}
		

		// === 1.work
		// projectSubjectSeq 있어야 한다.
		UserVO user = CmnUtil.getLoginUser(request);
		workVO.setMemberSeq(user.getSeq());
		CmnUtil.setCmnDate(workVO);
		dao.updateProjectWork(workVO);

		// === 2.workVer: 이미지 수정할때 버전 0.1씩 증가
		// === 이미지 처리
		String fileUploadDbPath = CmnUtil.handleFileUpload(request, "fileUriFile", FileUploadDomain.PROJECT_WORK_FILE);
		if (!StringUtils.isEmpty(fileUploadDbPath)) {
			/*
			 * 디자인 프로젝트 주제 목록에서 보여지는 Thumbnail 과
			 * 디자인 프로젝트 작업 상세 에서 보여지는 Thumbnail 저장 
			 */
			String fileUploadDir = CmnUtil.getFileUploadDir(request, FileUploadDomain.PROJECT_WORK_FILE);
			String fileName = File.separator + FilenameUtils.getName(fileUploadDbPath);
			if(CmnUtil.isImageFile(fileName)) {
				ThumbnailManager.saveThumbProjectWorkSmall(fileUploadDir + fileName);
				ThumbnailManager.saveThumbProjectWorkLarge(fileUploadDir + fileName);
			}
		}
		
		
		if (!StringUtils.isEmpty(fileUploadDbPath)) {
			String oriFileName = CmnUtil.handleFileUploadGetOriFileName(request, "fileUriFile");
			// 다음 버전 계산
			String lastVer = dao.selectProjectWorkLastVer(workVO.getSeq());
			String nextVer = CmnUtil.getNextVersion(lastVer);
			//
			ProjectWorkVerVO verVO = new ProjectWorkVerVO();
			verVO.setProjectWorkSeq(workVO.getSeq());
			verVO.setFileUri(fileUploadDbPath);
			verVO.setFilename(oriFileName);
			verVO.setVer(nextVer);
			CmnUtil.setCmnDate(verVO);
			dao.insertProjectWorkVer(verVO);

			// 2.1 작품 마지막 ver 변경
			workVO.setLastVer(nextVer);
			dao.updateProjectWorkLastVer(workVO);
		}

		// === 3. workMember
		// 참여자: (생성회원도 포함시킨다).
		Set<String> allEmails = new HashSet<String>();
		allEmails.add(user.getEmail());
		if (!CmnUtil.isEmpty(workVO.getWorkMemberEmails())) {
			for (String item : workVO.getWorkMemberEmails()) {
				allEmails.add(item);
			}
		}
		List<String> seqs = userDao.selectMemberSeqsFromEmails(allEmails);
		dao.deleteProjectWorkMember(workVO.getSeq());
		for (String seq : seqs) {
			insertWorkMember(workVO.getSeq(), seq);
		}

		// 포함된 프로젝트 seq 찾아서 알람 설정
		setProjectFromWork(workVO, user);
		
		// 포함된 프로젝트의 업데이트 날짜 변경
		dao.updateProjectDate(workVO);
		
		
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
		
	}

	/**
	 * <pre>
	 * 프로젝트 작품 삭제 (삭제 플래그로 처리)
	 * </pre>
	 * 
	 * @param workVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteProjectWork(ProjectWorkVO workVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 삭제 flag로 처리:
		// === 2.workVer
		//dao.deleteProjectWorkVer(workVO.getSeq());
		// === 3. workMember
		//dao.deleteProjectWorkMember(workVO.getSeq());
		// === 1.work
		dao.deleteProjectWork(workVO.getSeq()); 

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 프로젝트 주제,작품 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<ProjectSubjectVO> selectProjectSubjectWholeList(Map<String, Object> paramMap) {

		// 1. 주제 조회
		List<ProjectSubjectVO> subjectList = dao.selectProjectSubjectList(paramMap);

		// 2. 작품 조회
		if (!CmnUtil.isEmpty(subjectList)) {
			for (ProjectSubjectVO subject : subjectList) {
				Map<String, Object> subParamMap = new HashMap<>();
				
				subParamMap.put("subjectSeq", subject.getSeq());
				subParamMap.put("logonUserSeq", paramMap.get("logonUserSeq"));
				
				List<ProjectWorkVO> workList = dao.selectProjectWorkList(subParamMap);
				subject.setProjectWorkList(workList);
			}
		}

		return subjectList;
	}

	/**
	 * <pre>
	 * 프로젝트 작품 퍼가기
	 * 1. 선택된 주제(지금은 같은 프로젝트내만)에 작품을 등록한다. 
	 * 2. 버전은 퍼갈 작품버전과 같다. 퍼갈 버전은 지금 보는 버전.
	 * 3. 멤버는 생성회원만 있음
	 * </pre>
	 * 
	 * @param workVO:
	 *            seq: 퍼갈 workSeq
	 * @param workVO:
	 *            fromVerSeq: 퍼갈 버전seq
	 * @param workVO:
	 *            toSubjectSeq: 목적 주제 seq
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> shareProjectWork(ProjectWorkVO paramWorkVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 퍼갈 작품 seq
		String fromWorkSeq = paramWorkVO.getSeq();
		// 퍼갈 버전seq
		String fromVerSeq = paramWorkVO.getFromVerSeq();
		// 목적 주제 seq
		String toSubjectSeq = paramWorkVO.getToSubjectSeq();

		// === 0 parameter 처리
		// 퍼갈 작품 조회
		ProjectWorkVO fromWorkVO = this.selectProjectWorkWhole(fromWorkSeq);
		// 퍼갈 작품 버전 조회:
		ProjectWorkVerVO fromWorkVerVO = null;
		for (ProjectWorkVerVO item : fromWorkVO.getProjectWorkVerList()) {
			if (fromVerSeq.equals(item.getSeq())) {
				fromWorkVerVO = item;
				break;
			}
		}

		// === 1.work
		UserVO user = CmnUtil.getLoginUser(request);
		ProjectWorkVO workVO = new ProjectWorkVO();
		workVO.setTitle(fromWorkVO.getTitle());
		workVO.setContents(fromWorkVO.getContents());
		workVO.setProjectSubjectSeq(toSubjectSeq);
		workVO.setMemberSeq(user.getSeq());
		workVO.setReferProjectWorkSeq(fromWorkSeq);
		workVO.setThumbUri(fromWorkVO.getThumbUri());
		CmnUtil.setCmnDate(workVO);

		dao.insertProjectWork(workVO);

		// === 2.workVer
		ProjectWorkVerVO verVO = new ProjectWorkVerVO();
		verVO.setProjectWorkSeq(workVO.getSeq());
		verVO.setFileUri(fromWorkVerVO.getFileUri());
		verVO.setFilename(fromWorkVerVO.getFilename());
		verVO.setVer(fromWorkVerVO.getVer());
		CmnUtil.setCmnDate(verVO);

		dao.insertProjectWorkVer(verVO);

		// 2.1 작품 마지막 ver 변경
		workVO.setLastVer(fromWorkVerVO.getVer());
		dao.updateProjectWorkLastVer(workVO);

		// === 3. workMember
		// 참여자: (생성회원도 포함시킨다).
		insertWorkMember(workVO.getSeq(), user.getSeq());

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * <pre>
	 * 프로젝트 작품 퍼가기 팝업: 주제 목록 조회 
	 * (자기가 포함된 주제는 빼고)
	 * </pre>
	 * 
	 * @param workVOselectShareProjectSubjectList
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectShareProjectSubjectList(ProjectWorkVO workVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<ProjectSubjectVO> subjectList = dao.selectShareProjectSubjectList(workVO.getSeq());
		resultMap.put("subjectList", subjectList);

		return resultMap;
	}

	/**
	 * 그룹내의 프로젝트 목록 데이터 조회
	 * 
	 * @param memberSeq
	 * @param groupVO
	 * @return
	 */
	public List<ProjectVO> selectMyGroupProjectList(int memberSeq, ProjectGroupVO groupVO) {
		groupVO.setMemberSeq(memberSeq);
		return dao.selectMyGroupProjectList(groupVO);
	}

	/**
	 * 그룹 등록
	 * 
	 * @param memberSeq
	 * @param groupVO
	 * @return
	 */
	@Transactional
	public ProjectGroupVO insertMyGroup(int memberSeq, ProjectGroupVO groupVO) {
		String currentDate = Day.getYYYYMMddHHmm();
		groupVO.setMemberSeq(memberSeq);
		groupVO.setRegisterTime(currentDate);
		groupVO.setUpdateTime(currentDate);

		dao.insertMyGroup(groupVO);
		return groupVO;
	}

	/**
	 * 그룹 삭제 
	 * 
	 * @param memberSeq
	 * @param groupVO
	 */
	@Transactional
	public void deleteMyGroup(int memberSeq, ProjectGroupVO groupVO) {
		groupVO.setMemberSeq(memberSeq);
		List<ProjectVO> List = dao.selectMyGroupProjectList(groupVO);
		// 각 프로젝트가 속한 그룹 개수 미리 가져오기
		for (ProjectVO pro : List){
			int Count = dao.selectGroupforProjectCount(pro);
			pro.setGroupCount(Count);
		}
		dao.deleteMyGroup(groupVO);
		dao.deleteProjectGroupRequestByGroupSeq(String.valueOf(groupVO.getSeq())); // 그룹
																					// 신청도
																					// 지워줌
		// 그룹 삭제 시 해당 그룹 안에 있는 프로젝트들 삭제 (1개의 그룹에만 속했을 경우) 
		for (ProjectVO pj : List){
			int Count = pj.getGroupCount();
			if (Count == 1){
				dao.deleteProject(pj);	
			}
		}
	}

	/**
	 * 나의 프로젝트 조회
	 * 
	 * @param memberSeq
	 * @param projectVO
	 * @return
	 */
	public List<ProjectVO> selectMyProjectList(int memberSeq, ProjectVO projectVO) {
		projectVO.setOwnerSeq(memberSeq);
		return dao.selectMyProjectList(projectVO);
	}

	/**
	 * 프로젝트를 그룹에 등록
	 * 
	 * @param memberSeq
	 * @param groupSeq
	 * @param projectSeqs
	 */
	@Transactional
	public void insertGroupProject(String memberSeq, String groupSeq, String[] projectSeqs) {
		int iMemberSeq = Integer.parseInt(memberSeq);
		int iGroupSeq = Integer.parseInt(groupSeq);
		int myGroupCount = dao.selectMyGroupCount(iMemberSeq, iGroupSeq);

		/* 로그인한 회원의 그룹인지 확인 */
		if (myGroupCount > 0 && projectSeqs != null && projectSeqs.length > 0) {
			int index = 0;
			int[] iProjectSeqs = new int[projectSeqs.length];
			for (String aProjectSeq : projectSeqs) {
				iProjectSeqs[index] = (Integer.parseInt(aProjectSeq));
				index++;
			}

			dao.insertMyGroupProject(iGroupSeq, iProjectSeqs, Day.getYYYYMMddHHmm());

		}
	}

	/**
	 * 프로젝트를 그룹에서 제외
	 * 
	 * @param memberSeq
	 * @param groupSeq
	 * @param projectSeq
	 */
	@Transactional
	public void deleteGroupProject(String memberSeq, String groupSeq, String projectSeq) {
		int iMemberSeq = Integer.parseInt(memberSeq);
		int iGroupSeq = Integer.parseInt(groupSeq);
		int myGroupCount = dao.selectMyGroupCount(iMemberSeq, iGroupSeq);

		/* 로그인한 회원의 그룹인지 확인 */
		if (myGroupCount > 0) {
			dao.deleteMyGroupProject(iGroupSeq, Integer.parseInt(projectSeq));
			dao.deleteProjectGroupRequest(groupSeq, projectSeq); // 그룹 신청도 모두 지움
		}
	}

	/**
	 * 프로젝트 조회
	 * 
	 * @param type: 주제,작품
	 * @param seq
	 * @return
	 */
	public ProjectVO selectProjectByTypeAndSeq(String type, String seq) {
		return dao.selectProjectByTypeAndSeq(type, seq);
	}

	// ================================= 그룹 =================================
	/**
	 * 그룹에 신청한 프로젝트 목록 조회
	 * 
	 * @param reqVO
	 *            schProjectSeq
	 * @return
	 */
	public Map<String, Object> selectProjectGroupRequestInfo(SearchGroupVO reqVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<ProjectGroupReqVO> list = dao.selectProjectGroupRequestInfo(reqVO);
		resultMap.put(RstConst.P_NAME, list);

		return resultMap;
	}

	/**
	 * 그룹 목록 조회 (이름으로 정렬)
	 * 
	 * @param reqVO
	 *            schGroupName, schProjectSeq
	 * @return
	 */
	public Map<String, Object> selectProjectGroupListByName(SearchGroupVO reqVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<ProjectGroupReqVO> list = dao.selectProjectGroupListByName(reqVO);
		resultMap.put(RstConst.P_NAME, list);

		return resultMap;
	}

	/**
	 * 프로젝트를 그룹에 신청
	 * 
	 * @param reqVO
	 *            projectSeq,projectGroupSeq
	 * @param loginUserVO
	 * @return
	 */
	@Transactional
	public Map<String, Object> insertProjectGroupRequest(ProjectGroupReqVO reqVO, UserVO loginUserVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 0. 회원 체크
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NEED_LOGIN);
			return resultMap;
		}

		// 1. 그룹에 있는지 체크
		int checkGroupContain = dao.checkProjExistInGroup(reqVO);
		if (checkGroupContain > 0) {
			resultMap.put(RstConst.P_NAME, RstConst.V_GROUP_CONTAIN);
			return resultMap;
		}
		// 2. 신청중인지 체크
		int checkGroupRequested = dao.checkProjAlreadyRequested(reqVO);
		if (checkGroupRequested > 0) {
			resultMap.put(RstConst.P_NAME, RstConst.V_GROUP_REQUESTED);
			return resultMap;
		}

		// 3. 그룹 신청 record: 지우고 다시 신청
		ProjectGroupReqVO existRequest = dao.selectProjectGroupRequestBySeq(reqVO.getProjectGroupSeq(),
				reqVO.getProjectSeq());
		if (existRequest != null) {
			dao.deleteProjectGroupRequest(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq());
		}
		CmnUtil.setCmnDate(reqVO);
		dao.insertProjectGroupRequest(reqVO);
		ProjectGroupReqVO groupVO = dao.selectProjectGroupRequestBySeq(reqVO.getProjectGroupSeq(),
				reqVO.getProjectSeq());

		// ===== 알림 추가 ====
		notifyAlarmChangedForGroup(reqVO, loginUserVO, AlarmGroup.APPLY);
		// ===== ]]알림 추가 ====

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		resultMap.put("groupVO", groupVO); // 추가된 그룹 신청
		return resultMap;
	}

	/**
	 * 프로젝트를 그룹에 신청한것을 취소
	 * 
	 * @param reqVO
	 * @param loginUserVO
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateProjectGroupRequestCancel(ProjectGroupReqVO reqVO, UserVO loginUserVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 0. 회원 체크
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NEED_LOGIN);
			return resultMap;
		}

		// 0. 조회
		ProjectGroupReqVO searchedReq = dao.selectProjectGroupRequestBySeq(reqVO.getProjectGroupSeq(),
				reqVO.getProjectSeq());
		if (searchedReq == null) {
			LOGGER.error(">>> NO ProjectGroupReqVO ");
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
			return resultMap;
		}

		if (ProjectGroupReqStatus.WAITING.equals(searchedReq.getStatus())) {
			// 1. 신청중 취소
			dao.deleteProjectGroupRequest(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq());
		} else if (ProjectGroupReqStatus.APPROVE.equals(searchedReq.getStatus())) {
			// 2. 그롭 탈퇴
			dao.deleteProjectGroup(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq());
			dao.deleteProjectGroupRequest(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq());
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 그룹에 신청한 프로젝트 대기 목록 조회
	 * 
	 * @param reqVO
	 * @return
	 */
	public Map<String, Object> selectProjectGroupRequestWaitingList(SearchGroupVO reqVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<ProjectGroupReqVO> list = dao.selectProjectGroupRequestWaitingList(reqVO);

		resultMap.put(RstConst.P_NAME, list);
		return resultMap;
	}

	/**
	 * 그룹에 신청한 프로젝트를 승인
	 * 
	 * @param reqVO
	 * @param loginUserVO
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateProjectGroupRequestApprove(ProjectGroupReqVO reqVO, UserVO loginUserVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 0. 회원 체크
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NEED_LOGIN);
			return resultMap;
		}

		// 0. 조회
		ProjectGroupReqVO searchedReq = dao.selectProjectGroupRequestBySeq(reqVO.getProjectGroupSeq(),
				reqVO.getProjectSeq());
		if (searchedReq == null) {
			LOGGER.error(">>> NO ProjectGroupReqVO ");
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
			return resultMap;
		}

		if (ProjectGroupReqStatus.WAITING.equals(searchedReq.getStatus())) {
			// 1. 승인
			dao.updateProjectGroupRequestStatus(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq(),
					ProjectGroupReqStatus.APPROVE);
			// 승인되면 t_pgroup_project로 넣어준다.
			dao.deleteProjectGroup(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq());
			dao.insertProjectGroup(reqVO);

			// ===== 알림 추가 ====
			notifyAlarmChangedForGroup(reqVO, loginUserVO, AlarmGroup.APPROVE);
			// ===== ]]알림 추가 ====

		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 그룹에 신청한 프로젝트를 제외
	 * 
	 * @param reqVO
	 * @param loginUserVO
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateProjectGroupRequestReject(ProjectGroupReqVO reqVO, UserVO loginUserVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 0. 회원 체크
		if (loginUserVO == null || StringUtils.isEmpty(loginUserVO.getSeq())) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NEED_LOGIN);
			return resultMap;
		}

		// 0. 조회
		ProjectGroupReqVO searchedReq = dao.selectProjectGroupRequestBySeq(reqVO.getProjectGroupSeq(),
				reqVO.getProjectSeq());
		if (searchedReq == null) {
			LOGGER.error(">>> NO ProjectGroupReqVO ");
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
			return resultMap;
		}

		if (ProjectGroupReqStatus.WAITING.equals(searchedReq.getStatus())) {
			// 1. 제외
			dao.updateProjectGroupRequestStatus(reqVO.getProjectGroupSeq(), reqVO.getProjectSeq(),
					ProjectGroupReqStatus.REJECT);

			// ===== 알림 추가 ====
			notifyAlarmChangedForGroup(reqVO, loginUserVO, AlarmGroup.REJECT);
			// ===== ]]알림 추가 ====

		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 그룹 알림 관련
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 10. 20.
	 */
	public static enum AlarmGroup {
		/** 신청 */
		APPLY,
		/** 승인 */
		APPROVE,
		/** 제외 */
		REJECT;
	}

	/**
	 * 그룹에 관련된 알림정보 등록
	 * 
	 * @param itemVO
	 * @param groupVO
	 * @param userVO
	 * @return
	 */
	private AlarmVO notifyAlarmChangedForGroup(ProjectGroupReqVO groupVO, UserVO userVO, AlarmGroup alarmGroup) {
		ProjectGroupReqVO schGroupVO = dao.selectProjectGroupRequestBySeq(groupVO.getProjectGroupSeq(),
				groupVO.getProjectSeq());
		String alarmContents = "";
		String memberSeq = "";
		if (alarmGroup == AlarmGroup.APPLY) {
			alarmContents = String.format("나의 그룹 \"%s\"에 신청하셨습니다.", schGroupVO.getProjectGroupName());
			memberSeq = schGroupVO.getProjectGroupOwnerSeq();
		} else if (alarmGroup == AlarmGroup.APPROVE) {
			alarmContents = String.format("그룹 \"%s\"에 대한 신청을 승인하셨습니다.", schGroupVO.getProjectGroupName());
			memberSeq = schGroupVO.getProjectOwnerSeq();
		} else if (alarmGroup == AlarmGroup.REJECT) {
			alarmContents = String.format("그룹 \"%s\"에 대한 신청을 거절하셨습니다.", schGroupVO.getProjectGroupName());
			memberSeq = schGroupVO.getProjectOwnerSeq();
		}

		// 
		//
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setMemberSeq(memberSeq);
		alarmVO.setContents(alarmContents);
		alarmVO.setActionUri("");
		alarmVO.setActorSeq(userVO.getSeq());
		CmnUtil.setCmnDate(alarmVO);
		commonDao.insertAlarm(alarmVO);

		// ===== 알림 추가 ====
		websocketHandler.notifyAlarmChanged(alarmVO.getMemberSeq());
		// ===== ]]알림 추가 ====

		return alarmVO;
	}

	/**
	 * 그룹 목록 조회
	 * 
	 * @param searchVO
	 * @param loginUserVO
	 * @return
	 */
	public Map<String, Object> selectGroupList(SearchVO searchVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		int allCount = dao.selectGroupAllCount(searchVO);
		List<ProjectGroupVO> list = dao.selectGroupPagingList(searchVO);

		resultMap.put("all_count", allCount);
		resultMap.put("list", list);
		return resultMap;
	}
	

	/**
	 * 그룹 이름 변경
	 * 
	 * @param memberSeq
	 * @param groupVO
	 */
	@Transactional
	public void modifyMyGroup(int memberSeq, ProjectGroupVO groupVO) {
		groupVO.setMemberSeq(memberSeq);
		dao.modifyMyGroup(groupVO);
	}
	
	
	

	// ================================= ]]그룹 =================================
	
	// =============================[[프로젝트 관련 알람 기능 (18.01.02 추가) =====================
	
	/** 
	 * 프로젝트 work에서 프로젝트 seq 찾아오기
	 * 
	 * @param WorkVO
	 * @param userVO
	 */
	private void setProjectFromWork(ProjectWorkVO WorkVO, UserVO userVO) {
		ProjectVO projectVO = dao.setProjectFromWork(WorkVO);
		notifyAlarmChangedForProject(projectVO, userVO);
	}
	
	/**
	 * 프로젝트 work 업데이트에 대한 알림정보 등록
	 * 
	 * @param itemVO
	 * @param userVO
	 * @return
	 */
	private AlarmVO notifyAlarmChangedForProject(ProjectVO projectVO, UserVO userVO) {
		String alarmContents = "";
		alarmContents = String.format("프로젝트 \"%s\"의 내용을 변경하였습니다.", projectVO.getProjectName());
		List<ProjectMemberVO> memberSeqList = null;
		memberSeqList = selectProjectMemberList(projectVO.getSeq());
		AlarmVO alarmVO = new AlarmVO();
		for (ProjectMemberVO mem : memberSeqList){
			String memberSeq = mem.getSeq();
			
			alarmVO.setMemberSeq(memberSeq);
			alarmVO.setContents(alarmContents);
			alarmVO.setActionUri("");
			alarmVO.setActorSeq(userVO.getSeq());
			CmnUtil.setCmnDate(alarmVO);
			commonDao.insertAlarm(alarmVO);		
		}

		// ===== 알림 추가 ====
		for (ProjectMemberVO member : memberSeqList){
			LOGGER.info(member);
			LOGGER.info(member.getSeq());
			LOGGER.info(memberSeqList);
			String member_seq = member.getSeq();
			websocketHandler.notifyAlarmChanged(member_seq);
		}
		
		// ===== ]]알림 추가 ====

		return alarmVO;
	}

}
