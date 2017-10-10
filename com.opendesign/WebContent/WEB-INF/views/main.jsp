<%-- 화면ID : OD 01-01-01 --%>
<%@page import="com.opendesign.utils.CmnConst.RstConst"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<script type="text/javascript">
		$(function(){
			var pageWidth = $('body').innerWidth();
			$('.visual > .slideWrapper').css('width', pageWidth * 3);
			$('.visual > .slideWrapper .img-box').css('width', pageWidth);
			movingSlide(0);
			
			function movingSlide(count){
			    setInterval(function(){
			        var slide_arr = $('.visual .slideWrapper .img-box');
			        $('.visual .slideWrapper .img-box').css('display', 'none');
			        $('.visual .slideWrapper .img-box').removeClass('active');
			        var active_div = slide_arr[count++ % slide_arr.length];
			        var id = active_div.getAttribute('id');
			        $('#'+id).css('display', 'block').addClass('active');
			    }, 2000);
			}
		});
	</script>
		
	<div class="main-content">
		<div class="visual">
			<div class="slideWrapper">
				<div class="img-box" id="slide-1">
					<img src="/resources/image/new/design_open3.png ">
					<span>Open Design</span>
					<button onclick="javascript:modalShow('#main-opendesign-modal');">오픈 디자인</button>
				</div>
				<div class="img-box" id="slide-2">
					<img src="/resources/image/new/design_easy2.png" >
					<span>Easy Design</span>
					<button onclick="javascript:modalShow('#main-easydesign-modal');">쉬운 디자인</button>
				</div>
				<div class="img-box" id="slide-3">
					<img src="/resources/image/new/design_with.png">
					<span>Design Together</span>
					<button onclick="javascript:modalShow('#main-designwith-modal');">함께하는 디자인</button>
				</div>
			</div>
		</div>

		<div class="inner">
			<div class="best">
				<div class="best-head">
					<span class="mainChar">D</span>
					<span>이달의 디자이너</span>
				</div>
				<div class="best-inner">
					<ul class="list-type2 list1 swiper-wrapper" id="designView">
						
					</ul>
					<div class="slide-btn hide">
						<button type="button" class="btn-prevSlide purchase-prev"><img src="../resources/image/mypage/btn_prevSlide.png" alt="이전"></button>
						<button type="button" class="btn-nextSlide purchase-next"><img src="../resources/image/mypage/btn_nextSlide.png" alt="다음"></button>
					</div>
				</div>
			</div>
			<div class="recommend">
				<div class="best-head">
					<span class="mainChar">D</span>
					<span>추천 디자인</span>
				</div>
				<div class="best-inner">
					<ul class="list-type1 " id="productView">
						
					</ul>
				</div>
			</div>
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

