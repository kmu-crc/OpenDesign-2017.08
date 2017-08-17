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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendesign.utils.CmnConst.SessionKey;
import com.opendesign.vo.UserVO;

/**
 * 
 * <pre>
 * 웹소켓의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
public class SocketHandler extends TextWebSocketHandler implements InitializingBean {

	private static final Logger LOGGER = LogManager.getLogger(SocketHandler.class);
	/**
	 * WebSocketSessionWrapper Set
	 */
	private Set<WebSocketSessionWrapper> sessionWrapperSet = new HashSet<WebSocketSessionWrapper>();

	/**
	 * command map
	 */
	private Map<String, Command> commands = new HashMap<>();

	public SocketHandler() {
		super();

		LOGGER.info(">>> create SocketHandler instance!");

		initInCommands();

	}

	private void initInCommands() {
		commands.put(Command.IN_HANDSHAKE, new CommandForHandShake());
		commands.put(Command.IN_HEARTBEAT, new CommandForHeartbeat());
	}

	/**
	 * 연결 끊힘
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		try {
			super.afterConnectionClosed(session, status);

			WebSocketSessionWrapper sessionWrapper = findSessionWrapperById(sessionWrapperSet, session.getId());
			sessionWrapperSet.remove(sessionWrapper);
			LOGGER.info(">>> remove session!" + String.valueOf(sessionWrapper));
			LOGGER.info(">>> sessionSet.size()=" + sessionWrapperSet.size());
		} catch (Exception ex) {
			LOGGER.error(">>> afterConnectionClosed", ex);
			throw ex;
		}
	}

	/**
	 * 연결 확립
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		try {
			super.afterConnectionEstablished(session);

			WebSocketSessionWrapper sessionWrapper = new WebSocketSessionWrapper(session);
			sessionWrapperSet.add(sessionWrapper);
			LOGGER.info(">>> add session!" + sessionWrapper.toString());
			LOGGER.info(">>> sessionSet.size()=" + sessionWrapperSet.size());
		} catch (Exception ex) {
			LOGGER.error(">>> afterConnectionEstablished", ex);
			throw ex;
		}
	}

	/**
	 * msg 처리
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		try {
			super.handleTextMessage(session, message);

			LOGGER.info(">>> receive message:" + message.toString());

			// message 분석. from message
			String msgStr = (String) message.getPayload();
			Map<String, String> param = new HashMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(msgStr, new TypeReference<Map<String, String>>() {
			});

			// message 처리:
			Command cmd = commands.get(param.get(MessageConst.COMMAND));
			Map<String, String> result = cmd.execute(sessionWrapperSet, session, param);
			LOGGER.info(">>> cmd.execute:" + result);
		} catch (Exception ex) {
			LOGGER.error(">>> handleMessage", ex);
			throw ex;
		}
	}

	/** IE 11에서 pongMessage를 보내옴, chrome에서는 안보내는것 같음 */
	@Override
	public void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		LOGGER.info(">>> handlePongMessage");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// ignore
		// LOGGER.debug(">>> web socket error!", exception);
	}

	@Override
	public boolean supportsPartialMessages() {
		LOGGER.info(">>> call method!");
		return super.supportsPartialMessages();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 클라이언트로 메시지 보냄
	 * 
	 * @param to
	 * @param params
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public void sendMessage(SessionMatch to, Map<String, Object> params) throws IOException {
		try {
			List<WebSocketSessionWrapper> targets = findSessionWrapper(sessionWrapperSet, to);

			// convert map to JSON string
			ObjectMapper mapper = new ObjectMapper();
			String message;
			try {
				message = mapper.writeValueAsString(params);
			} catch (JsonProcessingException e) {
				LOGGER.error(e);
				throw new IOException(e);
			}
			LOGGER.info(">>> sendMessage: " + message);

			// send
			for (WebSocketSessionWrapper target : targets) {
				target.sendMessage(message);
			}
		} catch (Exception ex) {
			LOGGER.error(">>> sendMessage", ex);
			throw ex;
		}
	}

	/**
	 * 프로젝트 변경 통지
	 * 
	 * @param projectSeq
	 * @throws JsonProcessingException
	 */
	public void notifyProjectChanged(String projectSeq) {
		LOGGER.debug(">>> notifyProjectChanged: projectSeq=" + projectSeq);

		Map<String, Object> body = new HashMap<>();
		body.put(MessageConst.COMMAND, Command.OUT_NOTIFY_PROJECT_CHANGED);
		body.put(MessageConst.PROJECT_SEQ, projectSeq);

		try {
			sendMessage(new SessionMatchForProjectView(projectSeq), body);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * 알림 변경 통지
	 * 
	 * @param alarmVO
	 */
	public void notifyAlarmChanged(String userSeq) {
		LOGGER.debug(">>> notifyAlarmChanged: userSeq=" + userSeq);

		// 2. websocket 처리:
		// findUser / cmd
		Map<String, Object> body = new HashMap<>();
		body.put(MessageConst.COMMAND, Command.OUT_NOTIFY_ALARM_CHANGED);

		try {
			sendMessage(new SessionMatchForUserView(userSeq), body);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * 메시지 변경 통지
	 * 
	 * @param recieveSeq
	 */
	public void notifyMsgChanged(String userSeq) {
		LOGGER.debug(">>> notifyMsgChanged: userSeq=" + userSeq);

		// 2. websocket 처리:
		// findUser / cmd
		Map<String, Object> body = new HashMap<>();
		body.put(MessageConst.COMMAND, Command.OUT_NOTIFY_MSG_CHANGED);

		try {
			sendMessage(new SessionMatchForUserView(userSeq), body);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * Command
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static interface Command {
		// ====IN
		// 초기화
		String IN_HANDSHAKE = "IN_HANDSHAKE";
		// client와 server connection을 살리는 작용
		String IN_HEARTBEAT = "IN_HEARTBEAT";

		// ====OUT
		// 프로젝트 변경 일어남
		String OUT_NOTIFY_PROJECT_CHANGED = "OUT_NOTIFY_PROJECT_CHANGED";
		// 알림 변경 일어남
		String OUT_NOTIFY_ALARM_CHANGED = "OUT_NOTIFY_ALARM_CHANGED";
		// 메시지 변경 일어남
		String OUT_NOTIFY_MSG_CHANGED = "OUT_NOTIFY_MSG_CHANGED";

		// ===
		Map<String, String> execute(Set<WebSocketSessionWrapper> sessionWrapperSet, WebSocketSession session,
				Map<String, String> param);
	}

	/**
	 * 통지 메시지 속성 상수
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static interface MessageConst {
		String COMMAND = "cmd";
		String URI = "uri";
		String PROJECT_SEQ = "projectSeq";
	}

	/**
	 * session 초기화 Command
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static class CommandForHandShake implements Command {
		private final Logger LOGGER = LogManager.getLogger(this.getClass());

		@Override
		public Map<String, String> execute(Set<WebSocketSessionWrapper> sessionWrapperSet, WebSocketSession session,
				Map<String, String> param) {
			WebSocketSessionWrapper sessionWrapper = findSessionWrapperById(sessionWrapperSet, session.getId());
			String uri = param.get(MessageConst.URI);
			sessionWrapper.setUri(uri);
			LOGGER.info(">>> CommandForHandShake: uri=" + uri);
			return new HashMap<>();
		}
	}

	/**
	 * clent와 server 연결을 살리는 command
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static class CommandForHeartbeat implements Command {
		private final Logger LOGGER = LogManager.getLogger(this.getClass());

		@Override
		public Map<String, String> execute(Set<WebSocketSessionWrapper> sessionWrapperSet, WebSocketSession session,
				Map<String, String> param) {
			LOGGER.info(">>> CommandForHeartbeat: session.getId()=" + session.getId());
			// do nothing
			return new HashMap<>();
		}
	}

	/**
	 * session 찾는 규칙
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static interface SessionMatch {
		boolean match(WebSocketSessionWrapper sessionWrapper);
	}

	private static List<WebSocketSessionWrapper> findSessionWrapper(Set<WebSocketSessionWrapper> sessionSet,
			SessionMatch match) {
		List<WebSocketSessionWrapper> matched = new ArrayList<>();
		for (WebSocketSessionWrapper webSocketSessionWrapper : sessionSet) {
			if (match.match(webSocketSessionWrapper)) {
				matched.add(webSocketSessionWrapper);
			}
		}
		return matched;
	}

	private static WebSocketSessionWrapper findSessionWrapperById(Set<WebSocketSessionWrapper> sessionWrapperSet,
			String sessionId) {
		List<WebSocketSessionWrapper> resultList = findSessionWrapper(sessionWrapperSet,
				new SessionMatchById(sessionId));
		if (resultList.size() > 0) {
			return resultList.get(0);
		} else {
			LOGGER.error(">>> findSessionWrapperById is NULL");
			return new WebSocketSessionWrapper(null);
		}
	}

	/**
	 * projectSeq로 프로젝트 관련된 session 찾음
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static class SessionMatchForProjectView implements SessionMatch {
		String projectSeq;

		public SessionMatchForProjectView(String projectSeq) {
			this.projectSeq = projectSeq;
		}

		@Override
		public boolean match(WebSocketSessionWrapper sessionWrapper) {
			return sessionWrapper.getUri().indexOf("/project/") > -1
					&& sessionWrapper.getUri().indexOf("projectSeq=" + this.projectSeq) > -1;
		}
	}

	/**
	 * SessionMatchForUserView: 회원seq에 따라 session 찾음
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static class SessionMatchForUserView implements SessionMatch {
		String userSeq;

		public SessionMatchForUserView(String userSeq) {
			this.userSeq = userSeq;
		}

		@Override
		public boolean match(WebSocketSessionWrapper sessionWrapper) {
			UserVO userVO = sessionWrapper.getLoginUser();
			return (userVO != null && userSeq.equals(userVO.getSeq()));
		}
	}

	/**
	 * sessionId로 session 찾음
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static class SessionMatchById implements SessionMatch {
		String sessionId = "";

		public SessionMatchById(String sessionId) {
			this.sessionId = sessionId;
		}

		@Override
		public boolean match(WebSocketSessionWrapper sessionWrapper) {
			return sessionId.equals(sessionWrapper.getSocketSessionId());
		}
	}

	/**
	 * <pre>
	 * WebSocketSessionWrapper
	 * </pre>
	 * 
	 * @author hanchanghao
	 * @since 2016. 9. 25.
	 */
	public static class WebSocketSessionWrapper {
		private final Logger LOGGER = LogManager.getLogger(this.getClass());

		private WebSocketSession socketSession;
		private String uri;

		public WebSocketSessionWrapper(WebSocketSession session) {
			this.socketSession = session;
		}

		public UserVO getLoginUser() {
			return (UserVO) socketSession.getAttributes().get(SessionKey.SESSION_LOGIN_USER);
		}

		public WebSocketSession getSocketSession() {
			return socketSession;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getSocketSessionId() {
			return socketSession.getId();
		}

		public boolean isOpen() {
			return socketSession.isOpen();
		}

		public void sendMessage(String textMessage) {
			try {
				if (socketSession.isOpen()) {
					socketSession.sendMessage(new TextMessage(textMessage));
				}
			} catch (Exception ignored) {
				LOGGER.error(">>> fail to send message!", ignored);
			}
		}

		@Override
		public String toString() {
			return String.format("WebSocketSessionWrapper [socketSession=%s, uri=%s]", socketSession, uri);
		}

	}

}
