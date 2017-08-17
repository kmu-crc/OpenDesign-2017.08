package test.opendesign.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.utils.CmnConst.RstConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestProjectControllerForWork {

	private static final String USER_PASSWORD = "abcd!1234";

	private static final String USER_EMAIL = "shg@naver.com";

	private static final String WORK_SEQ = "78";

	private static final String SUBJECT_SEQ = "74";

	private static final String FROM_VER_SEQ = "116";
	private static final String TO_SUBJECT_SEQ = "83";

	@Autowired
	private WebApplicationContext context;
	private byte[] fileContent;
	private byte[] fileContentThumb;

	/* =========== mock 할것: =========== */
	private MockMvc mockMvc;
	protected MockHttpSession mockSession;

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
		//
		// mockMvc = MockMvcBuilders.standaloneSetup(controller,
		// userController).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockSession = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
		mockMvc.perform(post("/login.ajax").session(mockSession).param("email", USER_EMAIL)
				.param("passwd", USER_PASSWORD).accept(MediaType.ALL).characterEncoding("UTF-8"))
				.andExpect(status().isOk());

		// image Test
		fileContent = Files.readAllBytes(new File("D:/test.jpg").toPath());
		fileContentThumb = Files.readAllBytes(new File("D:/test2.jpg").toPath());
	}

	/**
	 * 작품 추가
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Test
	public void insertProjectWork() throws Exception {
		MockMultipartFile imageFile = new MockMultipartFile("fileUriFile", "my-image.jpeg", "image/jpeg", fileContent);
		MockMultipartFile imageFileThumb = new MockMultipartFile("fileUriFileThumb", "my-image.jpeg", "image/jpeg",
				fileContentThumb);
		commonResult(commonRequest(MockMvcRequestBuilders.fileUpload("/project/insertProjectWork.ajax").file(imageFile)
				.file(imageFileThumb)).param("projectSubjectSeq", SUBJECT_SEQ).param("title", "작품23").param("contents",
						"설명23")).andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

	/**
	 * 작품 상세 조회
	 * 
	 * @param request
	 * @return
	 */
	@Test
	public void selectProjectWorkDetail() throws Exception {
		commonResult(commonRequest(get("/project/selectProjectWorkDetail.ajax").param("seq", WORK_SEQ)));
	}

	/**
	 * 작품 수정 페이지
	 * 
	 * @param request
	 * @return
	 */
	@Test
	public void openUpdateProjectWork() throws Exception {
		commonResult(commonRequest(get("/project/openUpdateProjectWork.ajax").param("seq", WORK_SEQ)));
	}

	/**
	 * 작품 수정(이미지 변경)
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Test
	public void updateProjectWorkModImage() throws Exception {
		// ProjectWorkVO
		MockMultipartFile imageFile = new MockMultipartFile("fileUriFile", "my-image.jpeg", "image/jpeg", fileContent);
		MockMultipartFile imageFileThumb = new MockMultipartFile("fileUriFileThumb", "my-image.jpeg", "image/jpeg",
				fileContentThumb);
		commonResult(commonRequest(MockMvcRequestBuilders.fileUpload("/project/updateProjectWork.ajax").file(imageFile)
				.file(imageFileThumb)).param("seq", WORK_SEQ).param("title", "작품1-mod").param("contents", "설명1-mod"))
						.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

	/**
	 * 작품 수정(이미지 변경 없음)
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateProjectWorkNotModImage() throws Exception {
		commonResult(commonRequest(MockMvcRequestBuilders.fileUpload("/project/updateProjectWork.ajax"))
				.param("seq", WORK_SEQ).param("title", "작품1-mod2").param("contents", "설명1-mod2"))
						.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

	/**
	 * 작품 퍼가기
	 * 
	 * @throws Exception
	 */
	@Test
	public void shareProjectWork() throws Exception {
		commonResult(commonRequest(post("/project/shareProjectWork.ajax")).param("seq", WORK_SEQ)
				.param("fromVerSeq", FROM_VER_SEQ).param("toSubjectSeq", TO_SUBJECT_SEQ))
						.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

	/**
	 * 작품 삭제
	 * 
	 * @param request
	 * @return
	 */
	// @Test
	public void deleteProjectWork() throws Exception {
		mockMvc.perform(get("/project/deleteProjectWork.ajax").param("seq", "10"))
				.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

}
