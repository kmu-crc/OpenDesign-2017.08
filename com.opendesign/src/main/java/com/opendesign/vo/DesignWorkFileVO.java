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
 * 
 * <pre>
 * 디자인(작품) 오픈 소스 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class DesignWorkFileVO {

	/** seq */
	private String seq;
	/** 작품seq */
	private String designWorkSeq;
	/** 설명 */
	private String comments;
	/** 파일경로 */
	private String fileUri;
	/** 파일명 */
	private String filename;
	/** 등록일시 */
	private String registerTime;

	/** 파일size */
	private String fileSize;

	// =========================================================

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getDesignWorkSeq() {
		return designWorkSeq;
	}

	public void setDesignWorkSeq(String designWorkSeq) {
		this.designWorkSeq = designWorkSeq;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
