/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.utils;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 공통 사용 상수
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 23.
 */
public class CmnConst {

	/**
	 * 체크:
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 11. 11.
	 */
	public static class CheckRule {
		public static final long LIMIT_FILE_SIZE = 100000000L; // 100MB
	}

	/**
	 * 결과 constant
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 8. 23.
	 */
	public static class RstConst {
		/** param 이름 */
		public static final String P_NAME = "result";

		/** 성공 */
		public static final String V_SUCESS = "1";
		/** 실패 */
		public static final String V_FAIL = "0";
		/** 로그인 필요 */
		public static final String V_NEED_LOGIN = "900";
		/** 포인트 부족 */
		public static final String V_NOT_ENOUGH_POINT = "300";
		/** 이미 구매 */
		public static final String V_ALEADY_PURCHASED = "600";
		/** 이메일 중복 */
		public static final String V_EMAIL_DUP = "400";
		/** 닉네임 중복 */
		public static final String V_UNAME_DUP = "110";
		/** 그룹에 있음 */
		public static final String V_GROUP_CONTAIN = "200";
		/** 그룹에 신청했음 */
		public static final String V_GROUP_REQUESTED = "210";
		/** 파일 size 초과 */
		public static final String V_FILE_SIZE = "500";

	}

	/**
	 * session에 저장된 변수 key
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 8. 26.
	 */
	public static class SessionKey {
		/** 로그인 session user */
		public static final String SESSION_LOGIN_USER = "SESSION_LOGIN_USER";

		/** 회원가입 session user */
		public static final String SESSION_REG_USER = "SESSION_REG_USER";

	}

	/**
	 * <pre>
	 * 파일 업로드
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 8. 25.
	 */
	public static class FileUploadDomain {
		/** 회원 프로필 사진 */
		public static final String USER_PROFILE = "user_profile";
		/** 프로젝트 작품 이미지 */
		public static final String PROJECT_WORK_FILE = "project_work_file";
		/** 프로젝트 */
		public static final String PROJECT = "project";
		/** 작품 */
		public static final String PRODUCT = "product";
		/** 의뢰 게시판 */
		public static final String REQUEST_BOARD = "request_board";

	}

	/**
	 * 프로젝트 진행 상태
	 * 
	 * @author devhcc
	 * @since 2016. 8. 25.
	 */
	public static class ProjectProgressStatus {
		/** 진행중인 프로젝트 */
		public static final String PROGRESS = "P";
		/** 완료된 프로젝트 */
		public static final String COMPLETE = "C";
	}

	/**
	 * 정열 순서
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 5.
	 */
	public static class SchOrderType {
		/** 최신순 */
		public static final String LATEST = "L";
		/** 인기순 */
		public static final String HOTTEST = "H";
	}

	/**
	 * 디자이너/제작자 구분
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 8.
	 */
	public static class MemberDiv {
		/** 디자이너 */
		public static final String DESIGNER = "D";
		/** 제작자 */
		public static final String PRODUCER = "P";
		/** 작품 */
		public static final String PRODUCT = "T";
	}

	/**
	 * 등록/수정 플래그
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 8.
	 */
	public static class PageMode {
		/** insert */
		public static final String INSERT = "I";
		/** update */
		public static final String UPDATE = "U";
	}

	/**
	 * CaptCha 이미지 세션 저장 키
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 8.
	 */
	public static class CaptCha {
		/** insert */
		public static final String SESSION_KEY = "CAPTCHA_SESSION_KEY";

	}

	/**
	 * 카테고리 exclude
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 10. 5.
	 */
	public static class CateExclude {
		/** param 이름 */
		public static final String P_NAME = "excludeCodes";
		/** 분할 */
		public static final String P_DELIMETER = ",";
		/** 디자이너 /제작자: 자연 exclude */
		public static final String V_DESI_PROD = "007";
		/** 디자이너/제작자 url */
		public static final List<String> URL_DESI_PROD = Arrays.asList("/designer/designer.do", "/producer/producer.do",
				"/producer/openDesignRequestBoard.do", "/designer/openDesignRequestBoard.do");
	}
	
	/**
	 *
	 * <pre>
	 * 태그 목록*
	 * </pre>
	 * @author hanchanghao
	 * @since 2016. 10. 5.
	 */
	public static class TagList {
		/** 태그 목록*/
		public static final List<String> TAG_LIST = Arrays.asList("DIY", "Smartfashion", "wareable");
	}
	
	

}
