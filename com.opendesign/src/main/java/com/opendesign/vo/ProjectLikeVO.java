package com.opendesign.vo;

public class ProjectLikeVO {

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author KimHyungJun에서 가져옴
	 * @since 2017. 3. 23
	 */

	// ==================================================
	
	/** type(프로젝트work,디자인work구분) */

	/** 회원seq */
	private String memberSeq;
	/** 작품seq */
	private String projectSeq;
	/** 등록일시 */
	private String registerTime;
	
	// ==================================================

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getProjectSeq() {
		return projectSeq;
	}

	public void setProjectSeq(String projectSeq) {
		this.projectSeq = projectSeq;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}
