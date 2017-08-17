<%-- 화면ID : OD02-01-01 --%>
<%@page import="com.opendesign.vo.MainItemVO.MainItemType"%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.opendesign.utils.StringUtil"%>
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
	
	<!-- custom-btn 처리 -->
	<script id="tmpl-custom-btn" type="text/x-jsrender">
				<li class="last"><a href="javascript:goPage('/product/productRegi.do', true);" class="btn-product">디자인 등록</a></li>
	</script>
	<script>
		$(function(){
			$('.nav-cate').eq(0).find('ul').eq(0).append($('#tmpl-custom-btn').html());
		});
	</script>

	<!-- content -->
	<div class="content">
		<div class="inner">
			<h2 id="product_all_cnt"  class="title">디자인 (0건)</h2>
			<div class="sorting">
				<a href="javascript:sortProduct();" class="first active">최신순</a>
				<a href="javascript:sortProduct('LIKE');">인기순</a>
			</div>
			<ul id="ing-product-list" class="list-type1"></ul>
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
	//디자인 페이지 인덱스
	String schPage = request.getParameter("schPage");
	
	String schSort = request.getParameter("schSort");
%>
<form id="listParamForm" name="listParamForm" method="GET" action="" >
	<input type="hidden" name="schCate" value="<%=StringUtil.nullToBlank(schCate) %>" />
	<input type="hidden" name="schPage" value="" />
	<input type="hidden" name="schSort" value="" />
	<input type="hidden" name="seq" value="" />
	<input type="hidden" name="schLimitCount" value="20" />
</form>
<%--
<script id="tmpl-listTemplete" type="text/x-jsrender">
	<li><a href="javascript:goProductView('{{:seq}}');" style="width:301px;height:271px;" >
		<img src="{{:thumbUri}}" alt="" style="width:301px;height:234px;" />
		<div class="product-info">
			<p class="product-title">{{:title}}</p>
			<p class="designer">{{:memberName}}</p>
		</div>
		<p class="cate" >{{:cateNames}}&nbsp;</p>
		<div class="item-info">
			<span class="like"><img src="/resources/image/common/ico_like.png" alt="좋아요"> {{:likeCnt}}</span>
			<span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:viewCnt}}</span>
			<span class="update">{{:displayTime}}</span>
		</div>
	</a></li>
</script>
 --%>
<script id="tmpl-listTemplete" type="text/x-jsrender">
				<li><a href="javascript:void(0);" onclick="goDetailView('{{:itemType}}','{{:seq}}');" >
					{{if itemType == '<%=MainItemType.PROJECT%>' }} 
					<i class="label"><img src="/resources/image/common/label_project.png" alt="PROJECT"></i>
					{{/if}}		
					<img src="{{:thumbUriM}}" onerror="setDefaultImg(this, 3);" alt="이미지" >
					<div class="product-info">
						<p class="product-title">{{:title}}</p>
						<p class="designer">{{:memberName}}</p>
					</div>
					<p class="cate" >{{:cateName}} &nbsp;</p>
					<div class="item-info">
						
						{{if !curUserLikedYN }}	
						<span class="like"><img src="/resources/image/common/ico_like.png" alt="좋아요"> {{:likeCntF}}</span>
						{{else}}
						<span class="like"><img src="/resources/image/common/ico_like_active.png" alt="좋아요"> {{:likeCntF}}</span>
						{{/if}}

						{{if itemType == '<%=MainItemType.PROJECT%>' }}
						<span class="member"><img src="/resources/image/common/ico_member.png" alt="멤버"> {{:projectMemberCntF}}</span>
						{{else}}
						<span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:viewCntF}}</span>
						{{/if}}
						<span class="update">{{:displayTime}}</span>
					</div>
				</a></li>
</script>
<script>
/**
 * 상세 페이지 
 */
