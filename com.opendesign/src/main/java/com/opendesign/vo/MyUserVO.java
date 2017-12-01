/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.List;

import com.opendesign.utils.CmnUtil;

/**
 * <pre>
 * 마이 페이지 VO
 * </pre>
 * @author hanchanghao
 * @since 2016. 11. 15.
 */
public class MyUserVO {

	/** 나의 프로젝트명 */
	private String pjname;

	/** 나의 저작물 제목 */
	private String wtitle;

	/** 나의 저작물 내용 */
	private String wcontests;

	/** seq */
	private String wseq;

	/** 나의 작품 등록일 */
	private String wregistertime;

	/** 나의 작품 목록 */
	private List<MyUserVO> myWorkList;

	/** 회원seq */
	private String memberSeq;
	
	/** 디자이너 seq */
	private String useq;

	/** 디자이너 명 */
	private String uname;

	/** 작품 썸네일 이미지 */
	private String thumbUri;

	/** 작품 태그 */
	private String wtags;
	/** 작품 카테고리 */
	private String wcate;

	/** 작품 조회수 */
	private String wvcount;

	/** 좋아요수 */
	private String cntLike;
	
	/** 프로젝트 멤버수 */
	private String pmcount;

	/** 프로젝트 게시물 수 */
	private String pmworkcount;

	// ==================================================
	
	/**
	 * Return with Formatter (k/m so on...)
	 * @return
	 */
	public String getCntLikeF() {
		return CmnUtil.nFormatter(cntLike);
	}
	 
	/**
	 * Return with Formatter (k/m so on...)
	 * @return
	 */
	public String getWvcountF() {
		return CmnUtil.nFormatter(cntLike);
	}
	 
	
	/** 구입포인트 */
	private String oPoint;

	
	// ==================================================
	
	public String getoPoint() {
		return oPoint;
	}

	public void setoPoint(String oPoint) {
		this.oPoint = oPoint;
	}

	public String getCntLike() {
		return cntLike;
	}

	public void setCntLike(String cntLike) {
		this.cntLike = cntLike;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}

	public String getWtags() {
		return wtags;
	}

	public void setWtags(String wtags) {
		this.wtags = wtags;
	}

	public String getWvcount() {
		return wvcount;
	}

	public void setWvcount(String wvcount) {
		this.wvcount = wvcount;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getPjname() {
		return pjname;
	}

	public void setPjname(String pjname) {
		this.pjname = pjname;
	}

	public String getWtitle() {
		return wtitle;
	}

	public void setWtitle(String wtitle) {
		this.wtitle = wtitle;
	}

	public String getWcontests() {
		return wcontests;
	}

	public void setWcontests(String wcontests) {
		this.wcontests = wcontests;
	}

	public String getWseq() {
		return wseq;
	}

	public void setWseq(String wseq) {
		this.wseq = wseq;
	}

	public String getWregistertime() {
		return wregistertime;
	}

	public void setWregistertime(String wregistertime) {
		this.wregistertime = wregistertime;
	}

	public List<MyUserVO> getMyWorkList() {
		return myWorkList;
	}

	public void setMyWorkList(List<MyUserVO> myWorkList) {
		this.myWorkList = myWorkList;
	}

	/**
	 * 포맷팅 된  시간
	 * 
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(wregistertime);
	}

	/**
	 * 포맷팅 된  시간 yyyy.mm.dd
	 * 
	 * @return
	 */
	public String getDisplayTimeYMD() {
		return CmnUtil.getDisplayTimeYMD(wregistertime);
	}

	public String getDisplayNumber() {
		return CmnUtil.getDisplayNumber(oPoint);
	}

	public String getWcate() {
		return wcate;
	}

	public void setWcate(String wcate) {
		this.wcate = wcate;
	}

	public String getUseq() {
		return useq;
	}

	public void setUseq(String useq) {
		this.useq = useq;
	}

	public String getPmworkcount() {
		return pmworkcount;
	}

	public void setPmworkcount(String pmworkcount) {
		this.pmworkcount = pmworkcount;
	}

	public String getPmcount() {
		return pmcount;
	}

	public void setPmcount(String pmcount) {
		this.pmcount = pmcount;
	}
	
	/*
	 * public String getDisplaywaccum_amount() { return
	 * CmnUtil.getDisplayNumber(waccum_amount); }
	 */

}
