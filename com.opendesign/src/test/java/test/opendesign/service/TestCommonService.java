package test.opendesign.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.opendesign.service.CommonService;
import com.opendesign.vo.CategoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestCommonService {

	@Autowired
	private CommonService service;

	@Test
	public void test() {
		String codes = "001,002";
		List<CategoryVO> resultList = service.selectCategoryListDepth1(codes);
		Assert.assertTrue(resultList.size() > 0);
		List<CategoryVO> resultList2 = service.selectCategoryListDepth1();
		Assert.assertTrue(resultList2.size() > 0);
		System.out.println(resultList);
		System.out.println(resultList2);
	}

}
