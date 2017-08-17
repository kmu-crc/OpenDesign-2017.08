/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.MainItemVO;
import com.opendesign.vo.SearchVO;

/**
 * <pre>
 * 메인 DAO
 * </pre>
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Repository
public class MainDAO {

	private static final String SQL_NS = "MainSQL.";

	@Autowired
	SqlSession sqlSession;

	/**
	 * 메인 목록 데이터: project 총개수
	 * 
	 * @param searchVO
	 * @return
	 */
	public int selectProjectAllCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectProjectAllCount", param);
	}

	/**
	 * 메인 목록 데이터: project
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<MainItemVO> selectProjectPagingList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectProjectPagingList", param);
	}

	/**
	 * 메인 목록 데이터: work 총개수
	 * 
	 * @param searchVO
	 * @return
	 */
	public int selectWorkAllCount(SearchVO param) {
		return sqlSession.selectOne(SQL_NS + "selectWorkAllCount", param);
	}

	/**
	 * 메인 목록 데이터: work
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<MainItemVO> selectWorkPagingList(SearchVO param) {
		return sqlSession.selectList(SQL_NS + "selectWorkPagingList", param);
	}

	/**
	 * 카테고리
	 * 
	 * @param item:
	 *            itemType, seq
	 * @return
	 */
	public List<CategoryVO> selectMainCategoryList(String itemType, String seq) {
		SearchVO param = new SearchVO();
		param.setSchItemType(itemType);
		param.setSchSeq(seq);
		return sqlSession.selectList(SQL_NS + "selectMainCategoryList", param);
	}

}
