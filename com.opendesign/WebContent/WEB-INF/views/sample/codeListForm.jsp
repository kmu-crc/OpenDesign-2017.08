<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/common/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/common/jquery-ui-1.11.0/jquery-ui.min.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.validate.js"></script>

	
	
	<script type="text/javascript">
		$(function(){

			$('#testSubmitForm').validate( {
				//각 항목 별로 validation rule을 지정한다.
                rules:{
					id : { required: true, minlength : 5, remote: "validateScriptCheckID.view" }
					, password : { required: true  }
					, passwordre : { equalTo: "#password"  }
					, email : { required: true, email : true }
					, age : { required: true, digits : true }
					, homepage :  { required: true, url : true }
                },
                //rules에서 정의된 조건으로 validation에 실패했을 때 화면에 표시할 메시지를 지정한다.
                messages: {
					id : { required: "필수입력입니다"
						, minlength : "{0}글자 이상이어야 합니다."
						, remote: "사용할 수 없는 아이디 입니다." }
					, password : { required: "필수입력입니다"}
					, passwordre : { equalTo: "패스워드를 확인해 주세요"}
					, email : { required: "필수입력입니다"
						, email : "메일을 정확하게 입력하세요." }
					, age : { required: "필수입력입니다"
						, digits : "숫자만 입력 합니다."}
					, homepage :  { required: "필수입력입니다"
						, url : "정상적인 URL이 아닙니다." }
                },
                invalidHandler: function(form, validator) {
                    var errors = validator.numberOfInvalids();
                    if (errors) {
                        alert(validator.errorList[0].message);
                        validator.errorList[0].element.focus();
                    }          
               	},               
                submitHandler: function() {
                    var f = confirm("전송 하시겠습니까?");
                    if(f){
                        return true;
                    } else {
                        return false;
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
			
		}); 
			
		
		function testSubmit(){
			$("#testSubmitForm").submit();
		}
		
		function forceValidateTrigger(){
			//valid() 메서드가 호출되고 validation 결과가 true일 떄 validator는 submitHandler를 실행한다.
			$("#testSubmitForm").valid();
		}
		
	</script>
</head>
<body>

	<form id="testSubmitForm" method="get" action="" >
		<table style="padding-left: 200px;">
			<tr>
				<td colspan="1">
					<h1>Parameter Test</h1>
				</td>				
			</tr>
			<tr>
				<td>id</td>
				<td><input type="text" name="id" required="required" /></td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="text" name="password" /></td>
			</tr>
			<tr>
				<td>passwordre</td>
				<td><input type="text" name="passwordre" /></td>
			</tr>
			<tr>
				<td>email</td>
				<td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<td>age</td>
				<td><input type="text" name="age" /></td>
			</tr>
			<tr>
				<td>homepage</td>
				<td><input type="text" name="homepage" /></td>
			</tr>
			
			<tr>
				<td>취미</td>
				<td><input type="text" name="hobby" /><input type="text" name="hobby" /><input type="text" name="hobby" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" id="btn" value="Submit" onclick="testSubmit();" /></td>
			</tr>
			
			
							
		</table>
	</form>
	
	
</body>
</html>