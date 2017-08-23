<%-- 화면ID : OD01-02-01 --%>
<%-- 화면ID : OD01-02-02 --%>
<%-- 화면ID : OD01-02-03 --%>
<%-- 화면ID : OD01-03-01 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.MemberCategoryVO"%>
<%@page import="com.opendesign.vo.MyUserVO"%>
<%@page import="com.opendesign.vo.UserVO"%>
<%@page import="com.opendesign.vo.MyUserPointVO"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%
 
   
 List<MemberCategoryVO> cateList = (List<MemberCategoryVO>)request.getAttribute("cateList");
 List<UserVO> myInfoList = (List<UserVO>)request.getAttribute("myInfoList");
 
 String userName = (String)request.getAttribute("UserName");
 String comment = (String)request.getAttribute("Comment"); 
 String imageUrl = (String)request.getAttribute("ImageUrl"); 
 String memType = (String)request.getAttribute("MemType");
 
 
 
 if( myInfoList != null ){ 	
		for (UserVO mVo : myInfoList) {
			userName = (String) mVo.getUname().toString();	
			comment = (String) mVo.getComments().toString(); 
			imageUrl = (String) mVo.getImageUrl().toString(); 
			memType = (String) mVo.getMemberType().toString();
		}	  
	 }
	 
 String myPoint = (String)request.getAttribute("MyPoint").toString(); 
 String myPointF = CmnUtil.nFormatter(myPoint); 
 myPoint = CmnUtil.getDisplayNumber(myPoint);
 
 Integer cntWork = (Integer)request.getAttribute("cntWork");
 Integer cntLike = (Integer)request.getAttribute("cntLike");
 Integer cntTalk = (Integer)request.getAttribute("cntTalk");

 String cntWorkF = CmnUtil.nFormatter(cntWork);
 String cntLikeF = CmnUtil.nFormatter(cntLike);
 String cntTalkF = CmnUtil.nFormatter(cntTalk);
 
 Integer memberSeq = (Integer)request.getAttribute("memberSeq");
 Integer chkLayer = (Integer)request.getAttribute("chkLayer");
 //List<MyUserVO> myProjectList = (List<MyUserVO>)request.getAttribute("projectList");
 //List<MyUserVO> workSeq = (List<MyUserVO>)request.getAttribute("workSeq");
 String divLayer;
 
 if ( "00".equals(memType) == false && chkLayer <= 0){
	divLayer = "";
 }else{
	divLayer = "style='display:none;'"; 
 }
 
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<script id="tmpl-projectTemplate" type="text/x-jsrender">
    <li><a href="/project/openProjectDetail.do?projectSeq={{:wseq}}">{{:pjname}}</a></li>   
</script>

<script id="tmpl-workTemplate" type="text/x-jsrender">
	<li class="swiper-slide"><a href="javascript:goProductView({{:wseq}});">
	 <img src="{{:thumbUri}}" onerror="setDefaultImg(this, 2);" alt="">
	 <div class="product-info">
		<p class="product-title">{{:wtitle}}</p>
		<p class="designer">{{:uname}}</p>
	 </div>
	 <p class="cate">{{:wcate}}</p>
	 <div class="item-info">
	 <span class="like"><img src="/resources/image/common/ico_like.png" alt="좋아요"> {{:cntLikeF}}</span>
	 <span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:wvcountF}}</span>
	 <span class="update">{{:displayTime}}</span>
	 </div>
    </a></li>
</script>

<script id="tmpl-likeTemplate" type="text/x-jsrender">
	<li class="swiper-slide"><a href="javascript:goProductView({{:wseq}});">
	 <img src="{{:thumbUri}}" onerror="setDefaultImg(this, 2);" alt="">
	 <div class="product-info">
		<p class="product-title">{{:wtitle}}</p>
		<p class="designer">{{:uname}}</p>
	 </div>
	 <p class="cate">{{:wcate}}</p>
	 <div class="item-info">
	 <span class="like"><img src="/resources/image/common/ico_like_active.png" alt="좋아요"> {{:cntLikeF}}</span>
	 <span class="hit"><img src="/resources/image/common/ico_hit.png" alt="열람"> {{:wvcountF}}</span>
	 <span class="update">{{:displayTime}}</span>
	 </div>
    </a></li>
</script>

