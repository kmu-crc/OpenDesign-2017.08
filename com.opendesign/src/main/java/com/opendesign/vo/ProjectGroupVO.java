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
 * 그룹 VO
 * </pre>
 * @author hanchanghao
 * @since 2016. 11. 15.
 */
public class ProjectGroupVO {

	private int seq;
	/** 그룹명 */
	private String groupName;
	/** 생성자 */
	private int memberSeq;
	/** 등록일시 */
	private String registerTime;
	/** 수정일시 */
	private String updateTime;

	// ==================================================
	/** 생성자 */
	private String memberName;
	/** 프로젝트수 */
	private String projectCnt;
	/** 게시물수 */
	private String workCnt;
	/** 멤버수 */
	private String memberCnt;

	// ==================================================
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(int memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getProjectCnt() {
		return projectCnt;
	}
	
	public String getProjectCntF() {
		return CmnUtil.nFormatter(projectCnt);
	}

	public void setProjectCnt(String projectCnt) {
		this.projectCnt = projectCnt;
	}

	public String getWorkCnt() {
		return workCnt;
	}
	
	public String getWorkCntF() {
		return CmnUtil.nFormatter(workCnt);
	}

	public void setWorkCnt(String workCnt) {
		this.workCnt = workCnt;
	}

	public String getMemberCnt() {
		return memberCnt;
	}
	
	public String getMemberCntF() {
		return CmnUtil.nFormatter(memberCnt);
	}

	public void setMemberCnt(String memberCnt) {
		this.memberCnt = memberCnt;
	}

}
