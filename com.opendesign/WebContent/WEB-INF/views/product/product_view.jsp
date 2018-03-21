<%@page import="com.opendesign.utils.CmnConst.FileUploadDomain"%>
<%@page import="com.opendesign.utils.ControllerUtil"%>
<%@page import="com.opendesign.utils.ThumbnailManager"%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.opendesign.vo.UserVO"%>
<%@page import="com.opendesign.vo.ItemCmmtVO.ItemCmmtType"%>
<%@page import="com.opendesign.vo.ItemLikeVO.ItemType"%>
<%@page import="com.opendesign.vo.DesignWorkFileVO"%>
<%@page import="com.opendesign.vo.DesignPreviewImageVO"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.DesignerVO"%>
<%@page import="com.opendesign.vo.DesignWorkVO"%>
<%@page import="com.opendesign.utils.CmnConst.RstConst"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%
	//
	String schMemberDiv = (String)request.getAttribute("schMemberDiv");  //회원구분
	String loginSeq = null;
	UserVO loginUser = CmnUtil.getLoginUser(request);
	if (loginUser != null && StringUtil.isNotEmpty(loginUser.getSeq())) {
		loginSeq = loginUser.getSeq();
	}
	//디자인
	DesignWorkVO itemVO = (DesignWorkVO)request.getAttribute(RstConst.P_NAME);
	//디자이너 
	DesignerVO designerVO = (DesignerVO)request.getAttribute("designerVO");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<style>

.design_contents {
	overflow: hidden;
	text-overflow: ellipsis; 
	display: -webkit-box;
	-webkit-line-clamp: 4; /* 라인수 */	
	-webkit-box-orient: vertical; 
	word-wrap:break-word;
}

</style>
<script>
/**
 * 구매하기 view
 */
function goProductPurchaseView(seq) {
	checkedLogin(function(){
		<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
		window.location.href = '/designer/productPurchase.do?seq=' + seq;
		<% } else if(MemberDiv.PRODUCER.equals(schMemberDiv))  { %>
		window.location.href = '/producer/productPurchase.do?seq=' + seq;
		<% } else if(MemberDiv.PRODUCT.equals(schMemberDiv))  { %>
		window.location.href = '/product/productPurchase.do?seq=' + seq;
		<% } %>
	}); //end of checkedLogin
}
/**
 * 포트폴리오  view
 */
function goPortfolioView(seq) {
	<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
	window.location.href = '/designer/portfolio.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCER.equals(schMemberDiv))  { %>
	window.location.href = '/producer/portfolio.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCT.equals(schMemberDiv))  { %>
	window.location.href = '/product/portfolio.do?seq=' + seq;
	<% } %>
}

/**
 * 다운로드
 */
function fileDownload(fileUrl, dispName) {
	common.fileDownload(fileUrl, dispName);
}

/**
 * 좋아요 
 */
 var flag_prodViewWorkLike = false; //flag