<script id="tmpl-orderTemplate" type="text/x-jsrender">
   <li class="swiper-slide"><a href="javascript:goProductView({{:wseq}});">
	<img src="{{:thumbUri}}" alt="" onerror="setDefaultImg(this, 2);">
	 <ul class="purchase-info">
	 <li><span class="sbj">구입날짜</span>{{:displayTimeYMD}}</li>
	 <li><span class="sbj">구입 포인트</span>{{:displayNumber}}pt</li>
	 <li><span class="sbj">작가명</span>{{:uname}}<button type="button" onclick="goShowMsgView({{:useq}}); return false;"><img src="/resources/image/mypage/btn_msg.png" alt="메시지 보내기"></button></li>
	 <li><span class="sbj">디자인 제목</span><span class="ellip1" style="width:100px;display:inline-block;vertical-align:middle;">{{:wtitle}}</span></li>
	</ul>
	</a>
   </li>
</script>

<script id="tmpl-pointTemplate" type="text/x-jsrender">
   <tr>
	 <td class="first">{{:cntI}}</td>
	 <td>{{:displayTimeYMD}}</td>
	 <td>{{:wsign}}</td>
	 <td>{{:wcomments}}</td>
	 <td>{{:displaywamountF}}pt</td>
	 <td class="last">{{:displaywaccumamountF}}pt</td>
   </tr>
</script>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
<!--<script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>  -->

<!-- ################## 나의그룹 ############################################# -->
<script id="tmpl-groupTemplate" type="text/x-jsrender">
    <li><a href="javascript:goGroupDetailView('{{:seq}}');">{{:groupName}}</a></li> 
</script>
<script>
/**
 * group 상세 페이지
 */
function goGroupDetailView(seq) {
	window.location.href='/project/project.do?schMyGroup=' + seq;
}
</script>
<script>
var groupListView = null; //나의 그룹 view
var groupTemplate = null; 
$(function(){
	//뷰 	
   	groupListView = new ListView({
	    htmlElement : $('#groupArea')
	    ,htmlElementNoData: $('<p class="none">등록되어 있는 그룹이 없습니다.</p>')
   	});
   	groupTemplate = $('#tmpl-groupTemplate').html();
	
   	// load 
	loadGroup();
});

function loadGroup() {
		// 조회 및 load : 내가 포함된 그룹 조회
		$.ajax({				
			url: '/selectMyGroupList.ajax',
	        type: 'get',
	        data: { },
			error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data){
				console.log(_data);
		    	// load
		    	loadGroupWithData(_data.result);
			}
	    });					
}

function loadGroupWithData(resultDatas) {
	groupListView.clear();
	
	if(!resultDatas || resultDatas.length == 0) {
		console.log('>>> loadGroupWithData no data.');
		return;
	}
	//
	groupListView.addAll({
			keyName : "seq",
			data : resultDatas,
			htmlTemplate : groupTemplate
	});
	
	swipeInitGroup();
}

function swipeInitGroup() {
	var myGroup = new Swiper('.my-group', {
        scrollbar: '.my-group .swiper-scrollbar',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true,
        scrollbarHide: false
    });
}
</script>
<!-- ################## ]]나의그룹 ############################################# -->

<script>
/**
 * 디자인 상세
 */
