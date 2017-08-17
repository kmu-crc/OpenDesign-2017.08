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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opendesign.dao.MainDAO;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.CmnUtil.MainPageParamCalc;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.MainItemVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;

/**
 * 
 * <pre>
 * 메인 페이지의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class MainService {

	/**
	 * 메인 DAO 인스턴스
	 */
	@Autowired
	MainDAO dao;

	/**
	 * 메인 목록 데이터 조회
	 * 
	 * @param schPage
	 * @param schLimitCount
	 *            default 12
	 * @return all_count
	 * @return list
	 */
	public Map<String, Object> selectMainList(SearchVO searchVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MainItemVO> list = new ArrayList<MainItemVO>();
		int allCount = 0;
		UserVO userVO = CmnUtil.getLoginUser(request);
		if (userVO != null) {
			searchVO.setSchMemberSeq(userVO.getSeq());
		}

		/*
		 * 프로젝트 목록 조회
		 */
		SearchVO pSearchVO = new SearchVO();
		BeanUtils.copyProperties(searchVO, pSearchVO);
		pSearchVO.setSchLimitCount(MainPageParamCalc.getProjectCount());

		int projAllCount = dao.selectProjectAllCount(pSearchVO);
		List<MainItemVO> projList = dao.selectProjectPagingList(pSearchVO);
		CmnUtil.addAll(list, projList);

		/*
		 * 디자인(작품) 목록 조회
		 */
		SearchVO wSearchVO = new SearchVO();
		BeanUtils.copyProperties(searchVO, wSearchVO);
		wSearchVO.setSchLimitCount(MainPageParamCalc.getWorkCount());

		int workAllCount = dao.selectWorkAllCount(wSearchVO);
		List<MainItemVO> workList = dao.selectWorkPagingList(wSearchVO);
		CmnUtil.addAll(list, workList);

		/*
		 * 카테고리 목록 세팅
		 */
		if (!CmnUtil.isEmpty(list)) {
			for (MainItemVO item : list) {
				List<CategoryVO> cateList = dao.selectMainCategoryList(item.getItemType(), item.getSeq());
				item.setCateList(cateList);
			}
		}

		/*
		 * 조회된 메인 목록 재 정렬
		 */
		orderMainList(list);

		/*
		 * 조회된 데이터 카운트 세팅
		 */
		allCount = projAllCount + workAllCount;
		resultMap.put("all_count", allCount);
		resultMap.put("list", list);
		return resultMap;
	}

	/**
	 * 조회된 메인 목록 데이터 
	 * 다시 인기순으로 재정렬 
	 * 
	 * @param list
	 */
	private void orderMainList(List<MainItemVO> list) {
		if (CmnUtil.isEmpty(list)) {
			return;
		}

		Collections.sort(list, new LikeCntComparator());
	}

	/**
	 * 좋아요(likeCnt)기준으로 내림차순 정렬 하는 이너 클래스
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 22.
	 */
	public static class LikeCntComparator implements Comparator<MainItemVO> {

		@Override
		public int compare(MainItemVO o1, MainItemVO o2) {
			int o1Val = 0;
			if (o1 != null && !StringUtils.isEmpty(o1.getLikeCnt())) {
				o1Val = Integer.valueOf(o1.getLikeCnt());
			}
			int o2Val = 0;
			if (o2 != null && !StringUtils.isEmpty(o2.getLikeCnt())) {
				o2Val = Integer.valueOf(o2.getLikeCnt());
			}
			return (o2Val - o1Val);
		}

	}

}
