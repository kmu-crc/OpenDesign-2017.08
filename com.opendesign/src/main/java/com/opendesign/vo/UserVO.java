/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.Arrays;
import java.util.List;

import com.opendesign.utils.CmnUtil;

/**
 * 
 * <pre>
 * 회원관리 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class UserVO {
	/**
	 * 회원구분(00-일반,10-디자인,01-제작자,11-디자이너/제작자)
	 *
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 10. 4.
	 */
	public static interface MemberType {
		String NORMAL = "00";
		String DESIGNER = "10";
		String PRODUCER = "01";
		String DESINGER_PRODUCER = "11";
	}
	
	// ==================================================

	/** 회원seq */
	private String seq;
	/** 사용이메일 */
	private String email;
	/** 비밀번호 */
	private String passwd;
	/** 닉네임 */
	private String uname;
	/** 프로필사진 */
	private String imageUrl;
	/** 소개 */
	private String comments;
	/** 회원구분(00-일반,10-디자인,01-제작자,11-디자이너/제작자) */
	private String memberType;
	/** 포인트 */
	private String point = "0";
	/** facebook어세스 토큰 */
	private String fbAccessToken;
	/** 등록일시 */
	private String registerTime;
	/** 수정일시 */
	private String updateTime;
	/** 현재비밀번호*/
	private String passwdOld;
	// === user defined
	/** 회원구분 UI쪽: d,p */
	private String[] memberTypeCheck;
	/** 회원 카테고리 코드 */
	private String[] memberCateCode;

	private boolean chkDesigner;

	private boolean chkProDucer;
	/** 시/도 */
	private String sido;
	/** 시/도 seq*/
	private int sidoSeq;
	
	/** 시/도 list*/
	private List<SidoVO> sidoList;
	
	/** form 시/도 seq*/
	private int memberSido;
	
	// ==================================================
	
	/** 회원 카테고리 명 */
	private List<MemberCategoryVO> cateNameList;
	
	/**
	 * 선택된 카테고리
	 * @return
	 */
	public String getSelCateCode(){
		if(CmnUtil.isEmpty(cateNameList)) {
			return "";
		}
		return cateNameList.get(0).getCategoryCode();
	}
	
	// ==================================================
	
	public boolean isChkDesigner() {
		return chkDesigner;
	}

	public void setChkDesigner(boolean chkDesigner) {
		this.chkDesigner = chkDesigner;
	}

	public boolean isChkProDucer() {
		return chkProDucer;
	}

	public void setChkProDucer(boolean chkProDucer) {
		this.chkProDucer = chkProDucer;
	}

	public List<MemberCategoryVO> getCateNameList() {
		return cateNameList;
	}

	public void setCateNameList(List<MemberCategoryVO> cateNameList) {
		this.cateNameList = cateNameList;
	}

	public String[] getMemberCateCode() {
		return memberCateCode;
	}

	public void setMemberCateCode(String[] memberCateCode) {
		this.memberCateCode = memberCateCode;
	}

	public String[] getMemberTypeCheck() {
		return memberTypeCheck;
	}

	public void setMemberTypeCheck(String[] memberTypeCheck) {
		this.memberTypeCheck = memberTypeCheck;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getFbAccessToken() {
		return fbAccessToken;
	}

	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
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

	public String getPasswdOld() {
		return passwdOld;
	}

	public void setPasswdOld(String passwdOld) {
		this.passwdOld = passwdOld;
	}
	

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}
	
	public int getSidoSeq() {
		return sidoSeq;
	}

	public void setSidoSeq(int sidoSeq) {
		this.sidoSeq = sidoSeq;
	}

	public List<SidoVO> getSidoList() {
		return sidoList;
	}

	public void setSidoList(List<SidoVO> sidoList) {
		this.sidoList = sidoList;
	}

	public int getMemberSido() {
		return memberSido;
	}

	public void setMemberSido(int memberSido) {
		this.memberSido = memberSido;
	}

	@Override
	public String toString() {
		return String.format(
				"UserVO [seq=%s, email=%s, passwd=%s, uname=%s, imageUrl=%s, comments=%s, memberType=%s, point=%s, fbAccessToken=%s, registerTime=%s, updateTime=%s, memberTypeCheck=%s, memberCateCode=%s, chkDesigner=%s, chkProDucer=%s, cateNameList=%s]",
				seq, email, passwd, uname, imageUrl, comments, memberType, point, fbAccessToken, registerTime,
				updateTime, Arrays.toString(memberTypeCheck), Arrays.toString(memberCateCode), chkDesigner, chkProDucer,
				cateNameList);
	}

}
