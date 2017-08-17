package com.wdfall.sample;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.CookieGenerator;

@Controller
public class CookieHandleController {


	/**
	 * @author Joldo 2015. 9. 14.
	 * @param name 쿠키(name)의 값을 가져온다
	 * @param email 쿠키(email)의 값을 가져온다
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/sample/viewcookie.do")
	public String viewCookie(
			@CookieValue(value="name",defaultValue="") String name, 
			@CookieValue(value="email",defaultValue="") String email,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("Cookie.name = " + name );
		System.out.println("Cookie.email = " + email);
		
		return "sample/viewCookie" ;
	}
	


	@RequestMapping(value="/sample/clearcookie.do")
	public String clearCookie( HttpServletRequest request, HttpServletResponse response) 
	{
		
		CookieGenerator cookieGer = new CookieGenerator();	

		cookieGer.setCookieName("name");
		cookieGer.addCookie(response, "");

		cookieGer.setCookieName("email");
		cookieGer.addCookie(response, "");
		
		return "redirect:/sample/viewcookie.do" ;
	}
	
	@RequestMapping(value="/sample/setcookie.do")
	public String setCookie( HttpServletRequest request, HttpServletResponse response) 
	{
		
		CookieGenerator cookieGer = new CookieGenerator();	

		cookieGer.setCookieName("name");
		cookieGer.addCookie(response, "joldo");

		cookieGer.setCookieName("email");
		cookieGer.addCookie(response, "devhcchoi@gmail.com");
		
		return "redirect:/sample/viewcookie.do" ;
	}
}