<script type="text/javascript">
	
	/* designer list 탬플릿 */
	var designerListTemplete = null;
	/* 디자이너 리스트 박스 */
	var designView = null;
	
	/* 디자인 list 탬플릿 */
	var productListTemplete = null;
	/* 디자인 리스트 박스 */
	var productView = null;
	
	/* 초기화 */
	$(function(){
		
		designerListTemplete = $("#tmpl-designerListTemplete").html();
		
		designView = new ListView({
			htmlElement : $('#designView')
		});
		
		
		productListTemplete = $("#tmpl-productListTemplete").html();
		
		productView = new ListView({
			htmlElement : $('#productView')
		});
		
		loadPage();
		
		/* 윈도우 스크롤 이벤트 : 프로젝트 로드 */
		$(window).on('mousewheel', function(e){
			if( e.originalEvent.wheelDelta / 120 > 0 ) {
				// to do nothing...
	        } else {
	        	/* 스크롤이 최하단일 경우 프로젝트 로드 */
	        	if ( $(window).scrollTop() == $(document).height() - $(window).height()) {
	        		//
	        		if(!productView.data('existList')) {
	        			return;
	        		}
	        		loadProductData();
	            }
	        }
		});
		
	});
	
	
	/**
	 * 프로젝트 데이터 로드
	 */
	function loadPage(){
		
		$.ajax({
			url : "/selectMainList.ajax",
	        type: "GET",
	        cache: false,
			data : {},
			success : function(_data){
				
				// === designer
				loadDesignerData(_data.designerList);

				// === product
				window.gb_productList = _data.productList || [];
				loadProductData();
				
			},
			error : function(req){
			}
			
		});
	}
	
	/**
	 * 디자이너 load
	 */
	function loadDesignerData(designerList) {
		var hasDesigners = designerList.length > 0;
		
		designView.putData('existList', hasDesigners);
		designView.addAll({keyName:'seq', data:designerList, htmlTemplate:designerListTemplete });
		
		//특수처리: 한번에 8개씩 움직이게: loading할때 한번 처리
		while($('#designView > li').length != 0) {
			$('#designView > li').slice(0,8).wrapAll('<ul class="list-type2 swiper-slide"></ul>');
		}
		
		//swiper:
		swipeInitDesigner();
	}
	
	/**
	 * 작품 load
	 */
	var flag_loadProductData = false;
	function loadProductData() {
		if(flag_loadProductData) {
			return;
		}
		flag_loadProductData = true;
		
		$('.wrap-loading').show();
		
		var productList = getProductData();
		var hasProducts = productList.length > 0;
		
		productView.putData('existList', hasProducts);
		productView.addAll({keyName:'seq', data:productList, htmlTemplate:productListTemplete });
		
		var loadDelay = 500; //ms
		setTimeout(function(){
			$('.wrap-loading').hide(); 
			flag_loadProductData = false;	
		}, loadDelay);
	}
	
	var gb_productList = [];
	var gb_product_start = 0;
	var gb_product_limitCnt = 10;
	function getProductData() {
		var start = gb_product_start;
		var end = gb_product_start + gb_product_limitCnt;
		gb_product_start = end;
		var list = gb_productList.slice(start, end);
		
		console.log('>>> getProductData: start='+start +', end='+end+', list.length=' + list.length); 
		return list;
	}
	
	
	
	/*
	 *swiper 디자이너/제작자
	 */
	var designerSwipe = null;
	function swipeInitDesigner() {
		var swipeContSel = '.best-inner';
		var slideBtn = $(swipeContSel).find('.slide-btn');
		var item = $(swipeContSel).find('li').length;
		
		if(designerSwipe == null) {
			designerSwipe = new Swiper(swipeContSel, {
		        //slidesPerView: 4,
		        //slidesPerColumn: 2,
		        //spaceBetween: 19,
		        //slidesPerColumnFill: "row",
		        simulateTouch: false,
		        nextButton: '.purchase-next',
		    	prevButton: '.purchase-prev'
		    });
		} else {
			designerSwipe.onResize();
		}
		
		if(item > 4*2){
			slideBtn.show();
		} else{
			slideBtn.hide();
		}
	}
	
	/**
	 * 디자인 상세 화면 이동
	 */
	function goProductView(productSeq){
		window.location.href = "/product/productView.do?seq=" + productSeq;
	}
	
	function goPortfolioView(seq, memberType) {
		
		if( memberType == '01') {
			window.location.href='/producer/portfolio.do?seq=' + seq;
		} else {
			window.location.href='/designer/portfolio.do?seq=' + seq;	
		}
	}
	
</script>



<script id="tmpl-designerListTemplete" type="text/x-jsrender">
				<li >
					<a href="javascript:goPortfolioView('{{:seq}}', '{{:memberType}}');"  >
					<div class="profile-section">
						<div class="picture"  >
							<img src="{{:imageUrl}}" onerror="setDefaultImg(this, 1);" alt="{{:uname}}">
						</div>
						<div class="profile">
							<p class="designer">{{:uname}}</p>
							<p class="cate"  >{{:cateNames}}</p>
							<div class="item-info">
								<span class="portfolio"><img src="/resources/image/common/ico_portfolio.png" alt="포트폴리오"> {{:workCntF}}</span>
								{{if !curUserLikedYN }}
								<span class="like"><img src="/resources/image/common/ico_like.png" alt="좋아요"> {{:likeCntF}}</span>
								{{else}}
								<span class="like"><img src="/resources/image/common/ico_like_active.png" alt="좋아요"> {{:likeCntF}}</span>
								{{/if}}
								<span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:viewCntF}}</span>
							</div>
						</div>
					</div>
					<ul class="portfolio-section">
						{{for top3WorkList}}
						<li><img src="{{:thumbUriM}}" onerror="setDefaultImg(this, 4);" alt="포트폴리오"></li>
						{{/for}}
					</ul>
					</a>
				</li>
