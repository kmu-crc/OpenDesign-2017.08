/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import com.opendesign.utils.ThumbnailManager;

/**
 * <pre>
 * 디자인(작품) 이미지 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class DesignPreviewImageVO {

	/** seq */
	private String seq;
	/** 작품seq */
	private String designWorkSeq;
	/** 이미지URL */
	private String fileUri;
	/** 파일명 */
	private String filename;

	// ==================================================

	public String getDesignWorkSeq() {
		return designWorkSeq;
	}

	public void setDesignWorkSeq(String designWorkSeq) {
		this.designWorkSeq = designWorkSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFileUri() {
		return fileUri;
	}
	
	
	public String getFileUriM() {
		return ThumbnailManager.getThumbnail(fileUri, ThumbnailManager.SUFFIX_DESIGN_WORK_MEDIUM);
	}
	
	public String getFileUriL() {
		return ThumbnailManager.getThumbnail(fileUri, ThumbnailManager.SUFFIX_DESIGN_WORK_LARGE);
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

}
