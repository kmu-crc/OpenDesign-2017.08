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
<script type="text/javascript" src="/common/js/unj_commons.js"></script>
<script type="text/javascript" src="/common/js/unj_util.js"></script>
<script type="text/javascript" src="/common/js/unj_admin.js"></script>
	
	<script type="text/javascript">
		$(function(){			
		}); 
		
		
		function search(){			
		}
		
		function testSubmit(){
			$("#testSubmitForm").submit();
		}
		
	</script>
</head>
<body>

	
	
	
	<form id="testSubmitForm" method="get" action="transaction.do" >
		<table style="padding-left: 200px;">
			<tr>
				<td colspan="1">
					<h1>Parameter Test</h1>
				</td>				
			</tr>
			<tr>
				<td>id</td>
				<td><input type="text" name="title" /></td>
			</tr>
			<tr>
				<td>name</td>
				<td><input type="text" name="contents" /></td>
			</tr>
			<tr>
				<td>Commit</td>
				<td><input type="checkbox" name="commit" value="true"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" id="testAjaxFormButton" value="Submit" onclick="testSubmit();" /></td>
			</tr>
		</table>
	</form>
	
	
</body>
</html>