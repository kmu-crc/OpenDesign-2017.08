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
 * 포인트 가감 이력 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class PointHistoryVO {

	/**
	 * SignType
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 28.
	 */
	public static interface SignType {
		/** 구매 */
		String PURCHASE = "C";
		/** 판매 */
		String SELL = "S";
	}
	// ==================================================
	
	/** seq */
	private String seq;
	/** 가감구분 */
	private String sign;
	/** 금액 */
	private String amount;
	/** 누적결과프인트 */
	private String accumAmount;
	/** 회원seq */
	private String memberSeq;
	/** 구매seq */
	private String orderSeq;
	/** 비고 */
	private String comments;
	/** 등록일시 */
	private String registerTime;

	// ==================================================

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccumAmount() {
		return accumAmount;
	}

	public void setAccumAmount(String accumAmount) {
		this.accumAmount = accumAmount;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return String.format(
				"PointHistoryVO [seq=%s, sign=%s, amount=%s, accumAmount=%s, memberSeq=%s, orderSeq=%s, comments=%s, registerTime=%s]",
				seq, sign, amount, accumAmount, memberSeq, orderSeq, comments, registerTime);
	}

}
