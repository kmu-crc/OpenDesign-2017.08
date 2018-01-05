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

import org.apache.commons.lang3.StringUtils;

import com.opendesign.utils.CmnUtil;

/**
 * <pre>
 * 알림 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class AlarmVO {

	/** seq */
	private String seq;
	/** 회원seq */
	private String memberSeq;
	/** 이벤트 유발자 seq */
	private String actorSeq;
	/** 이벤트 유발자 이름 */
	private String actorName;
	/** 이벤트 유발자 이미지 */
	private String actorUrl;
	/** 내용 */
	private String contents;
	/** 등록일시 */
	private String registerTime;
	/** 액션url */
	private String actionUri;
	/** 확인일시 */
	private String confirmTime;
	/** 프로젝트 수정 시, 프로젝트 참여하는 회원 seq 전체 */
	private List <ProjectMemberVO> memberSeqList;

	// ============================================

	/**
	 * 이름과 콘텐츠 같이 보여줌
	 */
	public String getWholeContents() {
		return actorName + "님이 " + contents;
	}

	/**
	 * 알림 읽었는지 판단
	 * 
	 * @return
	 */
	public boolean isNew() {
		return StringUtils.isEmpty(confirmTime);
	}

	/**
	 * 포맷팅 된 시간
	 * 
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(registerTime);
	}

	// ============================================

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

	public String getActionUri() {
		return actionUri;
	}

	public void setActionUri(String actionUri) {
		this.actionUri = actionUri;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getActorSeq() {
		return actorSeq;
	}

	public void setActorSeq(String actorSeq) {
		this.actorSeq = actorSeq;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getActorUrl() {
		return actorUrl;
	}

	public void setActorUrl(String actorUrl) {
		this.actorUrl = actorUrl;
	}

	@Override
	public String toString() {
		return String.format(
				"AlarmVO [seq=%s, memberSeq=%s, actorSeq=%s, actorName=%s, actorUrl=%s, contents=%s, registerTime=%s, actionUri=%s, confirmTime=%s]",
				seq, memberSeq, actorSeq, actorName, actorUrl, contents, registerTime, actionUri, confirmTime);
	}

	public List<ProjectMemberVO> getMemberSeqList() {
		return memberSeqList;
	}

	public void setMemberSeqList(List<ProjectMemberVO> memberSeqList) {
		this.memberSeqList = memberSeqList;
	}

}
