/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.service;

import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.opendesign.utils.PropertyUtil;

/**
 * 
 * <pre>
 * 메일의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class MailService {

	@Autowired 
	private JavaMailSender mailDispatcher;
	
	@Autowired 
	private VelocityEngine velocityEngine;
	
	/**
	 * 로그(log4j) 인스턴스
	 */
	@SuppressWarnings("unused")
	private final Logger LOGGER = LogManager.getLogger(this.getClass());
	
	/**
	 * 메일 발송
	 * 
	 * @param model
	 * @throws MessagingException
	 */
	public void sendSimpleMail(Map<String, Object> model) throws MessagingException {
		
		/*
		 * 보내는 메일 (시스템 프로퍼티)
		 */
		String mailSender = PropertyUtil.getProperty("smtp.mail.sender");
		/*
		 * 받는 메일
		 */
		String mailTarget = (String)model.get("mail.target");
		/*
		 * 메일 제목
		 */
		String mailTitle = (String)model.get("title");
		/*
		 * 메일 템플릿 파일명
		 */
		String mailTemplate = (String)model.get("template");
		
		LOGGER.info("E-Mail Title:["+mailTitle+"], From :["+mailSender+"], To:["+mailTarget+"], \nTemplate :["+mailTemplate+"]");
		
		MimeMessage message = mailDispatcher.createMimeMessage();
		/*
		 * 서버 도메인 설정
		 */
		String globalHost = PropertyUtil.getProperty("global.host");
		model.put("globalHost", globalHost);
		
		/*
		 * 템플릿 변수 바인딩 (*** 변수 적용시 dot[.] 기호는 사용하면 안됨! velocity에서 hierarchy 구조를 의미함)
		 */
        String contents = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine,  mailTemplate, model);
        
		message.setSubject(mailTitle);
		message.setText(contents, "UTF-8", "html");
		message.setRecipient(RecipientType.TO , new InternetAddress(mailTarget));
		
		message.setFrom( new InternetAddress( mailSender ) );
		mailDispatcher.send(message);
		
		
	}
		

}
