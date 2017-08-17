package test.opendesign.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnConst.SessionKey;
import com.opendesign.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestUserController {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	protected MockHttpSession mockSession;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockSession = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
	}

	// @Test
	public void login() throws Exception {
		mockMvc.perform(get("/login.do").accept(MediaType.ALL).characterEncoding("UTF-8")).andExpect(status().isOk());
	}

	/**
	 * register
	 * 
	 * @throws Exception
	 */
	//@Test
	public void register2() throws Exception {
		//
		UserVO userVO = new UserVO();
		userVO.setEmail("gogogo@naver.com");
		userVO.setPasswd("abcd!1234");
		mockSession.putValue(SessionKey.SESSION_REG_USER, userVO);
		//
		String data = "test-data";
		MockMultipartFile imageFile = new MockMultipartFile("imageUrlFile", "my-image.jpeg", "image/jpeg",
				data.getBytes());
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/register2.ajax").file(imageFile).session(mockSession)
				.param("uname", "test1").param("comments", "comments").param("memberTypeCheck", "d", "p")
				.param("memberCategory", "001", "004").accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

}
