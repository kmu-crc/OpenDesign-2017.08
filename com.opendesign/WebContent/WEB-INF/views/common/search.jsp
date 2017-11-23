<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String searchWord = request.getParameter("searchWord");
if( searchWord == null || "".equals(searchWord) ) {
	searchWord = "";
}
%>

<!DOCTYPE html>
<html lang="ko">
<meta charset="utf-8">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- template: 디자인 -->
<script id="tmpl-0" type="text/x-jsrender">
					<li><a href="/product/productView.do?seq={{:seq}}">
						<div class="imgWrapper">
							<img src="{{:thumbUri}}" alt="" width="100%" height="auto">
						</div>
						<div class="product-info">
							<p class="product-title">{{:title}}</p>
							<p class="designer">{{:memberName}}</p>
						</div>
						<p class="cate">{{:cateNames}}</p>
						<div class="item-info">
							<span class="like"><i class="fa fa-heart-o" aria-hidden="true"></i> {{:likeCntF}}</span>
							<span class="hit"><i class="fa fa-hand-pointer-o" aria-hidden="true"></i>{{:viewCntF}}</span>
							<span class="update">{{:displayTime}}</span>
						</div>
					</a></li>
</script>
<!-- template: 프로젝트 -->
<script id="tmpl-1" type="text/x-jsrender">
					<li><a href="/project/openProjectDetail.do?projectSeq={{:seq}}">
						<div class="imgWrapper">
							<img src="{{:fileUrl}}" alt="프로젝트 이미지" width="100%" height="auto">
						</div>
						<div class="product-info">
							<p class="product-title">{{:projectName}}</p>
							<p class="designer">{{:ownerName}}</p>
						</div>
						<p class="cate">{{:categories}}&nbsp;</p>
						<div class="item-info">
							<span class="member"><i class="fa fa-user" aria-hidden="true"></i> {{:projectMemberCnt}}</span>
							<span><i class="fa fa-window-restore" aria-hidden="true"></i> {{:projectWorkCntF}}</span>
							<span><i class="fa fa-heart-o" aria-hidden="true"></i> {{:likeCnt}}</span>
							<span class="update">{{:displayTime}}</span>
						</div>
					</a></li>
</script>
<!-- template: 디자이너 -->
<script id="tmpl-2" type="text/x-jsrender">
					<li><a href="/designer/portfolio.do?seq={{:seq}}">
						<div class="profile-section">
							<div class="picture">
								<img src="{{:imageUrl}}" alt="{{:uname}}">
							</div>
							<div class="profile">
								<p class="designer">{{:uname}}</p>
								<p class="cate"  >{{:cateNames}}</p>
								<div class="item-info">
									<span class="portfolio"><i class="fa fa-list" aria-hidden="true"></i>{{:workCntF}}</span>
									<span class="like"><i class="fa fa-heart-o" aria-hidden="true"></i>{{:likeCntF}}</span>
									<span class="hit"><i class="fa fa-hand-pointer-o" aria-hidden="true"></i>{{:viewCntF}}</span>
								</div>
							</div>
						</div>
						<ul class="portfolio-section">
							{{for top3WorkList}}
							<li><img src="{{:thumbUri}}"   alt="포트폴리오"></li>
							{{/for}}
						</ul>
					</a></li>
</script>
<!-- template: 제작자 -->
<script id="tmpl-3" type="text/x-jsrender">
					<li><a href="/producer/portfolio.do?seq={{:seq}}">
						<div class="profile-section">
							<div class="picture">
								<img src="{{:imageUrl}}" alt="{{:uname}}">
							</div>
							<div class="profile">
								<p class="designer">{{:uname}}</p>
								<p class="cate"  >{{:cateNames}}</p>
								<div class="item-info">
									<span class="portfolio"><img src="/resources/image/common/ico_portfolio.png" alt="포트폴리오"> {{:workCnt}}</span>
									<span class="like"><img src="/resources/image/common/ico_like.png" alt="좋아요"> {{:likeCnt}}</span>
									<span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:viewCnt}}</span>
								</div>
							</div>
						</div>
						<ul class="portfolio-section">
							{{for top3WorkList}}
							<li><img src="{{:thumbUri}}"   alt="포트폴리오"></li>
							{{/for}}
						</ul>
					</a></li>
</script>
<!-- template: 그룹 -->
<script id="tmpl-4" type="text/x-jsrender">
					<li class="group-list-li"><a href="javascript:void(0);" onclick="goGroupDetailView(this, {{:seq}});" data-nm="{{:groupName}}" >
						<div class="project-info">
							<div class="head-groupName">
								<span>{{:groupName}}</span>
							</div>
							<div class="head-leader">
								<span>{{:memberName}}</span>
							</div>
							<div class="head-memberNum">
								<span><i class="fa fa-user" aria-hidden="true"></i>{{:memberCntF}}</span>
							</div>
							<div class="head-projectNum">
								<span><i class="fa fa-window-restore" aria-hidden="true"></i>{{:projectCntF}}</span>
							</div>
						</div>
					</a></li>
</script>


