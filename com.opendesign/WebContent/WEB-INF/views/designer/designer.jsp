<%-- 화면ID : OD04-01-01 --%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@page import="com.opendesign.utils.CmnConst.SchOrderType"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String schMemberDiv = (String)request.getAttribute("schMemberDiv");  //회원구분
	String memberDivString = "";
	if(MemberDiv.DESIGNER.equals(schMemberDiv)) {
		memberDivString = "디자이너";
	} else {
		memberDivString = "제작자";
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body>
<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="Y" />
	</jsp:include> 
	<!-- //header -->
	

	<!-- content -->
	<div class="content">
		<div class="inner">
			<h2 class="title" id="allCnt"><%=memberDivString%> (0건)</h2>
			<div class="product-btn-wrap">
				<div class="sorting">
					<a href="javascript:void(0);" onclick="goPageOrdered(this);" data-order="<%=SchOrderType.LATEST%>" class="first active btn-red">최신순</a>
					<a href="javascript:void(0);" onclick="goPageOrdered(this);" data-order="<%=SchOrderType.HOTTEST%>" class="btn-red">인기순</a>
				</div>
				<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
							<div class="last"><a href="/designer/openDesignRequestBoard.do?schMemberDiv=<%=schMemberDiv%>" class="btn-design btn-red">디자인 의뢰</a></div>
							<%	} else { %>
							<div class="last"><a href="/producer/openDesignRequestBoard.do?schMemberDiv=<%=schMemberDiv%>" class="btn-design btn-red">제작 의뢰</a></div>
							<%	} 	%>
				<button class="btn-help btn-red" type="button" onclick="javascript:modalShow('#help-modal-designer');">도움말</button>
			</div>
			<ul class="list-type2" id="designView">
				<!--  template
				<li><a href="portfolio.html">
					<div class="profile-section">
						<div class="picture">
							<img src="../resources/image/main/pic_profile.jpg" alt="김민희">
						</div>
						<div class="profile">
							<p class="designer">천재비행소년</p>
							<p class="cate">의상디자인</p>
							<div class="item-info">
								<span><img src="../resources/image/common/ico_portfolio.png" alt="포트폴리오"> 38</span>
								<span><img src="../resources/image/common/ico_like.png" alt="좋아요"> 380</span>
								<span><img src="../resources/image/common/ico_hit.png" alt="열람"> 181</span>
							</div>
						</div>
					</div>
					<ul class="portfolio-section">
						<li><img src="../resources/image/main/img_portfolio1.jpg" alt="포트폴리오"></li>
						<li><img src="../resources/image/main/img_portfolio2.jpg" alt="포트폴리오"></li>
						<li><img src="../resources/image/main/img_portfolio3.jpg" alt="포트폴리오"></li>
					</ul>
				</a></li>
				-->
				 
			</ul>
		</div>
	</div>
	<!-- //content -->

	<!-- footer -->
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<!-- //footer -->
</div>
<!-- modal -->
<%@include file="/WEB-INF/views/common/modal.jsp"%>
<!-- //modal -->

<%
	//카테고리
	String schCate = request.getParameter("schCate");
	//페이지 인덱스
	String schPage = request.getParameter("schPage"); 
%>
<form id="listParamForm" name="listParamForm" method="GET" action="" >
	<input type="hidden" name="schCate" value="<%=StringUtil.nullToBlank(schCate) %>" /> <!-- 카테고리 --> 
	<input type="hidden" name="schPage" value="" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schOrderType" value="<%=SchOrderType.LATEST%>" /> <!-- 최신순 --> 
	<input type="hidden" name="schMemberDiv" value="<%=schMemberDiv%>" /> <!-- 회원구분 --> 
	<input type="hidden" name="schLimitCount" value="16" />
</form>
<script>
	/**
	 * 정열된 page load
	 */
	function goPageOrdered(thisObj) {
		//ui
		$(thisObj).parent().find('a').removeClass('active');
		$(thisObj).addClass('active');
		//
		var orderType = $(thisObj).data('order');
		var myForm = $('#listParamForm');
		myForm.find('[name="schPage"]').val('1');
		myForm.find('[name="schOrderType"]').val(orderType);
		//
		designView.clear();
		loadPage(designView);
	}
</script>
<script id="tmpl-listTemplete" type="text/x-jsrender">
				<li><a href="javascript:goPortfolioView('{{:seq}}');"  >
					<div class="profile-section">
						<div class="picture"  >
							<img src="{{:imageUrl}}" onerror="setDefaultImg(this, 1);" alt="{{:uname}}">
						</div>
						<div class="profile">
							<p class="designer">{{:uname}}</p>
							<p class="cate"  >{{:cateNames}}</p>
							<div class="item-info">
								<span class="portfolio"><i class="fa fa-list" aria-hidden="true"></i> {{:workCntF}}</span>
								{{if !curUserLikedYN }}
								<span class="like"><i class="fa fa-heart-o" aria-hidden="true"></i> {{:likeCntF}}</span>
								{{else}}
								<span class="like"><i class="fa fa-heart" aria-hidden="true"></i> {{:likeCntF}}</span>
								{{/if}}
								<span class="hit"><i class="fa fa-hand-pointer-o" aria-hidden="true"></i> {{:viewCntF}}</span>
							</div>
						</div>
					</div>
					<ul class="portfolio-section">
						{{for top3WorkList}}
						<li><img src="{{:thumbUriM}}" onerror="setDefaultImg(this, 4);" alt="포트폴리오" alt="포트폴리오"></li>
						{{/for}}
					</ul>
				</a></li>