function prodViewWorkLike(thisObj) {
	checkedLogin(function(invokeAfterLogin){
		
		if(flag_prodViewWorkLike) {
			return;
		}
		flag_prodViewWorkLike = true;
		
		$.ajax({
	        url: '/common/likeItemWork.ajax',
	        type: 'post',
	        data: {"itemSeq":"<%=itemVO.getSeq()%>", "itemType":"<%=ItemType.DESIGN_WORK%>"},
	        complete : function(_data){
	        	flag_prodViewWorkLike = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	if(_data.result == '1') {
		    		
		    		// 좋아요 class 변경 @todo 디자인 요청
		    		window.location.reload();
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
		
	}); //end of checkedLogin
}
</script>

<%

String actionURL = ControllerUtil.getHostName(request) + "/product/productView.do?seq=" + itemVO.getSeq();
String ogTitle = "openDesign " + itemVO.getTitle();
String ogDesc = itemVO.getTitle();
String ogImage = ControllerUtil.getHostName(request) + itemVO.getThumbUriL();
int ogImageW = 600;
int ogImageH = 278;

%>
<meta property="og:title" content="<%=ogTitle%>"/>
<meta property="og:type" content="article"/>
<meta property="og:url" content="<%=actionURL%>"/>
<meta property="og:description" content="<%=ogDesc%>"/>
<meta property="og:image" content="<%=ogImage%>"/>
<meta property="og:image:width" content="<%=ogImageW %>" />
<meta property="og:image:height" content="<%=ogImageH %>" />

<script>

function is_ie() {
	  if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1) return false;
	  if(navigator.userAgent.toLowerCase().indexOf("msie") != -1) return true;
	  if(navigator.userAgent.toLowerCase().indexOf("windows nt") != -1) return true;
	  return false;
}

function browserCheck() {
	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
    var is_explorer = navigator.userAgent.toLowerCase().indexOf('msie') > -1;
    var is_firefox = navigator.userAgent.toLowerCase().indexOf('firefox') > -1;
    var is_safari = navigator.userAgent.toLowerCase().indexOf("safari") > -1;
    var is_opera = navigator.userAgent.toLowerCase().indexOf("op") > -1;
    if ((is_chrome)&&(is_safari)) {is_safari=false;}
    if ((is_chrome)&&(is_opera)) {is_chrome=false;}
    return {
    	is_ie :is_explorer
    	,is_safari: is_safari
    	,is_chromeLike: (is_chrome || is_firefox || is_opera)
    };
}
	
function facebookLink(){
	
	var linkURL = 'https://www.facebook.com/sharer/sharer.php?u=<%=actionURL%>';
	
	var opener=window.open('','_blank');
	opener.location=linkURL;
}

function copy2Clipboard() {
	var bc = browserCheck();
	console.log(bc);
	if( bc.is_ie ) {
		window.clipboardData.setData("Text", '<%=actionURL%>');
		alert("복사되었습니다.");
		return;
	} 
	prompt("Ctrl+C를 눌러 복사하세요.", '<%=actionURL%>');
	  
}

/**
 * 상세 내용 더보기
 */
function showContents(ele) {
	
	$('#contents_more_btn').hide();
	$('.design_contents').removeClass('design_contents');
}


$(function() {
	/*
	* 상세 내용이 4줄 이상 있을 경우 '더보기' 버튼 보여줄지 말지 여부
	*/
	if ($('.design_contents')[0].scrollHeight >  $('.design_contents').innerHeight()) {
		$('#contents_more_btn').show();
	} else {
		$('#contents_more_btn').hide();
	}
	
});

</script>

<style>
 #contents_more_btn {
	 text-align:center;
	 padding-top:5px;
	 border:1px solid;
	 border-radius: 3px;
 }
</style>

</head>
<body>
<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->

	<!-- content -->
	<div class="detail-content">
		<div class="inner">
			<h2 class="title"><%=itemVO.getTitle()%></h2>
			<div class="product-section">
				<div class="product-wrap">
					<div class="img-area" >
						<p class="cate" ><%=itemVO.getCateNames()%></p>
						<div class="btn-set" style="margin-bottom:20px;">
						<%if( itemVO.getMemberSeq().equals(loginSeq) ){ %>
							<a href="/product/productModify.do?seq=<%=itemVO.getSeq() %>" class="btn-edit">수정</a>
							<a href="javascript:deleteProduct('<%=itemVO.getSeq() %>');" class="btn-del">삭제</a>
						<%} %>
						</div>
					</div>			
	
					<div class="img-detail">
						<!-- <img src="<%=itemVO.getThumbUriL()%>"  alt="대표 이미지">  -->
						<!-- <img src="/resources/image/sub/img_detail1.jpg"> -->
						<%
							if(!CmnUtil.isEmpty(itemVO.getImageList())) {
								for(DesignPreviewImageVO image : itemVO.getImageList()) {
									
									if( ControllerUtil.isImageFile(request, image.getFileUriL(), 
											FileUploadDomain.PRODUCT)) {
						%>
							<img src="<%=image.getFileUriL()%>" alt="<%=image.getFilename()%>"> 
						<%
									} else {
						%>
							<div style="padding-top:20px;padding-bottom:20px;width:85%;">
							<img src="/resources/image/common/ico_attachment.png" alt="파일첨부" style="width:20px;height:22px;margin:auto;">
							&nbsp;<a href=<%=image.getFileUri() %> target="blank"> <%=image.getFilename() %> </a>
							</div>
						<%
									}
									
								}
							}
						%>
					</div>
				
					<!-- 디자인설명 -->
					<div class="opensource">
						<h3>디자인 설명</h3>
						<p class="design_contents">
						<%=itemVO.getContents().replaceAll("\n", "<br/>")%>
						</p>
						
						<p id="contents_more_btn">
							<a href="javascript:showContents();"> 더보기 
							<img src="/resources//image/sub/bg_arrowDown.png"  />
							</a>
						</p>
					</div>
	
					<div class="opensource">
						<h3>오픈소스<span>(작업에 사용된 오픈소스는 자유롭게 변경 및 공유 가능)</span></h3>
						<ul>
							<!-- <li>Font_style.jpg <span>(580KB)</span></li> -->
							<%
								if(!CmnUtil.isEmpty(itemVO.getFileList())) {
									for(DesignWorkFileVO fItem : itemVO.getFileList()) {
							%>
							<li style="cursor:pointer; display: inline-block; margin-right: 10px;" onclick="fileDownload('<%=fItem.getFileUri()%>', '<%=fItem.getFilename()%>');"><%=fItem.getFilename()%> <span>(<%=fItem.getFileSize()%>KB)</span></li>
							<%
									}
								}
							%>
						</ul>
					</div>
				</div>
				
				<div class="product-reply">
					<h3>댓글쓰기</h3>
					<%if(loginUser == null){ %>
					<p>댓글은 로그인 후에 작성 가능합니다.</p>
					<%} %>
					<form name="projDetailAddCmmtForm">
						<input type="hidden" name="itemSeq" value="<%=itemVO.getSeq()%>" /> <!-- itemSeq -->
						<input type="hidden" name="itemCmmtType" value="<%=ItemCmmtType.DESIGN_CMMT%>" /> <!-- 댓글 구분 -->
						<fieldset>
							<legend>댓글 쓰기</legend>
							<p id="allCnt" class="number">전체 댓글(0)</p>
							<textarea  name="contents" maxlength="500" placeholder="댓글 입력(최대 500자)"></textarea>
							<button type="button" class="btn-cmmt btn-red" >등록</button>
						</fieldset>
					</form>
					<ul class="reply-list" id="pdrListView" data-seq="<%=itemVO.getSeq()%>">
					
						<%-- template
						<li>
							<div>
								<div class="pic"><img src="/resources/image/common/pic_profile.jpg" alt="송준기"></div>
								<dl>
									<dt>송준기 <span class="date">오후 11:34분</span></dt>
									<dd><a href="#">[Elenfhant logo A #1]</a> 이 로고 붉은 컬러로 변경해서 올려도 좋을 것 같아요</dd>
								</dl>
							</div>
						</li>
						--%>
						
					</ul>
					<button onclick="pdrLoadMore();" id="pdrLoadMore" type="button" class="btn-more">댓글 더 보기</button>
				</div>
			</div>
			
			<div class="info-section">
					<div class="designer-area">
							<div class="profile-pic"><img src="<%=designerVO.getImageUrl()%>" onerror="setDefaultImg(this, 1);" alt=""></div>
							<div class="profile-name">
								<a href="javascript:goPortfolioView('<%=designerVO.getSeq()%>');" class="name"><%=designerVO.getUname()%></a>
								<a href="javascript:goShowMsgView('<%=designerVO.getSeq()%>');" class="btn-msg"><i class="fa fa-commenting" aria-hidden="true"></i></a>
							</div>
					</div>
					<div class="info"> 
						<%-- <dl class="summary" >
							<dt>디자인 설명</dt> 
							<dd ><%=itemVO.getContents()%></dd>
						</dl> --%>
						<%-- 2017.07.28
						<dl class="point">
							<dt>포인트</dt>
							<dd><%=itemVO.getDisplayPoint()%>pt</dd>
						</dl> --%>
						<dl class="like">
							<dt>좋아요 : <%=itemVO.getLikeCntF()%></dt>
							<div class="btn-set">
								<% if( !itemVO.isCurUserLikedYN() ) { %>
								<button type="button" class="btn-like" onclick="prodViewWorkLike(this);" >좋아요</button>
								<% } else { %>
								<button type="button" class="btn-like active" onclick="prodViewWorkLike(this);" >좋아요</button>
								<% } %>
								<%-- 2017.07.28
								<%if( itemVO.isLogonUserPurchased() ) { %>
								<a style="background-color:black;" class="btn-purchase">구매완료</a>
								<%} else { %>
									<% if(itemVO.isUserProduct()  && !"0".equals(itemVO.getDisplayPoint())) { %>
									<a href="javascript:goProductPurchaseView('<%=itemVO.getSeq()%>');" class="btn-purchase">구매하기</a>								
									<%} else {%>
									<a class="btn-purchase" style="background: #cecbcb;">구매하기</a>
									<%} %>
								<%} %> --%>
							</div>
						</dl>
						<dl class="hit">
							<dt>조회수 : <%=itemVO.getViewCntF()%> </dt>
						</dl>
						<dl class="date">
							<dt>게시일 : <%=itemVO.getDisplayRegTime()%></dt>
						</dl>
						<dl class="child">
							<dt>파생된 디자인 : </dt>
							<dd></dd>
						</dl>
						<dl class="licenses">
							<dt class="ccl-span">CCL : </dt>
							<dd class="ccl-span">
								<% if("1".equals(itemVO.getLicenseBY())) { %><span><img src="/resources/image/sub/licenses_by.png" alt="BY"></span><% } %>
								<% if("1".equals(itemVO.getLicenseNC())) { %><span><img src="/resources/image/sub/licenses_nc.png" alt="NC"></span><% } %>
								<% if("1".equals(itemVO.getLicenseND())) { %><span><img src="/resources/image/sub/licenses_nd.png" alt="ND"></span><% } %>
							</dd>
						</dl>
					</div>
					<div class="original-area">
						<button class="original-btn">원본디자인보기</button>
						<button class="child-btn">파생디자인등록</button>
					</div>
					<div class="tag-area">
					<%
						if(!CmnUtil.isEmpty(itemVO.getTagsArray())) {
					%>	
					<span><img src="/resources/image/sub/ico_tag.png" alt="태그"></span>
					<%
						}
					%>
						<ul>
							<!-- <li>의상디자인</li> -->
							<%
								if(!CmnUtil.isEmpty(itemVO.getTagsArray())) {
									for(String tagItem : itemVO.getTagsArray()) {
							%>
							<li><%=tagItem%></li>
							<%
									}
								}
							%>
						</ul>
					</div>
					<div class="producer-info">
						<div class="share">
							<button type="button" onclick="facebookLink();">
								<i class="fa fa-facebook" aria-hidden="true"></i>
								페이스북으로 공유하기
							</button>
						</div>
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

<!-- ******************** 댓글 ******************** -->
<script id="tmpl-project-detail-reply" type="text/x-jsrender">
					<li>
						<div>
							<div class="pic"><img src="{{:memberImageUrl}}" onerror="setDefaultImg(this, 1);" alt="{{:memberName}}"></div> 
							<dl style="width:90%; position: relative">
								{{if curUserAuthYN}}
									<div class="btn-set">
										<button class="btn-cmmt-modify" style="display: none">수정</button>
										<button class="btn-cmmt-del" onclick="projDetailDelCmmt('<%=ItemCmmtType.DESIGN_CMMT%>','{{:seq}}');" >삭제</button>
									</div>
								{{/if}}
								{{if memberType == "00" }}
								<dt>{{:memberName}} <span class="date">{{:displayTime}}</span></dt>
								{{else memberType == "01" }}
								<dt><a onclick="javascript:producerPortfolio({{:memberSeq}})" style=" color: #666; font-size: 17px; cursor: pointer;">{{:memberName}}</a> <span class="date">{{:displayTime}}</span></dt>
								{{else}}
								<dt><a onclick="javascript:designerPortfolio({{:memberSeq}})" style="color: #666; font-size: 17px; cursor: pointer;">{{:memberName}}</a> <span class="date">{{:displayTime}}</span></dt>
								{{/if}}
								<dd style="word-wrap:break-word;">
									{{:contents}}
								</dd>
							</dl>
							<div class="clear"></div>
						</div>
					</li>
</script>
<form id="pdrListParamForm" name="pdrListParamForm" method="GET" action="" >
	<input type="hidden" name="schSeq" value="" /> 	<!-- itemSeq --> 
	<input type="hidden" name="schPage" value="1" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schLimitCount" value="10" /> <!-- 한 page 개수 -->
	<input type="hidden" name="schItemCmmtType" value="<%=ItemCmmtType.DESIGN_CMMT%>" /> <!-- 댓글 구분 -->
</form>
<script type="text/javascript">

//start
$(function(){
	initPdrListView();
	pdrLoadPage();
	
	// 등록 event:
	var myForm = $('form[name="projDetailAddCmmtForm"]');
	myForm.find('.btn-cmmt').click(function (event) {
       	projDetailAddCmmt();
       	return;
	});
});


/* list 탬플릿 */
var pdrListTemplete = $("#tmpl-project-detail-reply").html();

/* pdrListView */
var pdrListView = null;

/* 초기화 */
function initPdrListView(){
	// param set:
	var myForm = $('form[name="pdrListParamForm"]');
	var itemSeq = $('#pdrListView').data('seq'); 
	myForm.find('[name="schSeq"]').val(itemSeq);
	myForm.find('[name="schPage"]').val('1');
	
	//
	pdrListView = new ListView({
		htmlElement : $('#pdrListView')
	});
	
	// clear
	pdrListView.clear();
	
}

/**
 * 페이지 load
 */
var flag_pdrLoadPage = false; //flag
function pdrLoadPage() {
	
	if(flag_pdrLoadPage) {
		return;
	}
	flag_pdrLoadPage = true;
	
	var myForm = $('form[name="pdrListParamForm"]');
	// 데이터 조회 및 load
	$.ajax({
        url: '/common/selectItemCmmtPagingList.ajax',
        type: 'get',
        data: myForm.serialize(),
		complete : function(_data){
			flag_pdrLoadPage = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	// load
	    	pdrLoadPageWithData(_data);
		}
    });
}

/**
 *  pdrLoadPageWithData
 */
function pdrLoadPageWithData(_data) {
	// allCount
	var allCount = _data.all_count;
	console.log('>>> allCount=' + allCount);
	$('#allCnt').html('전체 댓글(' + formatNumberCommaSeparate(allCount) + '건)');
	
	// pageNo
	var myForm = $('form[name="pdrListParamForm"]');
	var pageNo = myForm.find('[name="schPage"]').val();
	
	//
	var listData = _data.list;
	var listCount = listData.length;
	var existList = listCount > 0; 
	pdrListView.putData('existList', existList);
	// loadMore button
	if((! existList) || ((pdrListView.items.length + listCount)  == allCount) ) {
		$('#pdrLoadMore').hide();
	} else {
		$('#pdrLoadMore').show();
	}
	if( ! existList ){	
		console.log('>>> loadPageWithData no data.');
		return;
	}
	
	// data
	pdrListView.addAll({
		keyName:'seq',
		data:listData,
		htmlTemplate: pdrListTemplete
	});
}


/**
 * 더보기
 */
function pdrLoadMore() {
	// pageNo + 1
	var myForm = $('form[name="pdrListParamForm"]');
	var pageNo = myForm.find('[name="schPage"]').val();
	if(pageNo == '') {
		pageNo = '1';
	}
	
	var nextPage = parseInt(pageNo, 10);
	nextPage++;
	myForm.find('[name="schPage"]').val(nextPage);
	
	// load 
	pdrLoadPage();
	
}

/**
 * 댓글 등록
 */
var flag_projDetailAddCmmt = false; //flag
function projDetailAddCmmt() {
	checkedLogin(function(){
		var myForm = $('form[name="projDetailAddCmmtForm"]');
		if(!myForm.valid()) {
			return;
		}
		
		if(flag_projDetailAddCmmt) {
			return;
		}
		flag_projDetailAddCmmt = true;
		
		//
		$.ajax({
	        url: '/common/insertItemCmmt.ajax',
	        type: 'post',
	        data: myForm.serialize(),
	        complete : function(_data){
	        	flag_projDetailAddCmmt = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	if(_data.result == '1') {
		    		// reload 댓글
					initPdrListView();
		    		pdrLoadPage();
		    		// clear
		    		formValueClear('form[name="projDetailAddCmmtForm"]');
		    		
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
		
	}); //end of checkedLogin
}


/**
 * 댓글 삭제
 */
var flag_projDetailDelCmmt = false; //flag 
function projDetailDelCmmt(itemCmmtType, seq) {
	checkedLogin(function(invokeAfterLogin){
		
		if(!confirm('댓글 삭제하시겠습니까?')) { 
			return;
		}
		
		if(flag_projDetailDelCmmt) {
			return;
		}
		flag_projDetailDelCmmt = true;
		
		//
		$.ajax({
	        url: '/common/deleteItemCmmt.ajax',
	        type: 'post',
	        data: {itemCmmtType: itemCmmtType,seq: seq},
	        complete : function(_data){
	        	flag_projDetailDelCmmt = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	if(_data.result == '1') {
		    		if( invokeAfterLogin ) { //로그인 후 처리되는 프로세스 라면
		    			window.location.reload();
		    			return;
		    		}
		    		// reload 댓글
					initPdrListView();
		    		pdrLoadPage();
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
		
	}); //end of checkedLogin
} 


function deleteProduct(seq){
	
	if(flag_projDetailAddCmmt) {
		return;
	}
	flag_projDetailAddCmmt = true;
	
	if( ! confirm('디자인을 삭제 하시겠습니까?') ){
		flag_projDetailAddCmmt = false;
		return;
	}
	
	$.ajax({
        url: '/product/deleteProduct.ajax', 
        cache: false,
        type: 'post',
        data: {'seq': seq},
        complete : function(_data){
        	flag_projDetailAddCmmt = false;
		},
        error : function(_data){
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			if(_data.result == '1') {
				window.location.href = '/product/product.do';
	    	} else if(_data.result == '100') { //이메일 중복
	    		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
	    			modalShow('#login-modal');
	    		}
	    	} else if(_data.result == '101') { //이메일 중복
	    		alert('수정 권한이 없습니다.');
	    	} else {
	    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	    		
	    	}
		}
    });
}

//=== 검증 ==========
$(function(){
	projDetailAddCmmtFormValidRuleInit();
});

function projDetailAddCmmtFormValidRuleInit(){
	var myForm = $('form[name="projDetailAddCmmtForm"]');
	myForm.validate({
		rules:{
			contents : { required: true }
		},
		messages: {
			contents : { required: "필수로 입력해야 합니다." } 
		},
		showErrors: function(errorMap, errorList) {
	    	if( errorList && errorList.length > 0 ){
	    		for( var i = 0; i < errorList.length; i++ ){                			
					alert(errorList[i].message);
	               	errorList[i].element.focus();
	    			break;
	    		}
	    	}
	    },
	    ignore: [],
	    focusInvalid:false,
	    onfocusout: false,
	    onkeyup: false,
	    onclick: false
	});
}

//=== ]]검증 ==========

//디자이너 포트폴리오
function designerPortfolio(seq) {
	window.location.href = '/designer/portfolio.do?seq=' + seq;
}

//제작자 포트폴리오
function producerPortfolio(seq) {
	window.location.href = '/producer/portfolio.do?seq=' + seq;	
}

</script>
<!-- ******************** ]]댓글 ******************** -->



</body>
</html>