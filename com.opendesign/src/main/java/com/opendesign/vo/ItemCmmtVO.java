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
 * 댓글 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ItemCmmtVO {

	/**
	 * constant: 댓글 구분
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 20.
	 */
	public static interface ItemCmmtType {
		String PROJECT_CMMT = "P"; // 프로젝트 작품
		String DESIGN_CMMT = "D"; // 디자인 작품
		String BOARD_CMMT = "B"; // 의뢰 게시판
	}
	
	// ==================================================

	/** 댓글 구분(프로젝트 작품, 디자인 작품, 의뢰) */
	private String itemCmmtType;

	/** 작품댓글seq */
	private String seq;
	/** 회원seq */
	private String memberSeq;
	/** 작품seq */
	private String itemSeq;
	/** 회원이름 */
	private String memberName;
	/** 회원이미지 */
	private String memberImageUrl;
	/** 내용 */
	private String contents;
	/** 등록일시 */
	private String registerTime;
	/** 회원타입 */
	private String memberType;

	// ==================================================
	/** 제목 */
	private String itemTitle;
	/** 포인트 */
	private String itemPoint;

	/** 포맷팅 된 시간 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(registerTime);
	}
	
	/** login한 회원가 댓글 삭제할수 있는지 체크 */
	private boolean curUserAuthYN;
	// ==================================================

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getItemCmmtType() {
		return itemCmmtType;
	}

	public void setItemCmmtType(String itemCmmtType) {
		this.itemCmmtType = itemCmmtType;
	}

	public String getMemberImageUrl() {
		return memberImageUrl;
	}

	public void setMemberImageUrl(String memberImageUrl) {
		this.memberImageUrl = memberImageUrl;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemPoint() {
		return itemPoint;
	}

	public void setItemPoint(String itemPoint) {
		this.itemPoint = itemPoint;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public boolean isCurUserAuthYN() {
		return curUserAuthYN;
	}

	public void setCurUserAuthYN(boolean curUserAuthYN) {
		this.curUserAuthYN = curUserAuthYN;
	}
	
	

}
