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
 * @author 
 * @email 
 * @version 
 */
@SuppressWarnings("serial")
public class AccreditationError extends RuntimeException {
	
	public AccreditationError(String msg) {
        super(msg);
    }
	
	public AccreditationError(String msg, Throwable t) {
        super(msg, t);
    }
}
