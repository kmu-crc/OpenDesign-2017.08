<%-- 화면ID : OD03-01-01 --%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.opendesign.vo.ProjectVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.ProjectGroupVO"%>
<%@page import="com.opendesign.utils.CmnConst"%>
<%@page import="com.opendesign.utils.StringUtil"%><%
	/* 로그인 여부 */
	boolean isLogin = CmnUtil.isUserLogin(request);
	
	/* 로그인 유저 그룹 */
	List<ProjectGroupVO> myPGroupList = (List<ProjectGroupVO>)request.getAttribute("myPGroupList");
	List<ProjectVO> myProjectList = (List<ProjectVO>)request.getAttribute("myProjectList");

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
	<div class="project-content">
		<div class="inner">
			<h2 id="project_all_cnt" class="title">프로젝트 (0건)</h2>
			<div class="btn-area">
			<!-- 로그인후 보여줌 -->
				<% if(isLogin) { %>
				<div id="sch_my_group" class="select-area project-select custom-select">
					<input type="text" name="txt_select_name" ><i class="fa fa-caret-down" aria-hidden="true"></i>
					<select name="" >
						<option value="">내그룹(<%=myPGroupList.size()%>)</option>
					<%if( isLogin && myPGroupList.size() > 0 ){ %>
					<%
						for(ProjectGroupVO aPGroup : myPGroupList){
					%>
						<option value="<%=aPGroup.getSeq()%>"><%=aPGroup.getGroupName()%></option>
					<%
						}
					%>
					<%} %>
					</select>
				</div>
				<%} %>
				
			
				<a class="btn-red" href="javascript:goPage('/project/projectGroup.do', true);">그룹 생성 및 관리</a>
				<%
				if( isLogin && myProjectList.size() > 0 ) {
				%>
				<a class="btn-red" href="javascript:goPage('/project/projectManage.do', true);">프로젝트 관리</a>
				<%
				}
				%>
				<a class="btn-red" href="javascript:goPage('/project/projectRegi.do', true);" class="btn-product">프로젝트 생성</a>
				<button class="btn-help btn-red" type="button" onclick="javascript:modalShow('#help-modal-project');">도움말</button>
			</div>
		</div>

		<div class="tab-wrap">
			<ul class="tab">
				<li class="ing active btn-red"><a href="#ing-project">진행중인 프로젝트</a></li>
				<!-- <li class="complete"><a href="#complete-project"><span></span>완료된 프로젝트</a></li> -->
				<li class="complete btn-red"><a href="#group-tab">프로젝트 그룹</a></li>
			</ul>
			<div class="sorting">
				<a href="javascript:sortProduct();" class="first btn-red active">최신순</a>
				<a href="javascript:sortProduct('LIKE');" class="btn-red">인기순</a>
				<a href="javascript:sortProduct('LIKE');" class="btn-red">멤버순</a>
			</div>
			<div class="clear"></div>
		</div>
		<div class="tab-con project-con">
			<div id="ing-project" class="active">
				<ul id="ing-project-list" class="project-list"></ul>
			</div>
			<!-- <div id="complete-project">
				<ul id="complete-project-list" class="project-list"></ul>
			</div> -->
			<div id="group-tab">
				<ul id="groupListView" class="project-list"></ul>
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
<%
	//카테고리
	String schCate = request.getParameter("schCate");
	//진행중인 프로젝트 페이지 인덱스
	String schPPage = request.getParameter("schPPage");
	//완료된 프로젝트 페이지 인덱스
	String schCPage = request.getParameter("schCPage");
	//내 그룹
	String schMyGroup = request.getParameter("schMyGroup");
%>
<form id="listParamForm" name="listParamForm" method="GET" action="" >
	<input type="hidden" name="schCate" value="<%=StringUtil.nullToBlank(schCate) %>" />
	<input type="hidden" name="schPPage" value="" /> 
	<input type="hidden" name="schCPage" value="" />
	<input type="hidden" name="schMyGroup" value="<%=StringUtil.nullToBlank(schMyGroup) %>" />
	<input type="hidden" name="schProgressStatus" value="" />
	<input type="hidden" name="projectSeq" value="" />
	<input type="hidden" name="schLimitCount" value="16" />
