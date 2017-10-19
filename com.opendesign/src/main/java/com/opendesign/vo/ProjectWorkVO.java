/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.ThumbnailManager;

/**
 * 
 * <pre>
 * 프로젝트 작품 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ProjectWorkVO {

	/** 제목 */
	private String title;
	/** 작품설명 */
	private String contents;
	/** 작품seq */
	private String seq;
	/** 주제seq */
	private String projectSubjectSeq;
	/** 생성회원seq */
	private String memberSeq;
	/** 등록일시 */
	private String registerTime;
	/** 참조작품seq */
	private String referProjectWorkSeq;
	/** 마지막버전 */
	private String lastVer;
	/** 마지막버전seq */
	private String lastVerSeq;
	/** 썸네일 */
	private String thumbUri;
	// ==================================================
	/** 작품 이미지 이름 */
	private String verFilename;
	/** 작품 이미지 uri */
	private String verFileUri;
	/** 생성회원이름 */
	private String memberName;
	/** 좋아요 갯수 */
	private String likeCnt;
	/** 댓글 갯수 */
	private String commentCnt;
	/** 회원카테고리 */
	private String memberType;
	// === user define
	/// ** 참여자seq list(UI) @deprecated */
	// private String[] workMemberSeqs;
	/** 참여자 email list(UI) */
	private String[] workMemberEmails;

	/** 버전list */
	private List<ProjectWorkVerVO> projectWorkVerList;
	/** 참여자list */
	private List<ProjectWorkMemberVO> projectWorkMemberList;

	// ======== 퍼가기 ========
	/** 퍼갈 작품 버전 */
	private String fromVerSeq;
	/** 퍼갈 목적 주제 seq */
	private String toSubjectSeq;
	
	/** logon user seq */
	private String logonUserSeq;

	// ======== ]]퍼가기 ========

	/** 포맷팅 된 시간 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(registerTime);
	}

	/**
	 * 생성회원이외의 기타 참여자
	 * 
	 * @return
	 */
	public List<ProjectWorkMemberVO> getOtherProjectWorkMemberList() {
		List<ProjectWorkMemberVO> list = new ArrayList<ProjectWorkMemberVO>();
		if (!CmnUtil.isEmpty(projectWorkMemberList)) {
			for (ProjectWorkMemberVO item : projectWorkMemberList) {
				if (!StringUtils.stripToEmpty(memberSeq).equals(item.getMemberSeq())) {
					list.add(item);
				}
			}
		}
		return list;
	}

	/**
	 * 생성자을 앞에 놓은 참여자 목록
	 * 
	 * @return
	 */
	public List<ProjectWorkMemberVO> getOrderedProjectWorkMemberList() {
		List<ProjectWorkMemberVO> list = new ArrayList<ProjectWorkMemberVO>();
		if (!CmnUtil.isEmpty(projectWorkMemberList)) {
			for (ProjectWorkMemberVO item : projectWorkMemberList) {
				if (StringUtils.stripToEmpty(memberSeq).equals(item.getMemberSeq())) {
					list.add(item);
				}
			}
		}
		List<ProjectWorkMemberVO> other = getOtherProjectWorkMemberList();
		if (other != null) {
			list.addAll(other);
		}
		return list;
	}

	/**
	 * 생성자을 앞에 놓은 참여자 이름 목록
	 * 
	 * @return
	 */
	public String getWorkMemberNameList() {
		List<String> list = new ArrayList<String>();
		for (ProjectWorkMemberVO item : getOrderedProjectWorkMemberList()) {
			list.add(item.getMemberName());
		}
		return StringUtils.join(list, ",");
	}

	/** 작품 수정/삭제 할수 있는 회원인지 판단 */
	private boolean curUserAuthYN;
	/** 회원 좋아요 했는지 판단 */
	private boolean curUserLikedYN;

	/** 댓글 list */
	private List<ItemCmmtVO> cmmtList;
	
	/**
	 * 이미지인지 판단
	 * @return
	 */
	public boolean isVerFileUriImageType() {
		return CmnUtil.isImageFile(verFilename);
	}
	
	/**
	 * 문서 파일인지 판단
	 * @return
	 */
	public boolean isVerFileUriDocuType() {
		return CmnUtil.isDocuFile(verFilename);
	}
	
	/**
	 * 썸네일: small
	 * @return
	 */
	public String getThumbUriS() {
		return ThumbnailManager.getThumbnail(thumbUri, ThumbnailManager.SUFFIX_PROJECT_WORK_SMALL);
	}
	
	// ==================================================

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getProjectSubjectSeq() {
		return projectSubjectSeq;
	}

	public void setProjectSubjectSeq(String projectSubjectSeq) {
		this.projectSubjectSeq = projectSubjectSeq;
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

	public String getReferProjectWorkSeq() {
		return referProjectWorkSeq;
	}

	public void setReferProjectWorkSeq(String referProjectWorkSeq) {
		this.referProjectWorkSeq = referProjectWorkSeq;
	}

	public List<ProjectWorkVerVO> getProjectWorkVerList() {
		return projectWorkVerList;
	}

	public void setProjectWorkVerList(List<ProjectWorkVerVO> projectWorkVerList) {
		this.projectWorkVerList = projectWorkVerList;
	}

	public List<ProjectWorkMemberVO> getProjectWorkMemberList() {
		return projectWorkMemberList;
	}

	public void setProjectWorkMemberList(List<ProjectWorkMemberVO> projectWorkMemberList) {
		this.projectWorkMemberList = projectWorkMemberList;
	}

	public String getLastVer() {
		return lastVer;
	}

	public void setLastVer(String lastVer) {
		this.lastVer = lastVer;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getVerFilename() {
		return verFilename;
	}

	public void setVerFilename(String verFilename) {
		this.verFilename = verFilename;
	}

	public String getVerFileUri() {
		return verFileUri;
	}
	
	public String getVerFileUriS() {
		return ThumbnailManager.getThumbnail(verFileUri, ThumbnailManager.SUFFIX_PROJECT_WORK_SMALL);
	}
	
	public String getVerFileUriL() {
		return ThumbnailManager.getThumbnail(verFileUri, ThumbnailManager.SUFFIX_PROJECT_WORK_LARGE);
	}
	
	public void setVerFileUri(String verFileUri) {
		this.verFileUri = verFileUri;
	}

	public String[] getWorkMemberEmails() {
		return workMemberEmails;
	}

	public void setWorkMemberEmails(String[] workMemberEmails) {
		this.workMemberEmails = workMemberEmails;
	}

	public String getLikeCnt() {
		return likeCnt;
	}
	
	public String getLikeCntF() {
		return CmnUtil.nFormatter(likeCnt);
	}

	public void setLikeCnt(String likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getCommentCnt() {
		return commentCnt;
	}
	
	public String getCommentCntF() {
		return CmnUtil.nFormatter(commentCnt);
	}

	public void setCommentCnt(String commentCnt) {
		this.commentCnt = commentCnt;
	}

	public String getFromVerSeq() {
		return fromVerSeq;
	}

	public void setFromVerSeq(String fromVerSeq) {
		this.fromVerSeq = fromVerSeq;
	}

	public String getToSubjectSeq() {
		return toSubjectSeq;
	}

	public void setToSubjectSeq(String toSubjectSeq) {
		this.toSubjectSeq = toSubjectSeq;
	}

	public String getLastVerSeq() {
		return lastVerSeq;
	}

	public void setLastVerSeq(String lastVerSeq) {
		this.lastVerSeq = lastVerSeq;
	}

	public boolean isCurUserAuthYN() {
		return curUserAuthYN;
	}

	public void setCurUserAuthYN(boolean curUserAuthYN) {
		this.curUserAuthYN = curUserAuthYN;
	}

	public boolean isCurUserLikedYN() {
		return curUserLikedYN;
	}

	public void setCurUserLikedYN(boolean curUserLikedYN) {
		this.curUserLikedYN = curUserLikedYN;
	}

	public List<ItemCmmtVO> getCmmtList() {
		return cmmtList;
	}

	public void setCmmtList(List<ItemCmmtVO> cmmtList) {
		this.cmmtList = cmmtList;
	}
	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}

	public String getLogonUserSeq() {
		return logonUserSeq;
	}

	public void setLogonUserSeq(String logonUserSeq) {
		this.logonUserSeq = logonUserSeq;
	}
}