function goProductView(seq) {
	window.location.href='/product/productView.do?seq=' + seq;
}
</script>
<script>
	var memberSeq = '<%=memberSeq%>';
		
	//뷰 컨트롤러 생성	
	var projectView = null;
	var workView = null;
	var likeView = null;
	var orderView = null;
	var pointView = null;
	
	// 프로젝트 탬플릿
	var projectTemplate = $("#tmpl-projectTemplate").html();
	
	// 디자인 탬플릿
	var workTemplate = $("#tmpl-workTemplate").html();
	
	// 관심 탬플릿
	var likeTemplate = $("#tmpl-likeTemplate").html();
	
	// 구입목록 템플릿
	var orderTemplate = $("#tmpl-orderTemplate").html();
	
	// 마이포인트 템플릿
	var pointTemplate = $("#tmpl-pointTemplate").html();
	
	/**
	 * 초기화
	*/
	$(function(){		    
	
	    // load
		loadProject();
		loadWork();
		loadLike();
		loadOrder();
		loadPoint();
		
		// slide-btn init
		$('.slide-btn').hide();
		
	    
		$("#fromDate").datepicker(
		{
			numberOfMonths: 1,
			buttonImage: "/resources/image/mypage/ico_calendar.png", // 버튼 이미지
			dateFormat: "yy-mm-dd",				
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			changeMonth: false, //월변경가능
		    changeYear: false, //년변경가능
			dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
			showOn: "both"

		 }
		);
		
		$("#toDate").datepicker(
		{
					numberOfMonths: 1,
					buttonImage: "/resources/image/mypage/ico_calendar.png", // 버튼 이미지
					dateFormat: "yy-mm-dd",				
					monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
					changeMonth: false, //월변경가능
				    changeYear: false, //년변경가능
					dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
					showOn: "both"

		 }
		);
			  
	 
	  $("#1year").click(function(){
		   $('.btn-sorting').find('button').removeClass("active");
		   $("#1year").addClass("active");
		   $("#fromDate").val(getCalculatedDate(-1,0,0,'-'))
		   $("#toDate").val(getCalculatedDate(0,0,0,'-'))
	  });
		 
	  $("#6month").click(function(){	
		   $('.btn-sorting').find('button').removeClass("active");
		   $("#6month").addClass("active");		  
	       $("#fromDate").val(getCalculatedDate(0,-6,0,'-'))
	       $("#toDate").val(getCalculatedDate(0,0,0,'-'))
	  });

	  $("#3month").click(function(){
		   $('.btn-sorting').find('button').removeClass("active");
		   $("#3month").addClass("active");
		   $("#fromDate").val(getCalculatedDate(0,-3,0,'-'))
	       $("#toDate").val(getCalculatedDate(0,0,0,'-'))	        
	  });	  
	 

	  $("#1month").click(function(){
		  $('.btn-sorting').find('button').removeClass("active");
		  $("#1month").addClass("active");
		  $("#fromDate").val(getCalculatedDate(0,-1,0,'-'))
	      $("#toDate").val(getCalculatedDate(0,0,0,'-'))	        
	  });	 
	    

	  $("#1week").click(function(){
		  $('.btn-sorting').find('button').removeClass("active");
		  $("#1week").addClass("active");
		  $("#fromDate").val(getCalculatedDate(0,0,-7,'-'))
	      $("#toDate").val(getCalculatedDate(0,0,0,'-'))
	        
	  });
	  
	  $("#today").click(function(){
		  $('.btn-sorting').find('button').removeClass("active");
		  $("#today").addClass("active");
		  $("#fromDate").val(getCalculatedDate(0,0,0,'-'))
	      $("#toDate").val(getCalculatedDate(0,0,0,'-'))
	        
	  });	  
	  
	    
	});
	
	// ============== project ===================
	/**
	 * 나의 프로젝트 load
	 */
	var flag_loadProject = false; //flag
	function loadProject() {
			
		   //뷰 컨트롤러 생성	
		   projectView = new ListView({
			    htmlElement : $('#projectArea')
			    ,htmlElementNoData: $('<p class="none">등록되어 있는 프로젝트가 없습니다.</p>')
		   });
		  
			
			if(flag_loadProject) {
				return;
			}
			flag_loadProject = true;
			
			//alert(memberSeq);
			
			// 디자인 조회 및 load
			$.ajax({				
				url: '/selectProjectList.ajax',
		        type: 'get',
		        data: { 'memberSeq' : memberSeq },
				complete : function(_data){
					flag_loadProject = false;
				},
				error : function(_data) {
					console.log(_data);
					//alert(_data);
			    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
				},
				success : function(_data){
					console.log(_data);
			    	var projectDatas = _data.projectList;
			    	// load
			    	//alert(ProjectDatas.length);
			    	loadProjectWithData(projectDatas);
				}
		    });					
	}
	
	/**
	 * 나의 디자인 load
	 */
	function loadProjectWithData(projectDatas) {
		projectView.clear();
		
		if(!projectDatas || projectDatas.length == 0) {
				console.log('>>> loadProjectWithData no data.'); 
				return;
		}
			// 주제:
		projectView.addAll({
				keyName : "wseq",
				data : projectDatas,
				htmlTemplate : projectTemplate,		
		});		
		swipeInitProject();
	}
	
	function swipeInitProject() {
		var myProject = new Swiper('.my-project', {
	        scrollbar: '.my-project .swiper-scrollbar',
	        direction: 'vertical',
	        slidesPerView: 'auto',
	        mousewheelControl: true,
	        freeMode: true,
	        scrollbarHide: false
	    });
	}
	
	// ============== work ===================
	/**
	 * 나의 디자인 load
	 */
	var flag_loadWork = false; //flag
	function loadWork() {
		   //뷰 컨트롤러 생성	
		   workView = new ListView({
			    htmlElement : $('#workArea')
			    ,htmlElementNoData: $('<p class="none">등록되어 있는 디자인이 없습니다.</p>')
		   });
			
			if(flag_loadWork) {
				return;
			}
			flag_loadWork = true;
			
			//alert(memberSeq);
			
			// 디자인 조회 및 load
			$.ajax({				
				url: '/selectProjectWork.ajax',
		        type: 'get',
		        data: { 'memberSeq' : memberSeq },
				complete : function(_data){
					flag_loadWork = false;
				},
				error : function(_data) {
					console.log(_data);
					//alert(_data);
			    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
				},
				success : function(_data){
					console.log(_data);
			    	var workDatas = _data.workList;
			    	// load
			    	//alert(workDatas.length);
			    	loadWorkWithData(workDatas);
				}
		    });					
	}
	
	/**
	 * 나의 디자인 load
	 */
	function loadWorkWithData(workDatas) {
		workView.clear();
		
		if(!workDatas || workDatas.length == 0) {
				console.log('>>> loadWorkWithData no data.');
				//$('.portfolio.product-list').append('<div style="width:100%;top:50%;position:absolute;text-align:center;">등록되어 있는 제작물이 없습니다.</div>');
				return;
		}
			// 주제:
		workView.addAll({
				keyName : "wseq",
				data : workDatas,
				htmlTemplate : workTemplate,		
		});		
		swipeInitWork();
	}
	
	var workSwipe = null;
	function swipeInitWork() {
		var item = $('.portfolio').find('li').length;
				
		if(item > 4){			
			if( workSwipe == null ) {
				$('.portfolio').find('.slide-btn').show();
				workSwipe = new Swiper('.portfolio-slide', {
			        slidesPerView: 4,
			        spaceBetween: 23,
			        nextButton: '.portfolio-next',
			    	prevButton: '.portfolio-prev'
			    });
			} else {
				workSwipe.onResize();
			}
		} else{
			$('.portfolio').find('.slide-btn').hide();
		}
	}
	
	// ============== like ===================
	var flag_loadLike = false; //flag
	function loadLike() {
	  //뷰 컨트롤러 생성	
	  likeView = new ListView({
		    htmlElement : $('#likeArea')
		    ,htmlElementNoData: $('<p class="none">등록되어 있는 관심디자인이 없습니다.</p>')
	   });
	 // 디자인 조회 및 load	  
	  $.ajax({				
		url: '/selectProjectLike.ajax',
        type: 'get',
        data: { 'memberSeq' : memberSeq },
		complete : function(_data){
			flag_loadLike = false;
		},
		error : function(_data) {
			console.log(_data);
			//alert(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	var likeDatas = _data.likeList;
	    	// load
	    	//alert(likeDatas.length);
	    	loadLikeWithData(likeDatas);
		}
       });
     }
	
		
	function loadLikeWithData(likeDatas) {
		likeView.clear();
		//listView.htmlElement = $('#likeArea');
		if(!likeDatas || likeDatas.length == 0) {
				//$('.favorite.product-list').append('<div style="width:100%;top:50%;position:absolute;text-align:center;">등록되어 있는 관심디자인이 없습니다.</div>');
				console.log('>>> loadLikeWithData no data.');
				return;
		}
			// 주제:
		likeView.add({
				keyName : "wseq",
				data : likeDatas,
				htmlTemplate : likeTemplate,		
		});				
		swipeInitLike();
	}
	
	var likeSwipe = null;
	function swipeInitLike() {
		var item2 = $('.favorite').find('li').length;		
		if(item2 > 4){	
			if(likeSwipe == null) {
				$('.favorite').find('.slide-btn').show();
				likeSwipe = new Swiper('.favorite-slide', {
			        slidesPerView: 4,
			        spaceBetween: 23,
			        nextButton: '.favorite-next',
			    	prevButton: '.favorite-prev'
			    });
			} else {
				likeSwipe.onResize();
			}
		} else{
			$('.favorite').find('.slide-btn').hide();
		}
	}
	
	
	// ============== order ===================
	var flag_loadOrder = false; //flag
	function loadOrder() {
	  //뷰 컨트롤러 생성	
	  orderView = new ListView({
		    htmlElement : $('#orderArea')
		    ,htmlElementNoData: $('<p class="none">등록되어 있는 구입디자인이 없습니다.</p>')
	   });
	 // 디자인 조회 및 load	  
	  $.ajax({				
		url: '/selectProjectOrder.ajax',
        type: 'get',
        data: { 'memberSeq' : memberSeq },
		complete : function(_data){
			flag_loadOrder = false;
		},
		error : function(_data) {
			console.log(_data);
			//alert(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	var orderDatas = _data.orderList;
	    	// load
	    	//alert(orderDatas.length);
	    	loadOrderWithData(orderDatas);
		}
       });
     }
	
	function loadOrderWithData(orderDatas) {
		orderView.clear();
		//listView.htmlElement = $('#orderArea');
		//alert(orderDatas.length);
		if(!orderDatas || orderDatas.length == 0) {
				console.log('>>> loadOrderWithData no data.');
				return;
		}
			// 주제:
		orderView.add({
				keyName : "wseq",
				data : orderDatas,
				htmlTemplate : orderTemplate,		
		});		
		swipeInitOrder(); 
	}
	
	var orderSwipe = null;
	function swipeInitOrder() {
		var item3 = $('.purchase').find('li').length;
				
		if(item3 > 4*2){
			if(orderSwipe == null) {
				$('.purchase').find('.slide-btn').show();					
				orderSwipe = new Swiper('.purchase-list', {
					slidesPerView: 4,
			        slidesPerColumn: 2,
			        spaceBetween: 14,
			        slidesPerColumnFill : "row",
			        simulateTouch: false,
			        nextButton: '.purchase-next',
			    	prevButton: '.purchase-prev'
			    });
			} else {
				orderSwipe.onResize();
			}
		} else{
			$('.purchase').find('.slide-btn').hide();
		}
	}
	
	
	// ============== point ===================
	/**
	 * 포인트 view
	 */
	function loadPoint() {
		 	//포인트 컨트롤러 생성	
		   pointView = new ListView({
			    htmlElement : $('#pointArea')
			    ,htmlElementNoData: $('</p>')
		   });
		 
	}
	
	var flag_loadPoint = false; //flag
	function goSearch()
	{   			
		var myForm = $('.my-point').find('form');
		var fdate = myForm.find('[name="fromDate"]');
		var tdate = myForm.find('[name="toDate"]');
		if(fdate.val() == '' || tdate.val() == '') {
			alert('조회기간을 선택 선택하세요.');
			return;
		}
		
		if(flag_loadPoint) {
			return;
		}
		flag_loadPoint = true;
		//alert(myForm.serialize());
		// 퍼가기
		
		$('#noPointDiv').hide();
		
		 $.ajax({				
			 url: '/selectMyPoint.ajax',
		     type: 'get',
		     data: myForm.serialize(),
			 complete : function(_data){
				flag_loadPoint = false;
			 },
			 error : function(_data) {
				console.log(_data);				
			    alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			  },
			  success : function(_data){
				console.log(_data);
			    var pointDatas = _data.pointList;			    	
			    //alert(pointDatas);
			    loadPointWithData(pointDatas);
			  }
		    });
	}
	
	function loadPointWithData(pointDatas) {
		
		pointView.clear();
		
		//listView.htmlElement.clear();
		//alert(orderDatas.length);
		/*listView._removeByKey({
			keyName : "point",			
		});*/
		
		//뷰 컨트롤러 생성	
		
		//listView.htmlElement = $('#pointArea');
		
		if(!pointDatas || pointDatas.length == 0) {
			console.log('>>> loadpointWithData no data.');
			$('#noPointDiv').show();
			return;
		} else {
			$('#noPointDiv').hide();
		}
		
		pointView.addAll({
			keyName : "wseq",
			data : pointDatas,
			htmlTemplate : pointTemplate,
		});
		
	}
	
	
	function getCalculatedDate(iYear, iMonth, iDay, seperator)
	{
	  //현재 날짜 객체를 얻어옴.
	  var gdCurDate = new Date();
	  //현재 날짜에 날짜 게산.
	  gdCurDate.setYear( gdCurDate.getFullYear() + iYear );
	  gdCurDate.setMonth( gdCurDate.getMonth() + iMonth );
	  gdCurDate.setDate( gdCurDate.getDate() + iDay );
	 
	  //실제 사용할 연, 월, 일 변수 받기.
	  var giYear = gdCurDate.getFullYear();
	  var giMonth = gdCurDate.getMonth()+1;
	  var giDay = gdCurDate.getDate();
	  //월, 일의 자릿수를 2자리로 맞춘다.
	  giMonth = "0" + giMonth;
	  giMonth = giMonth.substring(giMonth.length-2,giMonth.length);
	  giDay   = "0" + giDay;
	  giDay   = giDay.substring(giDay.length-2,giDay.length);
	  //display 형태 맞추기.
	  return giYear + seperator + giMonth + seperator +  giDay;
	}
	
	//가이드 창 닫기
	function closeGuide() {
		$("#guide").css("display", "none");
	}
	
