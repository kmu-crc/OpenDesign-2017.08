/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.opendesign.dao.CommonDAO;
import com.opendesign.dao.DesignerDAO;
import com.opendesign.dao.UserDAO;
import com.opendesign.utils.CmnConst.FileUploadDomain;
import com.opendesign.utils.CmnConst.PageMode;
import com.opendesign.utils.CmnConst.RstConst;
import com.opendesign.utils.CmnUtil;
import com.opendesign.utils.CmnUtil.UpFileInfo;
import com.opendesign.vo.AlarmVO;
import com.opendesign.vo.CategoryVO;
import com.opendesign.vo.DesignPreviewImageVO;
import com.opendesign.vo.DesignWorkFileVO;
import com.opendesign.vo.DesignWorkVO;
import com.opendesign.vo.DesignerVO;
import com.opendesign.vo.ItemLikeVO.ItemType;
import com.opendesign.vo.ItemViewCntVO;
import com.opendesign.vo.ItemViewCntVO.ItemViewCntType;
import com.opendesign.vo.MainItemVO;
import com.opendesign.vo.MemberCategoryVO;
import com.opendesign.vo.OrderVO;
import com.opendesign.vo.PointHistoryVO;
import com.opendesign.vo.PointHistoryVO.SignType;
import com.opendesign.vo.ProjectVO;
import com.opendesign.vo.RequestBoardCateVO;
import com.opendesign.vo.RequestBoardCmmtVO;
import com.opendesign.vo.RequestBoardFileVO;
import com.opendesign.vo.RequestBoardVO;
import com.opendesign.vo.SearchVO;
import com.opendesign.vo.UserVO;
import com.opendesign.websocket.SocketHandler;

/**
 * 
 * <pre>
 * 디자이너/제작자의 서비스들을 담당하는 클래스
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 9. 21.
 */
@Service
public class DesignerService {

	/**
	 * 로그(log4j) 인스턴스
	 */
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	/**
	 * 디자이너/제작자 DAO 인스턴스 
	 */
	@Autowired
	DesignerDAO dao;

	/**
	 * 회원 DAO 인스턴스
	 */
	@Autowired
	UserDAO userDao;

	/**
	 * 공통 service 인스턴스
	 */
	@Autowired
	CommonService commonService;

	/**
	 * 공통 DAO 인스턴스
	 */
	@Autowired
	CommonDAO commonDao;

	/**
	 * 웹소켓 서비스 인스턴스
	 */
	@Autowired
	SocketHandler websocketHandler;
	
	/**
	 * 디자인(작품) 컨트롤러
	 */
	@Autowired
	ProductService productService;

