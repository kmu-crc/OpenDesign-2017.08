<%-- 화면ID : OD03-01-03 --%>
<%@page import="com.opendesign.utils.CmnConst.ProjectProgressStatus"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.UserVO"%>
<%@page import="com.opendesign.utils.CmnConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.vo.CategoryVO"%>
<%@page import="com.opendesign.vo.ProjectVO"%><%
	List<ProjectVO> projectList = (List<ProjectVO>)request.getAttribute("projectList");
	List<CategoryVO> cateList = (List<CategoryVO>)request.getAttribute("cateList");
	UserVO user = CmnUtil.getLoginUser(request);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/resources/js/lib/jquery-ui.min.css" />
<script src="/resources/js/lib/jquery-ui.min.js" ></script>
</head>
<body>
<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->

	<!-- content -->
	<div class="content regi-content">
		<div class="inner">
			<div class="best-head">
				<span class="mainChar">P</span>
				<span>프로젝트 관리</span>
			</div>
			<form name="projectForm" id="projectForm" enctype="multipart/form-data" onsubmit="return false;" >
				<input type="hidden" name="seq" id="projectSeq" />
			<table class="tbl-regi">
				<colgroup>
					<col style="width:220px">
					<col style="">
					<col style="width:220px">
					<col style="">
				</colgroup>
				<tr>
					<th scope="row"></th>
					<td colspan="3">
						<div id="div_select_project" class="my-select custom-select">
							<input id="txt_select_project" type="text"  >
							<select id="select_project" name="select_project">
								<option value="">내가 생성한 프로젝트</option>
							<%if( projectList != null ){ %>
							<%	for( ProjectVO aProject : projectList){ %>
								<option value="<%=aProject.getSeq() %>"><%=aProject.getProjectName() %></option>
							<%
								}
							}
							%>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" class="km-required">프로젝트명</th>
					<td colspan="3"><input type="text" name="projectName" class="no-border " maxlength="50" placeholder="최대 50자 이내 입력"></td>
				</tr>
				<tr>
					<th scope="row" class="km-required">공개 여부</th>
					<td colspan="3">
						<div class="checkbox">
							<input type="checkbox" name="publicYn" value="N" id="close">
							<label for="close">비공개(팀원들만 프로젝트 접근이 가능합니다)</label>
						</div>
						<div class="checkbox">
							<input type="checkbox" name="publicYn" value="Y" id="open">
							<label for="open">읽기만 공개(모든 이용자가 프로젝트 내용을 볼 수 있지만, 업로드는 허용하지 않습니다)</label>
						</div>
						<div class="checkbox">
							<input type="checkbox" name="publicYn" value="A" id="openAll">
							<label for="openAll">공개(모든 이용자가 프로젝트 내용을 읽고 업로드할 수 있습니다)</label> 
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" class="km-required">카테고리</th>
					<td colspan="3" id="cateWrapProjMana">
						<div class="select-area custom-select" id="firstCateDiv">
							<input type="text">
							<select id="cateDepth1" name="cateDepth1">
								<option value="">전체</option>
							<%-- <%for(CategoryVO aCate  : cateList){%>
								<option value="<%=aCate.getCategoryCode() %>"><%=aCate.getCategoryName() %></option>
							<%} %> --%>
							</select>
						</div>
						<div class="select-area custom-select ">
							<input type="text">
							<select name="cateDepth2">
								<option value="">전체</option>
							</select>
						</div>
						<div class="select-area custom-select " style="opacity: 0;">
							<input type="text">
							<select  name="cateDepth3">
								<option value="">전체</option>
							</select>
						</div>
						<button type="button" class="btn-add "><img src="../resources/image/common/btn_addBig.png" alt="더하기"></button>
						<ul id="ul_cate_list" class="cate-list">
						<%--
							<li>의상 <button type="button">x</button></li>
						--%>
						</ul>
					</td>
				</tr>
				<tr>
					<th scope="row">썸네일 등록</th>
					<td colspan="3">
						<div class="file-url ">
							<input type="text" id="fileName" readonly name="fileName" placeholder="jpg,png만 등록 가능합니다.">
							<!-- button type="btn-del">x</button-->
						</div>
						<div class="file ">
							<input type="file" name="fileUrlFile" accept="image/x-png, image/jpeg, image/jpg">
							<button type="button">검색</button>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">멤버 추가</th>
					<td colspan="3">
						<div class="member-add ">
							<input type="text" id="findName" name="schWord" placeholder="이름 검색" class="no-border">
							<!-- button type="button">검색</button -->
						</div>
						<ul id="ul_member_list" class="cate-list">
						<%--
							<li>의상 <button type="button">x</button></li>
						--%>
						</ul>
					</td>
				</tr>
				<tr>
					<th scope="row">그룹 추가</th>
					<td colspan="3">
						<div class="member-add ">
							<input type="text" id="findGroup" name="schWord" placeholder="그룹 검색" class="no-border">
							<!-- button type="button">검색</button -->
						</div>
						<ul id="ul_group_list" class="cate-list">
						<%--
							<li>의상 <button type="button">x</button></li>
						--%>
						</ul>
					</td>
				</tr>
				<tr>
					<th scope="row" class="km-required">완료 여부</th>
					<td id="td_status" colspan="3">
						<div class="checkbox ">
							<input type="checkbox" name="progressStatus" value="<%=CmnConst.ProjectProgressStatus.PROGRESS %>" id="progress">
							<label for="progress">진행</label>
						</div>
						<div class="checkbox ">
							<input type="checkbox" name="progressStatus" value="<%=CmnConst.ProjectProgressStatus.COMPLETE %>" id="complete">
							<label for="complete">완료</label>
						</div>
					</td>
				</tr>
			</table>
			<button id="btn_save" type="button" class="btn-complete ">수정완료</button>
			<button id="btn_delete" type="button" class="btn-complete " style="margin:0 5px 0 5px;">삭제</button>
			</form>
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

