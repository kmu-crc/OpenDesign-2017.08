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
 * 그룹 관련 조회 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class SearchGroupVO extends PageVO {

	// ==================================================
	/** 프로젝트seq */
	private String schProjectSeq;
	/** 그룹명 */
	private String schGroupName;
	/** 그룹seq */
	private String schGroupSeq;
	// ==================================================

	public String getSchProjectSeq() {
		return schProjectSeq;
	}

	public void setSchProjectSeq(String schProjectSeq) {
		this.schProjectSeq = schProjectSeq;
	}

	public String getSchGroupName() {
		return schGroupName;
	}

	public void setSchGroupName(String schGroupName) {
		this.schGroupName = schGroupName;
	}

	public String getSchGroupSeq() {
		return schGroupSeq;
	}

	public void setSchGroupSeq(String schGroupSeq) {
		this.schGroupSeq = schGroupSeq;
	}

}