	/**
	 * 디자이너/제작자 목록 데이터 조회
	 * 
	 * @param searchVO
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectDesignerList(SearchVO searchVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO user = CmnUtil.getLoginUser(request);
		searchVO.setSchLoginUser(user);
		
		int allCount = dao.selectDesignerAllCount(searchVO);
		List<DesignerVO> list = this.selectDesignerWholeList(searchVO);
		
		String category = searchVO.getSchCate();
		
		MainItemVO item = new MainItemVO();
		
		if(!StringUtils.isEmpty(category)) {
			item = productService.settingCategoryNm(category);
		}
		
		resultMap.put("all_count", allCount);
		resultMap.put("list", list);
		resultMap.put("item", item);
		return resultMap;
	}

	/**
	 * 디자이너/제작자 목록 데이터 조회: 관련된 작품등 상세정보도 함께 조회 
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<DesignerVO> selectDesignerWholeList(SearchVO searchVO) {
		List<DesignerVO> list = dao.selectDesignerList(searchVO);
		for (DesignerVO item : list) {
			// category
			List<MemberCategoryVO> cateList = dao.selectMemberCategoryList(item.getSeq());
			item.setCateList(cateList);
			// 작품
			//List<DesignWorkVO> workList = dao.selectDesignWorkList(item.getSeq());
			//item.setWorkList(workList);
			//프로젝트
			//List<ProjectVO> workPList = dao.selectProjectWorkList(item.getSeq());
			//item.setWorkPList(workPList);
			// 전체 작품 리스트
			//List<Object> totalList = dao.selectTotalWorkList(item.getSeq());
			//item.settotalList(totalList);
		}

		return list;
	}
	

	/**
	 * 디자이너/제작자 포트폴리오: 디자인 정보만 조회
	 * 
	 * @param desiVO:
	 *            seq
	 * @param reqeust
	 * @return
	 */
	public Map<String, Object> selectDesingerDetail(DesignerVO desiVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//
		DesignerVO desingerVO = dao.selectDesignerBySeq(desiVO.getSeq());
		
		/*
		 * 로그인 된 사용자라면 조회된 디자이너에 대해서
		 * 좋아요를 했는지 여부를 세팅한다.
		 */
		if( CmnUtil.isUserLogin(request) ) {
			
			UserVO user = CmnUtil.getLoginUser(request);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("logonUserSeq", user.getSeq());
			paramMap.put("designerSeq", desingerVO.getSeq());
			desingerVO.setCurUserLikedYN( isLogonUserLikesDesigner(paramMap) );
			
		}
		
		// category
		List<MemberCategoryVO> cateList = dao.selectMemberCategoryList(desiVO.getSeq());
		desingerVO.setCateList(cateList);
		// 

		resultMap.put("desingerVO", desingerVO);
		return resultMap;
	}

	/**
	 * 디자이너/제작자 포트폴리오 : 작품 목록 조회
	 * 
	 * @param desiVO:
	 *            seq
	 * @param reqeust
	 * @return
	 */
	public Map<String, Object> selectDesignWorkList(DesignerVO desiVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 작품
		List<DesignWorkVO> workList = dao.selectDesignWorkList(desiVO.getSeq());
		// 프로젝트
		List<ProjectVO> workPList = dao.selectProjectWorkList(desiVO.getSeq());
		// 작품 카테고리
		// if (!CmnUtil.isEmpty(workList)) {
		// for (DesignWorkVO item : workList) {
		// List<CategoryVO> cateList =
		// dao.selectDesignWorkCategoryList(item.getSeq());
		// item.setCateList(cateList);
		// }
		// }
		
		/*
		 * 로그인 되어 있다면 작품에 대한 좋아요 여부를 세팅한다.
		 */
		if( CmnUtil.isUserLogin(request) ) {
			
			UserVO user = CmnUtil.getLoginUser(request);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("logonUserSeq", user.getSeq());
			
			
			for( DesignWorkVO work : workList ) {
				paramMap.put("designWorkSeq", work.getSeq());
				work.setCurUserLikedYN( isLogonUserLikesDesignWork(paramMap) );
			}
		
		}
		
		resultMap.put("workList", workList);
		resultMap.put("workPList", workPList);
		return resultMap;
	}

