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
import com.opendesign.vo.PointHistoryVO.SignType;

/**
 * <pre>
 * 포인트 이력 VO
 * </pre>
 * @author hanchanghao
 * @since 2016. 11. 15.
 */
public class MyUserPointVO {
	

	/** 조회 시작날짜 */
	private String fromDate;
	
	/** 조회 마지막 날짜 */
	private String toDate;
	
	/** 구매-C,판매-S 구분기호 */
	private String sign; 
	/** 구매,판매 구분 */
	private String wsign;
	/** 금액 */
	private String wamount;
	/** 누적결과 포인트 */
	private String waccumamount;
	/** seq */
	private String wseq;
	/** 등록일시 */
	private String wregistertime;
	/** 내용 */
	private String wcomments;
	
	/** 조회 번호 */
	private int cntI;	
	
	
	// ========================================
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getWaccumamount() {
		return waccumamount;
	}
	
	public String getWaccumamountF() {
		return CmnUtil.nFormatter(waccumamount);
	}

	public void setWaccumamount(String waccumamount) {
		this.waccumamount = waccumamount;
	}
	
	public String getWregistertime() {
		return wregistertime;
	}

	public void setWregistertime(String wregistertime) {
		this.wregistertime = wregistertime;
	}

	public String getWseq() {
		return wseq;
	}

	public void setWseq(String wseq) {
		this.wseq = wseq;
	}
	
				
	public int getCntI() {
		return cntI;
	}

	public void setCntI(int cntI) {
		this.cntI = cntI;
	}
	
	public String getWsign() {
		return wsign;
	}

	public void setWsign(String wsign) {
		this.wsign = wsign;
	}

	public String getWamount() {
		return wamount;
	}
	
	public String getWamountF() {
		return CmnUtil.nFormatter(wamount);
	}

	public void setWamount(String wamount) {		
		this.wamount = wamount;
	}

	public String getWcomments() {
		return wcomments;
	}
	
	public void setWcomments(String wcomments) {
		this.wcomments = wcomments;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getDisplaywamount() {
		if(SignType.PURCHASE.equals(sign)) {
			return "-" + CmnUtil.getDisplayNumber(wamount);
		} else {
			return "+" + CmnUtil.getDisplayNumber(wamount);
		} 
	}
	
	public String getDisplaywamountF() {
		if(SignType.PURCHASE.equals(sign)) {
			return "-" + CmnUtil.nFormatter(wamount);
		} else {
			return "+" + CmnUtil.nFormatter(wamount);
		} 
	}
	
	public String getDisplaywaccumamount() {
		return CmnUtil.getDisplayNumber(waccumamount);
	}
	
	public String getDisplaywaccumamountF() {
		return CmnUtil.nFormatter(waccumamount);
	}
	
	/**
	 * 시간 yyyy.mm.dd
	 * 
	 * @return
	 */
	public String getDisplayTimeYMD() {
		return CmnUtil.getDisplayTimeYMD(wregistertime);
	}



}
