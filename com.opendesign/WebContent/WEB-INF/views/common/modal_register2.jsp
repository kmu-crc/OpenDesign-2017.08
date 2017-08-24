<%-- 화면ID : OD01-01-04 --%>
<%-- 화면ID : OD01-01-05 --%>
<%@page import="com.opendesign.utils.CmnConst.CateExclude"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.vo.CategoryVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 회원가입:상세 -->
<div class="modal" id="join2-modal">
	<div class="bg"></div>
	<div class="modal-inner modal-border">
		<h1>회원가입</h1>
		<p class="txt">회원가입을 위해서 프로필 사진,이름, 자기소개를 등록해 주세요!</p>
		<form name="regForm2" method="post" enctype="multipart/form-data" onsubmit="return false;" >
			<input type="hidden" name="memberType" value="">
			<input type="hidden" name="categoryList" value="">
			<fieldset>
				<legend>가입</legend>
				<div class="my-pic">
					<img class="pic-profile">
					<input type="file" name="imageUrlFile" accept="image/x-png, image/jpeg" onchange="imgPreview(event);">
				</div>
				<div class="my-pic-info">
					<p>프로필 사진 형식</p>
					<p>용량 : ~ 5Mbytes</p>
					<p>형식 : .png / .jpeg 파일만 가능</p>
				</div>
				<div class="clear"></div>
				<div class="base-info">
					<input type="text" name="uname" style="border: 2px solid #f5acac;" placeholder="닉네임 또는 회사명 (필수 최대 20자)" maxlength="20">
					<textarea name="comments" placeholder="자기소개 또는 회사소개 등록(최대 1,000자)" maxlength="1000"></textarea>
				</div>

				<div class="cate-wrap" id="cateWrapMemberReg">
					<p>나의 카테고리(필수)</p>
					<div class="select-area custom-select">
						<input type="text"><i class="fa fa-caret-down" aria-hidden="true"></i>
						<select name="cateDepth1" id="cateDepth1">
							<option value="">전체</option>
						</select>
					</div>
					<div class="select-area custom-select">
						<input type="text"><i class="fa fa-caret-down" aria-hidden="true"></i>
						<select name="cateDepth2" >
							<option value="">전체</option>
						</select>
					</div>
					<div class="select-area custom-select">
						<input type="text">
						<select name="cateDepth3" >
							<option value="">전체</option>
						</select>
					</div> 
					<button type="button" class="btn-add"><img src="/resources/image/common/btn_add.png" alt="더하기"></button>
					<ul class="cate-list">
						<!-- <li>의상 <button type="button" >X</button> <input type="hidden" name="memberCateCode" value=""></li> -->
					</ul>
				</div>
				<p style="font-size: 14px; margin-bottom: 8px;">거주지역(필수)</p>
				<div class="select-area custom-select" id="sidoMemberReg" style="border: 2px solid #f5acac; height: 35px; width: 367px;">
					<input type="text" style="width: 367px;">
					<select name="sidoVal" id="sidoVal">
						<option value="" selected="selected">전체</option>
					</select>
				</div>
				<div class="clear"></div>
				<div class="check-wrap">
					<div class="custom-check">
						<p>회원구분</p>
						<input type="checkbox" name="memberTypeCheck" value="d" id="designer">
						<label for="designer">디자이너</label>
					</div>
					<p class="txt"">디자이너가 아니라면 체크하지 않아도 됩니다.
						<span>* 체크를 하시면 디자이너 메뉴에 노출됩니다.</span>
					</p>
				</div>
				<div class="clear"></div>
				<div class="regi-btn-wrap btn-red">
					<a href="javascript:regForm2Submit();" class="btn-complete btn-modal">완료</a>
				</div>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>


<!-- 회원가입이 완료 -->
<div class="modal" id="complete-modal">
	<div class="bg"></div>
	<div class="modal-inner">
		<p>회원가입이 완료되었습니다<br>감사합니다</p>
	</div>
</div>

<!-- ################ 카테고리 ################### -->
<script >
	$(function() {
		new CategoryView({
			htmlContainer: $('#cateWrapMemberReg')
			,hiddenFieldName: 'memberCateCode'
			,excludeData: {<%=CateExclude.P_NAME%>: '<%=CateExclude.V_DESI_PROD%>' }
		}).render();
		
		selectSidoList();
	});
</script>
<!-- ################ ]]카테고리 ################### -->

<script>
/** 회원가입2 */
var flag_regForm2Submit = false; //flag
function regForm2Submit() {
	// 0.validate
	var myForm = $('form[name="regForm2"]');
	if(!myForm.valid()) {
		return;
	}
	
	if(flag_regForm2Submit) {
		return;
	}
	
	if($("#cateDepth1").val() == "" || $("#cateDepth1").val() == null) {
		alert("카테고리를 선택해주세요.");
		$("#cateDepth1").focus();
		return;
	}
	
	if($("#sidoVal").val() == "" || $("#sidoVal").val() == null) {
		alert("거주지역을 선택해주세요.");
		$("#sidoVal").focus();
		return;
	}
	
	flag_regForm2Submit = true;
	
	//== 1. submit
	myForm.ajaxSubmit({
		url : "/register2.ajax",
		type : "post",
		dataType : 'json',
		complete : function(_data){
			flag_regForm2Submit = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data) {
			console.log(_data);
			if(_data.result == '1') {
	    		modalShow('#complete-modal');
	    		//3초후 숨기고 mypage로 이동
	    		setTimeout(function(){ 
	    				modalHide('#complete-modal');
	    				window.location.href = '/user/myPage.do';
	    			}, 3000);
	    	} else if(_data.result == ErrCode.V_EMAIL_DUP) { //이메일 중복
	    		alert('이메일 중복입니다.'); 
	    	} else if(_data.result == ErrCode.V_UNAME_DUP) { //닉네임 중복
	    		alert('닉네임 중복입니다.'); 
	    	} else {
	    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	    	}
		}
	});
	
}

//=== 검증 ==========
$(function(){
	regForm2ValidRuleInit();
})

function regForm2ValidRuleInit(){
	$.validator.addMethod("validateFileExtension", function(value, element) {
		return acceptFileSuffix(value, "jpeg, png, jpg");
	}, "썸네일은 jpg, png 파일만 등록이 가능합니다.");
	
	var myForm = $('form[name="regForm2"]');
	myForm.validate({
		rules:{
			uname : { required: true }
			,imageUrlFile: {validateFileExtension: true }
		},
		messages: {
			uname : { required: "닉네임 또는 회사명을 입력해 주세요." }
			,imageUrlFile : { validateFileExtension: "프로필 사진은 jpg, png 파일만 등록이 가능합니다." }
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

//시/도 리스트 가져오기.
function selectSidoList() {
	$.ajax({
		url : "/selectSidoList.ajax",
		type : "post",
		dataType : 'json',
		complete : function(_data){
			
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data) {
			console.log(_data);
			
			var sidoList = _data.sidoList;
			var sidoOption = "";
			for(var i = 0; i < sidoList.length; i++) {
				var sido = sidoList[i];
				sidoOption += "<option value='"+sido.seq+"'>" + sido.sido + "</option>";
			}
			$("#sidoVal").append(sidoOption);
		}
	});
} 


</script>

