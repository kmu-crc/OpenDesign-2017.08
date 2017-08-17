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
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opendesign.vo.MemberCategoryVO;
import com.opendesign.vo.MyUserPointVO;
import com.opendesign.vo.MyUserVO;
import com.opendesign.vo.ProjectGroupVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.SidoVO;
import com.opendesign.vo.UserVO;

/**
 * <pre>
 * 회원 DAO
 * </pre>
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Repository
public class UserDAO {

	private static final String SQL_NS = "UserSQL.";

	@Autowired
	SqlSession sqlSession;

	/**
	 * 회원 등록
	 * 
	 * @param userVO
	 */
	public void insertUser(UserVO userVO) {
		sqlSession.insert(SQL_NS + "insertUser", userVO);
	}

	/**
	 * 메일 중복체크
	 * 
	 * @param userVO
	 * @return
	 */
	public int checkEmailDup(UserVO userVO) {
		return (Integer) sqlSession.selectOne(SQL_NS + "checkEmailDup", userVO);
	}

	/**
	 * 닉네임의 중복 체크
	 * 
	 * @param userVO
	 * @return
	 */
	public int checkUnameDup(UserVO userVO) {
		return (Integer) sqlSession.selectOne(SQL_NS + "checkUnameDup", userVO);
	}

	/**
	 * 회원 카테고리 등록
	 * 
	 * @param mcVO2
	 */
	public void insertMemberCategory(MemberCategoryVO vo) {
		sqlSession.insert(SQL_NS + "insertMemberCategory", vo);
	}

	/**
	 * 로그인 체크
	 * 
	 * @param userVO
	 * @return
	 */
	public int loginCheck(UserVO userVO) {
		return (Integer) sqlSession.selectOne(SQL_NS + "loginCheck", userVO);
	}

	/**
	 * 회원 조회 by 이메일
	 * 
	 * @param string
	 * @return
	 */
	public UserVO selectUserByEmail(String email) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("email", email);
		return sqlSession.selectOne(SQL_NS + "selectUserByEmail", pMap);
	}

	/**
	 * 회원 이메일로 seq 조회
	 * 
	 * @param allEmails
	 * @return
	 */
	public List<String> selectMemberSeqsFromEmails(Set<String> allEmails) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("allEmails", allEmails);
		return sqlSession.selectList(SQL_NS + "selectMemberSeqsFromEmails", param);
	}

	/**
	 * 회원 카테고리 조회 by seq
	 * 
	 * @param int
	 * @return
	 */
	public List<MemberCategoryVO> selectUCategoryByseq(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);
		return sqlSession.selectList(SQL_NS + "selectMemberCategory", paramMap);
	}

	/**
	 * 회원의 작품 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<MyUserVO> selectWorkByseq(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);
		return sqlSession.selectList(SQL_NS + "selectWorkList", paramMap);
	}

	/**
	 * 회원의 작품 개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectCntWork(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return (Integer) sqlSession.selectOne(SQL_NS + "selectCntWork", paramMap);
	}

	/**
	 * 회원의 좋아요 개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectCntLike(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		int rVal1, rVal2, rVal3;
		rVal1 = (Integer) sqlSession.selectOne(SQL_NS + "selectCntLikeProject", paramMap);
		rVal2 = (Integer) sqlSession.selectOne(SQL_NS + "selectCntLikeDesign", paramMap);
		rVal3 = rVal1 + rVal2;
		return rVal3;
	}

	/**
	 * 회원의 메시지 개수 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public int selectCntTalk(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return (Integer) sqlSession.selectOne(SQL_NS + "selectCntTalk", paramMap);
	}

	/**
	 * 회원의 포인트 조회
	 * 
	 * @param int
	 * @return
	 */
	public int selectMypointCnt(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return (Integer) sqlSession.selectOne(SQL_NS + "selectCntPoint", paramMap);
	}

	/**
	 * 회원의 작품 개수 조회
	 * 
	 * @param int
	 * @return
	 */
	public int selectMyProductCnt(int memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return (Integer) sqlSession.selectOne(SQL_NS + "selectCntMyProduct", paramMap);
	}

	/**
	 * <pre>
	 * 마이페이지: 나의 프로젝트 목록 조회
	 * 내가 포함된 프로젝트 목록을 조회한다.
	 * </pre>
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMyProjectList(String memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectList(SQL_NS + "selectProjectList", paramMap);
	}
	
	/**
	 * <pre>
	 * 마이페이지: 나의 그룹 현황 조회 
	 * 내가 포함된 그룹 목록을 조회한다.
	 * </pre>
	 * 
	 * @param schMemberSeq
	 * @return
	 */
	public List<ProjectGroupVO> selectMyGroupList(String schMemberSeq) {
		SearchVO param = new SearchVO();
		param.setSchMemberSeq(schMemberSeq);
		return sqlSession.selectList(SQL_NS + "selectMyGroupList", param);
	}

	/**
	 * 마이페이지: 최근등록한 제작물 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMyWorkList(String memberSeq) {
		/*
		 * MyUserVO vo = new MyUserVO(); vo.setWseq(wseq);
		 */
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectList(SQL_NS + "selectMyWorkList", paramMap);
	}

	/**
	 * 마이페이지: 나의 관심 디자인(작품) 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMylikeList(String memberSeq) {
		/*
		 * MyUserVO vo = new MyUserVO(); vo.setWseq(wseq);
		 */
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectList(SQL_NS + "selectMyLikelist", paramMap);
	}

	/**
	 * 나의 디자인(작품) 좋아요 개수 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public String selectMyWorkLike(String Wseq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("Wseq", Wseq);

		return sqlSession.selectOne(SQL_NS + "selectMyWorkLike", paramMap);
	}

	/**
	 * 마이페이지: 내가 구입한 디자인(작품) 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserVO> selectMyorderList(String memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectList(SQL_NS + "selectMyOrderlist", paramMap);
	}

	/**
	 * 마이페이지: 나의 포인트 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<MyUserPointVO> selectMyPointList(String memberSeq, String fromDate, String toDate) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("memberSeq", memberSeq);
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);

		return sqlSession.selectList(SQL_NS + "selectMyPointlist", paramMap);
	}

	/**
	 * 마이페이지: 나의 회원정보 조회
	 * 
	 * @param projectSeq
	 * @return
	 */
	public List<UserVO> selectMyInfoList(String memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectList(SQL_NS + "selectMyInfoList", paramMap);
	}
	
	/**
	 * 회원 타입 조회
	 * 
	 * @param memberSeq
	 * @return
	 */
	public String selectMymemberType(String memberSeq) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberSeq", memberSeq);

		return sqlSession.selectOne(SQL_NS + "selectMymemberType", paramMap);
	}

	/**
	 * 회원정보 수정
	 * 
	 * @param userVO
	 */
	public void updateUser(UserVO userVO) {
		sqlSession.update(SQL_NS + "updateUser", userVO);
	}

	/**
	 * 회원 카테고리 삭제
	 * 
	 * @param userVO
	 */
	public void deleteMemberCategory(UserVO userVO) {
		sqlSession.delete(SQL_NS + "deleteMemberCategory", userVO);
	}

	/**
	 * 회원정보 수정시 메일 중복체크
	 * 
	 * @param userVO
	 * @return
	 */
	public int checkEmailDup2(UserVO userVO) {
		return (Integer) sqlSession.selectOne(SQL_NS + "checkEmailDup2", userVO);
	}

	/**
	 * 회원조회 by seq
	 * 
	 * @param seq
	 * @return
	 */
	public UserVO selectUserBySeq(String schSeq) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("schSeq", schSeq);
		return sqlSession.selectOne(SQL_NS + "selectUserBySeq", param);
	}

	/**
	 * 포인트 변경
	 * 
	 * @param seq
	 * @param point
	 */
	public void updateUserPoint(String seq, int point) {
		UserVO param = new UserVO();
		param.setSeq(seq);
		param.setPoint(String.valueOf(point));
		sqlSession.update(SQL_NS + "updateUserPoint", param);
	}

	/**
	 * 패스월드 변경
	 * 
	 * @param userSeq
	 * @param encryptPassword
	 */
	public void updateUserPassword(String userSeq, String encryptPassword) {
		UserVO param = new UserVO();
		param.setSeq(userSeq);
		param.setPasswd(encryptPassword);
		sqlSession.update(SQL_NS + "updateUserPassword", param);
	}
	
	/**
	 * 패스월드 체크
	 * 
	 * @param userVO
	 * @return
	 */
	public int passwdCheck(UserVO userVO) {
		return (Integer) sqlSession.selectOne(SQL_NS + "passwdCheck", userVO);
	}
	
	/**
	 * 시/도 목록 가져오기
	 * @return
	 */
	public List<SidoVO> selectSidoList() {
		return sqlSession.selectList(SQL_NS + "selectSidoList");
	}
	
	/**
	 * 회원 시/도 명 가져오기
	 * 
	 * @param sidoSeq
	 * @return
	 */
	public String selectMemberSido(int sidoSeq) {
		return sqlSession.selectOne(SQL_NS + "selectMemberSido", sidoSeq);
	}
}
