/*
 * @(#)JsonModelAndView.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2015. 7. 10. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.spring;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *
 * </pre>
 * @author Hyun Chul Choi
 * @email devhcchoi@gmail.com
 * @version 1.0 Since 2015. 7. 10.
 */
public class JsonModelAndView extends ModelAndView {

	public JsonModelAndView(Map<String, ?> paramMap) {
		super("jsonView",  paramMap);		
	}


}