function goDetailView(itemType, seq) {
	console.log('itemType=' + itemType + ', seq=' + seq);
	if(itemType == '<%=MainItemType.PROJECT%>') {
		//프로젝트
		window.location.href='/project/openProjectDetail.do?projectSeq=' + seq;
	} else {
		//디자인
		window.location.href='/product/productView.do?seq=' + seq;
	}
}
</script>
<script type="text/javascript">
	
	/* project list 탬플릿 */
	var productLitTemplete = $('#tmpl-listTemplete').html();

	/* list form 객체 */
	var listForm = null;
	var prodcutView = null;
	
	/* 초기화 */
	$(function(){
		/* 진행중인 디자인 객체 생성 */
		prodcutView = new ListView({
			id : 'productView'
			, htmlElement : $('#ing-product-list')
		});
		
		/* 디자인목록 조건 form */
		listForm = $('#listParamForm');
		/* 파라미터 초기화 */
		initParam();
		
		/* 진행중인 디자인 데이터 로드 */
		loadProduct();
		
		/* 윈도우 스크롤 이벤트 : 디자인 로드 */
		$(window).on('mousewheel', function(e){
			if( e.originalEvent.wheelDelta / 120 > 0 ) {
				// to do nothing...
	        } else {
	        	/* 스크롤이 최하단일 경우 디자인 로드 */
	        	if ( $(window).scrollTop() == $(document).height() - $(window).height()) {
	        		if( ! prodcutView.data('existList') ){
	        			return;
	        		}
	        		
	        		loadProduct(true);
	            }
	        }
		});
	});
	
	/**
	 * 검색 파라미터 초기화
	 */
	function initParam(){
		var from = listForm;
		
		var sortVal = '<%=StringUtil.nullToBlank(schSort) %>';
		
		from.find('input[name="seq"]').val('');
		from.find('input[name="schPage"]').val('<%=StringUtil.emptyToString(schPage, "1") %>');
		from.find('input[name="schSort"]').val(sortVal);
		
		var aSorting = $('.sorting').find('a');
		aSorting.removeClass('active');
		
		var aIndex = 0;
		if( sortVal == 'LIKE' ){
			aIndex = 1;
			
		}
		
		aSorting.eq(aIndex).addClass('active');
		
	}
	
	function sortProduct(sortType){
		sortType = sortType || '';
		
		var from = listForm;
		from.find('input[name="seq"]').val('');
		from.find('input[name="schPage"]').val('<%=StringUtil.emptyToString(schPage, "1") %>');
		from.find('input[name="schSort"]').val(sortType);
		from.submit();
	}
	
	<%--
	/**
	 * 디자인 상세 화면 이동
	 */
	function goProductView(productSeq){
		listForm.find('input[name="seq"]').val(productSeq);
		listForm.prop('action', '/product/productView.do');
		listForm.submit();
	}
	--%>
	
	/* scroll 데이터 로드 여부 */
	var flagScrollLoad = false;
	/**
	 * 디자인 데이터 로드
	 */
	function loadProduct( flagScroll ){
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
			url : "/product/productList.ajax",
	        type: "GET",
	        cache: false,
			data : from.serializeArray(),
			success : function(_data){
				if( flagScroll ) flagScrollLoad = false;		
				
				var allCount = _data.all_count;
				var item = _data.item;
				
				if(item.thirdCategoryNm != null && item.thirdCategoryNm != "") {
					$('#product_all_cnt').html('디자인 > ' + item.firstCategoryNm + ' > ' + item.secondCategoryNm + ' > ' + item.thirdCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');	
				} else if(item.secondCategoryNm != null && item.secondCategoryNm != "") {
					$('#product_all_cnt').html('디자인 > ' + item.firstCategoryNm + ' > ' + item.secondCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');
				} else if(item.firstCategoryNm != null && item.firstCategoryNm != "") {
					$('#product_all_cnt').html('디자인 > ' + item.firstCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');
				} else {
					$('#product_all_cnt').html('디자인 (' + formatNumberCommaSeparate(allCount) + '건)');
				}

				var listData = _data.list;
				var listCount = listData.length;
				var existList = listCount > 0; 
				prodcutView.putData('existList', existList);				
				if( ! existList ){					
					return;
					
				}
				
				//var totalCount = _data.total_count;			
				var intPageCount = parseInt(pageTarget.val(), 10);
				intPageCount++;
				pageTarget.val(intPageCount);
				
				
				prodcutView.addAll({keyName:'seq', data:listData, htmlTemplate:productLitTemplete });
				
			},
			error : function(req){
				if( flagScroll ) flagScrollLoad = false;
				console.log("fail to loadProduct processing!");
			}
		});
	}
</script>
</body>
</html>