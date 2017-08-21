<%-- 화면ID : OD04-03-01 --%>
<%-- 화면ID : OD04-03-02 --%>
<%@page import="com.opendesign.utils.CmnConst.CateExclude"%>
<%@page import="com.opendesign.vo.ItemCmmtVO.ItemCmmtType"%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String schMemberDiv = (String)request.getAttribute("schMemberDiv");  //회원구분
	String memberDivString = "";
	if(MemberDiv.DESIGNER.equals(schMemberDiv)) {
		memberDivString = "디자인";
	} else {
		memberDivString = "제작";
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- pagingView -->
<%@include file="/WEB-INF/views/common/paging_template.jsp"%>  <!-- paging template -->
<script src="/resources/js/wd-paging-view.js"></script> <!-- paging -->
<!-- //pagingView -->
<script id="tmpl-listTemplete" type="text/x-jsrender">
				<li>
					<dl>
						<dt>
							<div class="txt">{{:title}}</div>
							<div class="btn-set" style="float:right;margin-right:80px;">
								{{if curUserAuthYN}}
								<a href="javascript:void(0);" onclick="goBoardModView('{{:seq}}');return false;" class="btn-modi">수정</a>
								<button onclick="deleteBoard('{{:seq}}');" type="button" class="btn-del" style="margin-left:5px;">삭제</button>
								{{/if }}
							</div>
						</dt>
						<dd>
							<div class="txt"> 
								<div style="margin-bottom:5px;word-wrap:break-word;">{{:contents}}</div>
								{{for fileList}}
								<img style="width:100px; height:100px;" src="{{:fileUrl}}">
								{{/for}}
							</div>
							<div class="reply-area" data-seq="{{:seq}}">
								<%-- <form name="projDetailAddCmmtForm">
									<input type="hidden" name="itemSeq" value="{{:seq}}" /> <!-- itemSeq -->
									<input type="hidden" name="itemCmmtType" value="<%=ItemCmmtType.BOARD_CMMT%>" /> <!-- 댓글 구분 --> --%>

									<textarea name="contents" maxlength="30" placeholder="댓글 입력(최대 30자)"></textarea>
									<button type="button" class="btn-cmmt btn-red">등록</button>
									
								<%--</form> --%>
								<ul data-name="pdrListView" data-seq="{{:seq}}">
								
								</ul>
								<a href="javascript:void(0);" onclick="pdrLoadMore();" data-name="pdrLoadMore" class="btn-more">댓글 더 보기</a>
							</div>
						</dd>
					</dl>
					<div class="info">
						<span class="replay" style="width:80px;margin-right:50px;"><img src="/resources/image/sub/ico_reply.png" alt="댓글"> {{:cmmtCnt}}</span>
						<span style="width:80px;margin-right:20px; display: inline-block;">{{:displayTime}}</span>
						<span style="width:100px; display: inline-block;">{{:memberName}}</span>
					</div>
				</li>
</script>
<script>
/**
 * 추가 페이지
 */
function goBoardAddView() {
	checkedLogin(function(){
		<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
		window.location.href = '/designer/openRequestBoardInsUpd.do?pageMode=I&schMemberDiv=<%=schMemberDiv%>';
		<% } else { %>
		window.location.href = '/producer/openRequestBoardInsUpd.do?pageMode=I&schMemberDiv=<%=schMemberDiv%>';
		<% } %>
	}); //end of checkedLogin
}

/**
 * 수정 페이지
 */
function goBoardModView(seq) {
	checkedLogin(function(){
		<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
		window.location.href = '/designer/openRequestBoardInsUpd.do?pageMode=U&schMemberDiv=<%=schMemberDiv%>&seq=' + seq;
		<% } else { %>
		window.location.href = '/producer/openRequestBoardInsUpd.do?pageMode=U&schMemberDiv=<%=schMemberDiv%>&seq=' + seq;
		<% } %>
	}); //end of checkedLogin
}

