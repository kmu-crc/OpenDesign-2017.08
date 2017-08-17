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
 * 
 * <pre>
 * 조회수 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ItemViewCntVO {

	/**
	 * constant: 구분
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 20.
	 */
	public static interface ItemViewCntType {
		String DESIGN_WORK = "D"; // 디자인 작품
	}
	// ==================================================
	
	/** 조회수 구분 */
	private String itemViewCntType;

	/** 작품seq */
	private String itemSeq;

	// ==================================================

	public String getItemViewCntType() {
		return itemViewCntType;
	}

	public void setItemViewCntType(String itemViewCntType) {
		this.itemViewCntType = itemViewCntType;
	}

	public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

}