</script>


<script id="tmpl-productListTemplete" type="text/x-jsrender">
	<li ><a href="javascript:goProductView('{{:seq}}');" style="width:301px;height:271px;" >
		<img src="{{:thumbUri}}" onerror="setDefaultImg(this, 3);" alt="" />
		<div class="product-info">
			<p class="product-title">{{:title}}</p>
			<p class="designer">{{:memberName}}</p>
		</div>
		<p class="cate" >{{:cateNames}}&nbsp;</p>
		<div class="item-info">
			{{if !curUserLikedYN }}
			<span class="like"><img src="/resources/image/common/ico_like.png" alt="좋아요"> {{:likeCntF}}</span>
			{{else}}
			<span class="like"><img src="/resources/image/common/ico_like_active.png" alt="좋아요"> {{:likeCntF}}</span>
			{{/if}}
			<span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:viewCntF}}</span>
			<span class="update">{{:displayTime}}</span>
		</div>
	</a></li>
</script>



<!-- 오픈디자인 모달창 -->
<div class="modal" id="main-opendesign-modal">
		<div class="bg"></div>
		<div class="modal-inner mainModal">
			<div class="modal-body">
				<h3 align="center">오픈 디자인</h3>
				<div class="row">
					<div class="para">
						디자인은 인간의 창의성을 담는 그릇입니다. <br>
						창의력이 핵심 경쟁력이 될 미래는 디자인 중심 사회가 될 것입니다. <br>
						세계 각국은 디자인의 중요성을 깨달아 디자인에 대한 투자를 늘리고 있지만
						아직 대부분의 사람들에게 디자인은 어려운 분야입니다. <br>
						사람들이 쉽게 디자인을 접하고, 경험하며, 배울 수 있는 디자인 인프라 구축이 중요한 시점입니다. <br>
						오픈 디자인은 “쉬운 디자인, 함께하는 디자인”을 추구하는 웹 사이트입니다. <br>
						누구나 쉽고 재미있게, 시간과 장소에 구애 받지 않고
						함께 어울리며 디자인할 수 있는 환경을 만들고자 합니다.
					</div>
					<h4>1. 쉬운 디자인</h4>
					 <p> 우리 사이트에서는 디자인이 쉬워집니다. 기존의 디자인을 약간 수정하거나 보완하여 새로운 디자인을 만들고, 
					 이를 다시 공유하는 디자인 공유 사이클이 활성화됩니다. 간단한 디자인 모듈들을 조립하여 복잡한 디자인을 만들 수 있는 모듈형 디자인도 가능해집니다. </p>
                    <img src="/resources/image/new/easy.png">
					<h4>2. 함께하는 디자인</h4>
					<p> 우리 사이트에서는 디자인이 재미있어집니다. 온라인 협업 디자인 환경을 제공하는 프로젝트 기능을 이용해서 
					언제, 어디서나, 누구와도 함께 어울리며 즐겁게 디자인을 경험하면서 배울 수 있습니다. </p>
                    <img src="/resources/image/new/pro8.png">
                    <img src="/resources/image/new/pro5.png">
                    <img src="/resources/image/new/pro6.png">
                    <img src="/resources/image/new/pro7.png">
                    <div class="para">
                    	디자인 공유 사이클이 활성화되기 위해서는 디자인 아이디어의 공유 못지않게 디자인 과정에서의 산출물들을 포함하는 디자인 소스 공유가 중요합니다. 
                    	우리 사이트는 디자인 프로젝트를 이용하여 디자인 소스를 공유할 수 있도록 지원합니다.
                        디자인 공유를 통해 오픈 소스 소프트웨어가 소프트웨어 분야에 미친 효과를 디자인 분야에도 가져올 수 있도록 노력할 것이며, 구체적으로 다음과 같은 효과를 기대합니다.
						<ul>
	                        <li>•	개개인의 작은 디자인 아이디어가 모여 최고의 디자인으로 발전할 수 있습니다. (집단 지성)</li>
	                        <li>•	사장될 수도 있는 개인의 디자인 아이디어가 다른 사람들에게 영감을 주어 디자인 생태계의 발전을 가져옵니다. (창의성 고양)</li>
	                        <li>•	일반인들의 디자인에 대한 관심을 끌고 안목을 키워줄 수 있습니다.</li>
	                        <li>•	디자인의 독창성, 우수성, 개선사항 등에 대한 다양한 분야의 전문가들의 평가와 조언을 즉각적으로 얻을 수 있습니다.</li>
	                        <li>•	많은 발명이나 발견이 인류공영에 이바지 하였듯이 훌륭한 디자인이 수많은 사람들에게 혜택을 줄 수 있습니다.</li>
						</ul>
                        디자인에 대한 투자는 기술에 대한 투자보다 30배 이상의 이득이 있다고 합니다. 우리나라 디자인 산업 분야에서의 1% 생산성 증가가 1조원 이상의 가치를 창출합니다. 
                        디자인 중심 사회가 될 미래에 핵심 가치를 창출할 수 있도록 여러분들의 적극적인 참여를 부탁드립니다.
                    </div>
				</div>
			</div>
			<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
		</div>
