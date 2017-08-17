package test.opendesign.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestCmnUtil {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	protected MockHttpSession mockSession;

	private MockHttpServletRequest mockRequest;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		// building MockServletContext !!prefix!!: "file:"
		String resourceBasePath = "file:D:/DEV/workspace_mars/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/kookmin";
		MockServletContext testFileCtx = new MockServletContext(resourceBasePath, null);
		mockRequest = new MockHttpServletRequest(testFileCtx);
	}

	// @Test
	public void test() {
	}

	/**
	 * CmnUtil.getCalcFileSizeFromUrl
	 */
	@Test
	public void testCmnUtil() {
		System.out.println(CmnUtil.getCalcFileSizeFromUrl(mockRequest, null));
		System.out.println(CmnUtil.getCalcFileSizeFromUrl(mockRequest,
				"/resources/km_upload/project_work_file/bddaf0e8-5c3a-4ccf-a274-745768766a4b.jpg"));
	}

}