</head>
<body>
<div class="wrap">
	<!-- header -->
	<%@include file="/WEB-INF/views/common/header.jsp" %>
	<!-- //header -->

	<!-- content -->
	<div class="search-content">
		<div class="inner">
			<h2 class="title">'<%=searchWord %>' 검색결과 &gt; 전체 <span id="all_count">0</span></h2>

			<div class="result-wrap">
				<h3>디자인 검색 결과 <span id="all_count_0">(0)</span></h3>
				<ul class="list-type1" id="ul_list_0">
				</ul>
				<button onclick="loadSearchResult(0);" type="button" class="btn-more2" >결과 더 보기</button></br>	
			</div>
			<div class="result-wrap">
				<h3>프로젝트 검색 결과 <span id="all_count_1">(0)</span></h3>
				<ul class="list-type1" id="ul_list_1">
				</ul>
				<button onclick="loadSearchResult(1);" type="button" class="btn-more2" >결과 더 보기</button></br>
			</div>
			<div class="result-wrap">
				<h3>디자이너 검색 결과 <span id="all_count_2">(0)</span></h3>
				<ul class="list-type2" id="ul_list_2">
				</ul>
				<button onclick="loadSearchResult(2);" type="button" class="btn-more2" >결과 더 보기</button></br>
			</div>
			<div class="result-wrap" style="display: none">
				<h3>제작자 검색 결과 <span id="all_count_3">(0)</span></h3>
				<ul class="list-type2" id="ul_list_3">
				</ul>
				<button onclick="loadSearchResult(3);" type="button" class="btn-more2" >결과 더 보기</button>
			</div> 
			<div class="result-wrap">
				<h3>그룹 검색 결과 <span id="all_count_4">(0)</span></h3>
				<ul class="list-group" id="ul_list_4">
				</ul>
				<button onclick="loadSearchResult(4);" type="button" class="btn-more2" >결과 더 보기</button></br>
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

<script>
	
	/* 각 메뉴별 템플릿 */
	var listViews = [];
	
	/* 각 메뉴별 요청 uri */
	var actionUris = ['productList.ajax', 'projectList.ajax', 'designerList.ajax', 'producerList.ajax', 'pGroupList.ajax'];
	
	/* 각 메뉴별 현재 인덱스 */
	var indices = [0, 0, 0, 0, 0];

	$(function(){
		/*
		* 리스뷰 뷰 초기화
		*/
		for( var i =0; i < actionUris.length; i++) {
			listViews[i] = new ListView({
					id : 'ul_template_' + i, 
					htmlElement : $('#ul_list_' + i),
					htmlElementNoData: $("<p class='none'>'<%=searchWord%>'에 대한 검색 결과가 없습니다.</p>")
			});
		}
		
		loadSearchResult(0); //디자인 검색
		loadSearchResult(1); //디자인 프로젝트 검색
		loadSearchResult(2); //디자이너 검색
		/*2017.07.08 loadSearchResult(3); */ //제작자 검색
		loadSearchResult(4); //그룹 검색
		
	});
	
	
	/**
	* 각 파트별 검색 'targetIndex' = index of actionUris[]
	*/
	function loadSearchResult(targetIndex) {
		
		var action = actionUris[targetIndex];
		var pageIndex = indices[targetIndex] + 1;
		
		$.ajax({
	        url: '/search/' + action,
	        type: 'post',
	        data: {searchWord:"<%=searchWord%>", pageIndex:pageIndex},
	        complete : function(_data){},
	        error : function(_data) {
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	
	        	var listData = _data.list;
	        	var allCount = _data.all_count;
	        	var tamplateView = $('#tmpl-' + targetIndex).html();

	        	<%--
	        	if( listData.length > 0 ) {
	        		listViews[targetIndex].addAll({keyName:'seq', data:listData, htmlTemplate:tamplateView });
	        		indices[targetIndex] = pageIndex;
	        	} else {
	        		if( listViews[targetIndex].items.length == 0 ) { 
		        		$('.result-wrap').eq(targetIndex).find('ul').remove();
		        		$('.result-wrap').eq(targetIndex).append("<p class='none'>'<%=searchWord%>'에 대한 검색 결과가 없습니다.</p>");
	        		}
	        	}
	        	--%>
	        	// 결과가 없습니다를 wd-components.js에서 공통처리 
	        	listViews[targetIndex].addAll({keyName:'seq', data:listData, htmlTemplate:tamplateView });
        		indices[targetIndex] = pageIndex;
	        	
	        	if( listViews[targetIndex].items.length == allCount ) {
	        		$('.result-wrap').eq(targetIndex).find('button').remove();
	        	}
	        	
	        	updateTotalCount(targetIndex, allCount);
	        	
	        }
	    });
	}
	
	/**
	* 검색 결과 개수 텍스트 갱신
	*/
	function updateTotalCount(targetIndex, count) {
		
		$('#all_count_' + targetIndex).text( '(' + formatNumberCommaSeparate(count) + ')' );
		
		var totalCount = 0;
		for( var i =0; i < actionUris.length; i++) {
			var eachCount = replaceAll( $('#all_count_' + i).text(), "(", "" );
			eachCount = replaceAll( eachCount, ")", "" );
			eachCount = replaceAll( eachCount, ",", "" );
			
			totalCount += parseInt(eachCount);
		}
		
		$('#all_count').text( formatNumberCommaSeparate(totalCount) );
	}

</script>

<script>
/**
 * group 상세 페이지로 이동
 */
function goGroupDetailView(_this, seq) {
	var name = $(_this).attr("data-nm");
	window.location.href='/project/project.do?schMyGroup=' + seq + '&schMyGroupName=' + name;
}
</script>


</body>
</html>