</script>
</head>
<body>
<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->
    
	<!-- content -->
	<div class="sub-content">
		<div class="inner mypage-wrap">
			<div class="sub-title">
				<h2><img src="<%=imageUrl%>" onerror="setDefaultImg(this, 1);" alt="프로필 사진"> <%=userName%>님의 페이지</h2>
				<a href="javascript:goMemModi('<%=memberSeq%>')" class="btn-modi btn-modal">정보수정</a>
				<a href="/product/productRegi.do" class="btn-regi">디자인 등록</a> 
				<!-- <a href="#" class="btn-regi">디자인 등록</a>-->
				<div class="regi-guide" <%=divLayer%> id="guide">
					<button style="background: url('../../../resources/image/common/x-mark-icon-ie.png'); width: 20px; height: 20px; float: right; margin-right: 10px; margin-top: -7px;" onclick="javascript:closeGuide();"></button>
					<p>디자이너 / 제작자인 경우,<br>디자인을 등록해야 메인에 노출이 됩니다!</p>
				</div>
			</div>
			<p class="hello" style="width:1024px; word-wrap:break-word;">
			<%=comment%>
            </p>          
			<div class="my-info">
				<ul class="info-list">
					<li><span class="sbj">닉네임</span> <%=userName%></li>
					<li><span class="sbj">디자인</span> <%=cntWorkF%> 디자인</li>
					<li><span class="sbj">카테고리</span> 
					<%
					 String strCagegory = "";						
					 if( cateList != null ){ 					  
					  List<String> ctgList = new ArrayList<String>();
					   for (MemberCategoryVO myCtg : cateList) {
						 ctgList.add(myCtg.getCategoryName());						 
					   }
					   strCagegory = StringUtils.join(ctgList, ",");
					  }
					%>					
					<%=strCagegory%>
					</li>
					<li><span class="sbj">총 좋아요 수</span> 총 <%=cntLikeF%>개</li>
					<%-- <li><span class="sbj">메시지</span> <%=cntTalk%>건</li> --%>
					<%-- <li><span class="sbj">마이 포인트</span> <%=myPointF%> pt</li> --%>
				</ul>
				<div class="project" >
					<h3>나의 프로젝트</h3>
					<div class="my-project" >
						 <div class="swiper-wrapper">						
						  	<ul class="swiper-slide" id="projectArea">					
						 	</ul>
						 </div>
						 <div class="swiper-scrollbar"></div>
					</div>
					
					<!-- 나의 그룹 -->
					</br>
					<h3>나의 그룹</h3>
					<div class="my-group">
						 <div class="swiper-wrapper" >						
						  	<ul class="swiper-slide" id="groupArea">
						 	</ul>
						 </div>
						 <div class="swiper-scrollbar"></div>
					</div>
					<!-- //나의 그룹 -->
				</div>
			</div>

			<div class="portfolio product-list">
				<div class="best-head">
					<span class="mainChar">D</span>
					<span>최근 등록한 디자인</span>
				</div>
				<div class="slide portfolio-slide">
					<ul class="list-type1 swiper-wrapper" id="workArea">
					</ul>
				</div>
				<div class="slide-btn">
					<button type="button" class="btn-prevSlide portfolio-prev"><img src="/resources/image/mypage/btn_prevSlide.png" alt="이전"></button>
					<button type="button" class="btn-nextSlide portfolio-next"><img src="/resources/image/mypage/btn_nextSlide.png" alt="다음"></button>
				</div>
			</div>
			<div class="favorite product-list">
				<div class="best-head">
					<span class="mainChar">D</span>
					<span>관심 디자인</span>
				</div>
				<div class="slide favorite-slide">
					<ul class="list-type1 swiper-wrapper" id="likeArea">						
					</ul>
				</div>
				<div class="slide-btn">
					<button type="button" class="btn-prevSlide favorite-prev"><img src="/resources/image/mypage/btn_prevSlide.png" alt="이전"></button>
					<button type="button" class="btn-nextSlide favorite-next"><img src="/resources/image/mypage/btn_nextSlide.png" alt="다음"></button>
				</div>
			</div>

			<!-- <div class="purchase">
				<h3>최근 구입한 디자인</h3>
				<div class="purchase-list">
					<ul class="list-type3  swiper-wrapper" id="orderArea">							 
            		</ul>
					 <div class="slide-btn">
						<button type="button" class="btn-prevSlide purchase-prev"><img src="/resources/image/mypage/btn_prevSlide.png" alt="이전"></button>
						<button type="button" class="btn-nextSlide purchase-next"><img src="/resources/image/mypage/btn_nextSlide.png" alt="다음"></button>
					</div>
				</div>				
			</div>  

			<div class="my-point">
				<h3>마이 포인트</h3>
				<div class="point-inner">
					<strong>보유 포인트 <%=myPointF%> pt</strong>
					<h4>포인트 내역 조회하기</h4>
					<form name="frmMypoint">					
					<input type="hidden" name="wseq" value="<%=memberSeq%>"/>
						<dl>
							<dt>조회기간</dt>
							<dd>
								<div class="btn-sorting">
									<button type="button" id="today">오늘</button>
									<button type="button" id="1week">일주일</button>
									<button type="button" id="1month">1개월</button>
									<button type="button" id="3month">3개월</button>
									<button type="button" id="6month">6개월</button>
									<button type="button" id="1year" class="last">1년</button>
								</div>
								<div class="period">
									<input type="text" id="fromDate" readonly name="fromDate" style="text-indent: 5px;"><!--<button type="button"><img src="/resources/image/mypage/ico_calendar.png" alt="달력보기"></button>  
									<span>~</span>
									<input type="text" id="toDate" readonly name="toDate" style="text-indent: 5px;"><button type="button"><img src="/resources/image/mypage/ico_calendar.png" alt="달력보기"></button>
									<button type="button" class="btn-check" onClick="goSearch();">조회하기</button>
								</div>
							</dd>
						</dl>
					</form>

					<table class="tbl-point">
						<colgroup>
							<col style="width:70px">
							<col style="width:115px">
							<col style="width:200px">
							<col>
							<col style="width:160px">
							<col style="width:160px">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" class="first">번호</th>
								<th scope="col">날짜</th>
								<th scope="col">유형</th>
								<th scope="col">내용</th>
								<th scope="col">포인트 내역</th>
								<th scope="col" class="last">잔여 포인트</th>
							</tr>
						</thead>
						<tbody id="pointArea">
							
						</tbody>
					</table>
					<p class="none" style="padding-bottom:20px;" id="noPointDiv"> 검색 결과가 없습니다. </p>
					<div class="paging">
						<a href="#"><img src="/resources/image/common/btn_prev.gif" alt="이전"></a>
						<a href="#">1</a>
						<a href="#">2</a>
						<a href="#">3</a>
						<a href="#"><img src="/resources/image/common/btn_next.gif" alt="다음"></a>
					</div>
				</div>
			</div> -->
		</div>
	</div>
	<!-- //content -->

	<!-- footer -->
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<!-- //footer -->
</div>

<!-- modal -->
<%@include file="/WEB-INF/views/common/modal.jsp"%>
<%@include file="/WEB-INF/views/common/modal_member_modi.jsp"%> 
<!-- //modal -->
</body>
</html>