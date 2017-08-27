<%-- 화면ID : OD04-03-02 --%>
<%-- 화면ID : OD04-03-03 --%>
<%@page import="com.opendesign.utils.CmnConst.CateExclude"%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.utils.CmnConst.RstConst"%>
<%@page import="com.opendesign.vo.CategoryVO"%>
<%@page import="com.opendesign.vo.RequestBoardVO"%>
<%@page import="com.opendesign.vo.RequestBoardFileVO"%>
<%@page import="com.opendesign.utils.CmnConst.PageMode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//
	String pageMode = (String)request.getAttribute("pageMode"); //추가/변경 구분
	RequestBoardVO resultVO  = (RequestBoardVO)request.getAttribute(RstConst.P_NAME);
	String schMemberDiv = (String)request.getAttribute("schMemberDiv");  //회원구분
	String memberDivString = "";
	if(MemberDiv.DESIGNER.equals(schMemberDiv)) {
		memberDivString = "디자이너";
	} else {
		memberDivString = "제작자";
	}
	String boardType = schMemberDiv; //게시판구분 
	
	// 
	String lblTitle = "디자인 의뢰";
	String lblSubmit = "등록";
	String seq = "";
	String title = "";
	String contents = "";
	List<CategoryVO> cateList = null;
	List<RequestBoardFileVO> fileList = null;
	String selCateCode = ""; // 선택된 카테고리
	if(PageMode.UPDATE.equals(pageMode)) {
		lblTitle = "글 수정";
		lblSubmit = "글 수정";
		//
		seq = resultVO.getSeq();
		title = resultVO.getTitle();
		contents = resultVO.getContents(); 
		cateList = resultVO.getCateList(); 
		fileList = resultVO.getFileList();
		selCateCode = resultVO.getSelCateCode();
	} 
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<script>
/**
 * 등록
 */
var flag_boardFormSubmit = false; //flag
function boardFormSubmit() {
	checkedLogin(function(){
		// 0.validate
		var myForm = $('form[name="boardForm"]');
		if(!myForm.valid()) {
			return;
		}
		//
		
		if(flag_boardFormSubmit) {
			return;
		}
		flag_boardFormSubmit = true;
		
		//== 1. submit
		myForm.ajaxSubmit({
			url : "/designer/insUpdRequestBoard.ajax",
			type : "post",
			dataType : 'json',
			complete : function(_data){
				flag_boardFormSubmit = false;
			},
			error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data) {
				console.log(_data);
				if(_data.result == '1') {
		    		//alert('성공했습니다.');
		    		//목록으로 이동
		    		window.location.href = '/designer/openDesignRequestBoard.do?schMemberDiv=<%=schMemberDiv%>';
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
			}
		}); 
	}); //end of checkedLogin
}
</script>
<script>
//==================== 검증 ====================
$(function(){
	boardFormValidRuleInit(); 
})

