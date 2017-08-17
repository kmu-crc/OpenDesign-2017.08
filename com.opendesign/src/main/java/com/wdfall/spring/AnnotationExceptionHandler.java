/*
 * @(#)AnnotationExceptionHandler.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2015. 7. 19. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.spring;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;



/**
 * <pre>
 * 모든 컨트롤러에서 발생하는 exception의 처리를 담당한다.
 * </pre>
 * @author 
 * @email 
 * @version 1.0 Since 2015. 7. 19.
 */
@ControllerAdvice
public class AnnotationExceptionHandler {
	private final Logger logger = Logger.getLogger(AnnotationExceptionHandler.class);
	
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        logger.error(  ex.toString() );
        printExceptionInfo(request, ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("result", "9999");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
 
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        logger.error( ex.toString());
        printExceptionInfo(request, ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("result", "8888");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
     
    
    
    @ExceptionHandler(AjaxRequestException.class)
    public JsonModelAndView handleAjaxRequestException(HttpServletRequest request, AjaxRequestException ex) {
    	if( AjaxRequestException.LOG_LEVEL_INFO == ex.getLogLevel() ){
    		logger.info( ex.toString() );
    	}else{
    		logger.error( ex.toString() );
    		
    	}

        Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String,String>>();
        
        Map<String, String> errorMap = new HashMap<String, String>();
        
        errorMap.put("code", ex.getCode() );
        errorMap.put("message", ex.getMessage());
        
        resultMap.put("error", errorMap );        
        JsonModelAndView mv = new JsonModelAndView(resultMap);
        return mv;
    }
    
    @ExceptionHandler(AccreditationError.class)
    public ModelAndView handleAccreditationError(HttpServletRequest request, AccreditationError ex) {
        logger.warn( ex.toString());
        
        Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String,String>>();
        
        Map<String, String> errorMap = new HashMap<String, String>();
        
        errorMap.put("code", "401" );
        errorMap.put("message", ex.getMessage());
        
        resultMap.put("error", errorMap );        
        JsonModelAndView mv = new JsonModelAndView(resultMap);
        
        return mv;
    }
    
    
    /**
     * 관리자 페이지 접근권한 에러
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        logger.warn( ex.toString());
        
        ModelAndView mv = new ModelAndView("sample/auth/adminAccessDeniedError");
        mv.addObject("result", "9999");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
     
    private void printExceptionInfo(HttpServletRequest request, Exception ex){
        logger.error(request.getParameterMap());
        for( StackTraceElement s : ex.getStackTrace() ){
            logger.error( s);
        }
    }
    
    
}
