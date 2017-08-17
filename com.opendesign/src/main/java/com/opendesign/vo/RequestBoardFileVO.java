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
 * 의뢰 게시판 이미지 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class RequestBoardFileVO {

	/** 게시판seq */
	private String boardSeq;
	/** seq */
	private String seq;
	/** 파일명 */
	private String filename;
	/** 파일url */
	private String fileUrl;
	/** 등록일시 */
	private String registerTime;
	
	// ==================================================

	public String getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

}
