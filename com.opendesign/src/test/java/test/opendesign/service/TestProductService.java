package test.opendesign.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.opendesign.service.ProductService;
import com.opendesign.vo.DesignWorkVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-root-context.xml", "/test-dispatcher-servlet.xml" })
@WebAppConfiguration
public class TestProductService {

	@Autowired
	private ProductService service;

	/**
	 * del_flag 
	 */
	@Test
	@Transactional
	public void deleteProduct() {
		String seq = "19";
		DesignWorkVO product = new DesignWorkVO();
		product.setSeq(seq);
		service.deleteProduct(product);

		Assert.assertTrue(true);
	}
	
	@Test
	public void selectProductWithProjCount() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("page_count", 0);
		param.put("limit_count", 20);
		param.put("schCate", "");
		param.put("schSort", "");
		service.selectProductWithProjCount(param);

		Assert.assertTrue(true);
	}
	
	@Test
	public void selectProductWithProjList() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("page_count", 0);
		param.put("limit_count", 20);
		param.put("schCate", "");
		param.put("schSort", "");
		service.selectProductWithProjList(param);
		
		Assert.assertTrue(true);
	}
	

}
