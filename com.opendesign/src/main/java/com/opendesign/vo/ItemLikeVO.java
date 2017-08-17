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
 * 좋아요 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ItemLikeVO {

	/**
	 * constant: ItemType
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 20.
	 */
	public static interface ItemType {
		String PROJECT_WORK = "P";
		String DESIGN_WORK = "D";
	}

	// ==================================================
	
	/** type(프로젝트work,디자인work구분) */
	private String itemType;

	/** 회원seq */
	private String memberSeq;
	/** 작품seq */
	private String itemSeq;
	/** 등록일시 */
	private String registerTime;
	
	// ==================================================

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
