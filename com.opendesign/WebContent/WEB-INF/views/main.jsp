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
			    }, 2500);
			}
		});
	</script>
		
	<div class="main-content">
		<div class="visual">
			<div class="slideWrapper">
				<div class="img-box" id="slide-1">
					<img src="/resources/image/common/main-slide1.png" alt="Open Source Design은, 디자인 소스의 공유를 통해서 일반인, 디자이너, 제작자 모두가 좋은 디자인 좋은 제품을 만들어 갈 수 있는 환경을 제공해 줍니다.">
				</div>
				<div class="img-box" id="slide-2">
					<img src="/resources/image/common/main-slide2.jpg" alt="Open Source Design은, 디자인 소스의 공유를 통해서 일반인, 디자이너, 제작자 모두가 좋은 디자인 좋은 제품을 만들어 갈 수 있는 환경을 제공해 줍니다.">
				</div>
				<div class="img-box" id="slide-3">
					<img src="/resources/image/common/main-slide3.jpg" alt="Open Source Design은, 디자인 소스의 공유를 통해서 일반인, 디자이너, 제작자 모두가 좋은 디자인 좋은 제품을 만들어 갈 수 있는 환경을 제공해 줍니다.">
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
					<span>추천디자인</span>
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




</body>
</html>