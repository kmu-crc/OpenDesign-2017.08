package test.opendesign.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.controller.ProductController;
import com.opendesign.service.CommonService;
import com.opendesign.service.DesignerService;
import com.opendesign.service.ProductService;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnConst.SessionKey;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestProductControllerUnitTest {

	@Autowired
	private WebApplicationContext context;

	/* =========== mock 할것: =========== */
	private MockMvc mockMvc;
	private MockHttpSession mockSession;
	
	@Mock
	ProductService service;

	@Mock
	DesignerService designerService;

	@Mock
	CommonService commonService;

	/* =========== test 할것: =========== */
	
	@InjectMocks
	ProductController controller;
	

	@Before
	public void setUp() throws Exception {
		
		// === init mock
		MockitoAnnotations.initMocks(this);

		// === mock mvc
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		//
		mockSession = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
		UserVO userVO = new UserVO();
		userVO.setSeq("37");
		userVO.setEmail("shg@naver.com");
		userVO.setPasswd("abcd!1234");
		mockSession.putValue(SessionKey.SESSION_LOGIN_USER, userVO);
		

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
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/registerProduct.ajax")
				.file(fileUrlFile)
				.file(productFile)
				.file(openSourceFile)
				.param("filename_openSourceFile", "오픈소스1")
				.session(mockSession).param("title", "작품-shg3").param("categoryCodes", "001")
				.param("license01", "1")
				.param("license02", "1")
				.param("license03", "1")
				.param("tag", "유행")
				.param("point", "100")
				.param("contents", "test")
				.accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				;
		/* =========== validation =========== */
		verify(service).insertProduct(any(), any());
	}
	
	
	
	/**
	 * 작품변경: /updateProduct.ajax
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateProduct() throws Exception {

		/* =========== expect =========== */
		DesignWorkVO prevProduct = new DesignWorkVO();
		prevProduct.setMemberSeq("37"); 
		when(designerService.getDesignWork(anyString())).thenReturn(prevProduct);
		
		/* =========== test =========== */
		String data = "test-data";
		MockMultipartFile fileUrlFile = new MockMultipartFile("fileUrlFile", "my-image.jpg", "image/jpeg",
				data.getBytes());
		MockMultipartFile productFile = new MockMultipartFile("productFile", "product.jpg", "image/jpeg",
				data.getBytes());
		MockMultipartFile openSourceFile = new MockMultipartFile("openSourceFile", "openSource.jpg", "image/jpeg",
				data.getBytes());
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/updateProduct.ajax")
				.file(fileUrlFile)
				.file(productFile)
				.file(openSourceFile)
				.param("filename_openSourceFile", "오픈소스1")
				.session(mockSession).param("title", "작품-shg3").param("categoryCodes", "001")
				.param("seq", "1")
				.param("license01", "1")
				.param("license02", "1")
				.param("license03", "1")
				.param("tag", "유행")
				.param("point", "100")
				.param("contents", "test")
				.accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				;
		/* =========== validation =========== */
		verify(service).updateProduct(any(), any(), any(), any());
	}
	

}
