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
 * 의뢰 게시판 카테고리 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class RequestBoardCateVO {

	/** 게시판seq */
	private String boardSeq;
	/** 카테고리코드 */
	private String categoryCode;

	// ==================================================

	public String getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}
