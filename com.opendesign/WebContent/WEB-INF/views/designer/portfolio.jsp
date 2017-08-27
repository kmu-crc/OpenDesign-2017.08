<%-- 화면ID : OD04-01-02 --%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@page import="com.opendesign.vo.DesignerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String schMemberDiv = (String)request.getAttribute("schMemberDiv");  //회원구분
	DesignerVO item = (DesignerVO)request.getAttribute("desingerVO");
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
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->

	<!-- content -->
	<div class="portfolio-content">
		<div class="inner" style="min-height: 500px;">
			<div class="title-area">
				<div class="profile-pic">
					<img src="<%=item.getImageUrl()%>" onerror="setDefaultImg(this, 1);">
				</div>
				<%
					String portfolioName = "포트폴리오";
					if(MemberDiv.PRODUCER.equals(schMemberDiv)) {
						portfolioName = "공방";
					}
				%>
				<h2><%=item.getUname()%>님의 <%=portfolioName%> 
					<button type="button" class="btn-red" onclick="goShowMsgView('<%=item.getSeq()%>');">메시지보내기</button>
				</h2>
				<p style="color: #113b88;"><%=item.getCateNames()%></p> 
			</div>
			<p class="txt"><%=item.getComments()%></p>
			<ul class="designer-info">
				<li class="item">
					<span class="icon"><i class="fa fa-list" aria-hidden="true"></i></span>
					<span>총 디자인 수</span><%=item.getWorkCntF()%>
				</li>
				
				<li class="like">
					<span class="icon"><i class="fa fa-heart" aria-hidden="true"></i></span>
					<span>총 좋아요 수</span><%=item.getLikeCntF()%>
				</li>
				
				<li class="hit">
					<span class="icon"><i class="fa fa-hand-pointer-o" aria-hidden="true"></i></span>
					<span>총 조회수</span><%=item.getViewCntF()%>
				</li>
				
				<li class="reply">
					<span class="icon"><i class="fa fa-comment" aria-hidden="true"></i></span>
					<span>총 댓글수</span><%=item.getCmmtCntF()%>
				</li>
			</ul>
			<ul id="listViewId" class="list-type1">
				
				<!-- template -->
				<!-- 
				<li><a href="product_view.html">
					<img src="/resources/image/main/pic_sample1.jpg" alt="">
					<div class="product-info">
						<p class="product-title">별빛이 흐르는</p>
						<p class="designer">김민희</p>
					</div>
					<p class="cate">의상디자인, 패션, 3d</p>
					<div class="item-info">
						<span><img src="/resources/image/common/ico_like.png" alt="좋아요"> 380</span>
						<span><img src="/resources/image/common/ico_hit.png" alt="열람"> 181</span>
						<span class="update">30분 전</span>
					</div>
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
<script id="tmpl-listTemplete" type="text/x-jsrender">
				<li><a href="javascript:goProductView('{{:seq}}');"  >
					<img src="{{:thumbUriM}}" onerror="setDefaultImg(this, 2);" alt=""  />
					<div class="product-info">
						<p class="product-title">{{:title}}</p>
						<p class="designer">{{:memberName}}</p>
					</div>
					<p class="cate"  >{{:cateNames}}</p>
					<div class="item-info">

						{{if !curUserLikedYN }}
						<span class="like"><i class="fa fa-heart" aria-hidden="true"></i> {{:likeCntF}}</span>
						{{else}}
						<span class="like"><i class="fa fa-heart-o" aria-hidden="true"></i> {{:likeCntF}}</span>
						{{/if}}

						<span class="hit"><i class="fa fa-hand-pointer-o" aria-hidden="true"></i> {{:viewCntF}}</span>
						<span class="update">{{:displayTime}}</span>
					</div>
				</a></li>
</script>
<script>
/**
 * 디자인 상세
 */
function goProductView(seq) {
	<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
	window.location.href='/designer/productView.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCER.equals(schMemberDiv))  { %>
	window.location.href='/producer/productView.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCT.equals(schMemberDiv))  { %>
	window.location.href='/product/productView.do?seq=' + seq;
	<% } %>
}
</script>
<script>

//뷰 컨트롤러 생성	
var listView = null;
//seq
var seq = '<%=item.getSeq()%>';

/**
 * 초기화
 */
$(function(){
	
	//뷰 컨트롤러 생성	
	listView = new ListView({
		htmlElement : $('#listViewId')
	});
	
	// load
	loadPage();
	
});


/**
 * 페이지 load
 */
var flag_loadPage = false; //flag
function loadPage() {
	
	if(flag_loadPage) {
		return;
	}
	flag_loadPage = true;
	
	// 데이터 조회 및 load
	$.ajax({
        url: '/designer/selectDesignWorkList.ajax',
        type: 'get',
        data: { 'seq' : seq },
		complete : function(_data){
			flag_loadPage = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	var workList = _data.workList;
	    	// load
	    	loadPageWithData(workList);
		}
    });
}

/**
 *  loadPageWithData
 */
function loadPageWithData(itemList) {
	listView.clear();
	if(!itemList || itemList.length == 0) {
		console.log('>>> loadPageWithData no data.');
		return;
	}
	// 
	listView.addAll({
		keyName: 'seq'
		,data: itemList
		,htmlTemplate: $('#tmpl-listTemplete').html()
	});
}


</script>
</body>
</html>