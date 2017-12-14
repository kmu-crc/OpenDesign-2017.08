/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.opendesign.service.DesignerService;
import com.opendesign.utils.CmnConst.MemberDiv;
import com.opendesign.utils.CmnConst.PageMode;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.DesignerVO;
import com.opendesign.vo.OrderVO;
import com.opendesign.vo.RequestBoardVO;
import com.opendesign.vo.SearchVO;
import com.wdfall.spring.JsonModelAndView;

/**
 * 
 * <pre>
 * 디자이너의 액션들을 담당하는 
 * 컨트롤러 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 8. 20.
 */
@Controller
@RequestMapping(value = "/designer")
public class DesignerController {

	/**
	 * 디자이너/제작자 서비스 인스턴스
	 */
	@Autowired
	private DesignerService service;

	/**
	 * <pre>
	 * 디자이너 페이지 조회(이동)
	 * ID#OD04-01-01
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/designer.do")
	public ModelAndView designer(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("schMemberDiv", MemberDiv.DESIGNER); // 회원구분

		return new ModelAndView("/designer/designer", resultMap);
	}

	/**
	 * 디자이너 목록 데이터 조회
	 * 
	 * @param schPage
	 * @param schCate
	 * @param schOrderType
	 *            default '최신순'
	 * @param schLimitCount
	 *            default 16
	 * @return all_count
	 * @return list
	 */
	@RequestMapping(value = "/designerList.ajax")
	public ModelAndView designerList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {
		Map<String, Object> resultMap = service.selectDesignerList(searchVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 디자이너 포트폴리오 페이지 조회(이동)
	 * ID#OD04-01-02
	 * </pre>
	 * 
	 * @param desiVO:
	 *            seq
	 * @return
	 */
	@RequestMapping(value = "/portfolio.do")
	public ModelAndView portfolio(@ModelAttribute DesignerVO desiVO, HttpServletRequest reqeust) {

		Map<String, Object> resultMap = service.selectDesingerDetail(desiVO, reqeust);
		resultMap.put("schMemberDiv", MemberDiv.DESIGNER); // 회원구분

		return new ModelAndView("/designer/portfolio", resultMap);
	}

	/**
	 * 디자이너 포트폴리오에서 사용하는 디자인(작품) 목록 조회
	 * 
	 * @param desiVO:
	 *            seq
	 * @return
	 */
	@RequestMapping(value = "/selectDesignWorkList.ajax")
	public ModelAndView selectDesignWorkList(@ModelAttribute DesignerVO desiVO, HttpServletRequest reqeust) {

		Map<String, Object> resultMap = service.selectDesignWorkList(desiVO, reqeust);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 디자인(작품) 상세 페이지 조회
	 * ID#OD04-02-01
	 * </pre>
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productView.do")
	public ModelAndView productView(@ModelAttribute DesignWorkVO dsWorkVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.productView(dsWorkVO, request);
		resultMap.put("schMemberDiv", MemberDiv.DESIGNER); // 회원구분

		return new ModelAndView("/product/product_view", resultMap);
	}

	/**
	 * 디자인(작품) 구매 페이지에서 사용하는 회원 포인트 현황 조회 
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectPointInfo.ajax")
	public ModelAndView selectPointInfo(@ModelAttribute DesignWorkVO dsWorkVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectPointInfo(dsWorkVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 디자인(작품) 구매 페이지 조회(이동)
	 * ID#OD04-02-02
	 * </pre>
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productPurchase.do")
	public ModelAndView productPurchaseView(@ModelAttribute DesignWorkVO dsWorkVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.productPurchaseView(dsWorkVO, request);
		resultMap.put("schMemberDiv", MemberDiv.DESIGNER); // 회원구분

		return new ModelAndView("/product/product_purchase", resultMap);
	}

	/**
	 * 디자인(작품) 구매
	 * 
	 * @param dsWorkVO:
	 *            designWorkSeq,deliveryInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/buyProduct.ajax")
	public ModelAndView buyProduct(@ModelAttribute OrderVO orderVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.buyProduct(orderVO, request);

		return new JsonModelAndView(resultMap);
	}

	// ==================== 디자인 의뢰 =========================
	/**
	 * <pre>
	 * 디자인 의뢰 목록 조회(이동) 
	 * ID#OD04-03-01
	 * </pre>
	 * 
	 * @param boardVO:
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openDesignRequestBoard.do")
	public ModelAndView openDesignRequestBoard(@ModelAttribute RequestBoardVO boardVO, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String schMemberDiv = request.getParameter("schMemberDiv");
		resultMap.put("schMemberDiv", schMemberDiv);

		return new ModelAndView("/designer/design_request", resultMap);
	}

	/**
	 * 디자인 의뢰 목록 데이터 조회
	 * 
	 * @param searchVO:
	 *            schCate, schContent
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectDesignRequestBoardList.ajax")
	public ModelAndView selectDesignRequestBoardList(@ModelAttribute SearchVO searchVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.selectDesignRequestBoardList(searchVO, request);

		return new JsonModelAndView(resultMap);
	}

	/**
	 * <pre>
	 * 디자인 의뢰 등록/수정 페이지 조회(이동)
	 * ID#OD04-03-02
	 * </pre>
	 * 
	 * @param boardVO:
	 *            pageMode, seq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openRequestBoardInsUpd.do")
	public ModelAndView openRequestBoardInsUpd(@ModelAttribute RequestBoardVO boardVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.openRequestBoardInsUpd(boardVO, request);

		return new ModelAndView("/designer/request_write", resultMap);
	}

	/**
	 * 디자인 의뢰 등록/수정
	 * 
	 * @param boardVO
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/insUpdRequestBoard.ajax")
	public ModelAndView insUpdRequestBoard(@ModelAttribute RequestBoardVO boardVO, MultipartHttpServletRequest request)
			throws IOException {
		Map<String, Object> resultMap = null;

		if (PageMode.INSERT.equals(boardVO.getPageMode())) {
			resultMap = service.insertRequestBoard(boardVO, request);
		} else if (PageMode.UPDATE.equals(boardVO.getPageMode())) {
			resultMap = service.updateRequestBoard(boardVO, request);
		}

		return new JsonModelAndView(resultMap);
	}

	/**
	 * 디자인 의뢰 삭제
	 * 
	 * @param boardVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteRequestBoard.ajax")
	public ModelAndView deleteRequestBoard(@ModelAttribute RequestBoardVO boardVO, HttpServletRequest request) {

		Map<String, Object> resultMap = service.deleteRequestBoard(boardVO, request);

		return new JsonModelAndView(resultMap);
	}

	// ====================]] 디자인 의뢰 =========================

}
