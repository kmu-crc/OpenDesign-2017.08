<%-- 화면ID : OD03-02-02 --%>
<%-- 화면ID : OD03-02-03 --%>
<%-- 화면ID : OD03-02-04 --%>
<%-- 화면ID : OD03-02-07 --%>
<%@page import="com.opendesign.vo.UserVO"%>
<%@page import="com.opendesign.vo.ProjectVO"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.ItemCmmtVO.ItemCmmtType"%>
<%@page import="com.opendesign.vo.ItemLikeVO.ItemType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

	boolean isProjectOwner = "true".equals( request.getParameter("isProjectOwner") );
	boolean isProjectMember = "true".equals( request.getParameter("isProjectMember") );
	boolean isProjNotComplete = "true".equals( request.getParameter("isProjNotComplete") );
	UserVO user = CmnUtil.getLoginUser(request);
	ProjectVO projectVO = (ProjectVO)request.getAttribute("projectVO"); //프로젝트 정보 

%>

<!-- 디자인 퍼가기 -->
<div class="modal" id="project-share">
</div>
<script id="tmpl-project-share" type="text/x-jsrender">
<div class="modal" id="project-share">
	<div class="bg"></div>
	<div class="modal-inner">
		<h1>퍼가기</h1>
		<form name="projShareForm">
			<input type="hidden" name="seq" value="{{:workSeq}}"/> <!-- workSeq -->
			<input type="hidden" name="fromVerSeq" value="{{:fromVerSeq}}"/> <!-- fromVerSeq -->
			<fieldset>
				<legend>주제 선택</legend>
				<div class="version">
					<div class="custom-select">
						<input type="text" value="선택하세요." >
						<select name="toSubjectSeq" >
							<option value="">선택하세요.</option>
							{{for subjectList}}
							<option value="{{:seq}}">{{:subjectName}}</option>
							{{/for}}
						</select>
					</div>
				</div>
				<button type="button" class="btn-add" onclick="projShareShare();">퍼가기</button>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times" aria-hidden="true"></i></button>
	</div>
</div>
</script>
<script>
/**
 * 퍼가기 view
 */
var flag_goWorkShareView = false; //flag
function goWorkShareView() {
	checkedLogin(function(){
		var myForm = $('#project-detail');
		var workSeq = myForm.find('[name="workSeq"]').val();
		var fromVerSeq = myForm.find('[name="curVerSeq"]').val();
		//
		console.log('workSeq=' + workSeq );
		console.log('fromVerSeq=' + fromVerSeq );
		
		if(flag_goWorkShareView) {
			return;
		}
		flag_goWorkShareView = true;
		
		$.ajax({
	        url: '/project/selectShareProjectSubjectList.ajax',
	        type: 'get',
	        data: {"seq":workSeq},
	        complete : function(_data){
				flag_goWorkShareView = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	//
		    	var subjectList = _data.subjectList;
		    	if(!subjectList || subjectList.length == 0) {
		    		alert("퍼갈 주제가 없습니다. 주제를 먼저 추가하세요.");
		    		return;
		    	}
		    	
		    	var jsonData = {
		    			workSeq: workSeq
		    			,fromVerSeq: fromVerSeq
		    			,subjectList: subjectList
		    	};
		    	//
		    	var htmlJ = $($.templates("#tmpl-project-share").render(jsonData));
		    	$('#project-share').replaceWith(htmlJ);
		    	
		    	
		    	modalShow("#project-share");
	        }
	    });
	}); //end of checkedLogin
}

/**
 * 퍼가기
 */
 var flag_projShareShare = false; //flag