<!-- ################ 카테고리 ################### -->
<script >
	var categoryProjMg = null;
	function refreshCategoryProjMg(selCateCode) {
		if(categoryProjMg == null) {
			categoryProjMg = new CategoryView({
				htmlContainer: $('#cateWrapProjMana')
				,hiddenFieldName: 'categoryCodes'
				,excludeData: { }
				,selCateCode: selCateCode
			}).render();
		} else {
			categoryProjMg.setSelectedCateCode(selCateCode);
		}
	}
	
	$(function(){
		refreshCategoryProjMg('');
	});
</script>
<!-- ################ ]]카테고리 ################### -->

<script>
	/**
	 * readonly:
	 */
	function readonlyAll(flag) {
		//$('form[name="projectForm"]').find('input,textarea,select,button').not('#select_project').not('#txt_select_project').add('#btn_save').add('#btn_delete').add('div.checkbox').readonly(flag);
		var myForm = $('form[name="projectForm"]');
		myForm.find('input,textarea,select,button,div.checkbox').not('#div_select_project input,select').readonly(flag); 
	}
	/**
	 * readonly: 완료된 프로젝트
	 */
	function readonlyCompleteProject() {
		var myForm = $('form[name="projectForm"]');
		myForm.find('input,textarea,select,button,div.checkbox').not('#div_select_project input,select')
			.not('#td_status input,#td_status div.checkbox').not('#btn_save') 
			.readonly(true);
	}
</script>


<!-- ################ ]]그룹 ################### -->
<script>
/** 그룹 추가 */
$(function(){
	$("#findGroup").autocomplete({
		source : function( request, response ) {
			searchProjectGroup(request, response);
	    },
	    focus: function( event, ui ) {
			$("#findGroup").val( ui.item.label2 );
			return false;
		},
	    //조회를 위한 최소글자수
	    minLength: 2,
	    select: function( event, ui ) {
	    	insertProjectGroupRequest(event, ui); 
	    }
	});
});

/** 그룹 검색 */
function searchProjectGroup(request, response) {
	var schProjectSeq = $('#projectSeq').val();
	if(schProjectSeq == '') {
		retun;
	}
	$.ajax({
    	type: 'post',
        url: "/project/selectProjectGroupListByName.ajax",
        dataType: "json",
        data: { schGroupName : request.term, schProjectSeq : schProjectSeq },
        success: function(data) {
        	console.log('>>> findGroup search result:');
        	console.log(data);
        	var result = data.result;
        	
            response( 
                $.map(result, function(item) {
                    return {
                        label:  item.displayGroupName,
                        label2: item.projectGroupName,
                        value: item.projectGroupSeq + '|' + item.projectSeq
                    }
                })
            );
        }
   });
}

