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
import com.opendesign.utils.ThumbnailManager;

/**
 * 
 * <pre>
 * main 항목 VO (작품 및 프로젝트) 
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 5.
 */
public class MainItemVO {

	/**
	 * constant: ItemType
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 20.
	 */
	public static interface MainItemType {
		String PROJECT = "P"; // 프로젝트
		String WORK = "W"; // 작품
	}
	
	// ==================================================
	/** type */
	private String itemType;

	/** seq */
	private String seq;
	/** 작품명 */
	private String title;
	/** 썸네일 */
	private String thumbUri;
	/** 회원seq */
	private String memberSeq;
	/** 디자이너 이름 */
	private String memberName;

	/** 등록일시 */
	private String registerTime;
	/** 수정일시 */
	private String updateTime;

	/** 좋아요 */
	private String likeCnt;

	/** 작품 조회수 */
	private String viewCnt;

	/** 프로젝트 멤버수 */
	private String projectMemberCnt;

	/** 카테고리 이름(단일 카테고리) */
	private String cateName;

	// ==================================================

	/** 카테고리 list */
	private List<CategoryVO> cateList;

	// ==================================================
	
	/** 카테고리 1depth 명*/
	private String firstCategoryNm;
	/** 카테고리 2depth 명*/
	private String secondCategoryNm;
	/** 카테고리 3depth 명*/
	private String thirdCategoryNm;

	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(updateTime);
	}

	/** 사용자 좋아요 했는지 판단 */
	private boolean curUserLikedYN;
	
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
		for (CategoryVO item : cateList) {
			aList.add(item.getCategoryName());
		}
		return StringUtils.join(aList, ",");
	}

	// ==================================================

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	public String getProjectMemberCnt() {
		return projectMemberCnt;
	}
	
	public String getProjectMemberCntF() {
		return CmnUtil.nFormatter(projectMemberCnt);
	}

	public void setProjectMemberCnt(String projectMemberCnt) {
		this.projectMemberCnt = projectMemberCnt;
	}

	public List<CategoryVO> getCateList() {
		return cateList;
	}

	public void setCateList(List<CategoryVO> cateList) {
		this.cateList = cateList;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	public String getThumbUriM() {
		return ThumbnailManager.getThumbnail(thumbUri, ThumbnailManager.SUFFIX_DESIGN_WORK_MEDIUM);
	}

	public String getThumbUriL() {
		return ThumbnailManager.getThumbnail(thumbUri, ThumbnailManager.SUFFIX_DESIGN_WORK_LARGE);
	}
	public String getFirstCategoryNm() {
		return firstCategoryNm;
	}

	public void setFirstCategoryNm(String firstCategoryNm) {
		this.firstCategoryNm = firstCategoryNm;
	}

	public String getSecondCategoryNm() {
		return secondCategoryNm;
	}

	public void setSecondCategoryNm(String secondCategoryNm) {
		this.secondCategoryNm = secondCategoryNm;
	}

	public String getThirdCategoryNm() {
		return thirdCategoryNm;
	}

	public void setThirdCategoryNm(String thirdCategoryNm) {
		this.thirdCategoryNm = thirdCategoryNm;
	}

	public boolean isCurUserLikedYN() {
		return curUserLikedYN;
	}

	public void setCurUserLikedYN(boolean curUserLikedYN) {
		this.curUserLikedYN = curUserLikedYN;
	}
	
	@Override
	public String toString() {
		return String.format(
				"MainItemVO [itemType=%s, seq=%s, title=%s, thumbUri=%s, memberSeq=%s, memberName=%s, registerTime=%s, updateTime=%s, likeCnt=%s, viewCnt=%s, projectMemberCnt=%s, cateList=%s]",
				itemType, seq, title, thumbUri, memberSeq, memberName, registerTime, updateTime, likeCnt, viewCnt,
				projectMemberCnt, cateList);
	}

}
