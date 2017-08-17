package test.opendesign.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.utils.PropertyUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:WebContent/WEB-INF/root-context.xml", "file:WebContent/WEB-INF/dispatcher-servlet.xml" })
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestMailDispatcher {

	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	protected MockHttpSession mockSession;
	
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	/**
	 * 작품 삭제
	 * 
	 * @param request
	 * @return
	 */
	@Test
	public void sendMailTest() throws Exception {
		
		mockMvc.perform(get("/mail/sendSimple.ajax")
				.param("mail.target", "seaoh@wdfall.com")
				.param("mail.title", "[E-Mail Test For OpenDesign]")
				.param("mail.template", "mail_password.vm")
		);
	}
	
	
	
}