function projShareShare() {
	// validation
	var myForm = $('#project-share').find('form');
	var toSubjectSeq = myForm.find('[name="toSubjectSeq"]');
	if(toSubjectSeq.val() == '') {
		alert('주제를 선택하세요.');
		return;
	}
	
	if(flag_projShareShare) {
		return;
	}
	flag_projShareShare = true;
	
	// 퍼가기
	$.ajax({
        url: '/project/shareProjectWork.ajax',
        type: 'get',
        data: myForm.serialize(),
        complete : function(_data){
			flag_projShareShare = false;
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

<!-- 디자인 상세 -->
<div class="modal" id="project-detail">
</div>
<script id="tmpl-project-detail" type="text/x-jsrender">

<div class="modal" id="project-detail">
	<div class="bg"></div>
	<div class="modal-inner">
		<div id="spinner"></div>
		<h1 data-nm="title">{{:title}} <span>{{:displayTime}}</span></h1>
		<p class="designer" data-nm="memberName">{{:workMemberNameList}}</p>
	
	 <div class="version" style="display: none">
			<strong data-nm="lastVer">ver {{:lastVer}}</strong>
			<div class="custom-select">
				<input type="text" value="이전 버전 보기" />
				<select name="" data-nm="verList" onchange="projDetailChangeVer(this);">
					<option value="">이전 버전 보기</option>
					{{for  projectWorkVerList }}
					<option value="{{:seq}}" data-docutype="{{:fileUriDocuType}}" data-fileuri="{{:fileUri}}" data-fileuril="{{:fileUriL}}" data-ftype="{{:fileUriImageType}}" data-filename="{{:filename}}" >ver {{:ver}}</option>
					{{/for}}
				</select>
			</div>
		</div> 

		<div style="min-height:30px;">
			<p class="opinion" data-nm="contents">{{:contents}}</p>
			<div class="btn-set">
				{{if <%=isProjNotComplete%> &&  curUserAuthYN }}
				<a href="javascript:goWorkModView();" class="btn-modal btn-modi" data-nm="btnMod"  >수정</a>
				<a href="javascript:projDetailDelWork();" class="btn-modal btn-del"  data-nm="btnDel"  >삭제</a>
				{{/if}}
				<input type="hidden" name="workSeq" value="{{:seq}}" /> <!-- workSeq -->
				<input type="hidden" name="dispName" value="{{:title}}" /><!-- dispName -->
				<input type="hidden" name="curVerSeq" value="{{:lastVerSeq}}" /><!-- 보는 버전 -->
			</div>
		</div>

		<div class="project-detail-img-wrap">
			<!-- 파일 -->
			{{include projectWorkVerList[0] tmpl="#tmpl-project-detail-sub-file" /}}
		</div>

		<div class="project-detail-info-wrap">
			<div class="img">
				<div>
					{{if !curUserLikedYN }}
					<button type="button" class="btn-like" onclick="projDetailWorkLike(this);">좋아요</button>
					{{else}}
					<button type="button" class="btn-like active" onclick="projDetailWorkLike(this);" >좋아요</button>
					{{/if}}

					{{if <%= isProjNotComplete && isProjectMember %> }}
					<button type="button" class="btn-share" onclick="goWorkShareView();" >퍼가기</button>
					{{/if}}
					{{if verFileUri && verFileUri !== 'null'}}
					<button type="button" class="btn-down" onclick="projDetailImgDown();"  >다운로드</button>
					{{/if}}
				</div>
			</div>

			<div class="reply-area">
				<form name="projDetailAddCmmtForm">
					<input type="hidden" name="itemSeq" value="{{:seq}}" /> <!-- itemSeq -->
					<input type="hidden" name="itemCmmtType" value="<%=ItemCmmtType.PROJECT_CMMT%>" /> <!-- 댓글 구분 -->
					<fieldset>
						<legend >댓글</legend>
						<textarea name="contents" maxlength="100" placeholder="댓글 입력(최대 100자)"></textarea>
						<button type="button" class="btn-cmmt btn-red" onclick="projDetailAddCmmt();">등록</button>
					</fieldset>
				</form>

				<ul class="reply-list" id="pdrListView" data-seq="{{:seq}}" >
				
				</ul>
				<a href="javascript:void(0);" onclick="pdrLoadMore();" id="pdrLoadMore" class="btn-more">댓글 더 보기</a>
			</div>
		</div>

		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>
</script>

<!-- 파일 -->
<script id="tmpl-project-detail-sub-file" type="text/x-jsrender">
			<div data-nm="fileComp" data-ftype="{{:fileUriImageType}}" data-docutype="{{:fileUriDocuType}}" >
				{{if fileUri == '' || fileUri == null}}
					<p>첨부된 파일이 없습니다</p>
				{{else}}
					<img style="width: 100%; height: auto" data-nm="verFileUri" src="{{:fileUriL}}" ori-src="{{:fileUri}}"
						{{if fileUriImageType}}
						{{else}}
						class="hide"
						{{/if}}
					/>

					{{if fileUriImageType}}
					<div class="display-file hide">
					{{else}}
					<div class="display-file " style="position: relative;">
					{{/if}}
						<img src="/resources/image/common/ico_attachment.png" alt="파일첨부" />&nbsp;
						<a class="display-file-link" id="embedFile" data-nm="verFileUri" ori-src="{{:fileUri}}" href="{{:fileUri}}" target="blank"> {{:filename}} </a>
					</div>
				{{/if}}
			</div>
</script>

<!-- ******************** 댓글 ******************** -->
<script id="tmpl-project-detail-reply" type="text/x-jsrender">
				<li>
					{{if memberType == "00" }}
					<span class="name">{{:memberName}}</span>
					{{else memberType == "01" }}
					<a onclick="javascript:producerPortfolio({{:memberSeq}})" style="cursor: pointer;"><span class="name">{{:memberName}}</span></a>
					{{else}}
					<a onclick="javascript:designerPortfolio({{:memberSeq}})" style="cursor: pointer;"><span class="name">{{:memberName}}</span></a>
					{{/if}}
					<p>
						{{:contents}}
					</p>
					{{if curUserAuthYN}}
						<button class="btn-cmmt-del"  onclick="projDetailDelCmmt('<%=ItemCmmtType.PROJECT_CMMT%>','{{:seq}}');" >x</button>
					{{else}}
						<div class="btn-cmmt-del" style="background:none;cursor:default;"></div>
					{{/if}}
					<span class="date">{{:displayTime}}</span>
						
				</li>
</script>
<form id="pdrListParamForm" name="pdrListParamForm" method="GET" action="" >
	<input type="hidden" name="schSeq" value="" /> 	<!-- itemSeq --> 
	<input type="hidden" name="schPage" value="1" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schLimitCount" value="10" /> <!-- 한 page 개수 -->
	<input type="hidden" name="schItemCmmtType" value="<%=ItemCmmtType.PROJECT_CMMT%>" /> <!-- 댓글 구분 -->
</form>
<script type="text/javascript">
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
	
	//validation
	projDetailAddCmmtFormValidRuleInit();
	
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
	
	var myForm = $('form[name="projDetailAddCmmtForm"]');
	
	checkedLogin(function(invokeAfterLogin){
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
	        	console.log('complete add comment ' + _data);
		    	if(_data.result == '1') {
		    		
		    		if( invokeAfterLogin ) { //로그인 후 처리되는 프로세스 라면
		    			//var workSeq = $(myForm).find('input[name=itemSeq]').val();
		    			//goWorkDetailView(workSeq);
		    			window.location.reload();
		    			return;
		    		}
		    		
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



//=== 검증 ==========

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
<script>

/**
 * 디자인 상세 view
 */
var flag_goWorkDetailView = false; //flag
function goWorkDetailView(workSeq) {
		
		if(flag_goWorkDetailView) {
			return;
		}
		flag_goWorkDetailView = true;
		
		//
		$.ajax({
	        url: '/project/selectProjectWorkDetail.ajax',
	        type: 'post',
	        data: {"seq":workSeq},
	        complete : function(_data){
	        	flag_goWorkDetailView = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	var workJson = _data.result;
		    	if(!workJson) {
		    		return;
		    	}
		    	console.log(workJson); 
		    	var htmlJ = $($.templates("#tmpl-project-detail").render(workJson));
		    	$('#project-detail').replaceWith(htmlJ);
		    	
		    	// 댓글 조회
		    	initPdrListView();
		    	pdrLoadPage();
		    	
		    	// 첨부 소스가 doc, docx, ppt, pptx일 경우 구글 docs viewer + pdf인 경우 자동 뷰어 붙이기
		    	//var viewTarget = $('a#embedFile');
		    	//var googleLocation = "https://docs.google.com/viewerng/viewer?url=http://opensrcdesign.kookmin.ac.kr";
		    	//var pdfLocation = workJson.verFileUri;
		    	//if (workJson.verFileUriDocuType){
		    		//viewTarget.attr('href', googleLocation+pdfLocation);
		    	//} else if (!workJson.verFileUriImageType){
		    		//viewTarget.attr('href', pdfLocation);
		    	//}
		    	
		    	modalShow('#project-detail');
		    	return;
	        }
	        
	    });
	
}


/**
 * 좋아요 
 */
 var flag_projDetailWorkLike = false; //flag
function projDetailWorkLike(thisObj) {
	checkedLogin(function(invokeAfterLogin){
		
		var myForm = $('#project-detail');
		var workSeq = myForm.find('[name="workSeq"]').val();
		
		if(flag_projDetailWorkLike) {
			return;
		}
		flag_projDetailWorkLike = true;
		
		$.ajax({
	        url: '/common/likeItemWork.ajax',
	        type: 'post',
	        data: {"itemSeq":workSeq, "itemType":"<%=ItemType.PROJECT_WORK%>"},
	        complete : function(_data){
	        	flag_projDetailWorkLike = false;
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
		    		
		    		// 좋아요 class 변경 
		    		if( $(thisObj).hasClass('active') ) {
		    			$(thisObj).removeClass('active')
		    		} else {
		    			$(thisObj).addClass('active')
		    		}
		    		//$(thisObj).removeAttr('onclick');
		    		loadPage();
		    		console.log('>>> like success');
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
		
	}); //end of checkedLogin
}

/**
 * 다운로드
 */
function projDetailImgDown() {
	var myForm = $('#project-detail');
	//var fileUrl = myForm.find('[data-nm="verFileUri"]').attr('ori-src');
	var myFileData = $('div[data-nm="fileComp"]');
	if (myFileData.attr('data-ftype') || myFileData.attr('data-docutype')){
		var fileUrl = myFileData.find('img[data-nm="verFileUri"]').attr('ori-src');
		console.log(fileUrl);
	} 
	//
	var dispName = myForm.find('[name="dispName"]').val(); 
	common.fileDownload(fileUrl, dispName); 
}

/**
 * 버전 change 
 */
function projDetailChangeVer(thisObj) {
	// == image change:
	var selOpt = $(thisObj).find('option:selected');
	var value = selOpt.val();
	if(!value) {
		return;
	}
	console.log(selOpt);
	
	//set value: 
	var htmlJ = $($.templates('#tmpl-project-detail-sub-file').render({
		fileUriImageType: selOpt.data('ftype')
		,fileUriDocuType: selOpt.data('docutype')
		,fileUri: selOpt.data('fileuri')
		,fileUriL: selOpt.data('fileuril')
		,filename: selOpt.data('filename')
	}));
	$('#project-detail').find('[data-nm="fileComp"]').replaceWith(htmlJ);
	
	// === curVerSeq
	var curVerSeq = $(thisObj).find('option:selected').val();
	if(curVerSeq != '') {
		$('#project-detail').find('[name="curVerSeq"]').val(curVerSeq);
	}
}

/**
 * 디자인 삭제
 */
var flag_projDetailDelWork = false; //flag
function projDetailDelWork() {
	checkedLogin(function(){
		var workSeq = $('#project-detail').find('[name="workSeq"]').val();  
		console.log('workSeq='+workSeq); 
		if(confirm('정말 삭제하시겠습니까?')) {
			
			if(flag_projDetailDelWork) {
				return;
			}
			flag_projDetailDelWork = true;
			
			//삭제: 
			$.ajax({
		        url: '/project/deleteProjectWork.ajax',
		        type: 'post',
		        data: {"seq":workSeq},
		        complete : function(_data){
		        	flag_projDetailDelWork = false;
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
	}); //end of checkedLogin
}


</script>

<!-- 디자인 수정 -->
<div class="modal" id="project-edit">
</div>
<script id="tmpl-project-edit" type="text/x-jsrender">
<div class="modal" id="project-edit">
	<div class="bg"></div>
	<div class="modal-inner">
		<form name="projEditForm" method="post" enctype="multipart/form-data" onsubmit="return false;" >
			<input type="hidden" name="seq" value="{{:seq}}" />
			<fieldset>
				<h1>디자인 수정</h1>
				<input type="text" name="title" maxlength="20" value="{{:title}}" placeholder="">
				<textarea name="contents" maxlength="200" placeholder="디자인 설명 (최대 200자)">{{:contents}}</textarea>
				
				<!-- 썸네일 이미지 -->
				<div style="height:60px; width: 600px;">
					<div class="file-url">
						<input type="text" readonly name="fileNameThumb" value="" placeholder="썸네일 이미지 (jpg, png만 등록 가능합니다.)" />
					</div>
					<div class="file">
						<input type="file" accept="image/x-png, image/jpeg" name="fileUriFileThumb" id="fileUriFileThumbMod"  onchange="projEditFormImgPreviewThumb(event);" >
						<button type="button">수정</button>
					</div>		
				</div>		
	
				<!--디자인 파일-->
				<div style="height:60px; width: 600px;">
					<div class="file-url">
						<input type="text" readonly name="fileName" value="" placeholder="첨부할 소스 업로드 (모든 파일 형식, 최대 100MB)" />
					</div>
				
					<div class="file">
						<input type="file" name="fileUriFile" onchange="projEditFormImgPreview(event);">
						<button type="button">수정</button>
					</div>
				</div>
				<div class="img">
					{{if verFileUriImageType}}
					<img id="img_projEditFormImgPreview" name="verFileUri" src="{{:verFileUriL}}">
					{{else}}
					<img class="hide" id="img_projEditFormImgPreview" name="verFileUri" src="{{:verFileUriL}}">
					{{/if}}
				</div>

				<!-- 작업자 추가 old -->
				<!-- <div class="person">
					<span>김정현</span>
					<input type="text">
					<button type="button">이름 또는 이메일 주소를 입력 후 Enter를 눌러주세요.</button>
				</div> -->
				
				<!-- 작업자 추가 new -->
				<div class="member-add" style="padding:0px;">
					<input type="text" data-nm="findName" name="schWord" placeholder="공동작업자(이름 또는 이메일 주소)를 입력 후 Enter를 눌러주세요" class="no-border">
				</div>
				<ul data-nm="ul_member_list" class="cate-list">
					<!-- <li>의상 <button type="button">x</button></li> -->
					{{for orderedProjectWorkMemberList}}
					<li>{{:memberName}}<button type="button" onclick="$(this).parent().remove();">x</button><input type="hidden" name="workMemberEmails" value="{{:memberEmail}}" /></li>
					{{/for}}
				</ul>
				<!-- //작업자 추가 new -->
				
				<button type="button" onclick="projEditFormSubmit();" class="btn-regi">수정 완료</button>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>
</script>
<script>
/**
 * 디자인 수정 view
 */
 var flag_goWorkModView = false; //flag
function goWorkModView() {
	checkedLogin(function(){
		var workSeq = $('#project-detail').find('[name="workSeq"]').val();  
		formValueClear('#project-edit');
		
		if(flag_goWorkModView) {
			return;
		}
		flag_goWorkModView = true;
		
		//
		$.ajax({
	        url: '/project/openUpdateProjectWork.ajax',
	        type: 'post',
	        data: {"seq":workSeq},
	        complete : function(_data){
	        	flag_goWorkModView = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
		    	var workJson = _data.result;
		    	if(!workJson) {
		    		return;
		    	}
		    	//
		    	var htmlJ = $($.templates("#tmpl-project-edit").render(workJson)); 
		    	$('#project-edit').replaceWith(htmlJ);
		    	projEditFormInitEvents();
		    	
		    	modalShow("#project-edit");
		    	
	        }
	    });
	}); //end of checkedLogin
}

/**
 * 이벤트
 */
function projEditFormInitEvents() {
	projEditFormInitEventsFindMember();
	//projEditFormValidRuleInit(); 
}
 
/**
 * 작업자 추가
 */
function projEditFormInitEventsFindMember() {
	 var myForm = $('#project-edit');
	 /* 자동 완성 */
	 myForm.find('[data-nm="findName"]').autocomplete({ 
	 	source : function( request, response ) {
	 		$.ajax({
	         	type: 'post',
	             url: "/project/findMember.ajax",
	             dataType: "json",
	             data: { schWord : request.term },
	             success: function(data) {
	             	var result = data.result;
	             	
	                 response( 
	                     $.map(result, function(item) {
	                         return {
	                             label: item.uname + '(' + item.email + ')',
	                             label2: item.uname,
	                             value: item.email
	                         }
	                     })
	                 );
	             }
	        });
	     },
	     focus: function( event, ui ) {
	    	event.preventDefault();
	    	myForm.find('[data-nm="findName"]').val( ui.item.label2 );
	 		return false;
	 	},
	     //조회를 위한 최소글자수
	     minLength: 2,
	     select: function( event, ui ) {
	    	event.preventDefault();
	    	myForm.find('[data-nm="findName"]').val( '' );
	     	var aCateli = $($.templates('<li>{{:label2}} <button type="button">x</button><input type="hidden" name="workMemberEmails" value="{{:value}}" /></li>').render(ui.item));
	 		aCateli.find('button').on('click', function(e){
	 			$(this).parent().remove();
	 		});
	     	
	 		myForm.find('[data-nm="ul_member_list"]').append(aCateli);	        	
	     	return false;
	     }
	 });
}


/**
 * 이미지 미리보기: 디자인:
 */
var projEditFormImgPreview = function(event){
	var reader = new FileReader();
	reader.onload = function(){
		var myForm = $('form[name="projEditForm"]');
		myForm.find('img[name="verFileUri"]').attr('src', reader.result);
	};
	reader.readAsDataURL(event.target.files[0]);
	
	// show hide image preview:
	var isImage = false;
	var fileType = event.target.files[0].type;
	if(fileType.startsWith('image')){
		isImage = true; 
	}
	console.log('isImage=' + isImage);
	if(isImage) {
		$('#img_projEditFormImgPreview').show();
	} else {
		$('#img_projEditFormImgPreview').hide();
	}
};

/**
 * 이미지 미리보기: 썸네일:
 */
var projEditFormImgPreviewThumb = function(event){
	var reader = new FileReader();
	reader.onload = function(){
		var myForm = $('form[name="projEditForm"]');
		myForm.find('img[name="fileUriFileThumb"]').attr('src', reader.result);
	};
	reader.readAsDataURL(event.target.files[0]);
};

/**
 * 디자인 수정
 */
var flag_projEditFormSubmit = false; //flag
function projEditFormSubmit() {
	// 0.validate
	var myForm = $('form[name="projEditForm"]');
	if(!myForm.valid()) {
		return;
	}
	
	if( !acceptFileSuffix( myForm.find('[name="fileUriFileThumb"]').val(), "jpeg, png, jpg") ) {
		alert("이미지는 jpg, png 파일만 등록이 가능합니다."); 
		return;
	}
	
	if(flag_projEditFormSubmit) {
		return;
	}
	flag_projEditFormSubmit = true;
	
	// xss warning 처리	
	var changed_contents = $('form[name="projEditForm"]').find('textarea[name="contents"]').val();
	$('form[name="projEditForm"]').find('textarea[name="contents"]').val(xssCheck(changed_contents));
	
	//== 1. submit
	myForm.ajaxSubmit({
		url : "/project/updateProjectWork.ajax",
		type : "post",
		dataType : 'json',
		complete : function(_data){
			flag_projEditFormSubmit = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.\n(4000픽셀 이상의 이미지는 등록하실 수 없습니다)");
		},
		success : function(_data) {
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

//=== 검증 ==========
function projEditFormValidRuleInit(){
	//== 
	//== 
	var myForm = $('form[name="projEditForm"]');
	myForm.validate({
		rules:{
			title : { required: true } 
			,fileUriFile : { required: true } 
		},
		messages: {
			title : { required: "제목을 입력하세요." } 
			, fileUriFile : { required: "첨부 파일을 등록하세요." }
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
</script>

<!-- 디자인 업로드 -->
<div class="modal" id="upload-modal">
</div>
<script id="tmpl-project-upload" type="text/x-jsrender">
<div class="modal" id="upload-modal">
	<div class="bg"></div>
	<div class="modal-inner" style="width:600px;height:540px;"> <!-- 수정  -->
		<h1>디자인 업로드</h1>
		<form name="NewUpForm" method="post" enctype="multipart/form-data" onsubmit="return false;" >
			<input type="hidden" name="projectSubjectSeq" value=""/> <!-- 주제seq -->
			<fieldset>
				<legend>디자인업로드</legend>
				<input type="text" name="title" maxlength="20" placeholder="제목 입력 (최대 20자)">
				<textarea name="contents" maxlength="200" placeholder="디자인 설명 (최대 200자)"></textarea>

				<!-- 썸네일 이미지 -->
				<div class="file-url" style="width:500px;">
					<input type="text" readonly placeholder="썸네일 이미지 (jpg, png만 등록 가능합니다.)">
					<!-- button type="btn-del" onclick="newUpFormFileClear();">x</button-->
				</div>
				<div class="file">
					<input type="file" accept="image/x-png, image/jpeg" name="fileUriFileThumb" id="fileUriFileThumb" >
					<button type="button" class="btn-red">검색</button>
				</div>			
	
				<!--디자인 파일-->
				<div class="file-url file-url-second" style="width:500px;">
					<input type="text" readonly placeholder="첨부할 소스 업로드 (모든 파일 형식, 최대 100MB)">
					<!-- button type="btn-del" onclick="newUpFormFileClear();">x</button-->
				</div>
				<div class="file">
					<input type="file" name="fileUriFile" id="fileUriFile" />
					<button type="button" class="btn-red">검색</button>
				</div>
				<div class="clear"></div>

				<!-- 작업자 추가 old -->
				<!-- <div class="person">
					<span>김정현</span>
					<button type="button">작업자 추가 +</button>
				</div> -->
				<!-- 작업자 추가 new -->
				<div class="member-add" style="padding:0px;">
					<input type="text" data-nm="findName" name="schWord" placeholder="공동작업자(이름 또는 이메일 주소)를 입력 후 Enter를 눌러주세요." class="no-border">
				</div>
				<ul data-nm="ul_member_list" class="cate-list">
					<% if(CmnUtil.isUserLogin(request)) { %>
					<li><%=user.getUname() %><input type="hidden" name="emails" value="<%=user.getEmail() %>" /></li>
					<% } %> 
				</ul>
				<!-- //작업자 추가 new -->

				<button type="button" class="btn-regi" onclick="newUpFormSubmit();">등록</button>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>
</script>
<script>
/**
 * 디자인 업로드 view
 */
function goWorkUploadView(subjectSeq) {
	checkedLogin(function(){
		// clear:
		//formValueClear('#upload-modal');
		//newUpFormFileClear();  //file clear
		//$('#upload-modal').find('[data-nm="ul_member_list"]').empty(); //작업자 clear 
		
		var htmlJ = $($.templates('#tmpl-project-upload').render());
		$('#upload-modal').replaceWith(htmlJ);
		
		$('#upload-modal').find('[name="projectSubjectSeq"]').val(subjectSeq);
		modalShow("#upload-modal");
		newUpFormInitEvents();
	}); //end of checkedLogin
}

/**
 * 작업자 추가
 */
function newUpFormInitEventsFindMember(){ 
	
	 var myForm = $('#upload-modal');
	 /* 자동 완성 */
	 myForm.find('[data-nm="findName"]').autocomplete({ 
	 	source : function( request, response ) {
	 		$.ajax({
	         	type: 'post',
	             url: "/project/findMember.ajax",
	             dataType: "json",
	             data: { schWord : request.term },
	             success: function(data) {
	             	var result = data.result;
	             	
	                 response( 
	                     $.map(result, function(item) {
	                         return {
	                             label: item.uname + '(' + item.email + ')',
	                             label2: item.uname,
	                             value: item.email
	                         }
	                     })
	                 );
	             }
	        });
	     },
	     focus: function( event, ui ) {
	    	event.preventDefault();
	    	myForm.find('[data-nm="findName"]').val( ui.item.label2 );
	 		return false;
	 	},
	     //조회를 위한 최소글자수
	     minLength: 2,
	     select: function( event, ui ) {
	    	event.preventDefault();
	    	myForm.find('[data-nm="findName"]').val( '' );
	    	var aCateli = $($.templates('<li>{{:label2}} <button type="button">x</button><input type="hidden" name="workMemberEmails" value="{{:value}}" /></li>').render(ui.item));
	 		aCateli.find('button').on('click', function(e){
	 			$(this).parent().remove();
	 		});
	     	
	 		myForm.find('[data-nm="ul_member_list"]').append(aCateli);	        	
	     	return false;
	     }
	 });
 }
</script>

<script>
/**
 * clear
 */
function newUpFormFileClear() {
	$('#upload-modal').find('div.file-url input[type="text"]').val('');
	$('#upload-modal').find('div.file input[type="file"]').val('');
}
/** 디자인 업로드 */
var flag_newUpFormSubmit = false; //flag
function newUpFormSubmit() {
	
	var myForm = $('form[name="NewUpForm"]');
	if(!myForm.valid()) {
		return;
	}
	
	if( !acceptFileSuffix( myForm.find('[name="fileUriFileThumb"]').val(), "jpeg, png, jpg") ) {
		alert("이미지는 jpg, png 파일만 등록이 가능합니다."); 
		return;
	}

	if(flag_newUpFormSubmit) {
		return;
	}
	flag_newUpFormSubmit = true;
	
	// xss warning 처리	
	var changed_contents = $('form[name="NewUpForm"]').find('textarea[name="contents"]').val();
	$('form[name="NewUpForm"]').find('textarea[name="contents"]').val(xssCheck(changed_contents));
	
	myForm.ajaxSubmit({
		url : "/project/insertProjectWork.ajax",
		type : "post",
		dataType : 'json',
		complete : function(_data){
			flag_newUpFormSubmit = false;
		},
		error: function(request, status, error) {
			console.log(request);
			console.log(status);
			console.log(error);
			alert("업로드에 실패하였습니다.\n(4000픽셀 이상의 이미지는 등록하실 수 없습니다)");
		},
		success : function(_data) {
			console.log(_data);
			if(_data.result == '1') {
				//성공
	    		alert("디자인 업로드를 완료하였습니다."); 
	    		modalHide();
	    		loadPage(); 
	    	} else {
	    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	    	}
		}
	});
	
}

function newUpFormInitEvents() {
	newUpFormInitEventsFindMember();
	newUpFormValidRuleInit(); 
}

//=== 검증 ==========

function newUpFormValidRuleInit(){
	
	var myForm = $('form[name="NewUpForm"]');
	myForm.validate({
		rules:{
			title : { required: true}
			, fileUriFile : { required: true }
		},
		messages: {
			title : { required: "제목을 입력하세요." } 
			, fileUriFile : { required: "첨부 파일을 등록하세요." }
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
</script>

<!-- 주제 추가 -->
<div class="modal" id="new-topic">
	<div class="bg"></div>
	<div class="modal-inner">
		<h1>새 주제 추가</h1>
		<form name="NewTopicForm">
			<input type="hidden" name="projectSeq" value=""/>
			<fieldset>
				<legend>주제 추가</legend>
				<input type="text" name="subjectName" maxlength="14" placeholder="주제 입력 (최대 14자)" onKeyDown="return checkEnter()" />
				<input type="text" style="display: none;" />
				<button type="button" class="btn-add" onclick="newTopicFormSubmit();">추가</button>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>

<!-- 주제 수정 -->
<div class="modal" id="modify-topic">
	<div class="bg"></div>
	<div class="modal-inner">
		<h1>주제 라인 수정</h1>
		<form name="ModifyTopicForm">
			<input type="hidden" name="projectSeq" value=""/>
			<input type="hidden" name="subjectSeq" value=""/>
			<fieldset>
				<legend>주제 라인 수정</legend>
				<input type="text" name="subjectName" maxlength="14" placeholder="주제 입력 (최대 14자)" onKeyDown="return checkEnter()" />
				<input type="text" style="display: none;" />
				<button type="button" class="btn-add" onclick="modifyTopicFormSubmit();">수정</button>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>

<script>
/**
 * 주제 추가
 */
function goSubjectAddView() {
	checkedLogin(function(){
	 	modalShow('#new-topic');
	 	
	}); //end of checkedLogin
}

/**  
 * 새 주제 추가
 */
 
var flag_newTopicFormSubmit = false; //flag
function newTopicFormSubmit() {
	//
	var myForm = $('form[name="NewTopicForm"]');
	myForm.find('[name="projectSeq"]').val(projectSeq); 
	console.log('>>> projectSeq=' + projectSeq);
	
	//
	var subjName = myForm.find('[name="subjectName"]');
	if(subjName.val() == "") {
		alert("주제를 입력하세요.");
		subjName.focus();
		return;
	}

	if(flag_newTopicFormSubmit) {
		return;
	}
	flag_newTopicFormSubmit = true;
	
	$.ajax({
        url: '/project/insertProjectSubject.ajax',
        type: 'post',
        data: myForm.serialize(),
        complete : function(_data){
        	flag_newTopicFormSubmit = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '1') {
        		//성공
        		//alert("새주제 추가 완료하였습니다.");
        		modalHide();
        		subjName.val('');
        		loadPage();
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
	
}

function checkEnter(){
	if (event.keyCode == 13){
		if ($('#new-topic').css('display') == 'block'){
			newTopicFormSubmit();
		} else if ($('#modify-topic').css('display') == 'block'){
			modifyTopicFormSubmit();
		}
	} else {
		return;
	}
}



/**
 * 주제 수정
 */
function goSubjectModifyView(subjectSeq) {
	
	checkedLogin(function(){
		var myForm = $('form[name="ModifyTopicForm"]');
		myForm.find('[name="projectSeq"]').val(projectSeq);
		myForm.find('[name="subjectSeq"]').val(subjectSeq);
		myForm.find('[name="subjectName"]').val( $('#subjectTitle_' + subjectSeq).val() );
		
	 	modalShow('#modify-topic');
	 	
	}); //end of checkedLogin
}

/**  
 * 주제 라인 수정
 */
var flag_modifyTopicFormSubmit = false; //flag
function modifyTopicFormSubmit() {
	//
	var myForm = $('form[name="ModifyTopicForm"]');
	
	var subjName = myForm.find('[name="subjectName"]');
	
	if(subjName.val() == "") {
		alert("주제를 입력하세요.");
		subjName.focus();
		return;
	}

	if(flag_modifyTopicFormSubmit) {
		return;
	}
	flag_modifyTopicFormSubmit = true;
	
	$.ajax({
        url: '/project/updateProjectSubject.ajax',
        type: 'post',
        data: myForm.serialize(),
        complete : function(_data){
        	flag_modifyTopicFormSubmit = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '1') {
        		//성공
        		//alert("새주제 추가 완료하였습니다.");
        		modalHide();
        		subjName.val('');
        		loadPage();
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
	
}


</script>



<!-- 프로젝트 전체에 대한 댓글 -->

<div class="modal" id="modal_project_totalReply">
	<div class="bg"></div>
	<div class="modal-inner">
		<div class="contents">
			<h2 class="totalReplyHead">프로젝트 댓글</h2>
			<ul class="project_totalReplyList" id="project_totalReply" data-seq="<%=projectVO.getSeq()%>"></ul>
			<form name="project_totalReplyForm">
				<input type="hidden" name="itemSeq" value="<%=projectVO.getSeq()%>" /> <!-- itemSeq -->
				<input type="hidden" name="itemCmmtType" value="<%=ItemCmmtType.PROJECT_TOTAL_CMMT%>" /> <!-- 댓글 구분 -->
				<fieldset>
					<legend>댓글</legend>
					<textarea name="contents" maxlength="500" placeholder="댓글 입력(최대 500자)"></textarea>
					<button type="button" class="btn-cmmt btn-red" onclick="projTotalAddCmmt();">등록</button>
				</fieldset>
			</form>
		</div>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>


<script id="tmpl-project-total-reply" type="text/x-jsrender">
	<li>
		<div>
			<dl style="width:100%; position: relative">
				{{if curUserAuthYN}}
					<div class="btn-set">
						<button class="btn-cmmt-del" onclick="projTotalDelCmmt('<%=ItemCmmtType.PROJECT_TOTAL_CMMT%>','{{:seq}}');" >x</button>
					</div>
				{{/if}}
				<dt>{{:memberName}} <span class="date">{{:displayTime}}</span></dt>
				<dd style="word-wrap:break-word;">
					{{:contents}}
				</dd>
			</dl>
			<div class="clear"></div>
		</div>
	</li>
</script>

<form id="pdrListTotalParamForm" name="pdrListTotalParamForm" method="GET" action="" >
	<input type="hidden" name="schSeq" value="" /> 	<!-- itemSeq --> 
	<input type="hidden" name="schPage" value="1" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schLimitCount" value="10" /> <!-- 한 page 개수 -->
	<input type="hidden" name="schItemCmmtType" value="<%=ItemCmmtType.PROJECT_TOTAL_CMMT%>" /> <!-- 댓글 구분 -->
</form>


<script type="text/javascript">

/* list 탬플릿 */
var pdrListTotalTemplete = $("#tmpl-project-total-reply").html();

/* project_totalReply */
var project_totalReply = null;

/* 초기화 */
function initPdrListTotalView(){
	// param set:
	var myForm = $('form[name="pdrListTotalParamForm"]');
	var itemSeq = $('#project_totalReply').data('seq'); 
	myForm.find('[name="schSeq"]').val(itemSeq);
	myForm.find('[name="schPage"]').val('1');
	
	//
	project_totalReply = new ListView({
		htmlElement : $('#project_totalReply')
	});
	
	// clear
	project_totalReply.clear();
	pdrLoadTotalPage();
	
}

/**
 * 페이지 load
 */
var flag_pdrLoadTotalPage = false; //flag
function pdrLoadTotalPage() {
	
	if(flag_pdrLoadTotalPage) {
		return;
	}
	flag_pdrLoadTotalPage = true;
	
	var myForm = $('form[name="pdrListTotalParamForm"]');
	// 데이터 조회 및 load
	$.ajax({
        url: '/common/selectItemCmmtPagingList.ajax',
        type: 'get',
        data: myForm.serialize(),
		complete : function(_data){
			flag_pdrLoadTotalPage = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	pdrLoadTotalPageWithData(_data);
		}
    });
}


/**
 *  pdrLoadPageWithData
 */
function pdrLoadTotalPageWithData(_data) {
	var allCount = _data.all_count;
	console.log('>>> allCount=' + allCount);
	var myForm = $('form[name="pdrListTotalParamForm"]');
	var pageNo = myForm.find('[name="schPage"]').val();
	
	//
	var listData = _data.list;
	var listCount = listData.length;
	var existList = listCount > 0; 
	
	if( ! existList ){	
		console.log('>>> loadPageWithData no data.');
		return;
	}
	
	project_totalReply.putData('existList', existList);
	
	// data
	project_totalReply.addAll({
		keyName:'seq',
		data:listData,
		htmlTemplate: pdrListTotalTemplete
	});
}


/**
 * 댓글 등록
 */
var flag_projTotalAddCmmt = false; //flag
function projTotalAddCmmt() {
	
	//initPdrListTotalView();
	projTotalAddCmmtFormValidRuleInit();
	
	checkedLogin(function(invokeAfterLogin){
		var myForm = $('form[name="project_totalReplyForm"]');
		if(!myForm.valid()) {
			return;
		}
		
		if(flag_projTotalAddCmmt) {
			return;
		}
		flag_projTotalAddCmmt = true;
		
		//
		$.ajax({
	        url: '/common/insertItemCmmt.ajax',
	        type: 'post',
	        data: myForm.serialize(),
	        complete : function(_data){
	        	flag_projTotalAddCmmt = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
		    	if(_data.result == '1') {
		    		
		    		if( invokeAfterLogin ) { //로그인 후 처리되는 프로세스 라면
		    			window.location.reload();
		    			return;
		    		}
		    		
					initPdrListTotalView();
		    		// clear
		    		formValueClear('form[name="project_totalReplyForm"]');
		    		
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
		
	}); //end of checkedLogin
}


//=== 검증 ==========

function projTotalAddCmmtFormValidRuleInit(){
	var myForm = $('form[name="project_totalReplyForm"]');
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


/**
 * 댓글 삭제
 */
var flag_projTotalDelCmmt = false; //flag 
function projTotalDelCmmt(itemCmmtType, seq) {
	checkedLogin(function(invokeAfterLogin){
		
		if(!confirm('댓글 삭제하시겠습니까?')) { 
			return;
		}
		
		
		if(flag_projTotalDelCmmt) {
			return;
		}
		flag_projTotalDelCmmt = true;
		
		//
		$.ajax({
	        url: '/common/deleteItemCmmt.ajax',
	        type: 'post',
	        data: {itemCmmtType: itemCmmtType,seq: seq},
	        complete : function(_data){
	        	flag_projTotalDelCmmt = false;
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
					initPdrListTotalView();
					pdrLoadTotalPage();
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
	        }
	    });
		
	}); //end of checkedLogin
} 

</script>

