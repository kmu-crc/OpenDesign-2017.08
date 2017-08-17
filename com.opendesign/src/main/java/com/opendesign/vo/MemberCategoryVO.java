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
 * 회원 카테고리 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class MemberCategoryVO {

	/** 회원seq */
	private String memberSeq;
	/** 카테고리코드 */
	private String categoryCode;
	/** 등록일시 */
	private String registerTime;
	/** 카테고리명 */
	private String categoryName;
	
	// ==================================================
	
	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
