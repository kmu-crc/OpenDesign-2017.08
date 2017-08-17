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
 * 시/도 VO
 * </pre>
 * @author hanchanghao
 * @since 2016. 11. 15.
 */
public class SidoVO {

	/** 시/도 seq*/
	private int seq;
	/** 시/도*/
	private String sido;
	
	// ==================================================
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
}