</div>


<!-- 쉬운 디자인 모달창 -->
<div class="modal" id="main-easydesign-modal">
		<div class="bg"></div>
		<div class="modal-inner mainModal">
			<div class="modal-body">
				<h3 align="center">쉬운 디자인</h3>
				<div class="row">
					<div class="para">
						우리 사이트에서는 디자인이 쉬워집니다.
						기존의 디자인을 약간 수정하거나 보완하여 새로운 디자인을 만들고,
						이를 다시 공유하는 디자인 공유 사이클이 활성화됩니다.
						간단한 디자인 모듈들을 조립하여 복잡한 디자인을 만들 수 있는 모듈형 디자인도 가능해집니다.
					</div>
					<h4>1. 디자인 공유하기</h4>
					<p>상단 메뉴에서 디자인 페이지로 이동합니다.</p>
                    <img src="/resources/image/new/design-process.png">
                    <p>로그인 후 디자인 등록 버튼을 클릭 합니다.</p>
                    <img src="/resources/image/new/design-process2.png">
                    <img src="/resources/image/new/design-process4.png">
                    <p>디자인 등록창에서 디자인명, 카테고리, 라이센스, 테크, 디자인 파일, 오픈디자인 파일을 등록하여 완료합니다.</p>
                    <img src="/resources/image/new/design-process3.png">
                    <h4>2. 파생디자인 등록하기</h4>
                    <p>새 디자인이란 기존에 있는 디자인 작품을 통해 새롭게 변형하거나 추가하여 새로운 디자인을 만드는 것을 얘기합니다.</p>
                    <p>디자인 상세 페이지에서 기존 디자인 작품을 올린 디자이너의 공유 범위를 CC라이센스를 통해 확인 할 수 있습니다. 
                    이를 통해 공유하거나 재생산 할 수 있는 작품이면 새디자인 버튼을 클릭하여 출처 등이 자동으로 등록되며 기존 디자인 작품의 소스를 다운받아 
                    새롭게 디자인 작품을 등록 할 수 있습니다. </p>
                    <img src="/resources/image/new/easy.png">
                    <h4>3. 원본 디자인 보기</h4>
                    <p>원본보기 버튼을 클릭하면 기존 작품이 어떠한 작품에서 파생되었는지를 확인 할 수 있습니다.
                    이를 통해 디자인 공유가 쉬워지고 보호 받을 수 있으며 그안에서 누구나 쉽게 디자인 할 수 있습니다.</p>
                    <img src="/resources/image/new/easy4.png">
				</div>
			</div>
			<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
		</div>
