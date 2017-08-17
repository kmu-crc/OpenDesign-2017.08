/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import org.apache.commons.lang3.StringUtils;

import com.opendesign.utils.StringUtil;

/**
 * <pre>
 * 페이징 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class PageVO {

	private static final int DEFAULT_LIMIT_COUNT = 16; // page당 item수 default 값

	// ====== paging param ==============
	/** [in]page 번호 */
	private String schPage;

	/** [in]한 page 개수 */
	private String schLimitCount;

	/**
	 * [out] pgPageCount: page 시작번호
	 * 
	 * @return
	 */
	public int getPgPageCount() {
		return (getPageIndex() - 1) * getLimitCount();
	}

	/**
	 * [out] pgLimitCount: 한 page 개수
	 * 
	 * @return
	 */
	public int getPgLimitCount() {
		return getLimitCount();
	}
	// ====== ]]paging param ==============

	private int getPageIndex() {
		int pageIndex = 1;
		try {
			pageIndex = Integer.parseInt(schPage);
		} catch (Exception e) {
		}
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		return pageIndex;
	}

	private int getLimitCount() {
		int limitCount = DEFAULT_LIMIT_COUNT;
		if (StringUtil.isNotEmpty(schLimitCount)) {
			try {
				limitCount = Integer.parseInt(schLimitCount);
			} catch (Exception e) {
			}
		}
		return limitCount;
	}

	// ============================================

	public String getSchPage() {
		return schPage;
	}

	public void setSchPage(String schPage) {
		this.schPage = schPage;
	}

	public void setSchPage(int schPage) {
		this.schPage = String.valueOf(schPage);
	}
	
	public String getSchLimitCount() {
		if (StringUtils.isEmpty(schLimitCount)) {
			schLimitCount = String.valueOf(DEFAULT_LIMIT_COUNT);
		}
		return schLimitCount;
	}

	public void setSchLimitCount(String schLimitCount) {
		this.schLimitCount = schLimitCount;
	}
	
	public void setSchLimitCount(int schLimitCount) {
		this.schLimitCount = String.valueOf(schLimitCount);
	}

}
