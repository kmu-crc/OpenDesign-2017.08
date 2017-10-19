/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.opendesign.utils.CmnConst.SessionKey;
import com.opendesign.vo.UserVO;
import com.wdfall.security.SHA256Encryptor;

/**
 * 
 * <pre>
 * 공통 사용 유틸리티
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 25.
 */
public class CmnUtil {

	private static final Logger LOGGER = LogManager.getLogger(CmnUtil.class);

	/**
	 * 로그인 여부 체크
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isUserLogin(HttpServletRequest request) {
		UserVO user = getLoginUser(request);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 로그인된 회원
	 * 
	 * @param request
	 * @return
	 */
	public static UserVO getLoginUser(HttpServletRequest request) {
		UserVO user = (UserVO) request.getSession().getAttribute(SessionKey.SESSION_LOGIN_USER);
		return user;
	}

	/**
	 * 로그인된 회원 Seq
	 * 
	 * @param request
	 * @return 없으면 ""
	 */
	public static String getLoginUserSeq(UserVO loginUserVO) {
		if (loginUserVO != null) {
			return loginUserVO.getSeq();
		} else {
			return "";
		}
	}

	/**
	 * 업로드 된 파일 정보
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 8.
	 */
	public static class UpFileInfo {
		/** server쪽 file */
		private File upfile;
		/** dp에 들어갈 Path */
		private String dbPath;
		/** filename */
		private String filename;

		public File getUpfile() {
			return upfile;
		}

		public void setUpfile(File upfile) {
			this.upfile = upfile;
		}

		public String getDbPath() {
			return dbPath;
		}

		public void setDbPath(String dbPath) {
			this.dbPath = dbPath;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

	}

	/**
	 * 파일 업로드 처리
	 * 
	 * @param request
	 * @param fileParamName
	 * @param subDomain
	 * @return dbPath
	 * @throws IOException
	 */
	public static List<UpFileInfo> handleMultiFileUpload(MultipartHttpServletRequest request, String fileParamName,
			String subDomain) throws IOException {
		List<MultipartFile> reqFileList = request.getFiles(fileParamName);
		List<UpFileInfo> resultList = new ArrayList<UpFileInfo>();
		for (MultipartFile reqFile : reqFileList) {
			UpFileInfo upInfo = new UpFileInfo();
			if (reqFile != null) {
				String fileUploadDir = CmnUtil.getFileUploadDir(request, subDomain);
				String saveFileName = UUID.randomUUID().toString();
				File file = CmnUtil.saveFile(reqFile, fileUploadDir, saveFileName);
				String fileUploadDbPath = CmnUtil.getFileUploadDbPath(request, file);
				upInfo.setUpfile(file);
				upInfo.setDbPath(fileUploadDbPath);
				upInfo.setFilename(reqFile.getOriginalFilename());
			}
			resultList.add(upInfo);
		}
		return resultList;
	}

	/**
	 * 파일 업로드 처리
	 * 
	 * @param request
	 * @param fileParamName
	 * @param subDomain
	 * @return dbPath
	 * @throws IOException
	 */
	public static String handleFileUpload(MultipartHttpServletRequest request, String fileParamName, String subDomain)
			throws IOException {
		MultipartFile reqFile = request.getFile(fileParamName);
		String fileUploadDbPath = "";
		if (reqFile != null) {
			String fileUploadDir = CmnUtil.getFileUploadDir(request, subDomain);
			String saveFileName = UUID.randomUUID().toString();
			File file = CmnUtil.saveFile(reqFile, fileUploadDir, saveFileName);
			fileUploadDbPath = CmnUtil.getFileUploadDbPath(request, file);
		}
		return fileUploadDbPath;
	}

