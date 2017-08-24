<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.vo.CategoryVO"%>
<%@page import="com.opendesign.utils.StringUtil"%><%
	//카테고리 파라미터
	String schCate = request.getParameter("schCate");
	String codeDepth2 = CmnUtil.getCodeDepth2(schCate);
	String codeDepth3 = CmnUtil.getCodeDepth3(schCate);
	String hideClassDep2 = StringUtil.equals("", codeDepth2, "hide");
	String hideClassDep3 = StringUtil.equals("", codeDepth3, "hide");
	
	//현재 URL를 가져온다.
	String currentUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
	//카테고리:
	List<CategoryVO> cateListDepth2 = (List<CategoryVO>)request.getAttribute("cateListDepth2");
	List<CategoryVO> cateListDepth3 = (List<CategoryVO>)request.getAttribute("cateListDepth3");
%>
<!-- sub 카테고리 -->
			<div class="nav-cate <%=hideClassDep2%> ">
				<ul>
					<% 
						if(!CmnUtil.isEmpty(cateListDepth2)) { 
							for( CategoryVO item : cateListDepth2 ) {
					%>
					<li id="<%=item.getCategoryCode() %>" class="<%=StringUtil.equals(item.getCategoryCode(), codeDepth2, "active") %>">
						<a href="<%=currentUrl %>?schCate=<%=item.getCategoryCode()%>" data-code="<%=item.getCategoryCode()%>" ><%=item.getCategoryName()%></a>
					</li>
					<% 		
							}
						}
					%>
				</ul>
			</div>
			<div class="nav-cate <%=hideClassDep3%>">
				<ul>
					<% 
						if(!CmnUtil.isEmpty(cateListDepth3)) { 
							for( CategoryVO item : cateListDepth3 ) {
					%>
					<li class="<%=StringUtil.equals(item.getCategoryCode(), codeDepth3, "active") %>">
						<a href="<%=currentUrl %>?schCate=<%=item.getCategoryCode()%>" data-code="<%=item.getCategoryCode()%>" ><%=item.getCategoryName()%></a>
					</li>
					<% 		
							}
						} 
					%>
				</ul>
			</div>

<!-- ################ 카테고리 js ############# -->
<script id="tmpl-navCateTemplete" type="text/x-jsrender">
<li>
	<a class="hi" href="<%=currentUrl %>?schCate={{:categoryCode}}" data-code="{{:categoryCode}}" >{{:categoryName}}</a>
</li>
</script>
<script>
$(function(){
	// main page flag
	window.isMainPage = <%="/main.do".equals(currentUrl)%>;
	//
	initNavCateEvents();
});
/**
 * nav 카테고리 event
 */
function initNavCateEvents() {
	//=== 
	$('body').on('mouseenter', '.nav-cate li',function(e){
		e.preventDefault();
		e.stopPropagation();
		var thisObj = $(this);
		console.log('>>> mouseenter:' + thisObj.find('a').data('code'));
		
		loadNavCateSub(thisObj);
	});
	
	//===
	$('body').on('mouseleave', '.nav-cate-wrap',function(e){ 
		e.preventDefault();
		e.stopPropagation();
		var thisObj = $(this);
		console.log('>>> mouseleave nav-cate-wrap:');
		
		//
		var navCate1 = $('.nav-cate').eq(0);
		var navCate2 = $('.nav-cate').eq(1);
		var navCate3 = $('.nav-cate').eq(2);
		navCate1.show(); 
		navCate2.show(); 
		navCate3.show();
		if(navCate1.find('li.active').length == 0) {
			if(isMainPage) {
				navCate1.hide(); 
			}
		}
		if(navCate2.find('li.active').length == 0) {
			navCate2.hide();
		}
		if(navCate3.find('li.active').length == 0) {
			navCate3.hide(); 
		}
		
	});
}

function showHideNavCate(code) {
	// 숨김처리:
	var navCate1 = $('.nav-cate').eq(0);
	var navCate2 = $('.nav-cate').eq(1);
	var navCate3 = $('.nav-cate').eq(2);
	if(code.length > 0 && code.length <=3) {
		navCate1.show();
		navCate2.hide();
		navCate3.hide();
	} else if(code.length > 3 && code.length <=6) {
		navCate1.show();
		navCate2.show();
		navCate3.hide();
	} else if(code.length > 6 && code.length <=9) {
		navCate1.show();
		navCate2.show();
		navCate3.show();
	} else {
		navCate1.show();
		navCate2.hide();
		navCate3.hide();
	}
}
/**
 * sub 카테고리 load
 */
function loadNavCateSub(thisObj) {
	console.log('>>> loadNavCateSub');
	var code = thisObj.find('a').data('code');
	if(!code) {
		console.log('>>> loadNavCateSub code is null'); 
		return; 
	}
	showHideNavCate(code);
	
	//세번째 depth는 load할 필요 없음.
	if(code.length > 6) {
		return;
	}
	
	// 데이터 조회 및 load
	$.ajax({
        url: '/common/selectCategoryListDepthSub.do',
        type: 'get',
        global: false,
        data: { 'parentCateCode' : code },
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	var listData = _data.result;
	    	
	    	// add data to ui
	    	var subCont = thisObj.closest('.nav-cate').next('.nav-cate');
	    	subCont.find('ul').empty();
	    	subCont.hide();
	    	if(!listData) {
	    		console.log('>>> loadNavCateSub no data');
	    		return;
	    	}
	    	
	    	var htmlJ = $($.templates('#tmpl-navCateTemplete').render(listData));
	    	subCont.find('ul').append(htmlJ);
	    	subCont.show();
		}
    });
	
}


/** main 특수 처리 */
$(function(){
	if(isMainPage) {
		var navCate1 = $('.nav-cate').eq(0);
		$('#menuBox > li').mouseenter(function(e){
			navCate1.show();
		});
		$('.nav').mouseleave(function(e){
			navCate1.hide();
		});
		
		// 초기화
		if($('.nav-cate').find('li.active').length == 0) {
			navCate1.hide();
		} else {
			navCate1.show();
		}
	}
});
</script>
<!-- ################ ]]카테고리 js ############# -->
			
			