package com.wdfall.sample;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SendmailSampleController {

	
	@Autowired 
	private JavaMailSender mailSender;
	
	
	@RequestMapping(value="/sample/sendmain.do")
	public String sendmail(final HttpServletRequest request, @RequestParam("email") String email ) throws MessagingException {
		//mail.noreply

		
		
		MimeMessage message = mailSender.createMimeMessage();
		
		String contents = "test contents";
		
		message.setSubject("[Mail Title]");
		message.setText(contents, "UTF-8", "html");
		
		message.setRecipient(RecipientType.TO , new InternetAddress(email));
		
		message.setFrom( new InternetAddress( "email address" ) );
		mailSender.send(message);
		return "";
	}
		

}
