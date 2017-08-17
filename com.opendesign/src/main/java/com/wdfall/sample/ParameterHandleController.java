/*
 * @(#)ParameterHandleController.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2015. 7. 6. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.sample;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wdfall.spring.AjaxRequestException;
import com.wdfall.spring.JsonModelAndView;

//관련 내용 참고 : http://warmz.tistory.com/727
@Controller
@RequestMapping(value="/sample")
public class ParameterHandleController {

	@Autowired
	ParameterHandleService parameterHandleService;	/** 스프링에서 생성한 ParameterHandleService 객체를 자동으로 바인딩  **/

	@RequestMapping(value = "/parameterHandleForm.do", method = RequestMethod.GET)
	public String showInputForm() {
		return "sample/parameterHandleForm";
	}
	
	@RequestMapping(value="/handleParameter.do")
	public ModelAndView handleParameter( 
			@RequestParam(value="id", required=true) String id,
			@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam(value="hobby", required=false) String[] hobby,
			HttpServletRequest request) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		resultMap.put("name", name);
		resultMap.put("age", age);
		resultMap.put("hobby", hobby);
		
		
		Map<String, Object> profileMap = new HashMap<String, Object>();
		profileMap.put("id", id);
		profileMap.put("name", name);
		profileMap.put("age", age);
		profileMap.put("hobby", hobby);
		resultMap.put("profile", profileMap);
		
    	ModelAndView modelAndView = new ModelAndView("sample/parameterHandleResult",resultMap);	
		return modelAndView;		
	}
	
	
	// 
	/**
	 * Ajax (response body) return
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/testAjax.ajax")	//반드시 확장자는 .ajax 여야 함.
	public ModelAndView  insertPartnerCompany(
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="name", required=true) String name,
			HttpServletRequest request) 
	{
		
		if( id != null && id.length() == 0 ){
			throw new AjaxRequestException("00", "ID is empty");
			//OR throw new AjaxRequestException( "ID is empty");
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		resultMap.put("name", name);
    	//ModelAndView modelAndView = new ModelAndView("jsonView",resultMap);	//반드시 모델명은 "jsonView"여야 함.
    	JsonModelAndView jmv = new JsonModelAndView( resultMap );
    	
		return jmv;
	}
	
	
}
