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

import com.opendesign.utils.CmnUtil;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.ProjectGroupReqVO;
import com.opendesign.vo.ProjectGroupVO;
import com.opendesign.vo.ProjectMemberVO;
import com.opendesign.vo.ProjectSubjectVO;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.ProjectWorkMemberVO;
import com.opendesign.vo.ProjectWorkVO;
import com.opendesign.vo.ProjectWorkVerVO;
import com.opendesign.vo.SearchGroupVO;
import com.opendesign.vo.SearchVO;

/**
 * <pre>
 * 디자인 프로젝트 DAO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Repository
public class ProjectDAO {

	private static final String SQL_NS = "ProjectSQL.";

	@Autowired
	SqlSession sqlSession;

	/**
	 * 디자인 프로젝트 총개수 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public int selectProjectCount(Map<String, Object> paramMap) {
		return (Integer) sqlSession.selectOne(SQL_NS + "selectProjectCount", paramMap);
	}

	/**
	 * 디자인 프로젝트 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<ProjectVO> selectProjectList(Map<String, Object> paramMap) {
		return sqlSession.selectList(SQL_NS + "selectProjectList", paramMap);
	}

	/**
	 * 나의 프로젝트 목록 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<ProjectVO> selectMyProjectList(int memberSeq) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectList(SQL_NS + "selectMyProject", paramMap);
	}

	/**
	 * 디자인 프로젝트 조회
	 * 
	 * @param memberSeq
	 * @param projectSeq
	 * @return
	 */
	public ProjectVO selectMyProject(int memberSeq, int projectSeq) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);
		paramMap.put("projectSeq", projectSeq);

		return sqlSession.selectOne(SQL_NS + "selectMyProject", paramMap);
	}

	/**
	 * 디자인 프로젝트 상세정보 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public ProjectVO selectProjectInfo(Map<String, Object> paramMap) {

		return sqlSession.selectOne(SQL_NS + "selectProjectInfo", paramMap);
	}

	/**
	 * 나의 그룹 목록 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<ProjectGroupVO> selectMyProjectGroupList(int memberSeq) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return selectMyProjectGroupList(paramMap);
	}

	/**
	 * 나의 그룹 목록 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	private List<ProjectGroupVO> selectMyProjectGroupList(Map<String, Object> paramMap) {
		return sqlSession.selectList(SQL_NS + "selectMyProjectGroupList", paramMap);
	}

	/**
	 * 나의 그룹 총개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectMyGroupCount(int memberSeq, int groupSeq) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);
		paramMap.put("seq", groupSeq);

		List<ProjectGroupVO> list = selectMyProjectGroupList(paramMap);
		return list != null ? list.size() : 0;
	}

	/**
	 * 회원 검색
	 * 
	 * @param schWord
	 * @return
	 */
	public List<ProjectMemberVO> selectFindMember(String schWord) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schWord", schWord);

		return sqlSession.selectList(SQL_NS + "selectFindMember", paramMap);
	}

	/**
	 * 디자인 프로젝트 등록
	 * 
	 * @param project
	 * @return
	 */
	public ProjectVO insertProject(ProjectVO project) {
		sqlSession.insert(SQL_NS + "insertProject", project);
		return project;
	}

	/**
	 * 디자인 프로젝트 수정
	 * 
	 * @param project
	 * @return
	 */
	public int updateProject(ProjectVO project) {
		return sqlSession.update(SQL_NS + "updateProject", project);
	}

	/**
	 * 디자인 프로젝트 카테고리 등록
	 * 
	 * @param projectSeq
	 * @param categorys
	 * @param registerTime
	 */
	public void insertProjectCategory(int projectSeq, String[] categorys, String registerTime) {
		sqlSession.delete(SQL_NS + "deleteProjectCategory", projectSeq);
		if (categorys != null && categorys.length > 0) {
			for (String aCateGory : categorys) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("projectSeq", projectSeq);
				paramMap.put("categoryCode", aCateGory);
				paramMap.put("registerTime", registerTime);

				sqlSession.insert(SQL_NS + "insertProjectCategory", paramMap);
			}
		}
	}

	/**
	 * 디자인 프로젝트 멤버 등록
	 * 
	 * @param projectSeq
	 * @param emails
	 * @param registerTime
	 */
	public void insertProjectMember(int projectSeq, String[] emails, String registerTime) {
		sqlSession.delete(SQL_NS + "deleteProjectMember", projectSeq);
		if (emails != null && emails.length > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("projectSeq", projectSeq);
			paramMap.put("emails", emails);
			paramMap.put("registerTime", registerTime);

			sqlSession.insert(SQL_NS + "insertProjectMember", paramMap);

		}
	}
	
	/**
	 * 디자인 프로젝트 삭제(삭제 플래그로 처리) 
	 * @param param
	 * @return
	 */
	public int deleteProject(ProjectVO param) {
		return sqlSession.update(SQL_NS + "deleteProject", param);
	}
	
	/**
	 * 디자인 프로젝트 카테고리 목록 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<CategoryVO> selectProjectCategoryList(int projectSeq) {
		return sqlSession.selectList(SQL_NS + "selectProjectCategoryByProjectSeq", projectSeq);
	}

	/**
	 * 디자인 프로젝트 멤버 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<ProjectMemberVO> selectProjectMemberList(int projectSeq) {
		return sqlSession.selectList(SQL_NS + "selectProjectMemberByProjectSeq", projectSeq);
	}

	/**
	 * 프로젝트 주제 등록
	 * 
	 * @param subjectVO
	 */
	public void insertProjectSubject(ProjectSubjectVO subjectVO) {
		sqlSession.insert(SQL_NS + "insertProjectSubject", subjectVO);
	}

	/**
	 * 프로젝트 주제 수정
	 * 
	 * @param subjectVO
	 */
	public void updateProjectSubject(ProjectSubjectVO subjectVO) {
		sqlSession.insert(SQL_NS + "updateProjectSubject", subjectVO);
	}

	/**
	 * 프로젝트 주제 삭제 체크
	 * 
	 * @param subjectVO
	 * @return
	 */
	public int checkDeleteProjectSubject(ProjectSubjectVO subjectVO) {
		return (Integer) sqlSession.selectOne(SQL_NS + "checkDeleteProjectSubject", subjectVO);
	}

	/**
	 * 프로젝트 주제 삭제
	 * 
	 * @param subjectVO
	 */
	public void deleteProjectSubject(ProjectSubjectVO subjectVO) {
		sqlSession.delete(SQL_NS + "deleteProjectSubject", subjectVO);
	}

	/**
	 * 프로젝트 작품 등록
	 * 
	 * @param workVO
	 */
	public void insertProjectWork(ProjectWorkVO workVO) {
		sqlSession.insert(SQL_NS + "insertProjectWork", workVO);
	}

	/**
	 * 프로젝트 작품 버전 등록
	 * 
	 * @param verVO
	 */
	public void insertProjectWorkVer(ProjectWorkVerVO verVO) {
		sqlSession.insert(SQL_NS + "insertProjectWorkVer", verVO);
	}

	/**
	 * 프로젝트 작품 멤버 등록
	 * 
	 * @param memberVO
	 */
	public void insertProjectWorkMember(ProjectWorkMemberVO memberVO) {
		sqlSession.insert(SQL_NS + "insertProjectWorkMember", memberVO);
	}

	/**
	 * 프로젝트 작품 마지막 버전 조회
	 * 
	 * @param workVO
	 */
	public String selectProjectWorkLastVer(String workSeq) {
		ProjectWorkVO workVO = new ProjectWorkVO();
		workVO.setSeq(workSeq);
		ProjectWorkVO result = sqlSession.selectOne(SQL_NS + "selectProjectWorkLastVer", workVO);
		return (result == null) ? null : result.getLastVer();
	}

	/**
	 * 프로젝트 작품 마지막 버전 수정
	 * 
	 * @param workVO
	 */
	public void updateProjectWorkLastVer(ProjectWorkVO workVO) {
		sqlSession.update(SQL_NS + "updateProjectWorkLastVer", workVO);
	}

	/**
	 * 프로젝트 작품 상세 조회
	 * 
	 * @param workSeq
	 * @return
	 */
	public ProjectWorkVO selectProjectWork(String seq) {
		ProjectWorkVO param = new ProjectWorkVO();
		param.setSeq(seq);
		return sqlSession.selectOne(SQL_NS + "selectProjectWork", param);
	}

	/**
	 * 프로젝트 작품 버전 조회
	 * 
	 * @param workSeq
	 * @return
	 */
	public List<ProjectWorkVerVO> selectProjectWorkVerList(String seq) {
		ProjectWorkVO param = new ProjectWorkVO();
		param.setSeq(seq);
		return sqlSession.selectList(SQL_NS + "selectProjectWorkVerList", param);
	}

	/**
	 * 프로젝트 작품 멤버 조회
	 * 
	 * @param seq
	 * @return
	 */
	public List<ProjectWorkMemberVO> selectProjectWorkMemberList(String seq) {
		ProjectWorkVO param = new ProjectWorkVO();
		param.setSeq(seq);
		return sqlSession.selectList(SQL_NS + "selectProjectWorkMemberList", param);
	}

	/**
	 * 프로젝트 작품 수정
	 * 
	 * @param workVO
	 */
	public void updateProjectWork(ProjectWorkVO workVO) {
		sqlSession.update(SQL_NS + "updateProjectWork", workVO);
	}

	/**
	 * 프로젝트 작품 삭제
	 * 
	 * @param seq
	 */
	public void deleteProjectWork(String workSeq) {
		ProjectWorkVO workVO = new ProjectWorkVO();
		workVO.setSeq(workSeq);
		sqlSession.update(SQL_NS + "deleteProjectWork", workVO);
	}

	/**
	 * 프로젝트 작품 버전 삭제
	 * 
	 * @param seq
	 */
	public void deleteProjectWorkVer(String workSeq) {
		ProjectWorkVO workVO = new ProjectWorkVO();
		workVO.setSeq(workSeq);
		sqlSession.delete(SQL_NS + "deleteProjectWorkVer", workVO);
	}

	/**
	 * 프로젝트 작품 멤버 삭제
	 * 
	 * @param workVO
	 */
	public void deleteProjectWorkMember(String workSeq) {
		ProjectWorkVO workVO = new ProjectWorkVO();
		workVO.setSeq(workSeq);
		sqlSession.delete(SQL_NS + "deleteProjectWorkMember", workVO);
	}

	/**
	 * 프로젝트 주제 목록 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<ProjectSubjectVO> selectProjectSubjectList(Map<String, Object> paramMap) {
		ProjectSubjectVO vo = new ProjectSubjectVO();
		vo.setProjectSeq((String)paramMap.get("projectSeq"));
		return sqlSession.selectList(SQL_NS + "selectProjectSubjectList", vo);
	}

	/**
	 * 프로젝트 작품 목록 조회
	 * 
	 * @param seq
	 * @return
	 */
	public List<ProjectWorkVO> selectProjectWorkList(Map<String, Object> paramMap) {
		ProjectWorkVO param = new ProjectWorkVO();
		param.setProjectSubjectSeq((String)paramMap.get("subjectSeq"));
		param.setLogonUserSeq((String)paramMap.get("logonUserSeq"));
		return sqlSession.selectList(SQL_NS + "selectProjectWorkList", param);
	}
	
	/**
	 * 프로젝트 work 업데이트시 전체 프로젝트 수정 날짜도 업데이트 (17.12.19)
	 * 
	 * @param ProjectWorkVO
	 */
	public void updateProjectDate(ProjectWorkVO workVO) {
		sqlSession.insert(SQL_NS + "updateProjectDate", workVO);
	}

	/**
	 * <pre>
	 * 프로젝트 작품 퍼가기 팝업: 주제 목록 조회 
	 * (자기가 포함된 주제는 빼고)
	 * </pre>
	 * 
	 * @param seq
	 * @return
	 */
	public List<ProjectSubjectVO> selectShareProjectSubjectList(String seq) { HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("seq", seq);
		return sqlSession.selectList(SQL_NS + "selectShareProjectSubjectList", param);
	}

	/**
	 * 그룹내의 프로젝트 목록 데이터 조회
	 * 
	 * @param groupVO
	 * @return
	 */
	public List<ProjectVO> selectMyGroupProjectList(ProjectGroupVO groupVO) {
		return sqlSession.selectList(SQL_NS + "selectMyGroupProjectList", groupVO);
	}

	/**
	 * 그룹 등록
	 * 
	 * @param groupVO
	 */
	public void insertMyGroup(ProjectGroupVO groupVO) {
		sqlSession.insert(SQL_NS + "insertMyGroup", groupVO);
	}

	/**
	 * 그룹 삭제 
	 * 
	 * @param groupVO
	 * @return
	 */
	public int deleteMyGroup(ProjectGroupVO groupVO) {
		sqlSession.delete(SQL_NS + "deleteMyGroupProject", groupVO);
		return sqlSession.delete(SQL_NS + "deleteMyGroup", groupVO);
	}

	/**
	 * 나의 프로젝트 조회
	 * 
	 * @param projectVO
	 * @return
	 */
	public List<ProjectVO> selectMyProjectList(ProjectVO projectVO) {
		return sqlSession.selectList(SQL_NS + "selectMyProjectList", projectVO);
	}

	/**
	 * 프로젝트를 그룹에 등록
	 * 
	 * @param groupSeq
	 * @param projectSeqs
	 * @param registerTime
	 */
	public void insertMyGroupProject(int groupSeq, int[] projectSeqs, String registerTime) {
		deleteMyGroupProjectBySeqs(groupSeq, projectSeqs);

		Map<String, Object> dParamMap = new HashMap<String, Object>();
		dParamMap.put("groupSeq", groupSeq);
		dParamMap.put("projectSeqs", projectSeqs);
		dParamMap.put("registerTime", registerTime);
		sqlSession.insert(SQL_NS + "insertMyGroupProject", dParamMap);

	}

	/**
	 * 프로젝트를 그룹에서 제외
	 * 
	 * @param groupSeq
	 * @param projectSeq
	 */
	public void deleteMyGroupProject(int groupSeq, int projectSeq) {
		int[] projectSeqs = { projectSeq };
		deleteMyGroupProjectBySeqs(groupSeq, projectSeqs);
	}

	/**
	 * 프로젝트를 그룹에서 제외
	 * 
	 * @param groupSeq
	 * @param projectSeqs
	 */
	public void deleteMyGroupProjectBySeqs(int groupSeq, int[] projectSeqs) {
		Map<String, Object> dParamMap = new HashMap<String, Object>();
		dParamMap.put("groupSeq", groupSeq);
		dParamMap.put("projectSeqs", projectSeqs);

		sqlSession.delete(SQL_NS + "deleteMyGroupProjectBySeqs", dParamMap);

	}

	/**
	 * 프로젝트 조회
	 * 
	 * @param type: 주제,작품
	 * @param seq
	 * @return
	 */
	public ProjectVO selectProjectByTypeAndSeq(String type, String seq) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("seq", seq);
		return sqlSession.selectOne(SQL_NS + "selectProjectByTypeAndSeq", param);
	}
	
	/**
	 * 프로젝트 work에서 프로젝트 정보 찾아오기
	 * 
	 * @param projectWorkVO
	 */
	public ProjectVO setProjectFromWork(ProjectWorkVO WorkVO) {
		return sqlSession.selectOne(SQL_NS + "setProjectFromWork", WorkVO);
	}

	
	

	// =================== 그룹new =======================
	/**
	 * 그룹에 신청한 프로젝트 목록 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<ProjectGroupReqVO> selectProjectGroupRequestInfo(SearchGroupVO param) {
		return sqlSession.selectList(SQL_NS + "selectProjectGroupRequestInfo", param);
	}

	/**
	 * 그룹 목록 조회 (이름으로 정렬)
	 * 
	 * @param param
	 * @return
	 */
	public List<ProjectGroupReqVO> selectProjectGroupListByName(SearchGroupVO param) {
		return sqlSession.selectList(SQL_NS + "selectProjectGroupListByName", param);
	}

	/**
	 * 프로젝트가 그룹에 있는지 체크
	 * 
	 * @param param
	 * @return
	 */
	public int checkProjExistInGroup(ProjectGroupReqVO param) {
		return sqlSession.selectOne(SQL_NS + "checkProjExistInGroup", param);
	}

	/**
	 * 프로젝트가 그룹에 신청중인지 체크
	 * 
	 * @param param
	 * @return
	 */
	public int checkProjAlreadyRequested(ProjectGroupReqVO param) {
		return sqlSession.selectOne(SQL_NS + "checkProjAlreadyRequested", param);
	}

	/**
	 * 프로젝트를 그룹에 신청 
	 * 
	 * @param param
	 * @return
	 */
	public int insertProjectGroupRequest(ProjectGroupReqVO param) {
		return sqlSession.insert(SQL_NS + "insertProjectGroupRequest", param);
	}

	/**
	 * 프로젝트를 그룹에 신청 현황 조회
	 * 
	 * @param projectGroupSeq
	 * @param projectSeq
	 * @return
	 */
	public ProjectGroupReqVO selectProjectGroupRequestBySeq(String projectGroupSeq, String projectSeq) {
		SearchGroupVO param = new SearchGroupVO();
		param.setSchGroupSeq(projectGroupSeq);
		param.setSchProjectSeq(projectSeq);
		return sqlSession.selectOne(SQL_NS + "selectProjectGroupRequestBySeq", param);
	}

	/**
	 * 프로젝트를 그룹에서 제외
	 * 
	 * @param projectGroupSeq
	 * @param projectSeq
	 * @return
	 */
	public int deleteProjectGroupRequest(String projectGroupSeq, String projectSeq) {
		SearchGroupVO param = new SearchGroupVO();
		param.setSchGroupSeq(projectGroupSeq);
		param.setSchProjectSeq(projectSeq);
		return sqlSession.delete(SQL_NS + "deleteProjectGroupRequest", param);
	}
	
	/**
	 * 그룹 신청 삭제 
	 * 
	 * @param projectGroupSeq
	 * @return
	 */
	public int deleteProjectGroupRequestByGroupSeq(String projectGroupSeq) {
		SearchGroupVO param = new SearchGroupVO();
		param.setSchGroupSeq(projectGroupSeq);
		return sqlSession.delete(SQL_NS + "deleteProjectGroupRequestByGroupSeq", param);
	}

	/**
	 * 그룹 프로젝트 관계 삭제
	 * @param projectGroupSeq
	 * @param projectSeq
	 * @return
	 */
	public int deleteProjectGroup(String projectGroupSeq, String projectSeq) {
		SearchGroupVO param = new SearchGroupVO();
		param.setSchGroupSeq(projectGroupSeq);
		param.setSchProjectSeq(projectSeq);
		return sqlSession.delete(SQL_NS + "deleteProjectGroup", param);
	}

	/**
	 * 그룹에 신청한 프로젝트 대기 목록 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<ProjectGroupReqVO> selectProjectGroupRequestWaitingList(SearchGroupVO param) {
		return sqlSession.selectList(SQL_NS + "selectProjectGroupRequestWaitingList", param);
	}

	/**
	 * 그룹에 신청한 프로젝트 신청상태 수정
	 * 
	 * @param projectGroupSeq
	 * @param projectSeq
	 * @param status
	 * @return
	 */
	public int updateProjectGroupRequestStatus(String projectGroupSeq, String projectSeq, String status) {
		ProjectGroupReqVO param = new ProjectGroupReqVO();
		param.setProjectGroupSeq(projectGroupSeq);
		param.setProjectSeq(projectSeq);
		param.setStatus(status);
		CmnUtil.setCmnDate(param);
		return sqlSession.delete(SQL_NS + "updateProjectGroupRequestStatus", param);
	}

	/**
	 * 그룹 프로젝트 관계 등록
	 * 
	 * @param param
	 * @return
	 */
	public int insertProjectGroup(ProjectGroupReqVO param) {
		CmnUtil.setCmnDate(param);
		return sqlSession.insert(SQL_NS + "insertProjectGroup", param);
	}

	/**
	 * 그룹 목록 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<ProjectGroupVO> selectGroupPagingList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectGroupPagingList", param);
	}

	/**
	 * 그룹 총개수 조회
	 * 
	 * @param param
	 * @return
	 */
	public int selectGroupAllCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectGroupAllCount", param);
	}
	
	/**
	 * 검색창에서 파라미터 받아서 그룹 목록 조회 (작성자 or 제목일때)
	 * 
	 * @param param
	 * @return
	 */
	public List<ProjectGroupVO> selectSearchGroupList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectSearchGroupPagingList", param);
	}

	/**
	 * 검색창에서 파라미터 받아서 그룹 총개수 조회 (작성자 or 제목일때)
	 * 
	 * @param param
	 * @return
	 */
	public int selectSearchGroupCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectSearchGroupAllCount", param);
	}
	
	/**
	 * 그룹 이름 바꾸기
	 * 
	 * @param groupVO
	 * @return
	 */
	public int modifyMyGroup(ProjectGroupVO groupVO) {
		return sqlSession.delete(SQL_NS + "modifyMyGroup", groupVO);
	}

	// =================== ]]그룹new =======================

}
