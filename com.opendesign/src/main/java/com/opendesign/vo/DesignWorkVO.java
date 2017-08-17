/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.StringUtil;
import com.opendesign.utils.ThumbnailManager;

/**
 * <pre>
 * 디자인(작품) VO
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 5.
 */
public class DesignWorkVO {

	/** seq */
	private String seq;
	/** 조회수 */
	private String viewCnt;
	/** 작품명 */
	private String title;
	/** 라이센스 */
	private String license;
	/** 포인트 */
	private String point;
	/** 썸네일 */
	private String thumbUri;
	
	/** 작품설명 */
	private String contents;
	/** 등록일시 */
	private String registerTime;
	/** 수정일시 */
	private String updateTime;
	/** 테그 */
	private String tags;
	/** 회원seq */
	private String memberSeq;
	/** 디자이너 이름 */
	private String memberName;
	/** 카테고리 이름 */
	private String cateNames;

	// ==================================================
	/** 좋아요 */
	private String likeCnt;

	/** 카테고리 list */
	private List<CategoryVO> cateList;
	/** 이미지 list */
	private List<DesignPreviewImageVO> imageList;
	/** 오픈 소스 파일 list */
	private List<DesignWorkFileVO> fileList;

	/** 현재 로그인 한 회원가 구매 하였는지 여부 **/
	private boolean logonUserPurchased; 
	
	/** 현재 로그인 한 회원와 작품을 등록한 회원가 같은지 여부*/
	private boolean isUserProduct;
	// ==================================================
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
	
	
	/**
	 * license BY
	 * 
	 * @return
	 */
	public String getLicenseBY() {
		if (StringUtils.isEmpty(license)) {
			return "";
		}
		return license.substring(0, 1);
	}

	/**
	 * license NC
	 * 
	 * @return
	 */
	public String getLicenseNC() {
		if (StringUtils.isEmpty(license)) {
			return "";
		}
		return license.substring(1, 2);
	}

	/**
	 * license ND
	 * 
	 * @return
	 */
	public String getLicenseND() {
		if (StringUtils.isEmpty(license)) {
			return "";
		}
		return license.substring(2, 3);
	}

	/**
	 * tag[]
	 * 
	 * @return
	 */
	public String[] getTagsArray() {
		String[] tempTags = StringUtils.stripToEmpty(tags).split("\\|");

		List<String> tagList = new ArrayList<String>();
		for (String aTag : tempTags) {
			if (StringUtil.isNotEmpty(aTag)) {
				tagList.add(aTag);
			}
		}

		return tagList.toArray(new String[tagList.size()]);
	}

	/**
	 * 포맷팅 된  시간
	 * 
	 * @return
	 */
	public String getDisplayTime() {
		return CmnUtil.getDisplayTime(updateTime);
	}

	/**
	 * 포맷팅 된  point
	 * 
	 * @return
	 */
	public String getDisplayPoint() {
		return CmnUtil.getDisplayNumber(point);
	}
	
	/**
	 * 포맷팅 된  point (k/m)
	 * 
	 * @return
	 */
	public String getDisplayPointF() {
		return CmnUtil.nFormatter(point);
	}

	/**
	 * 포맷팅 된  게시일
	 * 
	 * @return
	 */
	public String getDisplayRegTime() {
		return CmnUtil.getDisplayTimeYMD(registerTime);
	}

	/**
	 * 카테고리 이름
	 * 
	 * @return
	 */
	// public String getCateNames() {
	// if (CmnUtil.isEmpty(cateList)) {
	// return "";
	// }
	// List<String> aList = new ArrayList<String>();
	// for (CategoryVO item : cateList) {
	// aList.add(item.getCategoryName());
	// }
	// return StringUtils.join(aList, ",");
	// }

	public String getCateNames() {
		return cateNames;
	}

	public void setCateNames(String cateNames) {
		this.cateNames = cateNames;
	}

	/** 회원 좋아요 했는지 판단 */
	private boolean curUserLikedYN;

	// ==================================================
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getViewCnt() {
		return viewCnt;
	}
	
	public String getViewCntF() {
		return CmnUtil.nFormatter(viewCnt);
	}

	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPoint() {
		return point;
	}
	
	public String getPointF() {
		return CmnUtil.nFormatter(point);
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public List<CategoryVO> getCateList() {
		return cateList;
	}

	public void setCateList(List<CategoryVO> cateList) {
		this.cateList = cateList;
	}

	public String getLikeCnt() {
		return likeCnt;
	}
	
	public String getLikeCntF() {
		return CmnUtil.nFormatter(likeCnt);
	}

	public void setLikeCnt(String likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<DesignPreviewImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<DesignPreviewImageVO> imageList) {
		this.imageList = imageList;
	}

	public List<DesignWorkFileVO> getFileList() {
		return fileList;
	}

	public void setFileList(List<DesignWorkFileVO> fileList) {
		this.fileList = fileList;
	}

	public boolean isCurUserLikedYN() {
		return curUserLikedYN;
	}

	public void setCurUserLikedYN(boolean curUserLikedYN) {
		this.curUserLikedYN = curUserLikedYN;
	}
	

	public String getThumbUriM() {
		return ThumbnailManager.getThumbnail(thumbUri, ThumbnailManager.SUFFIX_DESIGN_WORK_MEDIUM);
	}

	public String getThumbUriL() {
		return ThumbnailManager.getThumbnail(thumbUri, ThumbnailManager.SUFFIX_DESIGN_WORK_LARGE);
	}
	
	

	public boolean isUserProduct() {
		return isUserProduct;
	}


	public void setUserProduct(boolean isUserProduct) {
		this.isUserProduct = isUserProduct;
	}


	public boolean isLogonUserPurchased() {
		return logonUserPurchased;
	}

	public void setLogonUserPurchased(boolean logonUserPurchased) {
		this.logonUserPurchased = logonUserPurchased;
	}

	@Override
	public String toString() {
		return String.format(
				"DesignWorkVO [seq=%s, viewCnt=%s, title=%s, license=%s, point=%s, thumbUri=%s, contents=%s, registerTime=%s, updateTime=%s, tags=%s, memberSeq=%s, memberName=%s, cateNames=%s, likeCnt=%s, cateList=%s, imageList=%s, fileList=%s, curUserLikedYN=%s]",
				seq, viewCnt, title, license, point, thumbUri, contents, registerTime, updateTime, tags, memberSeq,
				memberName, cateNames, likeCnt, cateList, imageList, fileList, curUserLikedYN);
	}

}