</form>
<script id="tmpl-listView" type="text/x-jsrender">
	<li><a href="javascript:goProject({{:seq}});" >
    	<dl>
        	<dt>{{:projectName}}</dt>
			<dd>{{:ownerName}}님의 프로젝트</dd>
		</dl>
        <div class="img-area">
        	<img src="{{:fileUrlM}}" onerror="setDefaultImg(this, 5);" alt="" >
		</div>
        <div class="project-info">
        	<div class="member">
            	<i>{{:projectMemberCntF}}</i>
                <span>멤버</span>
			</div>
            <!--div class="bbs">
            	<i>{{:projectWorkCntF}}</i>
                <span>게시글</span>
			</div-->
            <div class="file-num">
            	<i>{{:projectWorkFileCntF}}</i>
                <span>파일</span>
			</div>
		</div>
	</a></li>
</script>
<script type="text/javascript">
	
	
	/* project list 탬플릿 */
	var projectLitTemplete = $('#tmpl-listView').html();

	/* list form 객체 */
	var listForm = null;
	/* 진행중인 프로젝트 객체 */
	var designProjectIngView = null;
	/* 완료된 프로젝트 객체 */
	//var designProjectComView = null;
	
	/* 초기화 */
	$(function(){
		
		
		/* 진행중인 프로젝트 객체 생성 */
		designProjectIngView = new ListView({
			id : 'designProjectIngView'
			, htmlElement : $('#ing-project-list')
			, data : {progressStatus:'<%=CmnConst.ProjectProgressStatus.PROGRESS%>'}
		});
		
		/* 완료된 프로젝트 객체 생성 */
		<%--
		designProjectComView = new ListView({
			id : 'designProjectComView'
			, htmlElement : $('#complete-project-list')
			, data : {progressStatus:'<%=CmnConst.ProjectProgressStatus.COMPLETE%>'}
		});
		--%>
		
		/* 프로젝트목록 조건 form */
		listForm = $('#listParamForm');
		/* 파라미터 초기화 */
		initParam();
		
		/* 이벤트 : 사용자 그룹 변경 */
		$('#sch_my_group select').on('change', function(){
			var val = $(this).find('option:selected').val();			
			listForm.find('input[name="schMyGroup"]').val(val);
			listForm.find('input[name="schPPage"]').val('1');
			listForm.find('input[name="schCPage"]').val('1');
			listForm.find('input[name="schProgressStatus"]').val('');
			listForm.prop('action', '/project/project.do');
			listForm.submit();
			
		});
		
		/* 진행중인 프로젝트 데이터 로드 */
		//loadProject(designProjectIngView);
		/* 데이터 로드: 탭 click시  */
		//groupLoadPage(groupListView);
		var projectTab = $('.tab-wrap li.ing');
		$(projectTab).click(function(){
			initParam();
			designProjectIngView.clear(); 
			loadProject(designProjectIngView); 
		});
		
		/* 완료된 프로젝트 데이터 로딩 */
		//loadProject(designProjectComView);
		
		/* 윈도우 스크롤 이벤트 : 프로젝트 로드 */
		$(window).on('mousewheel', function(e){
			if( e.originalEvent.wheelDelta / 120 > 0 ) {
				// to do nothing...
	        } else {
	        	/* 스크롤이 최하단일 경우 프로젝트 로드 */
	        	if ( $(window).scrollTop() == $(document).height() - $(window).height()) {
	        		//var targetView = $('#ing-project').hasClass('active') ?
	        		//				 designProjectIngView : designProjectComView;
	        		var targetView = $('#ing-project').hasClass('active') ? designProjectIngView : null;
	        		if( !targetView || ! targetView.data('existList') ){
	        			return;
	        		}
	        		
	        		loadProject(targetView, true);
	            }
	        }
		});
		
		
		
		
	});
	
	/**
	 * 검색 파라미터 초기화
	 */
	function initParam(){
		var from = listForm;
		from.find('input[name="projectSeq"]').val('');
		
		/* 첫번째 option 선택 */
		$('#sch_my_group select > option[value=""]').prop('selected', true);
		/* 파라미터 option 선택 */
		var val = listForm.find('input[name="schMyGroup"]').val();
		$('#sch_my_group select > option[value="' + val + '"]').prop('selected', true);
		var name = $('#sch_my_group select > option:selected').text();
		$('#sch_my_group input:text').val(name);
		
		from.find('input[name="schPPage"]').val('<%=StringUtil.emptyToString(schPPage, "1") %>');
		from.find('input[name="schCPage"]').val('<%=StringUtil.emptyToString(schCPage, "1") %>');
	}
	
	/**
	 * 프로젝트 상세 화면 이동
	 */
	function goProject(projectSeq){
		listForm.find('input[name="projectSeq"]').val(projectSeq);
		listForm.prop('action', '/project/openProjectDetail.do');
		listForm.submit();
		
	}
	
	/* scroll 데이터 로드 여부 */
	var flagScrollLoad = false;
	/**
	 * 프로젝트 데이터 로드
	 */
	function loadProject( targetView, flagScroll ){
		/* 스크롤 */
		if( flagScroll ){
			if( flagScrollLoad ){
				return;
			}
			
			flagScrollLoad = true;		
		}
		var schProgressStatus = targetView.data('progressStatus');
		
		var from = listForm;
		var pageTarget = targetView.id == 'designProjectIngView' 
						 ? from.find('input[name="schPPage"]')
						 : from.find('input[name="schCPage"]');
		from.find('input[name="schProgressStatus"]').val(schProgressStatus);
		
		$.ajax({
			url : "/project/projectList.ajax",
	        type: "GET",
	        cache: false,
			data : from.serializeArray(),
			success : function(_data){
				if( flagScroll ) flagScrollLoad = false;		
				
				
				
				/*
				* css 처리로 변경 하였음 2016.09.29
				*
				for( key in listData ) {
					var project = listData[key];
					project.projectName = addDots(project.projectName, 6);
				}
				*/
				
				var allCount = _data.all_count;
				var item = _data.item;
				
				if(item.thirdCategoryNm != null && item.thirdCategoryNm != "") {
					$('#project_all_cnt').html('프로젝트 > ' + item.firstCategoryNm + ' > ' + item.secondCategoryNm + ' > ' + item.thirdCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');	
				} else if(item.secondCategoryNm != null && item.secondCategoryNm != "") {
					$('#project_all_cnt').html('프로젝트 > ' + item.firstCategoryNm + ' > ' + item.secondCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');
				} else if(item.firstCategoryNm != null && item.firstCategoryNm != "") {
					$('#project_all_cnt').html('프로젝트 > ' + item.firstCategoryNm + '(' + formatNumberCommaSeparate(allCount) + '건)');
				} else {
					$('#project_all_cnt').html('프로젝트 (' + formatNumberCommaSeparate(allCount) + '건)');
				}

				var listData = _data.list;
				var listCount = listData.length;				
				var existList = listCount > 0; 
				targetView.putData('existList', existList);
				if( ! existList ){
					return;
					
				}
				
				//var totalCount = _data.total_count;			
				var intPageCount = parseInt(pageTarget.val(), 10);
				intPageCount++;
				pageTarget.val(intPageCount);
				
								
				targetView.addAll({keyName:'seq', data:listData, htmlTemplate:projectLitTemplete });
				
			},
			error : function(req){
				if( flagScroll ) flagScrollLoad = false;
				console.log("fail to loadProject processing!");
			}
		});
	}
</script>

<!-- ################# 그룹 탭  ###################### -->
<%
	//그룹 페이지 인덱스
	String schPage = request.getParameter("schPage");
%>
<form id="groupListForm" name="groupListForm" method="GET" action="" >
	<input type="hidden" name="schPage" value="" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schLimitCount" value="16" />
</form>
<script id="tmpl-groupListView" type="text/x-jsrender">
	<li><a href="javascript:goGroupDetailView({{:seq}});" >
		<dl>
			<dt>{{:groupName}}</dt>
			<dd>{{:memberName}}님의 그룹</dd>
		</dl>
		<div class="project-info">
			<div class="project">
				<i>{{:projectCntF}}</i>
				<span>프로젝트</span>
			</div>
			<!--div class="bbs">
				<i>{{:workCntF}}</i>
				<span>게시물</span>
			</div-->
			<div class="member">
				<i>{{:memberCntF}}</i>
				<span>멤버</span>
			</div>
		</div>
	</a></li>
</script>
<script type="text/javascript">
	
	/* list 탬플릿 */
	var groupListTemplete = $("#tmpl-groupListView").html();
	/* list form 객체 */
	var groupListForm = null;
	/* view 객체 */
	var groupListView = null;
	
	/* 초기화 */
	$(function(){
		/* 뷰 객체 생성 */
		groupListView = new ListView({
			htmlElement : $('#groupListView')
		});
		
		/* 프로젝트목록 조건 form */
		groupListForm = $('#groupListForm');
		/* 파라미터 초기화 */
		groupInitParam();
		
		/* 데이터 로드: 탭 click시  */
		//groupLoadPage(groupListView);
		$('.tab-wrap li.complete').click(function(){
			groupInitParam();
			groupListView.clear();
			groupLoadPage(groupListView);
		});
		
		/* 윈도우 스크롤 이벤트 : 프로젝트 로드 */
		$(window).on('mousewheel', function(e){
			if( e.originalEvent.wheelDelta / 120 > 0 ) {
				// to do nothing...
	        } else {
	        	/* 스크롤이 최하단일 경우 프로젝트 로드 */
	        	if ( $(window).scrollTop() == $(document).height() - $(window).height()) {
	        		var targetView = $('#group-tab').hasClass('active') ? groupListView : null;
	        		if( ! targetView || ! targetView.data('existList') ){
	        			return;
	        		}
	        		
	        		groupLoadPage(targetView, true);
	            }
	        }
		});
	});
	
	/**
	 * 검색 파라미터 초기화
	 */
	function groupInitParam(){
		var from = groupListForm;
		from.find('input[name="schPage"]').val('<%=StringUtil.emptyToString(schPage, "1") %>');
	}
	
	
	/* scroll 데이터 로드 여부 */
	var flagScrollLoadGroupDetail = false;
	/**
	 * 프로젝트 데이터 로드
	 */
	function groupLoadPage( targetView, flagScroll ){
		/* 스크롤 */
		if( flagScroll ){
			if( flagScrollLoadGroupDetail ){
				return;
			}
			
			flagScrollLoadGroupDetail = true;		
		}
		
		var from = groupListForm;
		var pageTarget = from.find('input[name="schPage"]');
		
		$.ajax({
			url : "/project/selectGroupList.ajax",
	        type: "GET",
	        cache: false,
			data : from.serializeArray(),
			success : function(_data){
				console.log('>>> _data: ');
				console.log(_data);
				if( flagScroll ) { flagScrollLoadGroupDetail = false; }		
				
				var listData = _data.list;
				var listCount = listData.length;
				var existList = listCount > 0;
				
				var allCount = _data.all_count;
				$('#project_all_cnt').html('그룹 (' + formatNumberCommaSeparate(allCount) + '건)');
				
				targetView.putData('existList', existList);				
				if( ! existList ){					
					return;
					
				}
				
				
				var intPageCount = parseInt(pageTarget.val(), 10);
				intPageCount++;
				pageTarget.val(intPageCount);
				
				//$('#allCnt').html('(' + formatNumberCommaSeparate(allCount) + '건)');
				
				targetView.addAll({keyName:'seq', data:listData, htmlTemplate: groupListTemplete });
				
			},
			error : function(req){
				if( flagScroll ) {flagScrollLoadGroup = false;}
				console.log("fail to groupLoadPage processing!");
			}
		});
	}
</script>

<!-- ########## 그룹 상세 ########## -->
<script>
/**
 * group 상세 페이지
 */
function goGroupDetailView(seq) {
	window.location.href='/project/project.do?schMyGroup=' + seq;
}
</script>
<!-- 공개일때만 상세조회 가능 -->
<script id="tmpl-groupDetail" type="text/x-jsrender">
	{{if publicYn == 'Y' }} 
	<li><a href="javascript:goProject({{:seq}});" >
	{{else}}
	<li><a href="javascript:void(0);" >
	{{/if}}
    	<dl>
        	<dt>{{:projectName}}</dt>
			<dd>{{:ownerName}}님의 프로젝트</dd>
		</dl>
        <div class="img-area">
        	<img src="{{:fileUrlM}}" onerror="setDefaultImg(this, 5);" alt="">
		</div>
        <div class="project-info">
        	<div class="member">
            	<i>{{:projectMemberCntF}}</i>
                <span>멤버</span>
			</div>
            <!--div class="bbs">
            	<i>{{:projectWorkCntF}}</i>
                <span>게시글</span>
			</div-->
            <div class="file-num">
            	<i>{{:projectWorkFileCntF}}</i>
                <span>파일</span>
			</div>
		</div>
	</a></li>
</script>
<script>
	var groupDetailTemplate = $('#tmpl-groupDetail').html();
	/** 그룹 상세 */
	function groupDetailLoadPage(schMyGroup){
		$.ajax({
			url : "/project/selectGroupDetail.ajax",
	        type: "GET",
	        cache: false,
			data : {schMyGroup : schMyGroup},
			success : function(_data){
				console.log('>>> _data: ');
				console.log(_data);
				
				groupListView.clear();
				
				var listData = _data.list;
				var listCount = listData.length;
				var allCount = _data.all_count;
				$('#project_all_cnt').html('프로젝트 (' + formatNumberCommaSeparate(allCount) + '건)');
				groupListView.addAll({keyName:'seq', data:listData, htmlTemplate: groupDetailTemplate });
				
			},
			error : function(req){
				console.log("fail to groupDetailLoadPage processing!");
			}
		});
	}

</script>
<script>
/** 탭 페이지 init */
$(function(){
	var schMyGroup = '<%=StringUtils.stripToEmpty(schMyGroup)%>';
	console.log('>>> schMyGroup=' + schMyGroup);
	if(schMyGroup != '') {
		// tab ui 처리 :
		changeTabActiveUI('.tab-wrap li.complete');
		//
		groupDetailLoadPage(schMyGroup);
	} else {
		changeTabActiveUI('.tab-wrap li.ing');
		$('.tab-wrap li.ing').trigger('click');
	}
});

function changeTabActiveUI(tabLiObjSel) {
	var tabAObj = $(tabLiObjSel).find('a'); 
	var target = tabAObj.attr('href');
	tabAObj.parent().addClass('active').siblings().removeClass('active');
	$(target).addClass('active').siblings().removeClass('active');
}
</script>
<!-- ################# ]]그룹 탭  ###################### -->


<!-- 도움말 모달 -->

	<div class="modal" id="help-modal-project">
		<div class="bg"></div>
		<div class="modal-inner helpModal">
			<div class="modal-body">
				<h3 align="center">함께하는 디자인</h3>
				<div class="row">
					<h4>1. 프로젝트</h4>
					<p>상단 메뉴에서 프로젝트 버튼을 눌러 프로젝트 페이지로 넘어갑니다.</p>
					<img class="img-responsive mt-5"src="/resources/image/help/pro1.png">
					<p>프로젝트 생선 버튼을 클릭하여 새로운 프로젝트를 생성합니다.</p>
					<img class="img-responsive mt-5"src="/resources/image/help/proprocess1.png">
					<p>프로젝트 생성 페이지에서 양식에 맞추어 프로젝트 생성을 진행 합니다.</p>
					<p>멤버를 검색해 프로젝트 멤버로 초대 할수도 있습니다.</p>
					<p>프로젝트 서비스를 통해 여러사람이 함께모여 디자인 프로젝트를 진행 할 수 있습니다.</p>
					<img class="img-responsive mt-5"src="/resources/image/help/proprocess2.png">

					<p>프로젝트는 주제추가, 주제에 맞는 작품 추가를 통해 프로젝트를 진행해 나갑니다.</p>
					<img class="img-responsive mt-5"src="/resources/image/help/pro4.png">
					<p>또한 주제추가를 프로젝트 성격에 맞추어 주차별, 기능별, 프로젝트에 참여한 사람의 이름명으로 하여 자유롭게 프로젝트를 진행할 수 있습니다.</p>
					<img class="img-responsive mt-5"src="/resources/image/help/pro5.png">
					<img class="img-responsive"src="/resources/image/help/pro6.png">
					<img class="img-responsive"src="/resources/image/help/pro7.png">
					<h4 class="mt-5">2. 그룹</h4>
					<p>프로젝트 그룹 기능을 이용하여 학교나 기업체 같은 조직에서 프로젝트 팀들을 편리하게 관리할 수 있습니다.</p>
					<img class="img-responsive mt-5"src="/resources/image/help/grpro1.png">
					<img class="img-responsive"src="/resources/image/help/grpro2.png">
				</div>
			</div>
			<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
		</div>
	</div>
	


</body>
</html>