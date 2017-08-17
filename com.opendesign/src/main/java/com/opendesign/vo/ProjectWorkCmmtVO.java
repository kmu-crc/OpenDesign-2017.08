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
 * 
 * <pre>
 * 프로젝트 작품 댓글 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ProjectWorkCmmtVO {

	/** 작품댓글seq */
	private String seq;
	/** 회원seq */
	private String memberSeq;
	/** 회원이름 */
	private String memberName;
	/** 내용 */
	private String contents;
	/** 등록일시 */
	private String registerTime;
	/** 작품seq */
	private String projectWorkSeq;

	// ==================================================
	/** 포맷팅 된 시간 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(registerTime);
	}
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

	public String getProjectWorkSeq() {
		return projectWorkSeq;
	}

	public void setProjectWorkSeq(String projectWorkSeq) {
		this.projectWorkSeq = projectWorkSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
