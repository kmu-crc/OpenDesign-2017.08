/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import com.opendesign.utils.CmnUtil;

/**
 * <pre>
 * 의뢰 게시판 댓글 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class RequestBoardCmmtVO {

	/** 작성자seq */
	private String memberSeq;
	/** 작성자이름 */
	private String memberName;
	/** 게시판seq */
	private String boardSeq;
	/** 내용 */
	private String contents;
	/** 댓글seq */
	private String seq;
	/** 등록일시 */
	private String registerTime;
	/** 맴버유형 */
	private String memberType;
	// ==================================================
	/**
	 * 포맷팅 된 시간
	 * 
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(registerTime);
	}

	// ==================================================

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
}
