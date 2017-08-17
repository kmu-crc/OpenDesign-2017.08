package test.opendesign.unittest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.opendesign.dao.UserDAO;
import com.opendesign.service.UserService;
import com.opendesign.vo.UserVO;

@RunWith(MockitoJUnitRunner.class)
public class TestMockitoSolo {

	/* ===== to test obj: ==== */
	@InjectMocks
	private UserService service = new UserService();

	/* ===== mock obj: ==== */
	@Mock
	private UserDAO dao;

	@Before
	public void before() {
		/* ===== init mock ==== */
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {

		/* =========== expect =========== */
		UserVO rsUser = new UserVO();
		rsUser.setSeq("2");
		when(dao.selectUserByEmail(anyString())).thenReturn(rsUser);

		/* =========== test =========== */
		UserVO user = service.selectUserByEmail("shg@naver.com");

		/* =========== validation =========== */
		assertTrue("2".equals(user.getSeq()));
		assertThat("2", is(user.getSeq()));
		verify(dao).selectUserByEmail(anyString());
	}

}