function boardFormValidRuleInit(){
	//== 
	//== 
	//== 
	var myForm = $('form[name="boardForm"]');
	
	$.validator.addMethod("validateFileExtension", function(value, element) {
		return acceptFileSuffix(value, "jpeg, png, jpg");
	}, "썸네일은 jpg, png 파일만 등록이 가능합니다."); 
	
	myForm.validate({
		rules:{
			title : { required: true }
			,cateDepth1 : { required: true}
			,contents : { required: true }
			,uiImageUrlFile: {validateFileExtension: true}
		},
		messages: {
			title : { required: "제목을 입력하세요." }
			,cateDepth1 : { required: "카테고리를 설정하세요."}
			,contents : { required: "내용을 입력하세요." }
			,uiImageUrlFile : { validateFileExtension: "참고자료는 jpg, png 파일만 등록이 가능합니다." }
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
	    debug: true,
	    ignore: [],
	    focusInvalid:false,
	    onfocusout: false,
	    onkeyup: false,
	    onclick: false
	});
}
//==================== ]]검증 ====================
</script>

<!-- ################ 카테고리 ################### -->
<script >
	$(function() {
		new CategoryView({
			htmlContainer: $('#cateWrapRequestWrite')
			,hiddenFieldName: 'uiBoardCateCodes'
			,excludeData: { <%=CateExclude.P_NAME%>: '<%=CateExclude.V_DESI_PROD%>' }
			,selCateCode: '<%=selCateCode%>'
		}).render();
	});
</script>
<!-- ################ ]]카테고리 ################### -->

<!-- ###################### file upload ############################## -->
<script id="tmpl-boardFormFile" type="text/x-jsrender">
				<div class="file-wrap" >
					<div class="file-url">
						<input type="text" readonly="readonly" name="ttt" placeholder="참고 자료 (jpg, png만 등록, 최대 10MB)">
						<button onclick="boardFormRemoveFile(this);" type="btn-del"><i class="fa fa-times" aria-hidden="true"></i></button>
					</div>
					<div class="file">
						<input type="file" accept="image/x-png, image/jpeg" name="uiImageUrlFile" style="width: 100px; cursor: pointer;">
						<button type="button">검색</button>
					</div>
				</div>
</script>
<script>
////
	/**
	 * file add
	 */
	function boardFormAddFile(thisObj) {
		var myForm = $(thisObj).closest('form');
		var fileCont = myForm.find('.file-container');
		// check 4개 
		if(fileCont.find('.file-wrap').length >= 4) {
			alert('최대 4개까지 등록할수 있습니다.');
			return;
		}
		var htmlJ = $($.templates('#tmpl-boardFormFile').render());
		fileCont.append(htmlJ);
	}
	
 	/**
 	 * file remove
 	 */
 	function boardFormRemoveFile(thisObj) {
 		$(thisObj).closest('.file-wrap').remove();
 	}
 	
 	/**
 	*첫번째 참고자료는 등록된 이미지만 제거.
 	*/
 	function removeDataInFileWrap() {
		$("#ttt").val("");
 	}
</script>
<!-- ###################### ]]file upload ############################## -->

</head>
<body>
<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->

	<!-- content -->
	<div class="request-content">
		<div class="inner">
			<div class="best-head">
				<span class="mainChar">D</span>
				<span><%=lblTitle%></span>
			</div>

			<form name="boardForm" method="post" enctype="multipart/form-data" onsubmit="return false;" >
				<input type="hidden" name="pageMode" value="<%=pageMode%>" />
				<input type="hidden" name="seq" value="<%=seq%>" />
				<input type="hidden" name="boardType" value="<%=boardType%>" /> <!-- 게시판구분 --> 
				
				<div class="tbl-wrap">
					<table class="tbl-regi">
						<tr>
							<td id="cateWrapRequestWrite">
								<div class="select-area custom-select">
									<input type="text" >
									<select name="cateDepth1" id="cateDepth1">
										<option value="">전체</option>
									</select>
								</div>
								<div class="select-area custom-select">
									<input type="text">
									<select name="cateDepth2" >
										<option value="">전체</option>
									</select>
								</div>
								<div class="select-area custom-select">
									<input type="text">
									<select name="cateDepth3">
										<option value="">전체</option>
									</select>
								</div>
								<button type="button" class="btn-add" ><img src="/resources/image/common/btn_add.png" alt="더하기"></button>
								<ul class="cate-list">
									<!-- <li>의상 <button type="button" onclick="boardFormDelCategory(this);">X</button> <input type="hidden" name="uiBoardCateCodes" value=""></li> -->
									<% if( !CmnUtil.isEmpty(cateList)) { for(CategoryVO item : cateList) { %>
									<li><%=item.getCategoryName()%> <input type="hidden" name="uiBoardCateCodes" value="<%=item.getCategoryCode()%>"></li>
									<% } } %> 
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								<input type="text" name="title" maxlength="40" placeholder="제목 (최대 40자)" class="input-text" value="<%=title%>" />
							</td>
						</tr>
						<!-- <tr>
							<td>
								<div class="btn-add" style="margin-right:10px;" >이미지 추가: </div>
								<button type="button" class="btn-add" onclick="boardFormAddFile(this);"><img src="/resources/image/common/btn_add.png" alt="더하기"></button>
							</td>
						</tr> -->
						<tr>
							<td class="file-container">
								<div class="file-wrap" >
									<div class="file-url">
										<input type="text" readonly="readonly" name="ttt" id="ttt" placeholder="참고 자료 (jpg, png만 등록, 최대 10MB)">
										<button type="button"  onclick="removeDataInFileWrap();" ><i class="fa fa-times" aria-hidden="true"></i></button>
									</div>
									<div class="file">
										<input type="file" accept="image/x-png, image/jpeg" name="uiImageUrlFile" style="width: 100px; cursor: pointer;">
										<button type="button">검색</button>
									</div>
								</div>
								<button type="button" class="btn-add" onclick="boardFormAddFile(this);"><img src="/resources/image/common/btn_add.png" alt="더하기">추가</button>
							</td>
						</tr>
						<tr>
							<td><textarea name="contents" maxlength="5000" placeholder="새글쓰기 (최대 5,000자)"><%=contents%></textarea></td>
						</tr>
					</table>
				</div>
				<button type="button" onclick="boardFormSubmit();" class="btn-complete"><%=lblSubmit%></button>
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
</body>
</html>