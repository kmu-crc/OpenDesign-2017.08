<%@page import="java.util.List"%>
<%@page import="com.opendesign.utils.CmnConst.RstConst"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%@page import="com.opendesign.utils.ControllerUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	<script>
	function sendMail(){
		var form = $('form.mailForm');
		form.ajaxSubmit({
			url : "/mail/sendSimple.ajax",
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
				alert("메일이 전송되었습니다");
			}
		});
	}
	</script>
	
	<div class="modal" id="modal_contact">
	    <div class="bg"></div>
		<div class="modal-inner">
			<div class="rule-box">
				<h1>Contact Us</h1>
				<form class="mailForm" enctype="multipart/form-data" method="post">
					<div>
						<label for="from_email">보내는 사람</label>
						<input type="text" id="from_email" name="from">
					</div>
					<div>
						<label for="to_email">받는 사람</label>
						<input type="text" id="to_email" name="to" value="opensrcdesign@gmail.com" readonly>
					</div>
					<div>
						<input type="text" class="mailTitle" name="title" placeholder="제목">
						<textarea class="mailContent" name="template" placeholder="내용을 입력해주세요"></textarea>
					</div>
					<button type="submit" class="btn-red" onclick="javascript:sendMail();">보내기</button>
				</form>
			</div>
		</div>
	</div>
</div> 
</body>
</html>