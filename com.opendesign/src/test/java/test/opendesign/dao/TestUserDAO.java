package test.opendesign.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import com.opendesign.dao.ProjectDAO;
import com.opendesign.dao.UserDAO;
import com.opendesign.vo.MyUserVO;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestUserDAO {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private ProjectDAO pdao;

	@Test
	public void test() {
		UserVO result = dao.selectUserByEmail("shg@naver.com");
		Assert.notNull(result);
		Assert.notNull(result.getSeq());
		System.out.println(result.getSeq());
	}

	@Test
	public void testUnameDup() {
		UserVO shg = dao.selectUserByEmail("shg@naver.com");
		UserVO userVO = new UserVO();
		userVO.setUname(shg.getUname());
		int result = dao.checkUnameDup(userVO);
		Assert.isTrue(result > 0);
		//
		userVO.setUname(shg.getUname() + "-noSuchUname");
		Assert.isTrue(dao.checkUnameDup(userVO) == 0);
	}
	
	@Test
	public void selectMyProjectList() {
		List<MyUserVO> result = dao.selectMyProjectList("37");
		if(result != null) {
			System.out.println(result.size());
		}
		Assert.isTrue(result != null && result.size() > 0 );
	}
	
	@Test
	public void selectProjectList() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memberSeq", "37");
		paramMap.put("page_count", 0);
		paramMap.put("limit_count", 10);
		List<ProjectVO> result = pdao.selectProjectList(paramMap);
		if(result != null) {
			System.out.println(result.size());
		}
		Assert.isTrue(result != null && result.size() > 0 );
	}

}