	/**
	 * 디자이너/제작자 작품 상세 조회
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	public Map<String, Object> productView(DesignWorkVO dsWorkVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO userVO = CmnUtil.getLoginUser(request);
		String workSeq = dsWorkVO.getSeq();

		// ==조회수
		ItemViewCntVO viewCntVO = new ItemViewCntVO();
		viewCntVO.setItemSeq(workSeq);
		viewCntVO.setItemViewCntType(ItemViewCntType.DESIGN_WORK);
		commonService.updateItemViewCnt(viewCntVO, request);

		// 작품
		DesignWorkVO workVO = getDesignWork(workSeq);
		// 작품 카테고리
		List<CategoryVO> cateList = dao.selectDesignWorkCategoryList(workSeq);
		workVO.setCateList(cateList);
		// 상세 이미지
		List<DesignPreviewImageVO> imageList = dao.selectDesignPreviewImageList(workSeq);
		workVO.setImageList(imageList);
		// 오픈 소스
		List<DesignWorkFileVO> fileList = dao.selectDesignWorkFileList(workSeq);
		// calc file size
		for (DesignWorkFileVO file : fileList) {
			file.setFileSize(CmnUtil.getCalcFileSizeFromUrl(request, file.getFileUri()));
		}
		workVO.setFileList(fileList);

		// === like 했는지 조회
		if (userVO != null) {
			boolean curUserLikedYN = commonService.selectItemWorkLiked(userVO.getSeq(), workSeq, ItemType.DESIGN_WORK);
			workVO.setCurUserLikedYN(curUserLikedYN);
		}

		//작품 구매여부
		if( CmnUtil.isUserLogin(request) ) {
			UserVO user = CmnUtil.getLoginUser(request);
			workVO.setLogonUserPurchased(hasPurchasedWork(workSeq, user.getSeq()));
		}
		
		//로그인 한 회원와 작품을 등록한 회원가 같은지 판단.
		
		String userSeq = "";
		
		if(userVO != null) {
			userSeq = userVO.getSeq();
		}
		
		String productUserSeq = workVO.getMemberSeq();
		
		if(!"".equals(userSeq) && !"".equals(productUserSeq)) {
			if(!userSeq.equals(productUserSeq)) {
				workVO.setUserProduct(true);
			} else {
				workVO.setUserProduct(false);
			}
		} else {
			workVO.setUserProduct(true);
		}
				
		// 디자이너
		DesignerVO designerVO = dao.selectDesignerBySeq(workVO.getMemberSeq());

		resultMap.put(RstConst.P_NAME, workVO);
		resultMap.put("designerVO", designerVO);
		return resultMap;
	}

	/**
	 * 디자인(작품) 구매
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	public Map<String, Object> productPurchaseView(DesignWorkVO dsWorkVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 작품
		String workSeq = dsWorkVO.getSeq();
		DesignWorkVO workVO = getDesignWork(workSeq);
		
		
		//작품 구매여부
		if( CmnUtil.isUserLogin(request) ) {
			UserVO user = CmnUtil.getLoginUser(request);
			workVO.setLogonUserPurchased(hasPurchasedWork(workSeq, user.getSeq()));
		}
		
		// 작품 카테고리
		List<CategoryVO> cateList = dao.selectDesignWorkCategoryList(workSeq);
		workVO.setCateList(cateList);

		// 디자이너
		DesignerVO designerVO = dao.selectDesignerBySeq(workVO.getMemberSeq());

		resultMap.put(RstConst.P_NAME, workVO);
		resultMap.put("designerVO", designerVO);
		return resultMap;
	}

	/**
	 * 디자인(작품) 조회
	 * 
	 * @param workSeq
	 * @return
	 */
	public DesignWorkVO getDesignWork(String workSeq) {
		return dao.selectDesignWorkBySeq(workSeq);
	}
	
	/**
	 * 회원가 디자인(작품) 구매 여부 조회
	 * @param workSeq
	 * @param memberSeq
	 * @return
	 */
	public boolean hasPurchasedWork(String workSeq, String memberSeq) {
		return dao.hasPurchasedWork(workSeq, memberSeq);
	}

	/**
	 * 디자인(작품) 구매 페이지에서 사용하는 회원 포인트 현황 조회 
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectPointInfo(DesignWorkVO dsWorkVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 작품
		String workSeq = dsWorkVO.getSeq();
		DesignWorkVO workVO = getDesignWork(workSeq);
		// 디자이너
		DesignerVO designerVO = dao.selectDesignerBySeq(workVO.getMemberSeq());
		// 구매자:
		UserVO loginUserVO = CmnUtil.getLoginUser(request);

		/*
		 * 포인트 현황 계산
		 */
		String userName = "";
		String availDisplayPoint = "";
		String leftOverDisplayPoint = "";
		boolean canBuy = false;
		if (loginUserVO != null && !StringUtils.isEmpty(loginUserVO.getSeq())) {
			UserVO buyerVO = userDao.selectUserBySeq(loginUserVO.getSeq());
			userName = buyerVO.getUname();
			//
			availDisplayPoint = CmnUtil.getDisplayNumber(buyerVO.getPoint());
			// 잔여포인트 계산:
			long leftOverPoint = Long.valueOf(buyerVO.getPoint()) - Long.valueOf(workVO.getPoint());
			leftOverDisplayPoint = CmnUtil.getDisplayNumber(String.valueOf(leftOverPoint));
			canBuy = (leftOverPoint >= 0);
		}

