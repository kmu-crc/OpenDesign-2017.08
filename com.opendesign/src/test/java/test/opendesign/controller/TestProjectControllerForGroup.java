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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.opendesign.service.ProjectService;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.vo.ProjectGroupVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestProjectControllerForGroup {

	/*
	 * t_member: shg@naver.com 37 t_pgroup: shg그룹 16
	 */
	private static final String schProjectSeq = "39";
	private static final String schProjectGroupSeq = "16";

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
		commonResult(commonRequest(post("/login.ajax")).param("email", "shg@naver.com").param("passwd", "abcd!1234"));
	}

	@Test
	public void selectProjectGroupRequestInfo() throws Exception {
		commonResult(commonRequest(get("/project/selectProjectGroupRequestInfo.ajax")).param("schProjectSeq", schProjectSeq));
	}

	@Test
	public void selectProjectGroupListByName() throws Exception {
		commonResult(commonRequest(get("/project/selectProjectGroupListByName.ajax")).param("schGroupName", "shg그"))
				.andExpect(jsonPath(RstConst.P_NAME).exists());
	}
	
	// 추가: 
	@Test
	public void insertProjectGroupRequest() throws Exception {
		commonResult(commonRequest(get("/project/insertProjectGroupRequest.ajax"))
				.param("projectSeq", schProjectSeq)
				.param("projectGroupSeq", schProjectGroupSeq)
				)
		.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}
	
	@Test
	public void updateProjectGroupRequestCancel() throws Exception {
		commonResult(commonRequest(get("/project/updateProjectGroupRequestCancel.ajax"))
				.param("projectSeq", schProjectSeq)
				.param("projectGroupSeq", schProjectGroupSeq)
				)
		.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}
	
	@Test
	public void selectProjectGroupRequestWaitingList() throws Exception {
		commonResult(commonRequest(get("/project/selectProjectGroupRequestWaitingList.ajax"))
				.param("schGroupSeq", schProjectGroupSeq)
				)
		.andExpect(jsonPath(RstConst.P_NAME).isArray());
	}
	
	@Test
	public void updateProjectGroupRequestApprove() throws Exception {
		commonResult(commonRequest(get("/project/updateProjectGroupRequestApprove.ajax"))
				.param("projectSeq", schProjectSeq)
				.param("projectGroupSeq", schProjectGroupSeq)
				)
		.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}
	
	@Test
	public void updateProjectGroupRequestReject() throws Exception {
		commonResult(commonRequest(get("/project/updateProjectGroupRequestReject.ajax"))
				.param("projectSeq", schProjectSeq)
				.param("projectGroupSeq", schProjectGroupSeq)
				)
		.andExpect(jsonPath(RstConst.P_NAME).value(RstConst.V_SUCESS));
	}
	
	
	
	
	@Autowired
	ProjectService service;
	
	@Test
	@Transactional
	public void deleteMyGroup() throws Exception {
		ProjectGroupVO groupVO = new ProjectGroupVO();
		groupVO.setSeq(5);
		service.deleteMyGroup(24, groupVO);
	}
	
	
	/**
	 * 그룹 상세 페이지
	 */
	@Test
	public void projectList() throws Exception {
		commonResult(commonRequest(get("/project/projectList.ajax"))
				.param("schMyGroup", "16")
				);
	}
	
	
	
	
	
	
	
	

}