</script>
<script>
/**
 * 포트폴리오 페이지
 */
function goPortfolioView(seq) {
	<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
	window.location.href='/designer/portfolio.do?seq=' + seq;
	<% } else { %>
	window.location.href='/producer/portfolio.do?seq=' + seq;
	<% } %>
}
</script>
<script type="text/javascript">
	
	/* list 탬플릿 */
	var listTemplete = $("#tmpl-listTemplete").html();

	/* list form 객체 */
	var listForm = null;
	/* 디자이너 객체 */
	var designView = null;
	
	/* 초기화 */
	$(function(){
		/* 진행중인 프로젝트 객체 생성 */
		designView = new ListView({
			htmlElement : $('#designView')
		});
		
		/* 프로젝트목록 조건 form */
		listForm = $('#listParamForm');
		/* 파라미터 초기화 */
		initParam();
		
		/* 진행중인 프로젝트 데이터 로드 */
		loadPage(designView);
		
		/* 윈도우 스크롤 이벤트 : 프로젝트 로드 */
		$(window).on('mousewheel', function(e){
			if( e.originalEvent.wheelDelta / 120 > 0 ) {
				// to do nothing...
	        } else {
	        	/* 스크롤이 최하단일 경우 프로젝트 로드 */
	        	if ( $(window).scrollTop() == $(document).height() - $(window).height()) {
	        		var targetView = designView;
	        		if( ! targetView.data('existList') ){
	        			return;
	        		}
	        		
	        		loadPage(targetView, true);
	            }
	        }
		});
	});
	
	/**
	 * 검색 파라미터 초기화
	 */
	function initParam(){
		var from = listForm;
		from.find('input[name="schPage"]').val('<%=StringUtil.emptyToString(schPage, "1") %>');
	}
	
	
	/* scroll 데이터 로드 여부 */
	var flagScrollLoad = false;
	/**
	 * 프로젝트 데이터 로드
	 */
	function loadPage( targetView, flagScroll ){
		/* 스크롤 */
		if( flagScroll ){
			if( flagScrollLoad ){
				return;
			}
			
			flagScrollLoad = true;		
		}
		
		var from = listForm;
		var pageTarget = from.find('input[name="schPage"]');
		
		$.ajax({
			url : "/designer/designerList.ajax",
	        type: "GET",
	        cache: false,
			data : from.serializeArray(),
			success : function(_data){
				console.log('>>> _data: ');
				console.log(_data);
				if( flagScroll ) { flagScrollLoad = false; }		
				
				var allCount = _data.all_count;
				var item = _data.item;
				var memberDivString = '<%= memberDivString%>';
				
				if(item.thirdCategoryNm != null && item.thirdCategoryNm != "") {
					$('#allCnt').html(memberDivString + ' > ' + item.firstCategoryNm + ' > ' + item.secondCategoryNm + ' > ' + item.thirdCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');	
				} else if(item.secondCategoryNm != null && item.secondCategoryNm != "") {
					$('#allCnt').html(memberDivString + ' > ' + item.firstCategoryNm + ' > ' + item.secondCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');
				} else if(item.firstCategoryNm != null && item.firstCategoryNm != "") {
					$('#allCnt').html(memberDivString + ' > ' + item.firstCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');
				} else {
					$('#allCnt').html(memberDivString + '(' + formatNumberCommaSeparate(allCount) + '건)');
				}

				var listData = _data.list;
				var listCount = listData.length;
				var existList = listCount > 0; 
				targetView.putData('existList', existList);				
				if( ! existList ){					
					return;
				}
				
				
				var intPageCount = parseInt(pageTarget.val(), 10);
				intPageCount++;
				pageTarget.val(intPageCount);
				
				targetView.addAll({keyName:'seq', data:listData, htmlTemplate:listTemplete });
				
			},
			error : function(req){
				if( flagScroll ) {flagScrollLoad = false;}
				console.log("fail to loadPage processing!");
			}
		});
	}
</script>

<!-- 도움말 모달 -->

	<div class="modal" id="help-modal-designer">
		<div class="bg"></div>
		<div class="modal-inner helpModal">
			<div class="modal-body">
				<h3 align="center">쉬운 디자인</h3>
				<div class="row">
					<h4>1. 디자인의뢰</h4>
					<p>디자이너 페이지에서 디자인 의뢰 버튼을 클릭합니다.</p>
					<img src="/resources/image/help/designer1.png">
					<p>디자인 의뢰 게시판 페이지에서 글 등록 버튼을 클릭합니다.</p>
					<img src="/resources/image/help/designer2.png">
					<p>디자인 의뢰 양식에 맞추어 자신이 원하는 디자인을 공개적으로 의뢰합니다.</p>
					<img src="/resources/image/help/designer3.png">
				</div>
			</div>
			<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
		</div>
	</div>
	
</body>
</html>