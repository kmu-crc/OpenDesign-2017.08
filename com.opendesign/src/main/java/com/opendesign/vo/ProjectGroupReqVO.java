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
 * 그룹 신청
 * </pre>
 * @author hanchanghao
 * @since 2016. 11. 15.
 */
public class ProjectGroupReqVO {

	/**
	 * constant: 상태
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 20.
	 */
	public static interface ProjectGroupReqStatus {
		/** 대기 */
		String WAITING = "0";
		/** 승인 */
		String APPROVE = "1";
		/** 제외 */
		String REJECT = "2";
	}
	// ==================================================

	/** 신청그룹seq */
	private String projectGroupSeq;
	/** 신청프로젝트seq */
	private String projectSeq;
	/** 상태:0대기,1승인,2제외 */
	private String status;
	/** 신청일시 */
	private String registerTime;
	/** 변경일시 */
	private String updateTime;

	// ==================================================
	/** 신청프로젝트그룹 명 */
	private String projectGroupName;
	/** 신청프로젝트그룹 생성자 seq */
	private String projectGroupOwnerSeq;
	/** 신청프로젝트그룹 생성자 명 */
	private String projectGroupOwnerName;

	/** 신청프로젝트 명 */
	private String projectName;
	/** 신청프로젝트 생선자 seq */
	private String projectOwnerSeq;
	/** 신청프로젝트 생선자 명 */
	private String projectOwnerName;

	// ==================================================

	/**
	 * 상태 표시
	 * 
	 * @return
	 */
	public String getDisplayStatus() {
		if (ProjectGroupReqStatus.WAITING.equals(status)) {
			return "승인대기중 - ";
		} else if (ProjectGroupReqStatus.APPROVE.equals(status)) {
			return "";
		} else if (ProjectGroupReqStatus.REJECT.equals(status)) {
			return "제외됨";
		}
		return "error";
	}

	/**
	 * 포맷팅 된 그룹 이름
	 * @return
	 */
	public String getDisplayGroupName() {
		return String.format("%s(%s)", projectGroupName, projectGroupOwnerName);
	}

	/**
	 * 포맷팅 된 그룹 텍스트
	 * @return
	 */
	public String getGroupLabel() {
		return getDisplayStatus() + getDisplayGroupName();
	}

	/**
	 * 포맷팅 된 그룹 값
	 * 
	 * @return
	 */
	public String getGroupValue() {
		return projectGroupSeq + "|" + projectSeq;
	}

	// ==================================================

	public String getProjectGroupSeq() {
		return projectGroupSeq;
	}

	public void setProjectGroupSeq(String projectGroupSeq) {
		this.projectGroupSeq = projectGroupSeq;
	}

	public String getProjectSeq() {
		return projectSeq;
	}

	public void setProjectSeq(String projectSeq) {
		this.projectSeq = projectSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getProjectGroupName() {
		return projectGroupName;
	}

	public void setProjectGroupName(String projectGroupName) {
		this.projectGroupName = projectGroupName;
	}

	public String getProjectGroupOwnerSeq() {
		return projectGroupOwnerSeq;
	}

	public void setProjectGroupOwnerSeq(String projectGroupOwnerSeq) {
		this.projectGroupOwnerSeq = projectGroupOwnerSeq;
	}

	public String getProjectGroupOwnerName() {
		return projectGroupOwnerName;
	}

	public void setProjectGroupOwnerName(String projectGroupOwnerName) {
		this.projectGroupOwnerName = projectGroupOwnerName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectOwnerSeq() {
		return projectOwnerSeq;
	}

	public void setProjectOwnerSeq(String projectOwnerSeq) {
		this.projectOwnerSeq = projectOwnerSeq;
	}

	public String getProjectOwnerName() {
		return projectOwnerName;
	}

	public void setProjectOwnerName(String projectOwnerName) {
		this.projectOwnerName = projectOwnerName;
	}

}
