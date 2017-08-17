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
import com.opendesign.utils.ThumbnailManager;

/**
 * <pre>
 * 프로젝트 작품 버전 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class ProjectWorkVerVO {

	/** 작품seq */
	private String projectWorkSeq;
	/** 버전seq */
	private String seq;
	/** 버전 */
	private String ver;
	/** 파일명 */
	private String fileUri;
	/** 등록일시 */
	private String registerTime;
	/** 첨부파일 */
	private String filename;
	
	// ==================================================
	/**
	 * 이미지인지 판단
	 * @return
	 */
	public boolean isFileUriImageType() {
		return CmnUtil.isImageFile(filename);
	}
	
	// ==================================================

	public String getProjectWorkSeq() {
		return projectWorkSeq;
	}

	public void setProjectWorkSeq(String projectWorkSeq) {
		this.projectWorkSeq = projectWorkSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	public String getFileUriL() {
		return ThumbnailManager.getThumbnail(fileUri, ThumbnailManager.SUFFIX_PROJECT_WORK_LARGE);
	}
	
	

}
