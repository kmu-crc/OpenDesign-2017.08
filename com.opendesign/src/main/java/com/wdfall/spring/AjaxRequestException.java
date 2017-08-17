/*
 * @(#)AjaxRequestException.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2015. 7. 19. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.spring;
/**
 * <pre>
 *
 * </pre>
 * @author Hyun Chul Choi
 * @email devhcchoi@gmail.com
 * @version 1.0 Since 2015. 7. 19.
 */
public class AjaxRequestException extends RuntimeException {
	
	/** */
	private static final long serialVersionUID = -7641787113475570731L;

	public static final int LOG_LEVEL_INFO = 1000;
	public static final int LOG_LEVEL_ERROR = 9999;
	
	public static final String ERR_CODE_SYSTEM = "99";
	public static final String ERR_CODE_DEFAULT = "00";
	
	private int logLevel = LOG_LEVEL_INFO;
	private String code = ERR_CODE_SYSTEM;
	
	public AjaxRequestException(Throwable t){
		super(t);
		this.logLevel = LOG_LEVEL_ERROR;
				
	}
	
	public AjaxRequestException(String msg ){
		super( msg );
		this.code = ERR_CODE_DEFAULT;
		
	}
	
	public AjaxRequestException(String cd, String msg ){
		super( msg );
		this.code = cd;
	}
	
	public String getCode(){
		return code;
	}
	
	public int getLogLevel() {
		return logLevel;
	}
}
