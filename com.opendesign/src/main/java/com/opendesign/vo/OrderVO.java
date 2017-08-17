/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <pre>
 * 구매 내역 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class OrderVO {

	// sync with product_purchase: calcDedeliveryInfo() 
	private static final String DELIVERYINFO_REGEX = "받는 사람: (.*) \\| 전화 번호: (.*) \\| 주소: (.*)";
	// ==================================================
	
	/** seq */
	private String seq;
	/** 작품seq */
	private String designWorkSeq;
	/** 포인트 */
	private String point;
	/** 배송정보 */
	private String deliveryInfo;
	/** 상태 */
	private String status;
	/** 회원seq */
	private String memberSeq;
	/** 등록일시 */
	private String registerTime;

	// ==================================================
	/** 주소 */
	public String getOrderAddress() {
		String deliInfo = StringUtils.stripToEmpty(deliveryInfo);
		if(StringUtils.isEmpty(deliInfo)) {
			return "";
		}
		String result = "";
		Matcher matcher = Pattern.compile(DELIVERYINFO_REGEX).matcher(deliInfo);
		if(matcher.find()) {
			result = matcher.group(3); 
		}
		return StringUtils.stripToEmpty(result);
	}

	
	// ==================================================
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

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	@Override
	public String toString() {
		return String.format(
				"OrderVO [seq=%s, designWorkSeq=%s, point=%s, deliveryInfo=%s, status=%s, memberSeq=%s, registerTime=%s]",
				seq, designWorkSeq, point, deliveryInfo, status, memberSeq, registerTime);
	}
	
	
	public static void main(String[] args) {
		OrderVO o = new OrderVO();
		o.setDeliveryInfo("받는 사람: 송중기 | 전화 번호: 01033331111 | 주소:  광명리더스빌딩 707 ");
		//o.setDeliveryInfo("aaaa");
		System.out.println(">>>> start"); 
		System.out.println(o.getOrderAddress()); 
		System.out.println(">>>> "); 
	}

}
