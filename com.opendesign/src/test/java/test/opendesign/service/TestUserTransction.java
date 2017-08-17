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
public class TestUserTransction {

	@Autowired
	private UserService userService;

	@Test
	public void testTransaction() {

		String email = "test@naver.com";
		UserVO userVO = new UserVO();
		try {
			//userService.transactionTestMember(userVO);

			System.out.println(">>> test failed ");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof DuplicateKeyException);
			System.out.println(">>> throws DuplicateKeyException");

			UserVO isInsertedUser = userService.selectUserByEmail(email);
			Assert.assertNull(isInsertedUser);
			System.out.println(">>> test success");
		}

	}

}
