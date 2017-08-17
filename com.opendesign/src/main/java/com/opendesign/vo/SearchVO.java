/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

/**
 * <pre>
 * 조회 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class SearchVO extends PageVO {

	/** seq */
	private String schSeq;
	/** 카테고리 */
	private String schCate;
	/** 정열순서 */
	private String schOrderType;
	/** 내용 */
	private String schContent;
	/** 회원Seq */
	private String schMemberSeq;
	/** 회원구분 */
	private String schMemberDiv;
	/** 댓글구분 */
	private String schItemCmmtType;
	/** 구분 */
	private String schItemType;
	
	/** 통합 검색어 */
	private String searchWord;

	// ==================================================
	/** 현제 로그인 회원 */
	private UserVO schLoginUser;
	
	// ==================================================

	public String getSchSeq() {
		return schSeq;
	}

	public void setSchSeq(String schSeq) {
		this.schSeq = schSeq;
	}

	public String getSchCate() {
		return schCate;
	}

	public void setSchCate(String schCate) {
		this.schCate = schCate;
	}

	public String getSchOrderType() {
		return schOrderType;
	}

	public void setSchOrderType(String schOrderType) {
		this.schOrderType = schOrderType;
	}

	public String getSchContent() {
		return schContent;
	}

	public void setSchContent(String schContent) {
		this.schContent = schContent;
	}

	public String getSchMemberDiv() {
		return schMemberDiv;
	}

	public void setSchMemberDiv(String schMemberDiv) {
		this.schMemberDiv = schMemberDiv;
	}

	public String getSchItemCmmtType() {
		return schItemCmmtType;
	}

	public void setSchItemCmmtType(String schItemCmmtType) {
		this.schItemCmmtType = schItemCmmtType;
	}

	public String getSchMemberSeq() {
		return schMemberSeq;
	}

	public void setSchMemberSeq(String schMemberSeq) {
		this.schMemberSeq = schMemberSeq;
	}

	public String getSchItemType() {
		return schItemType;
	}

	public void setSchItemType(String schItemType) {
		this.schItemType = schItemType;
	}

	public UserVO getSchLoginUser() {
		return schLoginUser;
	}

	public void setSchLoginUser(UserVO schLoginUser) {
		this.schLoginUser = schLoginUser;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	

}
