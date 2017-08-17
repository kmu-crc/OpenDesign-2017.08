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

/**
 * <pre>
 * 의뢰 게시판 VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 7.
 */
public class RequestBoardVO {
	/** 등록/수정 모드 */
	private String pageMode;
	
	// ==================================================

	/** seq */
	private String seq;
	/** 구분 */
	private String boardType;
	/** 제목 */
	private String title;
	/** 내용 */
	private String contents;
	/** 작성자seq */
	private String memberSeq;
	/** 작성자이름 */
	private String memberName;
	/** 등록일시 */
	private String registerTime;
	/** 수정일시 */
	private String updateTime;
	/** 댓글수 */
	private String cmmtCnt;

	// ==================================================
	/** 게시글 카테고리 */
	private List<CategoryVO> cateList;
	/** 게시글 이미지 */
	private List<RequestBoardFileVO> fileList;
	/** 게시글 댓글 */
	private List<RequestBoardCmmtVO> cmmtList;

	/** 작품 수정/삭제 할수 있는 회원인지 판단 */
	private boolean curUserAuthYN;

	// UI ===========================
	/** ui 카테고리 코드 array */
	private String[] uiBoardCateCodes;

	// ==================================================
	/**
	 * 포맷팅 된 시간
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(updateTime);
	}
	
	/**
	 * 선택된 카테고리
	 * @return
	 */
	public String getSelCateCode() {
		if(CmnUtil.isEmpty(cateList)) {
			return "";
		}
		return cateList.get(0).getCategoryCode();
	}
	
	// ==================================================

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
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

	public List<CategoryVO> getCateList() {
		return cateList;
	}

	public void setCateList(List<CategoryVO> cateList) {
		this.cateList = cateList;
	}

	public List<RequestBoardFileVO> getFileList() {
		return fileList;
	}

	public void setFileList(List<RequestBoardFileVO> fileList) {
		this.fileList = fileList;
	}

	public List<RequestBoardCmmtVO> getCmmtList() {
		return cmmtList;
	}

	public void setCmmtList(List<RequestBoardCmmtVO> cmmtList) {
		this.cmmtList = cmmtList;
	}

	public String[] getUiBoardCateCodes() {
		return uiBoardCateCodes;
	}

	public void setUiBoardCateCodes(String[] uiBoardCateCodes) {
		this.uiBoardCateCodes = uiBoardCateCodes;
	}

	public String getCmmtCnt() {
		return cmmtCnt;
	}

	public void setCmmtCnt(String cmmtCnt) {
		this.cmmtCnt = cmmtCnt;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPageMode() {
		return pageMode;
	}

	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	public boolean isCurUserAuthYN() {
		return curUserAuthYN;
	}

	public void setCurUserAuthYN(boolean curUserAuthYN) {
		this.curUserAuthYN = curUserAuthYN;
	}

}
