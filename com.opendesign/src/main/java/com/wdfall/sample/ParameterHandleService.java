/*
 * @(#)ParameterHandleService.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2015. 7. 6. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.sample;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Service
public class ParameterHandleService {

	@Autowired
    private SqlSession sqlSession;	
	
	
	public void somethingWithDBNonTransaction( Map<String, Object> param ) throws IOException {

		//sqlSession.insert("sqlid", param);
		//sqlSession.update("sqlid", param);
		//sqlSession.delete("sqlid", param);
		//sqlSession.selectOne("sqlid", param);
		//sqlSession.selectList("sqlid", param);

	}
	
	@Transactional
	public void somethingWithDBTransaction( Map<String, Object> param ) throws IOException {
		//sqlSession.insert("sqlid", param);
		//sqlSession.update("sqlid", param);
		//sqlSession.delete("sqlid", param);
		//sqlSession.selectOne("sqlid", param);
		//sqlSession.selectList("sqlid", param);

	}
}
