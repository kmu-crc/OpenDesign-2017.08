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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.opendesign.utils.CmnConst.FileUploadDomain;

public class ControllerUtil {

	/**
	 * Request 에 전달되는 모든 파라미터 Map 을 던진다. 배열 형식의 파라미터는 java.util.Array 로 처리된다.
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> createParamMap(HttpServletRequest request) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 2015.09.13 joldo
		// String updateID = SessionManager.getAdminId(request.getSession());
		// String token = request.getParameter("token");
		// paramMap.put("updateID", StringUtils.isEmpty(updateID) ?
		// "${anonymous}" : updateID );
		// paramMap.put("token", StringUtils.isEmpty(token) ? "${anonymous}" :
		// token );

		@SuppressWarnings("rawtypes")
		Enumeration enums = request.getParameterNames();
		while (enums.hasMoreElements()) {

			String paramName = (String) enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

			// Parameter가 배열일 경우
			if (parameters.length > 1) {
				for (int i = 0; i < parameters.length; i++) {
					String param = StringUtils.stripToEmpty(parameters[i]);
					parameters[i] = param;
				}
				paramMap.put(paramName, parameters);
				// Parameter가 배열이 아닌 경우
			} else {
				paramMap.put(paramName, StringUtils.stripToEmpty(parameters[0]));
			}
		}
		
		
		request.setAttribute("param_map", paramMap);

		return paramMap;
	}
	
	public static boolean isImageFile(HttpServletRequest request, String filePathOnWebBase, String fileDomain) {
		
		String fileUploadDir = CmnUtil.getFileUploadDir(request, fileDomain);
		//String fileName = filePathOnWebBase.substring(filePathOnWebBase.lastIndexOf(File.separator) );
		String fileName = File.separator + FilenameUtils.getName(filePathOnWebBase);
		File file = new File(fileUploadDir + fileName);
		
		return ThumbnailManager.isImageFile(file);
	}
	
	
	public static String getHostName(HttpServletRequest request ) {
		
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int portNumber = request.getServerPort();
		String contextPath = request.getContextPath();
		
		String host = scheme + "://" + serverName + contextPath + ":" + portNumber;
		if( portNumber == 80 ) {
			host = scheme + "://" + serverName + contextPath;
		}
		
		return host;
	}
	
	
}
