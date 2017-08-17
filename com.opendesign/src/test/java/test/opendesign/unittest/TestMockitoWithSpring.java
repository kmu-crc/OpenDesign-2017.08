package test.opendesign.unittest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.UUID;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.dao.CommonDAO;
import com.opendesign.dao.UserDAO;
import com.opendesign.service.CommonService;
import com.opendesign.service.UserService;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.CmnConst.SessionKey;
import com.opendesign.vo.MessageVO;
import com.opendesign.vo.UserVO;
import com.opendesign.websocket.SocketHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestMockitoWithSpring {

	@Autowired
	private WebApplicationContext context;

	/* =========== mock 할것: =========== */
	private MockMvc mockMvc;
	private MockHttpSession mockSession;
	private MockHttpServletRequest mockRequest;

	@Mock
	CommonDAO dao;
	@Mock
	UserDAO userDao;
	@Mock
	SocketHandler websocketHandler;

	/* =========== test 할것: =========== */
	@InjectMocks
	private CommonService service;

	@Before
	public void setUp() {
		// === init mock
		MockitoAnnotations.initMocks(this);

		// === mock mvc
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		// === mock session
		mockSession = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
		UserVO userVO = new UserVO();
		userVO.setSeq("3");
		userVO.setEmail("shg@naver.com");
		userVO.setPasswd("abcd!1234");
		mockSession.putValue(SessionKey.SESSION_LOGIN_USER, userVO);

		// === mock request
		mockRequest = new MockHttpServletRequest();
		mockRequest.setSession(mockSession);

	}

	@Test
	public void testSendUserMessage() {

		/* =========== expect =========== */
		MessageVO msgVO = new MessageVO();
		doNothing().when(websocketHandler).notifyMsgChanged(anyString());

		/* =========== test =========== */
		service.sendUserMessage(msgVO, mockRequest);

		/* =========== validation =========== */
		verify(dao).insertMessage(msgVO);
		verify(websocketHandler, times(2)).notifyMsgChanged(anyString());

	}

}
