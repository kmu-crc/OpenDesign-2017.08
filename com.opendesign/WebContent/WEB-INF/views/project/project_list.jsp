<%-- 화면ID : OD03-02-01 --%>
<%@page import="com.opendesign.utils.CmnConst"%>
<%@page import="com.opendesign.vo.UserVO"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%@page import="com.opendesign.vo.ProjectVO"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String projectSeq = (String)request.getAttribute("projectSeq"); //프로젝트seq
	ProjectVO projectVO = (ProjectVO)request.getAttribute("projectVO"); //프로젝트 정보 
	UserVO user = CmnUtil.getLoginUser(request);
	// 완료된 프로젝트 flag:
	boolean isProjNotComplete = !CmnConst.ProjectProgressStatus.COMPLETE.equals(projectVO.getProgressStatus());
	System.out.println(">>> isProjNotComplete=" + isProjNotComplete);
	// 좋아요 기능 flag
	Boolean liked = (Boolean)request.getAttribute("liked");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- 이름 검색 -->
<link rel="stylesheet" type="text/css" href="/resources/js/lib/jquery-ui.min.css" />
<script src="/resources/js/lib/jquery-ui.min.js" ></script>
<!-- //이름 검색 -->
<style type="text/css">
/** 
 * 퍼가기 팝업: id="project-share"  (copy from project-detail,new-topic and customize) 
 */
#project-share .modal-inner{width:430px; height:180px; box-sizing:border-box; margin:-90px 0 0 -215px; padding:25px 15px; background:#f4f4f4;}
#project-share h1{padding:0 0 5px; font-size:30px; font-weight:700; color:#113b88;}
#project-share h1 span{font-size:12px; font-weight:bold; color:#686868;}
#project-share .designer{margin:0 0 5px; font-size:18px; font-weight:bold;}
#project-share .custom-select{width:150px;}
#project-share select{position:absolute; top:0; left:0; width:100%; height:32px; opacity:0;}
#project-share input[type="text"]{width:100%; height:32px; box-sizing:border-box; padding:0 40px 0 10px; color:#848484; border:1px solid #848484; border-radius:7px; background:#fff url(/image/common/bg_select.png) no-repeat right 0;}
#project-share .version{overflow:hidden; margin:0 0 10px; font-size:14px;}
#project-share .version strong{float:left; margin:8px 15px 0 0;}
#project-share .opinion{float:left; margin: 0 0 15px; font-size:12px;}
#project-share .btn-add{float:none; width:65px; height:28px; font-size:15px; line-height:28px; color:#fff; border-radius:5px; background:#b5b5b5;}
</style>
<script id="tmpl-subjectTemplate" type="text/x-jsrender">
								<div class="topic swiper-slide">
									<div class="btn-set">
										{{if <%= isProjNotComplete && projectVO.getIsProjectMember()%>}}
										<a href="javascript:goWorkUploadView('{{:seq}}')" class="btn-modal">업로드</a>
										<a href="javascript:goSubjectModifyView('{{:seq}}');" class="btn-modal">수정</a>
										<a href="javascript:delProjectSubject('{{:seq}}')" >삭제</a>
										{{/if}}
									</div>
									<h3>{{:subjectName}}</h3>
									<input type="hidden" id="subjectTitle_{{:seq}}" value="{{:subjectName}}" /> 
									<ul>
									</ul>
								</div>
</script>
<script id="tmpl-workTemplate" type="text/x-jsrender">
										<li><a href="javascript:goWorkDetailView('{{:seq}}')" class="btn-modal">
											<div class="pic">
												<!-- <img src="{{:verFileUriS}}" alt=""> --> 
												<img src="{{:thumbUriS}}" onerror="setDefaultImg(this, 5);" alt="">
											</div>
											<dl>
												<dt>{{:title}}</dt>
												<dd>{{:contents}}</dd>
											</dl>
											<div class="item-info">
												<span class="name">{{:memberName}}</span>
												<div>
													<span><i class="fa fa-commenting" aria-hidden="true"></i> {{:commentCntF}}</span>
													{{if !curUserLikedYN }}
													<span class="hit"><i class="fa fa-heart-o" aria-hidden="true"></i> {{:likeCntF}}</span>
													{{else}}
													<span class="hit"><i class="fa fa-heart" aria-hidden="true"></i> {{:likeCntF}}</span>
													{{/if}}
												</div>
											</div>
										</a></li>