		resultMap.put("userName", userName);
		resultMap.put("availDisplayPoint", availDisplayPoint);
		resultMap.put("leftOverDisplayPoint", leftOverDisplayPoint);
		resultMap.put("canBuy", canBuy);
		return resultMap;
	}

	/**
	 * 디자인(작품) 구매
	 * 
	 * @param dsWorkVO:
	 *            seq
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> buyProduct(OrderVO orderVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 작품
		String workSeq = orderVO.getDesignWorkSeq();
		DesignWorkVO workVO = getDesignWork(workSeq);
		// 판매인 (디자이너)
		DesignerVO sellerVO = dao.selectDesignerBySeq(workVO.getMemberSeq());
		// 구매인 (회원): 현재 포인트 조회
		UserVO loginUser = CmnUtil.getLoginUser(request);
		UserVO buyerVO = userDao.selectUserBySeq(loginUser.getSeq());

		//작품 구매여부 체크
		if( CmnUtil.isUserLogin(request) ) {
			UserVO user = CmnUtil.getLoginUser(request);
			workVO.setLogonUserPurchased(hasPurchasedWork(workSeq, user.getSeq()));
		}
		
		if( workVO.isLogonUserPurchased() ) {
			resultMap.put(RstConst.P_NAME, RstConst.V_ALEADY_PURCHASED);
			return resultMap;
		}
		
		// 작품 point
		int workPoint = CmnUtil.getIntValue(workVO.getPoint());
		// 구매인 현재 point
		int buyerCurPoint = CmnUtil.getIntValue(buyerVO.getPoint());
		// 판매인 현재 point
		int sellerCurPoint = CmnUtil.getIntValue(sellerVO.getPoint());

		// === check:
		if (buyerCurPoint <= 0 || buyerCurPoint < workPoint) {
			resultMap.put(RstConst.P_NAME, RstConst.V_NOT_ENOUGH_POINT);
			return resultMap;
		}

		// === 구매 이력
		orderVO.setMemberSeq(buyerVO.getSeq());
		orderVO.setPoint(String.valueOf(workPoint));
		// 
		CmnUtil.setCmnDate(orderVO);
		dao.insertOrder(orderVO);
		
		
		// comment: (작품제목, 주소)
		String comments = workVO.getTitle();
		if(!StringUtils.isEmpty(orderVO.getOrderAddress())) {
			comments = comments + ", " +  orderVO.getOrderAddress();
		}

		// === 포인트 가감이력(구매인,판매인)
		// 1. 회원 현재 포인트 -point 및 이력
		int buyerNewPoint = buyerCurPoint - workPoint;
		userDao.updateUserPoint(buyerVO.getSeq(), buyerNewPoint);
		// 이력 -
		PointHistoryVO buyHistVO = new PointHistoryVO();
		buyHistVO.setSign(SignType.PURCHASE);
		buyHistVO.setAmount(String.valueOf(workPoint));
		buyHistVO.setAccumAmount(String.valueOf(buyerNewPoint));
		buyHistVO.setMemberSeq(buyerVO.getSeq());
		buyHistVO.setOrderSeq(orderVO.getSeq());
		buyHistVO.setComments(comments);
		CmnUtil.setCmnDate(buyHistVO);
		dao.insertPointHistory(buyHistVO);

		// 2. 디자이너 현제 포인트 +point
		int sellerNewPoint = sellerCurPoint + workPoint;
		userDao.updateUserPoint(sellerVO.getSeq(), sellerNewPoint);
		// 이력 +
		PointHistoryVO sellHistVO = new PointHistoryVO();
		sellHistVO.setSign(SignType.SELL);
		sellHistVO.setAmount(String.valueOf(workPoint));
		sellHistVO.setAccumAmount(String.valueOf(sellerNewPoint));
		sellHistVO.setMemberSeq(sellerVO.getSeq());
		sellHistVO.setOrderSeq(orderVO.getSeq());
		sellHistVO.setComments(comments);
		CmnUtil.setCmnDate(sellHistVO);
		dao.insertPointHistory(sellHistVO);

		// ===== 알림 추가 ====
		AlarmVO alarmVO = insertAlarmForBuy(orderVO, buyerVO);
		websocketHandler.notifyAlarmChanged(alarmVO.getMemberSeq());
		// ===== ]]알림 추가 ====

		// ===log
		LOGGER.debug(">>> buyProduct");
		LOGGER.debug(workVO);
		LOGGER.debug(buyerVO);
		LOGGER.debug(sellerVO);
		LOGGER.debug(orderVO);
		LOGGER.debug(">>> workPoint=" + workPoint);
		LOGGER.debug(">>> buyerCurPoint=" + buyerCurPoint);
		LOGGER.debug(">>> sellerCurPoint=" + sellerCurPoint);
		LOGGER.debug(">>> buyerNewPoint=" + buyerNewPoint);
		LOGGER.debug(">>> sellerNewPoint=" + sellerNewPoint);
		LOGGER.debug(buyHistVO);
		LOGGER.debug(sellHistVO);
		LOGGER.debug(comments);

		//
		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 구매에 관련된 알림정보 등록
	 * 
	 * @param orderVO
	 * @param buyerVO
	 * @return
	 */
	private AlarmVO insertAlarmForBuy(OrderVO orderVO, UserVO userVO) {
		DesignWorkVO itemWorkVO = dao.selectDesignWorkBySeq(orderVO.getDesignWorkSeq());
		String alarmContents = String.format("나의 게시물 \"%s\"을 %s포인트로 구매하셨습니다.", itemWorkVO.getTitle(),
				itemWorkVO.getPoint());
		// 
		//
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setMemberSeq(itemWorkVO.getMemberSeq());
		alarmVO.setContents(alarmContents);
		alarmVO.setActionUri("");
		alarmVO.setActorSeq(userVO.getSeq());
		CmnUtil.setCmnDate(alarmVO);
		commonDao.insertAlarm(alarmVO);
		return alarmVO;
	}

	// ==================== 디자인 의뢰 =========================
	/**
	 * 디자인/제작 의뢰 목록 데이터 조회
	 * 
	 * @param searchVO:
	 *            schCate, schContent
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectDesignRequestBoardList(SearchVO searchVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO user = CmnUtil.getLoginUser(request);
		searchVO.setSchLoginUser(user);

		int allCount = dao.selectDesignRequestBoardAllCount(searchVO);
		List<RequestBoardVO> list = this.selectDesingRequestBoardWholeList(searchVO);

		resultMap.put("all_count", allCount);
		resultMap.put("list", list);
		return resultMap;
	}

	/**
	 * 디자인/제작 의뢰 조회: 관련된 상세정보도 함께 조회
	 * 
	 * @param searchVO
	 * @return
	 */
	private List<RequestBoardVO> selectDesingRequestBoardWholeList(SearchVO searchVO) {
		//
		String userSeq = CmnUtil.getLoginUserSeq(searchVO.getSchLoginUser());
		// 의뢰 게시글
		List<RequestBoardVO> list = dao.selectDesignRequestBoardList(searchVO);
		if (!CmnUtil.isEmpty(list)) {
			for (RequestBoardVO item : list) {
				// 게시글 카테고리
				List<CategoryVO> cateList = dao.selectRequestBoardCateList(item.getSeq());
				item.setCateList(cateList);
				// 게시글 이미지
				List<RequestBoardFileVO> fileList = dao.selectRequestBoardFileList(item.getSeq());
				item.setFileList(fileList);
				// 게시글 댓글
				List<RequestBoardCmmtVO> cmmtList = dao.selectRequestBoardCmmtList(item.getSeq());
				item.setCmmtList(cmmtList);

				// 권한관련
				if (!"".equals(userSeq) && userSeq.equals(item.getMemberSeq())) {
					item.setCurUserAuthYN(true);
				}
			}
		}
		return list;
	}

	/**
	 * 디자인/제작 의뢰 등록/수정 페이지 조회(이동)
	 * 
	 * @param boardVO:
	 *            seq
	 * @param request
	 * @return
	 */
	public Map<String, Object> openRequestBoardInsUpd(RequestBoardVO boardVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 회원구분
		String schMemberDiv = request.getParameter("schMemberDiv");
		resultMap.put("schMemberDiv", schMemberDiv);

		if (PageMode.UPDATE.equals(boardVO.getPageMode())) {
			String boardSeq = boardVO.getSeq();
			RequestBoardVO resultVO = dao.selectRequestBoardBySeq(boardSeq);
			// 줄바꿈처리
			CmnUtil.handleHtmlEnterBR2RN(resultVO, "contents");
			
			if (resultVO != null) {
				// 게시글 카테고리
				List<CategoryVO> cateList = dao.selectRequestBoardCateList(boardSeq);
				resultVO.setCateList(cateList);
				// 게시글 이미지
				List<RequestBoardFileVO> fileList = dao.selectRequestBoardFileList(boardSeq);
				resultVO.setFileList(fileList);
			}

			resultMap.put(RstConst.P_NAME, resultVO);
		} else {
			resultMap.put(RstConst.P_NAME, null);
		}

		resultMap.put("pageMode", boardVO.getPageMode());

		return resultMap;
	}

	/**
	 * 디자인/제작 의뢰 등록
	 * 
	 * @param boardVO
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Map<String, Object> insertRequestBoard(RequestBoardVO boardVO, MultipartHttpServletRequest request)
			throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO userVO = CmnUtil.getLoginUser(request);
		
		// 줄바꿈 처리
		CmnUtil.handleHtmlEnterRN2BR(boardVO, "contents");

		// === 1.db
		// 1.1 의뢰글
		boardVO.setMemberSeq(userVO.getSeq());
		CmnUtil.setCmnDate(boardVO);
		dao.insertRequestBoard(boardVO);

		String boardSeq = boardVO.getSeq();
		// 1.2 카테고리
		if (!CmnUtil.isEmpty(boardVO.getUiBoardCateCodes())) {
			for (String ccode : boardVO.getUiBoardCateCodes()) {
				RequestBoardCateVO cItem = new RequestBoardCateVO();
				cItem.setBoardSeq(boardSeq);
				cItem.setCategoryCode(ccode);
				dao.insertRequestBoardCategory(cItem);
			}
		}

		// 1.3 파일
		List<UpFileInfo> upFileInfoList = CmnUtil.handleMultiFileUpload(request, "uiImageUrlFile",
				FileUploadDomain.REQUEST_BOARD);
		if (!CmnUtil.isEmpty(upFileInfoList)) {
			for (UpFileInfo upInfo : upFileInfoList) {
				RequestBoardFileVO file = new RequestBoardFileVO();
				file.setBoardSeq(boardSeq);
				file.setFilename(upInfo.getFilename());
				file.setFileUrl(upInfo.getDbPath());
				CmnUtil.setCmnDate(file);
				dao.insertRequestBoardFile(file);
			}
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 디자인/제작 의뢰 수정
	 * 
	 * @param boardVO
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Map<String, Object> updateRequestBoard(RequestBoardVO boardVO, MultipartHttpServletRequest request)
			throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserVO userVO = CmnUtil.getLoginUser(request);
		
		// 줄바꿈 처리
		CmnUtil.handleHtmlEnterRN2BR(boardVO, "contents");

		// === 1.db
		// 1.1board
		CmnUtil.setCmnDate(boardVO);
		dao.updateRequestBoard(boardVO);

		String boardSeq = boardVO.getSeq();
		// 1.2category
		dao.deleteRequestBoardCategoryByBoardSeq(boardSeq);
		if (!CmnUtil.isEmpty(boardVO.getUiBoardCateCodes())) {
			for (String ccode : boardVO.getUiBoardCateCodes()) {
				RequestBoardCateVO cItem = new RequestBoardCateVO();
				cItem.setBoardSeq(boardSeq);
				cItem.setCategoryCode(ccode);
				dao.insertRequestBoardCategory(cItem);
			}
		}

		// 1.3 file
		List<UpFileInfo> upFileInfoList = CmnUtil.handleMultiFileUpload(request, "uiImageUrlFile",
				FileUploadDomain.REQUEST_BOARD);
		if (!CmnUtil.isEmpty(upFileInfoList)) {
			// 파일 올린것 있을때 수정
			dao.deleteRequestBoardFileByBoardSeq(boardSeq);
			for (UpFileInfo upInfo : upFileInfoList) {
				RequestBoardFileVO file = new RequestBoardFileVO();
				file.setBoardSeq(boardSeq);
				file.setFilename(upInfo.getFilename());
				file.setFileUrl(upInfo.getDbPath());
				CmnUtil.setCmnDate(file);
				dao.insertRequestBoardFile(file);
			}
		}

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	/**
	 * 디자인/제작 의뢰 삭제
	 * 
	 * @param boardVO
	 * @param request
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteRequestBoard(RequestBoardVO boardVO, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 1.1board
		String boardSeq = boardVO.getSeq();
		dao.deleteRequestBoard(boardSeq);
		// 1.2category
		dao.deleteRequestBoardCategoryByBoardSeq(boardSeq);
		// 1.3 file
		dao.deleteRequestBoardFileByBoardSeq(boardSeq);

		resultMap.put(RstConst.P_NAME, RstConst.V_SUCESS);
		return resultMap;
	}

	// ====================]] 디자인 의뢰 =========================
	
	
	/**
	 * 이달의 Best 디자이너/제작자 목록 조회
	 * @param paramMap
	 * @return
	 */
	public List<DesignerVO> selectBestDesignerList(Map<String, Object> paramMap) {
		
		List<DesignerVO> list = dao.selectBestDesignerList(paramMap);
		
		for (DesignerVO item : list) {
			// category
			List<MemberCategoryVO> cateList = dao.selectMemberCategoryList(item.getSeq());
			item.setCateList(cateList);
			// 작품
			List<DesignWorkVO> workList = dao.selectDesignWorkList(item.getSeq());
			item.setWorkList(workList);
			// 프로젝트
			List<ProjectVO> workPList = dao.selectProjectWorkList(item.getSeq());
			item.setWorkPList(workPList);
			
			// 전체 작품 리스트
			//List<Object> totalList = dao.selectTotalWorkList(item.getSeq());
			//item.settotalList(totalList);
		}

		return list;
	}
	
	
	/**
	 * 현재 로그온 사용자가 해당 디자이너에 대한 좋아요(작품 기준) 를 했는지 여부
	 * @param paramMap - logonUserSeq, designerSeq
	 * @return
	 */
	public boolean isLogonUserLikesDesigner(Map<String, Object> paramMap) {
		return dao.selectDesignerLikesByLogonUser(paramMap) > 0;
	}
	
	/**
	 * 현재 로그온 사용자가 해당 작품 기준 대한 좋아요를 했는지 여부
	 * @param paramMap - logonUserSeq, designerSeq
	 * @return
	 */
	public boolean isLogonUserLikesDesignWork(Map<String, Object> paramMap) {
		return dao.selectDesignWorkLikesByLogonUser(paramMap) > 0;
	}
	
	
}
