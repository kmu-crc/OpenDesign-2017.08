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

import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.DesignPreviewImageVO;
import com.opendesign.vo.DesignWorkFileVO;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.DesignerVO;
import com.opendesign.vo.MemberCategoryVO;
import com.opendesign.vo.OrderVO;
import com.opendesign.vo.PointHistoryVO;
import com.opendesign.vo.RequestBoardCateVO;
import com.opendesign.vo.RequestBoardCmmtVO;
import com.opendesign.vo.RequestBoardFileVO;
import com.opendesign.vo.RequestBoardVO;
import com.opendesign.vo.SearchVO;

/**
 * <pre>
 * 디자이너/제작자 DAO
 * </pre>
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Repository
public class DesignerDAO {

	@Autowired
	SqlSession sqlSession;

	private static final String SQL_NS = "DesignerSQL.";

	/**
	 * 디자이너/제작자 목록 데이터: 총개수
	 * 
	 * @param searchVO
	 * @return
	 */
	public int selectDesignerAllCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectDesignerAllCount", param);
	}

	/**
	 * 디자이너/제작자 목록 데이터
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<DesignerVO> selectDesignerList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectDesignerList", param);
	}

	/**
	 * 디자인 카테고리
	 * 
	 * @param memberSeq
	 * @return
	 */
	public List<MemberCategoryVO> selectMemberCategoryList(String memberSeq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("memberSeq", memberSeq);
		return sqlSession.selectList(SQL_NS + "selectMemberCategoryList", param);
	}

	/**
	 * 작품 목록
	 * 
	 * @param seq
	 * @return
	 */
	public List<DesignWorkVO> selectDesignWorkList(String memberSeq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("memberSeq", memberSeq);
		return sqlSession.selectList(SQL_NS + "selectDesignWorkList", param);
	}

	/**
	 * 디자이너 조회
	 * 
	 * @param seq
	 * @return
	 */
	public DesignerVO selectDesignerBySeq(String seq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(SQL_NS + "selectDesignerBySeq", param);
	}

	/**
	 * 작품 카테고리
	 * 
	 * @param seq
	 * @return
	 */
	public List<CategoryVO> selectDesignWorkCategoryList(String designWorkSeq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("designWorkSeq", designWorkSeq);
		return sqlSession.selectList(SQL_NS + "selectDesignWorkCategoryList", param);
	}

	/**
	 * 작품 조회
	 * 
	 * @param seq
	 * @return
	 */
	public DesignWorkVO selectDesignWorkBySeq(String seq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(SQL_NS + "selectDesignWorkBySeq", param);
	}

	/**
	 * 디자인 작품: 상세 이미지
	 * 
	 * @param workSeq
	 * @return
	 */
	public List<DesignPreviewImageVO> selectDesignPreviewImageList(String workSeq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("designWorkSeq", workSeq);
		return sqlSession.selectList(SQL_NS + "selectDesignPreviewImageList", param);
	}

	/**
	 * 디자인 작품: 오픈 소스
	 * 
	 * @param workSeq
	 * @return
	 */
	public List<DesignWorkFileVO> selectDesignWorkFileList(String workSeq) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("designWorkSeq", workSeq);
		return sqlSession.selectList(SQL_NS + "selectDesignWorkFileList", param);
	}

	/**
	 * 작품 구매
	 * 
	 * @param orderVO
	 */
	public void insertOrder(OrderVO param) {
		sqlSession.insert(SQL_NS + "insertOrder", param);
	}

	/**
	 * 포인트 가감이력
	 * 
	 * @param buyHistVO
	 */
	public void insertPointHistory(PointHistoryVO param) {
		sqlSession.insert(SQL_NS + "insertPointHistory", param);
	}

	/**
	 * 의뢰 게시글 개수
	 * 
	 * @param searchVO
	 * @return
	 */
	public int selectDesignRequestBoardAllCount(SearchVO searchVO) {
		return sqlSession.selectOne(SQL_NS + "selectDesignRequestBoardAllCount", searchVO);
	}

	/**
	 * 의뢰 게시글 목록 조회
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<RequestBoardVO> selectDesignRequestBoardList(SearchVO searchVO) {
		return sqlSession.selectList(SQL_NS + "selectDesignRequestBoardList", searchVO);
	}

	/**
	 * 게시글 카테고리
	 * 
	 * @param seq
	 * @return
	 */
	public List<CategoryVO> selectRequestBoardCateList(String seq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(seq);
		return sqlSession.selectList(SQL_NS + "selectRequestBoardCateList", param);
	}

	/**
	 * 게시글 이미지
	 * 
	 * @param seq
	 * @return
	 */
	public List<RequestBoardFileVO> selectRequestBoardFileList(String seq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(seq);
		return sqlSession.selectList(SQL_NS + "selectRequestBoardFileList", param);
	}

	/**
	 * 게시글 댓글
	 * 
	 * @param seq
	 * @return
	 */
	public List<RequestBoardCmmtVO> selectRequestBoardCmmtList(String seq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(seq);
		return sqlSession.selectList(SQL_NS + "selectRequestBoardCmmtList", param);
	}

	/**
	 * 게시글 등록
	 * 
	 * @param boardVO
	 */
	public void insertRequestBoard(RequestBoardVO param) {
		sqlSession.insert(SQL_NS + "insertRequestBoard", param);
	}

	/**
	 * 게시글 카테고리 등록
	 * 
	 * @param cItem
	 */
	public void insertRequestBoardCategory(RequestBoardCateVO param) {
		sqlSession.insert(SQL_NS + "insertRequestBoardCategory", param);
	}

	/**
	 * 게시글 이미지 등록
	 * 
	 * @param file
	 */
	public void insertRequestBoardFile(RequestBoardFileVO param) {
		sqlSession.insert(SQL_NS + "insertRequestBoardFile", param);
	}

	/**
	 * 의뢰 게시판 조회
	 * 
	 * @param boardSeq
	 * @return
	 */
	public RequestBoardVO selectRequestBoardBySeq(String boardSeq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(boardSeq);
		return sqlSession.selectOne(SQL_NS + "selectRequestBoardBySeq", param);
	}

	/**
	 * 게시글 수정
	 * 
	 * @param boardVO
	 */
	public void updateRequestBoard(RequestBoardVO param) {
		sqlSession.update(SQL_NS + "updateRequestBoard", param);
	}

	/**
	 * 게시판 카테고리 삭제
	 * 
	 * @param boardSeq
	 */
	public void deleteRequestBoardCategoryByBoardSeq(String boardSeq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(boardSeq);
		sqlSession.delete(SQL_NS + "deleteRequestBoardCategoryByBoardSeq", param);
	}

	/**
	 * 게시판 이미지 삭제
	 * 
	 * @param boardSeq
	 */
	public void deleteRequestBoardFileByBoardSeq(String boardSeq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(boardSeq);
		sqlSession.delete(SQL_NS + "deleteRequestBoardFileByBoardSeq", param);
	}

	/**
	 * 디자인 의뢰 삭제
	 * 
	 * @param boardSeq
	 */
	public void deleteRequestBoard(String boardSeq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(boardSeq);
		sqlSession.delete(SQL_NS + "deleteRequestBoard", param);
	}

	/**
	 * 이달의 Best 디자이너/제작자 목록 조회 
	 * @param paramMap
	 * @return
	 */
	public List<DesignerVO> selectBestDesignerList(Map<String, Object> paramMap) {
		return sqlSession.selectList(SQL_NS + "selectBestDesignerList", paramMap);
	}

	/**
	 * 회원 작품 구매 여부 조회
	 * @param workSeq
	 * @param memberSeq
	 * @return
	 */
	public boolean hasPurchasedWork(String workSeq, String memberSeq) {
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("workSeq", workSeq);
		paramMap.put("memberSeq", memberSeq);
		int count = (Integer)sqlSession.selectOne(SQL_NS + "selectPurchasedWorkCount", paramMap);
		
		return  count > 0;
	}

	/**
	 * 현재 로그온 사용자가 해당 디자이너에 대한 좋아요 개수(디자인 작품 기준) 조회
	 * @param paramMap
	 * @return
	 */
	public int selectDesignerLikesByLogonUser(Map<String, Object> paramMap) {
		return sqlSession.selectOne(SQL_NS + "selectDesignerLikesByLogonUser", paramMap);
	}
	
	/**
	 * 현재 로그온 사용자가 해당 디자인 작품 대한 좋아요 개수 조회
	 * @param paramMap
	 * @return
	 */
	public int selectDesignWorkLikesByLogonUser(Map<String, Object> paramMap) {
		return sqlSession.selectOne(SQL_NS + "selectDesignWorkLikesByLogonUser", paramMap);
	}

}
