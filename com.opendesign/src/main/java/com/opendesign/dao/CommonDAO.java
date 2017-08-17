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
import com.opendesign.vo.AlarmVO;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.ItemCmmtVO;
import com.opendesign.vo.ItemLikeVO;
import com.opendesign.vo.ItemViewCntVO;
import com.opendesign.vo.ItemWorkVO;
import com.opendesign.vo.MessageVO;
import com.opendesign.vo.SearchVO;

@Repository
public class CommonDAO {

	private static final String SQL_NS = "CommonSQL.";

	@Autowired
	SqlSession sqlSession;

	/**
	 * 카테고리조회
	 * 
	 * @param userVO
	 * @return
	 */
	public List<CategoryVO> selectCategoryList(Map<String, Object> pMap) {
		return sqlSession.selectList(SQL_NS + "selectCategoryList", pMap);
	}

	// ========================= 좋아요 =========================================

	/**
	 * 좋아요
	 * 
	 * @param seq
	 * @param seq2
	 * @return
	 */
	public void likeItemWork(String memberSeq, String itemSeq, String itemType) {
		ItemLikeVO param = new ItemLikeVO();
		param.setItemType(itemType);
		param.setMemberSeq(memberSeq);
		param.setItemSeq(itemSeq);
		CmnUtil.setCmnDate(param);
		sqlSession.insert(SQL_NS + "likeItemWork", param);
	}

	/**
	 * 좋아요 취소
	 * 
	 * @param seq
	 * @param seq2
	 * @return
	 */
	public void unlikeItemWork(String memberSeq, String itemSeq, String itemType) {
		ItemLikeVO param = new ItemLikeVO();
		param.setItemType(itemType);
		param.setMemberSeq(memberSeq);
		param.setItemSeq(itemSeq);

		sqlSession.insert(SQL_NS + "unlikeItemWork", param);
	}

	/**
	 * 좋아요
	 * 
	 * @param param
	 */
	public void likeItemWork(ItemLikeVO param) {
		sqlSession.insert(SQL_NS + "likeItemWork", param);
	}

	/**
	 * 작품의 좋아요 여부 조회
	 * 
	 * @param memberSeq
	 * @param itemSeq
	 * @return
	 */
	public int selectItemWorkLiked(String memberSeq, String itemSeq, String itemType) {
		ItemLikeVO param = new ItemLikeVO();
		param.setItemType(itemType);
		param.setMemberSeq(memberSeq);
		param.setItemSeq(itemSeq);
		return sqlSession.selectOne(SQL_NS + "selectItemWorkLiked", param);
	}

	// ========================= ]]좋아요 =========================================

	// ========================= 댓글 =========================================
	/**
	 * 댓글 등록
	 * 
	 * @param cmmtVO
	 */
	public void insertItemCmmt(ItemCmmtVO cmmtVO) {
		sqlSession.insert(SQL_NS + "insertItemCmmt", cmmtVO);
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * @param cmmtVO
	 */
	public void deleteItemCmmt(ItemCmmtVO cmmtVO) {
		sqlSession.delete(SQL_NS + "deleteItemCmmt", cmmtVO);
	}

	/**
	 * 댓글 목록 조회
	 * 
	 * @param itemCmmtType
	 * 
	 * @param seq
	 * @return
	 */
	public List<ItemCmmtVO> selectItemCmmtList(String itemSeq, String itemCmmtType) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("itemSeq", itemSeq);
		param.put("itemCmmtType", itemCmmtType);
		return sqlSession.selectList(SQL_NS + "selectItemCmmtList", param);
	}