/** 그룹 신청*/
function insertProjectGroupRequest(event, ui) {
	// 그룹 추가
	var itemValArr = ui.item.value.split('|');
	if(itemValArr.length < 2) {
		return;
	}
	var projectGroupSeq = itemValArr[0];
	var projectSeq = itemValArr[1];
	$.ajax({
    	type: 'post',
        url: "/project/insertProjectGroupRequest.ajax",
        dataType: "json",
        data: { projectGroupSeq : projectGroupSeq, projectSeq : projectSeq },
        error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
        success: function(data) {
        	console.log('>>> findGroup select:');
        	console.log(data);
        	var result = data.result;
        	if(result == ErrCode.V_SUCESS) {
        		var groupVO = data.groupVO;
        		// ui add
	        	$("#findGroup").val( '' );
	        	$('#ul_group_list').append(generatedLiForGroup(groupVO));      	
	        	return false;
        	} else if(result == ErrCode.V_NEED_LOGIN) {
        		alert("로그인이 필요합니다.");
        	} else if(result == ErrCode.V_GROUP_CONTAIN) {
        		alert("이미 그룹에 있습니다.");
        	} else if(result == ErrCode.V_GROUP_REQUESTED) {
        		alert("이미 신청하셨습니다.");
        	} else {
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        	$("#findGroup").val( '' );
        }
   });
}

/** 그룹 template */
function generatedLiForGroup(groupData) {
	// 그룹 ui
	var groupUi = $($.templates('<li>{{:groupLabel}}  <input type="hidden" name="uiGroup" value="{{:groupValue}}" /> <button type="button">x</button></li>').render(groupData));
	
	// 그룹 btn event
	groupUi.find('button').on('click', function(e){
		var thisObj = $(this);
		//그룹 취소
		var groupValue = thisObj.prev('input').val();
		var itemValArr = groupValue.split('|');
		if(itemValArr.length < 2) {
			return;
		}
		var projectGroupSeq = itemValArr[0];
		var projectSeq = itemValArr[1];
		
		$.ajax({
	    	type: 'post',
	        url: "/project/updateProjectGroupRequestCancel.ajax",
	        dataType: "json",
	        data: { projectGroupSeq : projectGroupSeq, projectSeq : projectSeq  },
	        error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
	        success: function(data) {
	        	console.log('>>> findGroup updateProjectGroupRequestCancel:');
	        	console.log(data);
	        	var result = data.result;
	        	if(result == ErrCode.V_SUCESS ) {
	        		thisObj.parent().remove();
	        	} else {
	        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        	}
	        }
	   });
	});
	//
	return groupUi;
} 

/** 그룹 신청 현황 조회*/
function initProjectGroupRequestList() {
	$('#ul_group_list').empty();
	var schProjectSeq = $('#projectSeq').val();
	if(schProjectSeq == '') {
		return;
	}
	
	$.ajax({
    	type: 'post',
        url: "/project/selectProjectGroupRequestInfo.ajax",
        dataType: "json",
        data: { schProjectSeq : schProjectSeq },
        error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
        success: function(data) {
        	console.log('>>> findGroup initProjectGroupRequestList:');
        	console.log(data);
        	var resultList = data.result;
        	if(resultList ) {
        		// ui add
	        	$('#ul_group_list').append(generatedLiForGroup(resultList));      	
        	} 
        }
   });
}
</script>
<!-- ################ ]]그룹 ################### -->

<script type="text/javascript">
	$(function(){
		$.validator.addMethod("validateChecked", function(value, element) {
			var obj = $('input[name="' + element.name + '"]:checked');			
			return obj && obj.length > 0;
		}, "선택해 주세요.");
		
		$.validator.addMethod("validateCheckedOne", function(value, element) {
			var obj = $('input[name="' + element.name + '"]:checked');
			return obj && obj.length == 1;
		}, "하나만  선택해 주세요.");
		
		$.validator.addMethod("validateFileExtension", function(value, element) {
			return acceptFileSuffix(value, "jpeg, png, jpg");
		}, "썸네일은 jpg, png 파일만 등록이 가능합니다.");
		
		$('#projectForm').validate( {
			//각 항목 별로 validation rule을 지정한다.
            rules:{
            	projectName : { required: true }
				, cateDepth1 : { required: true }
				, publicYn : { validateChecked: true, validateCheckedOne: true  }
				//, fileUrlFile : { required: true, validateFileExtension:true }
				, progressStatus : { validateChecked: true, validateCheckedOne: true  }
            },
            //rules에서 정의된 조건으로 validation에 실패했을 때 화면에 표시할 메시지를 지정한다.
            messages: {
            	projectName : { required: "프로젝트명을 입력해 주세요." }
            	, cateDepth1 : { required: "카테고리를 설정해주세요." }
            	//, fileUrlFile : { required: "썸네일을 입력해 주세요.", validateFileExtension: "썸네일은 jpg, png 파일만 등록이 가능합니다." }
				, publicYn : { validateChecked: "공개여부를 선택해 주세요.", validateCheckedOne: "공개여부는 하나만 선택이 가능합니다."}
				, progressStatus : { validateChecked: "완료여부를 선택해 주세요.", validateCheckedOne: "완료여부는 하나만 선택이 가능합니다."}
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
        } );
		
	
		
		/* 자동 완성 */
		$("#findName").autocomplete({
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
				$("#findName").val( ui.item.label2 );
				return false;
			},
	        //조회를 위한 최소글자수
	        minLength: 2,
	        select: function( event, ui ) {
	        	$("#findName").val( '' );
	        	$('#ul_member_list').append(generatedLi('emails', ui.item.label2, ui.item.value));	        	
	        	return false;
	        }
	    });
		
		
		$('input[name="publicYn"]').on('click', function(e){
			var val = $(this).val();
			$('input[name="publicYn"][value!="' + val + '"]').prop('checked', false);
			
		});
		
		$('input[name="progressStatus"]').on('click', function(e){
			var val = $(this).val();
			$('input[name="progressStatus"][value!="' + val + '"]').prop('checked', false);
			
		});
		
		$('#btn_save').on('click', function(e){
			e.preventDefault();
			saveProject();			
		});
		
		$('#btn_delete').click(function(e){
			e.preventDefault();
			deleteProject();
		});
		
		// readonly: default
		readonlyAll(true);
		var cateDepth1 = $('#cateWrapProjMana').find('[name="cateDepth1"]');
		$('#select_project').on('change', function(e){
			var val = $(this).val();
			$('#projectSeq').val(val);
			
			var form = $('#projectForm');
			form.find('input[name="projectName"]').val('');
			cateDepth1.find('option:eq(0)').prop('selected', true);
			cateDepth1.prev('input:text').val('전체');
			cateDepth1.trigger('change');
			
			$('#open').prop('checked', false);
			$('#close').prop('checked', false);
			$('#progress').prop('checked', false);
			$('#complete').prop('checked', false);
			
			$('#fileName').val('');
			
			$('#ul_cate_list').empty();
			$('#ul_member_list').empty();
			
			// default readonly true
			readonlyAll(true);
			
			if( val ){
				loadProject(val);
			} 
			
			// readonly
			//if(val) {
			//	readonlyAll(false);
			//} else {
			//	readonlyAll(true);
			//}
			
		});
	});
	
	function generatedLi(inputName, label, val){
		var userName = "<%=user.getUname()%>";
		
		var aCateli = $('<li>' + label + ' <input type="hidden" name="' + inputName + '" value="' + val + '" /> <button type="button">x</button></li>');
		/* 현재 로그인 된 사용자 라면 x 버튼 비활성 */
		if( userName == label ) {
			aCateli = $('<li>' + label + ' <input type="hidden" name="' + inputName + '" value="' + val + '" />');
		}
		
		//맴버 취소
		aCateli.find('button').on('click', function(e){
			$(this).parent().remove();
		});

		return aCateli;
	}
	
	
	function saveProject(){
		
		var form = $('#projectForm');
		if( ! form.valid() ){
			return;
		}
		
		form.ajaxSubmit({
			url : "/project/updateProject.ajax",
			type : "post",
			dataType : 'json',
			error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data) {
				if(_data.result == '1') {
					window.location.href = '/project/projectManage.do';
		    	} else if(_data.result == '100') { 
		    		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
		    			modalShow('#login-modal');
		    		}
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
			}
		});
	}
	
	/**
	 * 프로젝트 삭제:
	 */
	function deleteProject(){
		 checkedLogin(function(){
			if(confirm('정말 삭제하시겠습니까?')) {
				var form = $('#projectForm');
				if($('#projectSeq').val() == '') {
					alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
					return; 
				}
				
				form.ajaxSubmit({
					url : "/project/deleteProject.ajax",
					type : "post",
					dataType : 'json',
					error : function(_data) {
						console.log(_data);
				    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
					},
					success : function(_data) {
						if(_data.result == '1') {
							window.location.href = '/project/projectManage.do';
				    	} else {
				    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
				    	}
					}
				});
			}
		 }); //end of checkedLogin
	}
	
	function loadProject(projectSeq){
		//return;
		$.ajax({
			url : "/project/projectDetail.ajax",
	        type: "GET",
	        cache: false,
			data : {seq : projectSeq},
			success : function(_data){
				console.log(_data);
				if( '100' == _data.result ){
					if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
		    			modalShow('#login-modal');
		    		}
					return;
				}else if( '404' == _data.result ){
					alert('프로젝트를 찾을 수 없습니다. 다른 프로젝트를 선택해 주세요.');
					return;
				}
				
				var aProject = _data.project;
				
				var form = $('#projectForm');
				form.find('input[name="seq"]').val(aProject.seq);
				form.find('input[name="projectName"]').val(aProject.projectName);
				
				if( !isEmpty(aProject.fileName) ) {
					form.find('input[name="fileName"]').val(aProject.fileName);
				}
				
				var publicYn = aProject.publicYn;
				form.find('input[name="publicYn"][value="' + publicYn + '"]').prop('checked', true);
				
				var cateList = _data.cate_list;
				if( cateList ){
					var cateArray = new Array();
					for( var i = 0; i < cateList.length; i++ ){
						var aCate = cateList[i];
						cateArray.push(generatedLi('categoryCodes', aCate.categoryName, aCate.categoryCode));
						
					}
					$('#ul_cate_list').append(cateArray);
					
				}
				
				var memberList = _data.member_list;
				if( memberList ){
					var memberArray = new Array();
					for( var i = 0; i < memberList.length; i++ ){
						var aMember = memberList[i];
						memberArray.push(generatedLi('emails', aMember.uname, aMember.email));
						
					}
					$('#ul_member_list').append(memberArray);
					
				}
				
				//그룹 초기화
				initProjectGroupRequestList();
				
				var progressStatus = aProject.progressStatus;
				form.find('input[name="progressStatus"][value="' + progressStatus + '"]').prop('checked', true);
				
				//카테고리 초기화
				refreshCategoryProjMg(aProject.selCateCode); 
				
				
				// ==== readonly 처리 ============
				readonlyAll(false);
				// readonly category
				var cateDepth2 = $('#cateWrapProjMana').find('[name="cateDepth2"]');
				var cateDepth3 = $('#cateWrapProjMana').find('[name="cateDepth3"]');
				common.cateReadonly(cateDepth2, true);
				common.cateReadonly(cateDepth3, true);
				
				// 완료된 프로젝트 수정 못함:(상태 수정 빼고) 
				handleProjectCompleteStatus(progressStatus);
				
				// ==== ]]readonly 처리 ============
			},
			error : function(req){
				console.log("fail to loadProject processing!");
			}
		});
	}
	
	/**
	 * 완료된 프로젝트 수정 못함:(상태와 수정버튼 빼고 다 수정못함)
	 */
	function handleProjectCompleteStatus(progressStatus) {
		if(isEmpty(progressStatus) || progressStatus == '<%=ProjectProgressStatus.PROGRESS%>' ) {
			console.log('>>> handleProjectCompleteStatus : progressStatus=' + progressStatus);
			return;
		}
		
		readonlyCompleteProject();
	}
	
</script>
</body>
</html>