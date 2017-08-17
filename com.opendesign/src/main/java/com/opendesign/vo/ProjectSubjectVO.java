/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.List;

/**
 * <pre>
 * 프로젝트 주제
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ProjectSubjectVO {

	/** 주제seq */
	private String seq;
	/** 프로젝트seq */
	private String projectSeq;
	/** 서브젝트seq */
	private String subjectSeq;
	/** 주제명 */
	private String subjectName;
	/** 생성회원seq */
	private String memberSeq;
	/** 등록일시 */
	private String registerTime;

	// ==================================================
	/** 작품list */
	private List<ProjectWorkVO> projectWorkList;

	// ==================================================

	public List<ProjectWorkVO> getProjectWorkList() {
		return projectWorkList;
	}

	public void setProjectWorkList(List<ProjectWorkVO> projectWorkList) {
		this.projectWorkList = projectWorkList;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getProjectSeq() {
		return projectSeq;
	}

	public void setProjectSeq(String projectSeq) {
		this.projectSeq = projectSeq;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	
	public String getSubjectSeq() {
		return subjectSeq;
	}

	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}

}
