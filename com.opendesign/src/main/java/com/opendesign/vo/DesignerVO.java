/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opendesign.utils.CmnUtil;

/**
 * <pre>
 * 디자이너/제작자 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 5.
 */
public class DesignerVO extends UserVO {

	/** 작품수 */
	private String workCnt;
	/** 좋아요 */
	private String likeCnt;
	/** 조회수 */
	private String viewCnt;
	/** 댓글수 */
	private String cmmtCnt;
	
	// ============================================
	/** 카테고리 list */
	private List<MemberCategoryVO> cateList;
	/** 작품 list */
	private List<DesignWorkVO> workList;
	/** 프로젝트 list */
	private List<ProjectVO> workPList;

	/** 사용자 좋아요 했는지 판단 */
	private boolean curUserLikedYN;
	
	// ============================================
	
	/**
	 * 사용가능 포인트
	 * 
	 * @return
	 */
	public String getDisplayPoint() {
		return CmnUtil.getDisplayNumber(getPoint());
	}

	/**
	 * 카테고리 이름
	 * 
	 * @return
	 */
	public String getCateNames() {
		if (CmnUtil.isEmpty(cateList)) {
			return "";
		}
		List<String> aList = new ArrayList<String>();
		for (MemberCategoryVO item : cateList) {
			aList.add(item.getCategoryName());
		}
		return StringUtils.join(aList, ",");
	}

	/**
	 * 인기순 3개 작품 list
	 * 
	 * @return
	 */
	public List<DesignWorkVO> getTop3WorkList() {
		if (CmnUtil.isEmpty(workList)) {
			return new ArrayList<DesignWorkVO>();
		}
		if (workList.size() < 3) {
			return workList;
		} else {
			return workList.subList(0, 3);
		}
	}
	
	/**
	 * 인기순 2개 프로젝트 list
	 * 
	 * @return
	 */
	public List<ProjectVO> getTop2ProjectList() {
		if (CmnUtil.isEmpty(workPList)) {
			return new ArrayList<ProjectVO>();
		}
		if (workPList.size() < 2) {
			return workPList;
		} else {
			return workPList.subList(0, 2);
		}
	}

	// ============================================
	public String getWorkCnt() {
		return workCnt;
	}
	
	public String getWorkCntF() {
		return CmnUtil.nFormatter(workCnt);
	}

	public void setWorkCnt(String workCnt) {
		this.workCnt = workCnt;
	}

	public String getLikeCnt() {
		return likeCnt;
	}
	
	public String getLikeCntF() {
		return CmnUtil.nFormatter(likeCnt);
	}

	public void setLikeCnt(String likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getViewCnt() {
		return viewCnt;
	}
	
	public String getViewCntF() {
		return CmnUtil.nFormatter(viewCnt);
	}

	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}

	public List<MemberCategoryVO> getCateList() {
		return cateList;
	}

	public void setCateList(List<MemberCategoryVO> cateList) {
		this.cateList = cateList;
	}

	public List<DesignWorkVO> getWorkList() {
		return workList;
	}

	public void setWorkList(List<DesignWorkVO> workList) {
		this.workList = workList;
	}

	public String getCmmtCnt() {
		return cmmtCnt;
	}
	
	public String getCmmtCntF() {
		return CmnUtil.nFormatter(cmmtCnt);
	}

	public void setCmmtCnt(String cmmtCnt) {
		this.cmmtCnt = cmmtCnt;
	}
	
	public boolean isCurUserLikedYN() {
		return curUserLikedYN;
	}

	public void setCurUserLikedYN(boolean curUserLikedYN) {
		this.curUserLikedYN = curUserLikedYN;
	}

	public List<ProjectVO> getWorkPList() {
		return workPList;
	}

	public void setWorkPList(List<ProjectVO> workPList) {
		this.workPList = workPList;
	}

}