	/**
	 * 원 파일명 가져오기
	 * 
	 * @param request
	 * @param fileParamName
	 * @param subDomain
	 * @return
	 * @throws IOException
	 */
	public static String handleFileUploadGetOriFileName(MultipartHttpServletRequest request, String fileParamName)
			throws IOException {
		MultipartFile reqFile = request.getFile(fileParamName);
		String oriFileName = "";
		if (reqFile != null) {
			oriFileName = reqFile.getOriginalFilename();
		}
		return oriFileName;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param multipartFile
	 * @param fileUploadDir
	 * @param saveFileName
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static File saveFile(MultipartFile multipartFile, String fileUploadDir, String saveFileName)
			throws IllegalStateException, IOException {
		String originalFilename = multipartFile.getOriginalFilename();
		String ext = FilenameUtils.getExtension(originalFilename);
		saveFileName = saveFileName + "." + ext;

		// System.out.println(saveFileName);
		// System.out.println(fileUploadDir);

		File directory = new File(fileUploadDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(directory, saveFileName);
		multipartFile.transferTo(file);

		return file;
	}
	
	/**
	 * 파일 복사
	 * 
	 * @param oldFilePath
	 * @param newFilePath
	 */
	public static void fileCopy(String oldFilePath, String newFilePath) {
		File oldFile = new File(oldFilePath);
		File newFile = new File(newFilePath);
		try {
			FileInputStream inputStream = new FileInputStream(oldFile);
			FileOutputStream outputStream = new FileOutputStream(newFile);
			FileChannel fcin = inputStream.getChannel();
			FileChannel fcout = outputStream.getChannel();

			long size = fcin.size();
			fcin.transferTo(0, size, fcout);

			fcout.close();
			fcin.close();
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 파일 복사
	 * 
	 * @param oldFilePath
	 * @param oldFileName
	 * @param newFilePath
	 * @param newFileName
	 */
	public static void fileCopy(String oldFilePath, String oldFileName, String newFilePath, String newFileName) {
		File oldFile = new File(oldFilePath, oldFileName);
		File newFile = new File(newFilePath, newFileName);
		try {
			FileInputStream inputStream = new FileInputStream(oldFile);
			FileOutputStream outputStream = new FileOutputStream(newFile);
			FileChannel fcin = inputStream.getChannel();
			FileChannel fcout = outputStream.getChannel();

			long size = fcin.size();
			fcin.transferTo(0, size, fcout);

			fcout.close();
			fcin.close();
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 파일 업로드 기본 목록
	 * 
	 * @param request
	 * @return
	 */
	public static String getFileUploadDir(HttpServletRequest request, String subDomain) {
		// 
		String realBasePath = request.getServletContext().getRealPath("/resources/km_upload");
		String fileUploadDir = realBasePath + File.separator + subDomain;
		return fileUploadDir;
	}

	/**
	 * 파일 업로드 database에 저장할 path
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileUploadDbPath(HttpServletRequest request, File file) throws IOException {
		String realRootPath = request.getServletContext().getRealPath("/");
		String dbPath = file.getCanonicalPath().replace(realRootPath, "/");
		dbPath = dbPath.replaceAll("\\\\", "/");
		dbPath = dbPath.replaceAll("//", "/");
		return dbPath;
	}

	/**
	 * database path로 실제 path 조회
	 * 
	 * @param request
	 * @param dbPath
	 * @return
	 * @throws IOException
	 */
	public static String getRealPathFromDbPath(HttpServletRequest request, String dbPath) throws IOException {
		String result = "";
		String realRootPath = request.getServletContext().getRealPath("/");
		result = realRootPath + dbPath;
		result = result.replaceAll("//", "/");
		return result;
	}

	/**
	 * 공통으로 vo 의 등록일시 / 수정일시를 세트해준다.
	 * 
	 * @param vo
	 */
	public static void setCmnDate(Object vo) {
		String curDateStr = Day.getCurrentDateString("yyyyMMddHHmm");
		Class<?> clazz = vo.getClass();
		// 등록일시
		Field regTimeField = ReflectionUtils.findField(clazz, "registerTime");
		if (regTimeField != null) {
			ReflectionUtils.makeAccessible(regTimeField);
			ReflectionUtils.setField(regTimeField, vo, curDateStr);
		}
		// 수정일시
		Field updTimeField = ReflectionUtils.findField(clazz, "updateTime");
		if (updTimeField != null) {
			ReflectionUtils.makeAccessible(updTimeField);
			ReflectionUtils.setField(updTimeField, vo, curDateStr);
		}
	}

	/**
	 * 현재시간 "yyyyMMddHHmm"
	 * 
	 * @return
	 */
	public static String getCurrentDateStr() {
		return Day.getCurrentDateString("yyyyMMddHHmm");
	}

	/**
	 * addAll
	 * 
	 * @param toAddList
	 * @param col
	 */
	public static <T> void addAll(Collection<T> toAddList, Collection<T> col) {
		if (col != null && toAddList != null) {
			toAddList.addAll(col);
		}
	}

	/**
	 * empty 판단
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null) || (collection.isEmpty());
	}

	/**
	 * empty 판단
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(String[] array) {
		return (array == null) || (array.length == 0);
	}

	/**
	 * empty 판단
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * 다음 버전 계산: (+0.1)
	 * 
	 * @param lastVer
	 * @return
	 */
	public static String getNextVersion(String lastVer) {
		double dVer = Double.valueOf(lastVer);
		return new DecimalFormat("###.0").format((dVer + 0.1));
	}

	private static class TIME_MAXIMUM {
		public static final int SEC = 60;
		public static final int MIN = 60;
		public static final int HOUR = 24;
		public static final int DAY = 30;
		public static final int MONTH = 12;
	}

	/**
	 * 시간 나타내는 법
	 * 
	 * <pre>
	 * 1분이내-방금전, 1시간 이내-분단위, 24시간 이내-시단위, 
	 * 한달이내-일단위,1년 이내-달단위, 그 이후-년단위
	 * </pre>
	 * 
	 * @param time:
	 *            "yyyyMMddHHmm"
	 * @return
	 */
	public static String getDisplayTime(String inTimeStr) {
		if (StringUtils.isEmpty(inTimeStr)) {
			return "";
		}
		Date inDate = Day.getDateWithFormatString(inTimeStr, "yyyyMMddHHmm");

		long curTime = System.currentTimeMillis();
		long regTime = inDate.getTime();
		long diffTime = (curTime - regTime) / 1000;

		String msg = "";
		if (diffTime < TIME_MAXIMUM.SEC) {
			// sec
			msg = "방금 전";
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			// min
			msg = diffTime + "분 전";
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			// hour
			msg = (diffTime) + "시간 전";
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			// day
			msg = (diffTime) + "일 전";
		} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
			// month
			msg = (diffTime) + "달 전";
		} else {
			diffTime /= TIME_MAXIMUM.MONTH;
			msg = (diffTime) + "년 전";
		}

		return msg;
	}

	/**
	 * 포맷팅 된 시간 "yyyy.MM.dd"
	 * 
	 * @param inTimeStr
	 * @return
	 */
	public static String getDisplayTimeYMD(String inTimeStr) {
		if (StringUtils.isEmpty(inTimeStr)) {
			return "";
		}
		Date inDate = Day.getDateWithFormatString(inTimeStr, "yyyyMMddHHmm");
		return Day.toString(inDate, "yyyy.MM.dd");
	}

	/**
	 * 숫자 format "#,###"
	 * 
	 * @param number
	 * @return
	 */
	public static String getDisplayNumber(String number) {
		if (StringUtils.isEmpty(number) || !StringUtils.isNumeric(number)) {
			return "0";
		}
		Double dNum = Double.valueOf(number);
		DecimalFormat fm = new DecimalFormat("#,###");
		return fm.format(dNum);
	}

	/**
	 * 파일 size 계산
	 * 
	 * @return KB
	 */
	public static String getCalcFileSizeFromUrl(HttpServletRequest request, String fileUrl) {
		if (StringUtils.isEmpty(fileUrl)) {
			return "0";
		}
		String realPath = "";
		try {
			realPath = getRealPathFromDbPath(request, fileUrl);
		} catch (IOException e) {
			//
		}
		long resultInByte = 0L;
		File file = new File(realPath);
		if (file.exists()) {
			resultInByte = file.length();
		}
		long resultInKB = resultInByte / 1024;
		return getDisplayNumber(String.valueOf(resultInKB));
	}

	/**
	 * main page 항목수 계산
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 22.
	 */
	public static class MainPageParamCalc {
		/**
		 * 프로젝트 개수
		 * 
		 * @return
		 */
		public static String getProjectCount() {
			return PropertyUtil.getProperty("main.page.count.project");
		}

		/**
		 * 작품 개수
		 * 
		 * @return
		 */
		public static String getWorkCount() {
			return PropertyUtil.getProperty("main.page.count.work");
		}
	}

	/**
	 * int 값 가져옴
	 * 
	 * @param prop
	 * @return
	 */
	public static Integer getIntValue(String prop) {
		int result = 0;
		try {
			result = Integer.valueOf(prop);
		} catch (NumberFormatException ignored) {
			// ignored
		}
		return result;
	}

	/**
	 * 비밀번호 encrypt
	 */
	public static String encryptPassword(String password) {
		String result = "";
		try {
			if (password != null) {
				result = SHA256Encryptor.encrypt(password);
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return result;
	}

	/**
	 * 카테고리 대분류
	 * @param cateCode
	 */
	public static String getCodeDepth1(String cateCode) {
		if (StringUtils.isEmpty(cateCode) || cateCode.length() < 3) {
			return "";
		}
		return cateCode.substring(0, 3);
	}
	/**
	 * 카테고리 중분류
	 * @param cateCode
	 */
	public static String getCodeDepth2(String cateCode) {
		if (StringUtils.isEmpty(cateCode) || cateCode.length() < 6) {
			return "";
		}
		return cateCode.substring(0, 6);
	}
	/**
	 * 카테고리 소분류
	 * @param cateCode
	 */
	public static String getCodeDepth3(String cateCode) {
		if (StringUtils.isEmpty(cateCode) || cateCode.length() < 9) {
			return "";
		}
		return cateCode.substring(0, 9);
	}
	
	private static final char[] magnitudes = {'k', 'M', 'G', 'T', 'P', 'E'}; // enough for long
	
	/**
	 * 포인트 나타낼 형식
	 * @param number
	 * @return
	 */
	public static String nFormatter(long number) {
	    String ret;
	    if (number >= 0) {
	        ret = "";
	    } else if (number <= -9200000000000000000L) {
	        return "-9.2E";
	    } else {
	        ret = "-";
	        number = -number;
	    }
	    if (number < 1000)
	        return ret + number;
	    for (int i = 0; ; i++) {
	        if (number < 10000 && number % 1000 >= 100)
	            return ret + (number / 1000) + '.' + ((number % 1000) / 100) + magnitudes[i];
	        number /= 1000;
	        if (number < 1000)
	            return ret + number + magnitudes[i];
	    }
	}
	
	/**
	 * 포인트 나타낼 형식
	 * @param number
	 * @return
	 */
	public static String nFormatter(String number) {
		if( StringUtils.isEmpty(number) ) {
			number = "0";
		}
		
		return nFormatter(Long.valueOf(number));
	}
	
	
	private static String handleHtmlEnterRN2BR(String contents) {
		if(StringUtils.isEmpty(contents)) {
			return "";
		}
		return contents.replaceAll("\r\n", "<br/>");
	}
	
	private static String handleHtmlEnterBR2RN(String contents) {
		if(StringUtils.isEmpty(contents)) {
			return "";
		}
		return contents.replaceAll("<br/>", "\r\n");
	}
	
	/**
	 * client to server: html enter 줄바꿈 처리
	 * @param vo
	 * @param fieldName
	 */
	public static void handleHtmlEnterRN2BR(Object vo, String fieldName) {
		if(vo == null) {
			return;
		}
		
		Class<?> clazz = vo.getClass();
		// 
		Field aField = ReflectionUtils.findField(clazz, fieldName);
		if (aField != null) {
			ReflectionUtils.makeAccessible(aField);
			String contents = (String)ReflectionUtils.getField(aField, vo);
			contents = handleHtmlEnterRN2BR(contents);
			ReflectionUtils.setField(aField, vo, contents);
		}
	}
	
	/**
	 * server to client: html enter 줄바꿈 처리
	 * @param vo
	 * @param fieldName
	 */
	public static void handleHtmlEnterBR2RN(Object vo, String fieldName) {
		if(vo == null) {
			return;
		}
		
		Class<?> clazz = vo.getClass();
		// 
		Field aField = ReflectionUtils.findField(clazz, fieldName);
		if (aField != null) {
			ReflectionUtils.makeAccessible(aField);
			String contents = (String)ReflectionUtils.getField(aField, vo);
			contents = handleHtmlEnterBR2RN(contents);
			ReflectionUtils.setField(aField, vo, contents);
		}
	}
	
	
	/**
	 * 이미지인지 판단:(suffix로부터)
	 * @param file
	 * @return
	 */
	public static boolean isImageFile(String fileName) {
		if(CmnUtil.isEmpty(fileName)) {
			return false;
		}
		String[] validSuffixes = {"jpg", "jpeg", "png", "gif", "bmp"};
		for( String suffix : validSuffixes ) {
			String filePath = fileName.toLowerCase();
			if( filePath.endsWith(suffix) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 문서인지 판단:(suffix로부터)
	 * @param file
	 * @return
	 */
	public static boolean isDocuFile(String fileName) {
		if(CmnUtil.isEmpty(fileName)) {
			return false;
		}
		String[] validSuffixes = {"pdf", "ppt", "pptx", "doc", "docx"};
		for( String suffix : validSuffixes ) {
			String filePath = fileName.toLowerCase();
			if( filePath.endsWith(suffix) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(nFormatter(-111));
	}

}
