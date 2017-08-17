package test.opendesign.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.service.UserService;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestUserTransctionController {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	protected MockHttpSession mockSession;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	//@Test
	public void test() throws Exception {

		try {
			mockMvc.perform(post("/testTrans.ajax").accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print())
					.andExpect(status().isOk());

			Assert.assertTrue(false);
			System.out.println(">>> test failed ");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(true);
			System.out.println(">>> test success ");
		}
	}

}
