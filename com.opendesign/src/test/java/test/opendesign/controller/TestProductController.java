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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.controller.ProductController;
import com.opendesign.service.DesignerService;
import com.opendesign.service.ProductService;
import com.opendesign.utils.CmnConst.RstConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestProductController {

	@Autowired
	private WebApplicationContext context;

	/* =========== mock 할것: =========== */
	private MockMvc mockMvc;
	private MockHttpSession mockSession;

	/* =========== test 할것: =========== */

	// =============== common
	private MockHttpServletRequestBuilder commonRequest(MockHttpServletRequestBuilder req) {
		return req.session(mockSession).accept(MediaType.ALL).characterEncoding("UTF-8");
	}

	private ResultActions commonResult(MockHttpServletRequestBuilder req) throws Exception {
		return mockMvc.perform(req).andDo(print()).andExpect(status().isOk());
	}
	// ===============

	@Before
	public void setUp() throws Exception {

		// === mock mvc
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockSession = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
		mockMvc.perform(post("/login.ajax").session(mockSession).param("email", "shg@naver.com")
				.param("passwd", "abcd!1234").accept(MediaType.ALL).characterEncoding("UTF-8"))
				.andExpect(status().isOk());

		// clean

	}

	/**
	 * 작품등록: /registerProduct.ajax
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsertProduct() throws Exception {

		/* =========== expect =========== */

		/* =========== test =========== */
		String data = "test-data";
		MockMultipartFile fileUrlFile = new MockMultipartFile("fileUrlFile", "my-image.jpg", "image/jpeg",
				data.getBytes());
		MockMultipartFile productFile = new MockMultipartFile("productFile", "product.jpg", "image/jpeg",
				data.getBytes());
		MockMultipartFile openSourceFile = new MockMultipartFile("openSourceFile", "openSource.jpg", "image/jpeg",
				data.getBytes());
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/registerProduct.ajax").file(fileUrlFile)
				.file(productFile).file(openSourceFile).param("filename_openSourceFile", "오픈소스1").session(mockSession)
				.param("title", "작품-shg3").param("categoryCodes", "001").param("license01", "1").param("license02", "1")
				.param("license03", "1").param("tag", "유행").param("point", "100").param("contents", "test")
				.accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
		/* =========== validation =========== */
	}

	/**
	 * 작품등록: /productList.ajax
	 * 
	 * @throws Exception
	 */
	@Test
	public void productList() throws Exception {
		/* =========== expect =========== */
		/* =========== test =========== */
		commonResult(commonRequest(get("/product/productList.ajax")).param("schCate", "").param("schSort", "")).andExpect(jsonPath("list").isArray()); 
		/* =========== validation =========== */
	}
	
	

}