</div>


<!-- 함께하는 디자인 모달창 -->
<div class="modal" id="main-designwith-modal">
		<div class="bg"></div>
		<div class="modal-inner mainModal">
			<div class="modal-body">
				<h3 align="center">함께하는 디자인</h3>
				<div class="row">
					<div class="para">
						우리 사이트에서는 디자인이 재미있어집니다.
						온라인 협업 디자인 환경을 제공하는 프로젝트 기능을 이용해서
						언제, 어디서나, 누구와도 함께 어울리며 즐겁게 디자인을 경험하면서 배울 수 있습니다.
					</div>
					<h4>1. 프로젝트</h4>
                    <p>상단 메뉴에서 프로젝트 버튼을 눌러 프로젝트 페이지로 넘어갑니다.</p>
                    <img src="/resources/image/new/pro1.png">
                    <p>프로젝트 생성 버튼을 클릭하여 새로운 프로젝트를 만듭니다.</p>
                    <img src="/resources/image/new/proprocess1.png">
                    <p>프로젝트 생성 페이지에서 양식에 맞추어 프로젝트 생성을 진행합니다.
                 		멤버를 검색해 프로젝트 멤버로 초대할 수도 있습니다.
                    	프로젝트 서비스를 통해 여러 사람이 함께 모여 디자인 프로젝트를 진행 할 수 있습니다.</p>
                    <img src="/resources/image/new/proprocess2.png">
                    <p>프로젝트 안에서 업로드를 통해 단계 추가, 단계에 맞는 작품 추가를 하며 프로젝트를 진행해 나갑니다.</p>
                    <img src="/resources/image/new/pro4.png">
                    <p>또한 단계 추가를 프로젝트 성격에 맞추어 주차별, 기능별, 프로젝트에 참여한 사람의 이름명으로 하여 자유롭게 프로젝트를 진행할 수 있습니다.</p>
                    <img src="/resources/image/new/pro5.png">
                    <img src="/resources/image/new/pro6.png">
                    <img src="/resources/image/new/pro7.png">
                    <h4>2. 그룹</h4>
                    <p>프로젝트 그룹 기능을 이용하여 학교나 기업체 같은 조직에서 프로젝트 팀들을 편리하게 관리할 수 있습니다.
                    	프로젝트 페이지에서 그룹 생성 및 관리 버튼을 클릭합니다.</p>
                    <img src="/resources/image/new/grpro1.png">
                    <p>그룹명을 입력하고 추가 버튼을 클릭합니다. 완료버튼을 눌러 그룹 생성을 완료합니다.</p>
                    <img src="/resources/image/new/group1.png">
                    <img src="/resources/image/new/group2.png">
                    <p>이제 프로젝트를 만든 프로젝트장이 자신의 프로젝트를 원하는 그룹에 가입 신청을 합니다.
                    		프로젝트 페이지에서 프로젝트 관리 버튼을 클릭합니다.</p>
                    <img src="/resources/image/new/group3.png">
                    <p>프로젝트장이 자신이 개설한 프로젝트를 검색합니다.</p>
                    <img src="/resources/image/new/group4.png">
                    <p>프로젝트를 가입하게 할 그룹을 검색합니다.
                    	수정 완료를 누르면 그룹장에게 프로젝트 가입 요청이 갑니다.</p>
                    <img src="/resources/image/new/group5.png">
                    <p>그룹장이 그룹 관리및 생성 페이지에서 승인을 누르면 해당 프로젝트가 그룹에 가입됩니다.</p>
                    <img src="/resources/image/new/group6.png">
				</div>
			</div>
			<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
		</div>
</div>




</body>
</html>