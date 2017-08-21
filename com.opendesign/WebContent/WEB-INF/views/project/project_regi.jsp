<%-- 화면ID : OD03-01-02 --%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.vo.CategoryVO"%><%
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
				<span>프로젝트 생성</span>
			</div>
			<form name="projectForm" id="projectForm" enctype="multipart/form-data" onsubmit="return false;" >
			<table class="tbl-regi">
				<colgroup>
					<col style="width:220px">
					<col style="">
					<col style="width:220px">
					<col style="">
				</colgroup>
				<tr>
					<th scope="row" class="km-required">프로젝트명</th>
					<td colspan="3"><input type="text" name="projectName" class="no-border" maxlength="50" placeholder="최대 50자 이내 입력"></td>
				</tr>
				<tr>
					<th scope="row" class="km-required">공개 여부</th>
					<td colspan="3">
						<div class="checkbox">
							<input type="checkbox" name="publicYn" value="Y" id="open">
							<label for="open">공개</label>
						</div>
						<div class="checkbox">
							<input type="checkbox" name="publicYn" value="N" id="close">
							<label for="close">비공개</label>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" class="km-required">카테고리</th>
					<td colspan="3" id="cateWrapProjRegi">
						<div class="select-area custom-select" id="firstCateDiv">
							<input type="text">
							<select id="cateDepth1" name="cateDepth1">
								<option value="">전체</option>
							<%-- <%for(CategoryVO aCate  : cateList){%>
								<option value="<%=aCate.getCategoryCode() %>"><%=aCate.getCategoryName() %></option>
							<%} %> --%>
							</select>
						</div>
						<div class="select-area custom-select">
							<input type="text">
							<select id="cateDepth2" name="cateDepth2">
								<option value="">전체</option>
							</select>
						</div>
						<div class="select-area custom-select">
							<input type="text">
							<select id="cateDepth3" name="cateDepth3">
								<option value="">전체</option>
							</select>
						</div>
						<button id="btn_add_cate" type="button" class="btn-add"><img src="../resources/image/common/btn_addBig.png" alt="더하기"></button>
						<ul id="ul_cate_list" class="cate-list">
						<%--
							<li>의상 <button type="button">x</button></li>
						--%>
						</ul>
					</td>
				</tr>
				<tr>
					<th scope="row" class="km-required">썸네일 등록</th>
					<td colspan="3">
						<div class="file-url">
							<input type="text" readonly placeholder="jpg,png만 등록 가능합니다.">
							<!-- button type="btn-del">x</button-->
						</div>
						<div class="file">
							<input type="file" name="fileUrlFile" accept="image/x-png, image/jpeg, image/jpg" />
							<button type="button">검색</button>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">멤버 추가</th>
					<td colspan="3">
						<div class="member-add">
							<input type="text" id="findName" name="schWord" placeholder="이름 또는 이메일 주소를 입력 후 Enter를 눌러주세요" class="no-border">
							<!-- button type="button">검색</button -->
						</div>
						<ul id="ul_member_list" class="cate-list">
						<%--
							<li>의상 <button type="button">x</button></li>
							<li>의상 <button type="button">x</button></li>
							<li>의상 <button type="button">x</button></li>
							<li>의상 <button type="button">x</button></li>
							<li>의상 <button type="button">x</button></li>
						--%>
							
							<li><%=user.getUname() %><input type="hidden" name="emails" value="<%=user.getEmail() %>" /></li>
						</ul>
					</td>
				</tr>
			</table>
			</form>
			<button id="btn_register" type="button" class="btn-complete btn-red">등록</button>
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
	$(function() {
		new CategoryView({
			htmlContainer: $('#cateWrapProjRegi')
			,hiddenFieldName: 'categoryCodes'
			,excludeData: { }
		}).render();
	});
</script>
<!-- ################ ]]카테고리 ################### -->

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
				, fileUrlFile : { required: true, validateFileExtension:true }
				, publicYn : { validateChecked: true, validateCheckedOne: true  }
            },
            //rules에서 정의된 조건으로 validation에 실패했을 때 화면에 표시할 메시지를 지정한다.
            messages: {
            	projectName : { required: "프로젝트명을 입력해 주세요." }
            	, cateDepth1 : { required: "카테고리를 설정해주세요." }
            	, fileUrlFile : { required: "썸네일을 입력해 주세요.", validateFileExtension: "썸네일은 jpg, png 파일만 등록이 가능합니다." }
				, publicYn : { validateChecked: "공개여부를 선택해 주세요.", validateCheckedOne: "공개여부는 하나만 선택이 가능합니다."}
            },
            invalidHandler: function(form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    alert(validator.errorList[0].message);
                    validator.errorList[0].element.focus();
                }          
           	},
          	//validator는 기본적으로 validation 실패 시 실패한 노드 우측에 실패 메시지를 표시하게 되어있다. 
          	//작동을 원하지 않으면 내용이 없는 errorPlacement를 선언한다.
          	//이 샘플에서는 alert를 사용하고 있으므로 빈 메서드를 선언하였다.
            errorPlacement: function(error, element) {
                // do nothing               	
            },
            ignore: [],
            focusInvalid:false,
            onfocusout: false,	// onblur 시 해당항목을 validation 할 것인지 여부 (default: true)
            onkeyup: false,
            onclick: false,
            debug:false, 			//true일 경우 validation 후 submit을 수행하지 않음. (default: false)
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
	        	var aCateli = $('<li>' + ui.item.label2 + ' <button type="button">x</button><input type="hidden" name="emails" value="' + ui.item.value + '" /></li>');
				aCateli.find('button').on('click', function(e){
					$(this).parent().remove();
				});
	        	
	        	$('#ul_member_list').append(aCateli);	        	
	        	return false;
	        }
	    });
		
		$('input[name="publicYn"]').on('click', function(e){
			var val = $(this).val();
			$('input[name="publicYn"][value!="' + val + '"]').prop('checked', false);
			
		});
		
		$('#btn_register').on('click', function(e){
			e.preventDefault();
			registerProject();			
		});
	});
	
	
	function registerProject(){
		
		var form = $('#projectForm');
		if( ! form.valid() ){
			return;
		}

		form.ajaxSubmit({
			url : "/project/registerProject.ajax",
			type : "post",
			dataType : 'json',
			error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data) {
				if(_data.result == '1') {
					window.location.href = '/project/project.do';
		    	} else if(_data.result == '100') { //이메일 중복
		    		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
		    			modalShow('#login-modal');
		    		}
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
			}
		});
		
	}
</script>
</body>
</html>