package test.opendesign.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.opendesign.service.UserService;
import com.opendesign.utils.CmnUtil;
import com.opendesign.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestUserService {

	@Autowired
	private UserService userService;

	@Test
	public void testTransaction() {
		String email = "sss@naver.com";
		UserVO test = userService.selectUserByEmail(email);
		String newPasswd = userService.updateUserNewPassword(test.getSeq());
		UserVO test2 = userService.selectUserByEmail(email);
		System.out.println(test.getPasswd());
		System.out.println(test2.getPasswd());
		Assert.assertTrue(!test2.getPasswd().equals(test.getPasswd()));

		//
		System.out.println(newPasswd);
		Assert.assertTrue(test2.getPasswd().equals(CmnUtil.encryptPassword(newPasswd)));
	}

}
