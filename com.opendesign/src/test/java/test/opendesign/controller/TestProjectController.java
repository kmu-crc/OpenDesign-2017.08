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
public class TestProjectController {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	protected MockHttpSession mockSession;

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
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockSession = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
		mockMvc.perform(post("/login.ajax").session(mockSession).param("email", "chhan@naver.com")
				.param("passwd", "abcd!1234").accept(MediaType.ALL).characterEncoding("UTF-8"))
				.andExpect(status().isOk());
	}

	//@Test
	public void test() {

	}

	/**
	 * 프로젝트 상세: 페이지
	 * 
	 * @throws Exception
	 */
	// @Test
	public void openProjectDetail() throws Exception {
		mockMvc.perform(get("/project/openProjectDetail.do").accept(MediaType.ALL).characterEncoding("UTF-8"))
				.andExpect(status().isOk());
	}

	/**
	 * 프로젝트 상세: 주제,작품 list 조회
	 * 
	 * @throws Exception
	 */
	// @Test
	public void selectProjectDetail() throws Exception {
		mockMvc.perform(get("/project/selectProjectDetail.ajax").session(mockSession).param("projectSeq", "1")
				.accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * 새 주제 추가
	 * 
	 * @throws Exception
	 */
	// @Test
	public void insertProjectSubject() throws Exception {
		mockMvc.perform(get("/project/insertProjectSubject.ajax").session(mockSession).param("projectSeq", "1")
				.param("subjectName", "주제test3").accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));

	}

	/**
	 * 주제 삭제 체크
	 * 
	 * @param request
	 * @return
	 */
	// @Test
	public void checkDeleteProjectSubject() throws Exception {
		mockMvc.perform(get("/project/checkDeleteProjectSubject.ajax").session(mockSession).param("seq", "1")
				.accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

	/**
	 * 주제 삭제
	 * 
	 * @param request
	 * @return
	 */
	// @Test
	public void deleteProjectSubject() throws Exception {
		mockMvc.perform(get("/project/deleteProjectSubject.ajax").session(mockSession).param("seq", "4")
				.accept(MediaType.ALL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}

	
	/**
	 * 작품등록: /selectGroupList.ajax
	 * 
	 * @throws Exception
	 */
	@Test
	public void selectGroupList() throws Exception {
		/* =========== expect =========== */
		/* =========== test =========== */
		commonResult(commonRequest(get("/project/selectGroupList.ajax"))
				.param("schPage", "1")
				//.param("schLimitCount", "2")
				).andExpect(jsonPath("list").isArray()); 
		/* =========== validation =========== */
	}
	
	

}