</script>
<script>
	var projectSeq = '<%=projectSeq%>'; //프로젝트seq
	
	// subject 탬플릿
	var subjectTemplate =  $("#tmpl-subjectTemplate").html();
		
	// work 탬플릿
	var workTemplate = $("#tmpl-workTemplate").html();

/**
 * 초기화
 */
$(function(){
	
	//뷰 컨트롤러 생성	
	new ProjectView({
		id:"projectView",
		htmlElement : $("#subjectArea")	
	});
	
	// swipe init
	swipeInit();
	
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
        url: '/project/selectProjectDetail.ajax',
        type: 'get',
        data: { 'projectSeq' : '<%=projectSeq%>' },
		complete : function(_data){
			flag_loadPage = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	var subjectDatas = _data.subjectList;
	    	// load
	    	loadPageWithData(subjectDatas);
		}
    });
}

/**
 * 주제,디자인 load
 */
function loadPageWithData(subjectDatas) {
	projectView.clear();
	if(!subjectDatas || subjectDatas.length == 0) {
		console.log('>>> loadPageWithData no data.');
		return;
	}
	// 주제:
	projectView.addSubjects({
		keyName : "seq",
		data : subjectDatas,
		htmlTemplate : subjectTemplate,		
	});
	// 디자인:
	for(var i=0; i< subjectDatas.length; i++) {
		var workDatas = subjectDatas[i].projectWorkList;
		if(!workDatas || workDatas.length == 0) {
			continue;
		}
		projectView.addWorks({
			parentKeyName : "projectSubjectSeq",
			keyName : "seq",
			data : workDatas,
			htmlTemplate : workTemplate,		
		});
	}
	
	// swipe init 
	swipeInit();
	
}

/**
 * 주제 삭제
 */
