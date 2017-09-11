/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.MailService;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.ControllerUtil;
import com.wdfall.spring.JsonModelAndView;

/**
 * 
 * <pre>
 * 메일에 관한 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Controller
@RequestMapping(value="/mail")
public class SimpleMailController {
	
	@RequestMapping(value = "/mail.do")
	public ModelAndView mail(HttpServletRequest request) {

		return new ModelAndView("mail/mail");
	}

	/**
	 * 메일 서비스 인스턴스
	 */
	@Autowired
	MailService service;
	
	/**
	 * 로그(log4j) 인스턴스
	 */
	@SuppressWarnings("unused")
	private final Logger LOGGER = LogManager.getLogger(this.getClass());
	
	/**
	 * 메일 발송
	 * 
	 * @param request
	 * @return
	 * @throws MessagingException
	 */
	@RequestMapping(value="/sendSimple.ajax")
	public JsonModelAndView sendSimpleMail(final HttpServletRequest request ) throws MessagingException {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		try {
			/*
			 * 메일 템플릿 모델
			 */
			Map model = ControllerUtil.createParamMap(request);
			
			/*
			 * 메일 서비스 동기방식으로 처리
			 */
			service.sendSimpleMail(model);
			
		} catch( Exception e ) {
			resultMap.put(RstConst.P_NAME, RstConst.V_FAIL);
		}
		
		return new JsonModelAndView(resultMap);
	}
		

}
