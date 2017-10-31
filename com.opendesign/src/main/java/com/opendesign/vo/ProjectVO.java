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

import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.StringUtil;
import com.opendesign.utils.ThumbnailManager;

/**
 * <pre>
 * 프로젝트 VO
 * </pre>
 * 
 * @author devhcc
 */
public class ProjectVO {
	
	/** seq */
	private int seq;
	/** 소유자 seq */
	private int ownerSeq;
	/** 공개여부 */
	private String publicYn;
	/** 프로젝트 이름 */
	private String projectName;
	/** 프로젝트 상태(진행중P,완료C) */
	private String progressStatus;
	/** 대표 이미지 url */
	private String fileUrl;
	/** 대표 이미지 이름 */
	private String fileName;
	/** 등록일시 */
	private String registerTime;
	/** 수정일시 */
	private String updateTime;
	
	/** 멤버 수 */
	private int projectMemberCnt;
	/** 작품 개수 */
	private int projectWorkCnt;
	/** 파일 개수 */
	private int projectWorkFileCnt;
	/** 좋아요 개수 */
	private int likeCnt;
	
	/** 소유자 이름 */
	private String ownerName;
	/** 프로젝트 멤버 판단 */
	private boolean isProjectMember;
	/** 프로젝트 소유자 판단 */
	private boolean isProjectOwner;
	/** 카테고리 목록 */
	private List<CategoryVO> categoryList;
	

	// ==================================================
	
	/**
	 * 선택된 카테고리
	 * @return
	 */
	public String getSelCateCode() {
		if(CmnUtil.isEmpty(categoryList)) {
			return "";
		}
		return categoryList.get(0).getCategoryCode();
	}
	
	// ==================================================
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getOwnerSeq() {
		return ownerSeq;
	}
	public void setOwnerSeq(int ownerSeq) {
		this.ownerSeq = ownerSeq;
	}
	public String getPublicYn() {
		return publicYn;
	}
	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProgressStatus() {
		return progressStatus;
	}
	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	
	public int getProjectMemberCnt() {
		return projectMemberCnt;
	}
	
	public String getProjectMemberCntF() {
		return CmnUtil.nFormatter(projectMemberCnt);
	}
	
	public void setProjectMemberCnt(int projectMemberCnt) {
		this.projectMemberCnt = projectMemberCnt;
	}
	public int getProjectWorkCnt() {
		return projectWorkCnt;
	}
	
	public String getProjectWorkCntF() {
		return CmnUtil.nFormatter(projectWorkCnt);
	}
	
	public void setProjectWorkCnt(int projectWorkCnt) {
		this.projectWorkCnt = projectWorkCnt;
	}
	public int getProjectWorkFileCnt() {
		return projectWorkFileCnt;
	}
	
	public String getProjectWorkFileCntF() {
		return CmnUtil.nFormatter(projectWorkFileCnt);
	}
	
	public void setProjectWorkFileCnt(int projectWorkFileCnt) {
		this.projectWorkFileCnt = projectWorkFileCnt;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public int getLikeCnt() {
		return likeCnt;
	}
	public String getLikeCntF() {
		return CmnUtil.nFormatter((long)likeCnt);
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public void setCategoryList(List<CategoryVO> categoryList) {
		this.categoryList = categoryList;
	}
	
	public List<CategoryVO> getCategoryList() {
		return this.categoryList;
	}
	
	public String getCategories() {
		String categories = "";
		
		if( this.categoryList != null ) {
			for( CategoryVO category : this.categoryList ) {
				categories += category.getCategoryName() + ", ";
			}
		}
		
		return StringUtil.removeLastDelimeter(categories, ", ");
	}
	
	public void setIsProjectMember(boolean isMember) {
		this.isProjectMember = isMember;
	}
	
	/**
	 * 프로젝트에 포함된 멤버인지 여부
	 * @return
	 */
	public boolean getIsProjectMember() {
		return this.isProjectMember;
	}
	
	public void setIsProjectOwner(boolean isOwner) {
		this.isProjectOwner = isOwner;
	}
	
	/**
	 * 프로젝트를 생성한 오너인지 여부
	 * 
	 * @return
	 */
	public boolean getIsProjectOwner() {
		return this.isProjectOwner;
	}
	
	/**
	 * 포맷팅 된  시간
	 * 
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(updateTime);
	}

	/**
	 * 포맷팅 된  게시일
	 * 
	 * @return
	 */
	public String getDisplayRegTime() {
		return CmnUtil.getDisplayTimeYMD(registerTime);
	}
	
	public String getFileUrlM() {
		return ThumbnailManager.getThumbnail(fileUrl, ThumbnailManager.SUFFIX_DESIGN_WORK_MEDIUM);
	}
	
	

}
