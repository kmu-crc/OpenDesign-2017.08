/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import org.apache.commons.lang3.StringUtils;

import com.opendesign.utils.CmnUtil;

/**
 * 
 * <pre>
 * 메시지 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class MessageVO {

	public static interface MessageMode {
		/** 조회 */
		String SEARCH = "S";
		/** 신규 */
		String NEW = "N";
	}

	// ==================================================
	/** 모드 */
	private String schMode;
	/** 현제 회원 seq */
	private String schLoginUserSeq;
	/** search: 조건 */
	private String schWord;
	/** new: 새 회원 */
	private String schNewUserSeq;
	/** 선택된 회원 */
	private String schSelectedUserSeq;

	// ==================================================

	/** seq */
	private String seq;
	/** 내용 */
	private String contents;
	/** 받는seq */
	private String recieveSeq;
	/** 받는 이름 */
	private String recieveName;
	/** 받는 이미지 */
	private String recieveImageUrl;
	/** 보낸seq */
	private String sendSeq;
	/** 보낸 이름 */
	private String sendName;
	/** 보낸 이미지 */
	private String sendImageUrl;
	/** 등록일시 */
	private String registerTime;
	/** 확인일시 */
	private String confirmTime;

	// ==================================================
	/** 메시지 방 seq */
	private String roomSeq;

	// ==================================================
	/** 메시지 방 회원 seq */
	public String getRoomUserSeq() {
		if (isLoginUserRecieveUser()) {
			return sendSeq;
		} else {
			return recieveSeq;
		}
	}

	/**
	 * 등록자가 받는사람인지 판단
	 * 
	 * @return
	 */
	public boolean isLoginUserRecieveUser() {
		return StringUtils.stripToEmpty(schLoginUserSeq).equals(recieveSeq);
	}

	/** 메시지 방 회원 이름 */
	public String getRoomUserName() {
		if (isLoginUserRecieveUser()) {
			return sendName;
		} else {
			return recieveName;
		}
	}

	/** 메시지 방 회원 이미지 */
	public String getRoomUserImageUrl() {
		if (isLoginUserRecieveUser()) {
			return sendImageUrl;
		} else {
			return recieveImageUrl;
		}
	}

	/**
	 * <pre>
	 * 포맷팅 된 시간
	 * (1분이내-방금전, 1시간 이내-분단위, 24시간 이내-시단위, 
	 * 한달이내-일단위,1년 이내-달단위, 그 이후-년단위)
	 * </pre>
	 * 
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(registerTime);
	}

	/**
	 * 읽었는지 판단
	 * 
	 * @return
	 */
	public boolean isNew() {
		return StringUtils.isEmpty(confirmTime);
	}

	// ==================================================

	public String getSchMode() {
		return schMode;
	}

	public void setSchMode(String schMode) {
		this.schMode = schMode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRecieveSeq() {
		return recieveSeq;
	}

	public void setRecieveSeq(String recieveSeq) {
		this.recieveSeq = recieveSeq;
	}

	public String getSendSeq() {
		return sendSeq;
	}

	public void setSendSeq(String sendSeq) {
		this.sendSeq = sendSeq;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getSchWord() {
		return schWord;
	}

	public void setSchWord(String schWord) {
		this.schWord = schWord;
	}

	public String getSchLoginUserSeq() {
		return schLoginUserSeq;
	}

	public void setSchLoginUserSeq(String schLoginUserSeq) {
		this.schLoginUserSeq = schLoginUserSeq;
	}

	public String getSchNewUserSeq() {
		return schNewUserSeq;
	}

	public void setSchNewUserSeq(String schNewUserSeq) {
		this.schNewUserSeq = schNewUserSeq;
	}

	public String getSchSelectedUserSeq() {
		return schSelectedUserSeq;
	}

	public void setSchSelectedUserSeq(String schSelectedUserSeq) {
		this.schSelectedUserSeq = schSelectedUserSeq;
	}

	public String getRecieveName() {
		return recieveName;
	}

	public void setRecieveName(String recieveName) {
		this.recieveName = recieveName;
	}

	public String getRecieveImageUrl() {
		return recieveImageUrl;
	}

	public void setRecieveImageUrl(String recieveImageUrl) {
		this.recieveImageUrl = recieveImageUrl;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendImageUrl() {
		return sendImageUrl;
	}

	public void setSendImageUrl(String sendImageUrl) {
		this.sendImageUrl = sendImageUrl;
	}

	public String getRoomSeq() {
		return roomSeq;
	}

	public void setRoomSeq(String roomSeq) {
		this.roomSeq = roomSeq;
	}

}