	/**
	 * 댓글 총개수 조회
	 * 
	 * @param param
	 * @return
	 */
	public int selectItemCmmtAllCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectItemCmmtAllCount", param);
	}

	/**
	 * 댓글 목록 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<ItemCmmtVO> selectItemCmmtPagingList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectItemCmmtPagingList", param);
	}

	// ========================= ]]댓글 =========================================

	// ========================= 조회수 =========================================

	/**
	 * 디자인(작품) 조회수 증가
	 * 
	 * @param viewCntVO
	 */
	public void updateItemViewCnt(ItemViewCntVO param) {
		sqlSession.update(SQL_NS + "updateItemViewCnt", param);
	}

	// ========================= ]]조회수 =========================================

	// ========================= 알림 =========================================
	/**
	 * 최신알림 개수 조회
	 * 
	 * @param userSeq
	 * @return
	 */
	public int selectLatestAlarmCount(String userSeq) {
		SearchVO param = new SearchVO();
		param.setSchSeq(userSeq);
		return sqlSession.selectOne(SQL_NS + "selectLatestAlarmCount", param);
	}

	
	/**
	 * 알림 총개수 조회
	 * 
	 * @param searchVO
	 * @return
	 */
	public int selectLatestAlarmAllCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectLatestAlarmAllCount", param);
	}
	
	/**
	 * 알림 목록 조회
	 * 
	 * @param seq
	 * @return
	 */
	public List<AlarmVO> selectLatestAlarmList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectLatestAlarmList", param);
	}
	

	/**
	 * 최신알림 이미 읽었음으로 수정
	 * 
	 * @param seq
	 * @return
	 */
	public void updateLatestAlarmRead(String memberSeq) {
		AlarmVO param = new AlarmVO();
		param.setMemberSeq(memberSeq);
		CmnUtil.setCmnDate(param);
		sqlSession.update(SQL_NS + "updateLatestAlarmRead", param);
	}

	/**
	 * 알림 등록
	 * 
	 * @param param
	 */
	public void insertAlarm(AlarmVO param) {
		sqlSession.update(SQL_NS + "insertAlarm", param);
	}

	/**
	 * 작품 정보 조회
	 * 
	 * @param itemSeq
	 * @param itemType
	 * @return
	 */
	public ItemWorkVO selectItemWorkBySeq(String itemSeq, String itemType) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("itemSeq", itemSeq);
		param.put("itemType", itemType);
		return sqlSession.selectOne(SQL_NS + "selectItemWorkBySeq", param);
	}

	/**
	 * 댓글 정보 조회
	 * 
	 * @param itemSeq
	 * @param itemCmmtType
	 * @return
	 */
	public ItemCmmtVO selectItemCmmtBySeq(String itemSeq, String itemCmmtType) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("itemSeq", itemSeq);
		param.put("itemCmmtType", itemCmmtType);
		return sqlSession.selectOne(SQL_NS + "selectItemCmmtBySeq", param);
	}

	// ========================= ]]알림 =========================================

	// ========================= 메시지 =========================================

	/**
	 * 최신메시지 개수 조회
	 * 
	 * @param msgVO
	 * @return
	 */
	public int selectLatestMessageCount(MessageVO msgVO) {
		return sqlSession.selectOne(SQL_NS + "selectLatestMessageCount", msgVO);
	}

	/**
	 * <pre>
	 * 최신 메시지 대상 목록 조회 (by 검색조건)
	 * </pre>
	 * 
	 * @param msgVO
	 * @return
	 */
	public List<MessageVO> selectLatestMessageRoomListBySchWord(MessageVO msgVO) {
		return sqlSession.selectList(SQL_NS + "selectLatestMessageRoomListBySchWord", msgVO);
	}

	/**
	 * 최신 메시지 대상 목록 조회 (by 회원)
	 * 
	 * @param msgVO
	 * @return
	 */
	public List<MessageVO> selectLatestMessageRoomListByNewUserSeq(MessageVO msgVO) {
		return sqlSession.selectList(SQL_NS + "selectLatestMessageRoomListByNewUserSeq", msgVO);
	}

	/**
	 * 최신 메시지 내용 목록 조회
	 * 
	 * @param msgVO:
	 *            schSelectedUserSeq
	 * @return
	 */
	public List<MessageVO> selectLatestMessageContentList(MessageVO msgVO) {
		return sqlSession.selectList(SQL_NS + "selectLatestMessageContentList", msgVO);
	}

	/**
	 * 메시지 내용 이미 읽었음으로 수정
	 * 
	 * @param msgVO
	 */
	public void updateLatestMessageRead(MessageVO msgVO) {
		sqlSession.update(SQL_NS + "updateLatestMessageRead", msgVO);
	}

	/**
	 * 메시지 등록
	 * 
	 * @param msgVO
	 */
	public void insertMessage(MessageVO msgVO) {
		sqlSession.insert(SQL_NS + "insertMessage", msgVO);
	}

	// ========================= ]]메시지 =========================================

}
