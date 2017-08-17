package test.opendesign.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.opendesign.dao.ProjectDAO;
import com.opendesign.service.ProjectService;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.ProjectWorkVO;
import com.opendesign.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestProjectService {

	@Autowired
	private ProjectService service;
	@Autowired
	private ProjectDAO dao;

	@Before
	public void setUp() {

	}

	/**
	 * del_flag
	 */
	@Test
	@Transactional
	public void deleteProjectWork() {
		String seq = "5";
		ProjectWorkVO param = new ProjectWorkVO();
		param.setSeq(seq);
		service.deleteProjectWork(param, null);

		Assert.assertTrue(true);
	}

	@Test
	public void selectProjectWorkDetail() {
		dao.selectProjectWork("5");

		Assert.assertTrue(true);
	}

	@Test
	@Transactional
	public void deleteProject() {
		int seq = 22;
		UserVO loginUser = new UserVO();
		loginUser.setSeq("37");
		ProjectVO param = new ProjectVO();
		param.setSeq(seq);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectSeq", String.valueOf(seq));
		ProjectVO beforeDelete = service.selectProjectInfo(paramMap);
		Assert.assertTrue((beforeDelete != null) && (beforeDelete.getSeq() == seq));

		service.deleteProject(param, loginUser);

		ProjectVO afterDelete = null;
		try {
			afterDelete = service.selectProjectInfo(paramMap);
		} catch (Exception e) {
		}
		Assert.assertTrue((afterDelete == null) || (afterDelete.getSeq() == 0));
		Assert.assertTrue(true);
	}

}
