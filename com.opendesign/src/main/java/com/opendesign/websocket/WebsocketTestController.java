/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.websocket.SocketHandler.Command;
import com.opendesign.websocket.SocketHandler.MessageConst;
import com.opendesign.websocket.SocketHandler.SessionMatchForProjectView;
import com.wdfall.spring.JsonModelAndView;

/**
 *
 * <pre>
 * WebsocketTestController 테스트용
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 26.
 */
@Controller
@RequestMapping(value = "/websocket")
public class WebsocketTestController {

	@SuppressWarnings("unused")
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Autowired
	SocketHandler websocketHandler;

	/**
	 * websocketSend Test
	 * 
	 * @param request:
	 *            projectSeq
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/send.do")
	public ModelAndView websocketSend(HttpServletRequest request) throws IOException {
		HashMap<String, Object> paramMap = new HashMap<>();
		//
		final String projectSeq = request.getParameter("projectSeq");
		Map<String, Object> body = new HashMap<>();
		body.put(MessageConst.COMMAND, Command.OUT_NOTIFY_PROJECT_CHANGED);
		body.put(MessageConst.PROJECT_SEQ, projectSeq);

		websocketHandler.sendMessage(new SessionMatchForProjectView(projectSeq), body);

		paramMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return new JsonModelAndView(paramMap);
	}

}