var flag_delProjectSubject = false; //flag
function delProjectSubject(seq) {
	checkedLogin(function(invokeAfterLogin){
		
		if(flag_delProjectSubject) {
			return;
		}
		flag_delProjectSubject = true;
		//삭제 체크: 
		$.ajax({
	        url: '/project/checkDeleteProjectSubject.ajax',
	        type: 'get',
	        data: {"seq":seq},
	        complete : function(_data){
	        	flag_delProjectSubject = false;
			},
	        error : function(_data) {
	        	console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	if(_data.result == '1') {
		    		//성공
		    		if(confirm('정말 삭제하시겠습니까?')) {
		    			//실제삭제:
		    			doDelProjectSubject(seq);
		    		}
		    	} else if(_data.result == '0') {
		    		alert('주제 라인에 작픔이 하나 이상 남아 있을 경우 삭제할 수 없습니다.');
		    		if( invokeAfterLogin ) {
		    			window.location.reload();
		    		}
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
	}); //end of checkedLogin
}


/**
 * 주제 실제 삭제
 */
var flag_doDelProjectSubject = false; //flag
function doDelProjectSubject(seq) {
	 
	if(flag_doDelProjectSubject) {
		return;
	}
	flag_doDelProjectSubject = true;
	
    //삭제: 
	$.ajax({
        url: '/project/deleteProjectSubject.ajax',
        type: 'post',
        data: {"seq":seq},
        complete : function(_data){
        	flag_doDelProjectSubject = false;
		},
        error : function(_data) {
        	console.log(_data);
        	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '1') {
        		modalHide();
        		loadPage();
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
}


/**
 * 주제 수정
 */
var flag_doModifyProjectSubject = false; //flag
function doModifyProjectSubject(seq) {
	 
	if(flag_doModifyProjectSubject) {
		return;
	}
	flag_doModifyProjectSubject = true;
	
    //삭제: 
	$.ajax({
        url: '/project/modifyProjectSubject.ajax',
        type: 'post',
        data: {"seq":seq},
        complete : function(_data){
        	flag_doModifyProjectSubject = false;
		},
        error : function(_data) {
        	console.log(_data);
        	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '1') {
        		modalHide();
        		loadPage();
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
}

</script>


<script>
/**
* swipe init
*/
var topicSwipe;

function swipeInit() {
	var item = $('.topic-wrap').find('.topic').length;
	
	if(item > 6){
		
		if( topicSwipe == null ) {
			
			$('.topic-container').find('button').show();	
			
			topicSwipe = new Swiper('.topic-slide', {
		        slidesPerView: 6,
		        spaceBetween: 7,
		        nextButton: '.btn-topicNext',
		    	prevButton: '.btn-topicPrev'
		    });
			
		} else {
			topicSwipe.onResize()
		}		
		
	} else{
		$('.topic-container').find('button').hide();
	}
}
</script>


<script>
/**
 * 좋아요 기능
 */
var flag_prodViewWorkLike = false; 
function prodViewWorkLike(thisObj) {
    checkedLogin(function(invokeAfterLogin){
    	var seq = <%=request.getParameter("projectSeq")%>
        if(flag_prodViewWorkLike) {
            return;
        }
        flag_prodViewWorkLike = true;

        $.ajax({
            url: '/common/likeProject.ajax',
            type: 'post',
            data: {"projectSeq":seq},
            complete : function(_data){
                flag_prodViewWorkLike = false;
            },
            error : function(_data) {
                console.log(_data);
                alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
            },
            success : function(_data){
                console.log(_data);
                window.location.reload();
            }
        });
    }); //end of checkedLogin   
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
	<div class="list-content">
		<div class="inner">
			<h2 class="title">
			<%if( StringUtil.isNotEmpty(projectVO.getCategories() ) ) {%>
				[<%=projectVO.getCategories() %>] 
			<%}%>
			
			<%=projectVO.getProjectName()%></h2>
			<div class="btn-area">
				<button type="button" class="btn-red" onclick="javascript:modalShow('#modal_project_totalReply');" >프로젝트 댓글</button>
				<%if( isProjNotComplete && projectVO.getIsProjectMember() ) { %>
				<a href="javascript:goSubjectAddView();" class="btn-modal btn-red project-add-btn">새 주제 추가</a>
				<%} %>
				
				<% if( !liked ) { %>
					<button type="button" class="project-like-btn btn-red" onclick="prodViewWorkLike(this);">좋아요</button>
					<% } else { %>
					<button type="button" class="project-like-btn btn-red active" onclick="prodViewWorkLike(this);">좋아요</button>
					<% } %>
			</div>

			<div class="topic-container">
				<button type="button" class="btn-topicPrev"><img src="/resources/image/sub/btn_projectPrev.png" alt="이전"></button>
				<div class="topic-slide">
					<div id="subjectArea" class="topic-wrap swiper-wrapper">
						
						<%-- 
						<!-- template -->
						<div class="topic swiper-slide">
							<div class="btn-set">
								<a href="#upload-modal" class="btn-modal">업로드</a>
								<a href="#">삭제</a>
							</div>
							<h3><span>주제</span> 포스터 기획</h3>
							<ul>
								<li><a href="#project-detail" class="btn-modal">
									<div class="pic">
										<img src="/resources/image/main/pic_sample1.jpg" alt="">
									</div>
									<dl>
										<dt>디자인 시안1</dt>
										<dd>한번 확인해보세요</dd>
									</dl>
									<div class="item-info">
										<span class="name">김은희</span>
										<div>
											<span><img src="/resources/image/sub/blt_msg.png" alt="메시지"> 181</span>
											<span><img src="/resources/image/sub/blt_likeGray.png" alt="좋아요"> 0</span>
										</div>
									</div>
								</a></li>
							</ul>
						</div>
						<!-- //template -->
						--%>

						
					</div>
				</div>
				<button type="button" class="btn-topicNext"><img src="/resources/image/sub/btn_projectNext.png" alt="다음"></button>
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
<jsp:include page="/WEB-INF/views/common/modal_project_list.jsp" flush="false">
	<jsp:param name="isProjectOwner" value="<%=projectVO.getIsProjectOwner()%>" />
	<jsp:param name="isProjectMember" value="<%=projectVO.getIsProjectMember()%>" />
	<jsp:param name="isProjNotComplete" value="<%=isProjNotComplete%>" />
</jsp:include>
<!-- //modal -->

</body>
</html>