/**
 * 삭제
 */
var flag_deleteBoard = false; //flag 
function deleteBoard(seq) {
	checkedLogin(function(){
		if(!confirm('정말 삭제하시겠습니까?')) {
			return;
		}
		
		if(flag_deleteBoard) {
			return;
		}
		flag_deleteBoard = true;
		
		// 
		$.ajax({
	        url: '/designer/deleteRequestBoard.ajax',
	        type: 'post',
	        data: {'seq' : seq },
			complete : function(_data){
				flag_deleteBoard = false;
			},
			error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data){
				console.log(_data);
		    	// load
		    	loadPage();
			}
	    });
	}); //end of checkedLogin
}

</script>
<script type="text/javascript">
	
/* list 탬플릿 */
var listTemplete = $("#tmpl-listTemplete").html();

/* listView */
var listView = null;

/* pagingView */
var pagingView = null;

$(function(){
   	
	listView = new ListView({
		htmlElement : $('#listViewId')
		,htmlElementNoData: $('<p class="none" style="line-height: 300px;">결과가 없습니다.</p>')
	});
	
	pagingView = new PagingView({
		targetFormId: '#searchForm',
		htmlContainer: 'div.paging-wrap',
		goPageCallback: function() {
			loadPage();
		}
	});
	
	
	// default 조회:
	searchFormSubmit();
	
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
	
	var myForm = $('form[name="searchForm"]');
	// 데이터 조회 및 load
	$.ajax({
        url: '/designer/selectDesignRequestBoardList.ajax',
        type: 'post',
        data: myForm.serialize(),
		complete : function(_data){
			flag_loadPage = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	// load
	    	loadPageWithData(_data);
		}
    });
}

/**
 *  loadPageWithData
 */
function loadPageWithData(_data) {
	// clear
	listView.clear();
	
	// allCount
	var allCount = _data.all_count;
	console.log('>>> allCount=' + allCount);
	// pageNo
	var myForm = $('form[name="searchForm"]');
	var pageNo = myForm.find('[name="schPage"]').val();
	
	// paging:
	pagingView.refresh({
		allCount : allCount,
		pageNo: pageNo
	});
	
	//
	var listData = _data.list;
	var listCount = listData.length;
	var existList = listCount > 0; 
	listView.putData('existList', existList);				
	if( ! existList ){	
		console.log('>>> loadPageWithData no data.');
		$("#pagingDiv").css("display", "none");
		return;
	}
	
	// data
	listView.addAll({
		keyName:'seq',
		data:listData,
		htmlTemplate:listTemplete 
	});
}
		
</script>

<!-- ################ 카테고리 ################### -->
<script >
	$(function() {
		new CategoryView({
			htmlContainer: $('form[name="searchForm"]')
			,hiddenFieldName: 'memberCateCode'
			,excludeData: { <%=CateExclude.P_NAME%>: '<%=CateExclude.V_DESI_PROD%>'  }
		}).render();
	});
	
	/**
	 * 카테고리 선택 조회
	 */
	function searchFormSubmit() {
		var myForm = $('form[name="searchForm"]');
		// 2. calc category
		var cateCode = "";
		var cateName = "";
		var tmpCateCode3 =  myForm.find('[name="cateDepth3"]');
		var tmpCateCode2 =  myForm.find('[name="cateDepth2"]');
		var tmpCateCode1 =  myForm.find('[name="cateDepth1"]');
		if( tmpCateCode3.val() != '') {
			cateCode = tmpCateCode3.val();
			cateName = tmpCateCode3.find('option:selected').text();
		} else if( tmpCateCode2.val() != '') {
			cateCode = tmpCateCode2.val();
			cateName = tmpCateCode2.find('option:selected').text();
		} else if( tmpCateCode1.val() != '') {
			cateCode = tmpCateCode1.val();
			cateName = tmpCateCode1.find('option:selected').text();
		}
		console.log('>>> cateCode=' + cateCode + ', cateName=' + cateName);
		myForm.find('[name="schCate"]').val(cateCode);
		
		// 조회
		loadPage();
		
	}
</script>
<!-- ################ ]]카테고리 ################### -->
</head>
<body>
<div class="wrap">
	<!-- header -->
	<%@include file="/WEB-INF/views/common/header.jsp" %>
	<!-- //header -->

	<!-- content -->
	<div class="request-content">
		
		<div class="inner" style="min-height: 500px;">
			<div id="searchCategory" class="select-wrap" >
				<!-- 조회 form -->
				<form id="searchForm" name="searchForm" method="post" >
					<input type="hidden" name="schPage" value="1" /> <!-- page번호 -->
					<input type="hidden" name="schLimitCount" value="10" /> <!-- 한 page 개수 --> 
					<input type="hidden" name="schCate" value="" /> <!-- 카테고리 -->
					<input type="hidden" name="schMemberDiv" value="<%=schMemberDiv%>" /> <!-- 회원구분 --> 
					
				<div class="custom-select">
					<input type="text">
					<select name="cateDepth1" >
						<option value="">전체</option>
					</select>
				</div>
				<div class="custom-select">
					<input type="text">
					<select name="cateDepth2" >
						<option value="">전체</option>
					</select>
				</div>
				<div class="input-search">
					<input type="text" name="schContent" >
					<button type="button" onclick="searchFormSubmit();"><img src="/resources/image/sub/btn_search.png" alt="검색"></button>
				</div>
				
				</form>
			</div>

			<a href="javascript:goBoardAddView();" class="btn-write btn-red">새 글 쓰기</a>
			<div style="clear: both;">
				<ul id="listViewId" class="request-list" >
					
					<%-- template
					<li>
						<dl>
							<dt>
								<div class="txt">이런 느낌의 캐리커쳐 디자인을 사고 싶습니다.</div>
								<div class="btn-set">
									<a href="request_modi.html" class="btn-modi">수정</a>
									<button type="button" class="btn-del">삭제</button>
								</div>
							</dt>
							<dd>
								<div class="txt">이런 느낌의 캐리커쳐 디자인을 사고 싶습니다. 가능하신 디자이너분이 계신다면 메시지 보내주세요.
									<img src="/resources/image/sub/img_sample.jpg">
								</div>
								<div class="reply-area">
									<form name="">
										<textarea placeholder="댓글을 남기고 Enter를 치시면 자동으로 입력됩니다 (최대 30자)"></textarea>
									</form>
									<ul>
										<li>
											<span class="name">최나리</span>
											저 캐리커쳐 디자인 엄청 잘 하는데요!! 제 포트폴리오 보시고 연락주세요 ^^
											<span class="date">1일전</span>
										</li>
										<li>
											<span class="name">최나리</span>
											저 캐리커쳐 디자인 엄청 잘 하는데요!! 제 포트폴리오 보시고 연락주세요 ^^
											<span class="date">3일전</span>
										</li>
									</ul>
									<a href="#" class="btn-more">댓글 더 보기</a>
								</div>
							</dd>
						</dl>
						<div class="info">
							<span class="replay"><img src="/resources/image/sub/ico_reply.png" alt="댓글"> 25</span>
							<span>김기진</span>
						</div>
					</li>
					--%>
					
				</ul>
		</div>
			
		</div>
		<!-- 페이징 -->
			<div id="pagingDiv" class="paging-wrap" style="margin-bottom: 26px;">
			</div>
			<!-- //페이징 -->
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
									<li style="width:90%;">
										{{if memberType == "00" }}
										<span class="name">{{:memberName}}</span>
										{{else memberType == "01" }}
										<a onclick="javascript:producerPortfolio({{:memberSeq}})" style="cursor: pointer;"><span class="name">{{:memberName}}</span></a>
										{{else}}
										<a onclick="javascript:designerPortfolio({{:memberSeq}})" style="cursor: pointer;"><span class="name">{{:memberName}}</span></a>			
										{{/if}}
										<span style="display:inline-block;">
											{{:contents}}
										</span>
										{{if curUserAuthYN}}
											<button class="btn-cmmt-del" style="position:absolute;float:none;top:0;right:0;" onclick="projDetailDelCmmt('<%=ItemCmmtType.BOARD_CMMT%>','{{:seq}}');" ></button>
										{{else}}
											<div class="btn-cmmt-del" style="position:absolute;float:none;top:0;right:0;background:none;cursor:default;"></div>
										{{/if}}
										<span class="date" style="right:35px;" >{{:displayTime}}</span>
									</li>
</script>
<form name="projDetailAddCmmtForm">
	<input type="hidden" name="itemSeq" value="" /> <!-- itemSeq -->
	<input type="hidden" name="contents" value="" /> <!-- 댓글내용 -->
	<input type="hidden" name="itemCmmtType" value="<%=ItemCmmtType.BOARD_CMMT%>" /> <!-- 댓글 구분 -->
</form>
<form id="pdrListParamForm" name="pdrListParamForm" method="GET" action="" >
	<input type="hidden" name="schSeq" value="" /> 	<!-- itemSeq --> 
	<input type="hidden" name="schPage" value="1" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schLimitCount" value="5" /> <!-- 한 page 개수 -->
	<input type="hidden" name="schItemCmmtType" value="<%=ItemCmmtType.BOARD_CMMT%>" /> <!-- 댓글 구분 -->
</form>
<script type="text/javascript">
/* 게시글 click 했을때 댓글 load */
var curReplyArea = null; //현재 댓글 dom
$('body').on('click', '.request-list dt', function(e){
	var parent = $(this).parents('li')
	if(parent.hasClass('active')){
		// 
		curReplyArea = null;
	} else{
		//=== 상세내용 펼쳐졌을경우:
		curReplyArea = $(this).closest('li').find('div.reply-area');
		console.log('>>> curReplyArea:');
		console.log(curReplyArea);
		
		//
		initPdrListView();
		pdrLoadPage();
		
	}
});

/* 댓글 등록 이벤트 */
$('body').on('click','.reply-area .btn-cmmt', function(e){
//$('body').on('keydown', '.reply-area textarea[name="contents"]', function(e){
//	if(e.keyCode == 13){//키가 13이면 실행 (엔터는 13)
    	e.preventDefault(); 
		// 1. set param
		var myAddForm = $('form[name="projDetailAddCmmtForm"]');
		var contents = $(this).closest('.reply-area').find('[name="contents"]').val(); 
		myAddForm.find('[name="contents"]').val(contents);
		console.log('>>> contents=' + contents);
    	// 2. submit
    	projDetailAddCmmt();
    	return;
//    }
});

/* list 탬플릿 */
var pdrListTemplete = $("#tmpl-project-detail-reply").html();

/* pdrListView */
var pdrListView = null;

/* 초기화 */
function initPdrListView(){
	var itemSeq = curReplyArea.data('seq');
	// === param set:
	// 1) 등록 form
	var myAddForm = $('form[name="projDetailAddCmmtForm"]');
	myAddForm.find('[name="itemSeq"]').val(itemSeq);
	myAddForm.find('[name="contents"]').val('');
	
	// 2) list form
	var myForm = $('form[name="pdrListParamForm"]');
	myForm.find('[name="schSeq"]').val(itemSeq);
	myForm.find('[name="schPage"]').val('1');
	
	// ===
	pdrListView = new ListView({
		htmlElement : curReplyArea.find('[data-name="pdrListView"]')
	});
	
	// === clear
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
		curReplyArea.find('[data-name="pdrLoadMore"]').hide();
	} else {
		curReplyArea.find('[data-name="pdrLoadMore"]').show();
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
		    		curReplyArea.find('[name="contents"]').val('');
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