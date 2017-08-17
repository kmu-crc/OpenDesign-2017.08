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
 * 카테고리 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class CategoryVO {

	/** 카테고리 대분류 */
	public static final int DEPTH_1 = 1;
	/** 카테고리 중분류 */
	public static final int DEPTH_2 = 2;
	/** 카테고리 소분류 */
	public static final int DEPTH_3 = 3;
	
	// ============================================

	/** 카테고리코드 */
	private String categoryCode;
	/** 뎁스 */
	private String depth;
	/** 카테고리명 */
	private String categoryName;
	
	// ============================================

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
