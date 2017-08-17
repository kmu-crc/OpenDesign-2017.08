/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.utils;

/**
 * 
 * String 처리 유틸리티
 * 
 * @author devhcc
 *
 */
public class StringUtil {
	
	/**
	 * 문자열이 null이 아닌 경우를 확인한다.
	 * @param str : 문자열
	 * @return boolean
	 */
	public static boolean isNotNull(String str){
		return str != null;
	}
	
	/**
	 * 문자열이 null인 경우를 확인한다.
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String str){
		return ! isNotNull(str);
	}
	
	/**
	 * 문자열이 null이 아니고 공백이 아닌경우를 확인한다.
	 * 빈 공백들 들어간 경우도 해당 한다. (" ")
	 * @param str : 문자열
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str){
		return isNotNull(str) && ! "".equals(str.trim());
	}
	
	/**
	 * 두문자열을 비교 한다.
	 * 문자열 1이 null인 경우 false를 반환한다.
	 * @param str1 : 문자열 1 
	 * @param str2 : 문자열 2
	 * @return boolean
	 */
	public static boolean equals(String str1, String str2){
		return isNotNull(str1) ? str1.equals(str2) : false;
	}
	
	/**
	 * 두문자열이 같은 경우 반환 값을 받는다.
	 * 아닌  경우 공백을 반환 받는다.("")
	 * @param str1 : 문자열 1 
	 * @param str2 : 문자열 2
	 * @param returnStr : true인 경우 반환 되는 값
	 * @return String
	 */
	public static String equals(String str1, String str2, String returnStr){
		return equals(str1, str2) ? returnStr : "";
	}
	
	/**
	 * 문자열이 null일 경우 공백을 반환한다.
	 * @param str : 문자열
	 * @return String
	 */
	public static String nullToBlank(String str){
		return nullToString(str, "");
	}
	
	/**
	 * 문자열이 null일 경우 반환값을 반환한다.
	 * @param str : 문자열
	 * @param returnStr : 반환값
	 * @return String
	 */
	public static String nullToString(String str, String returnStr){
		return isNotNull(str) ? str : returnStr;
	}
	
	/**
	 * 문자열이 빈 문자열이라면 반환값을 반환한다.
	 * @param str : 문자열 
	 * @param returnStr : 반환값
	 * @return String
	 */
	public static String emptyToString(String str, String returnStr){
		return isNotEmpty(str) ? str : returnStr;
	}
	
	/**
	 * 마지막 문자열에 포함되어 있는 delimeter 를 삭제한다.
	 * @param str
	 * @param delimeter
	 * @return
	 */
	public static String removeLastDelimeter(String str, String delimeter) {
		if( str != null && str.length() > 0 ){
			return str.substring(0, str.lastIndexOf( delimeter ) );
		}else{
			return str;	
		}		
	}
	
}
