<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String[] hobbies = (String[]) pageContext.getAttribute("hobby");

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
		
		
		
	</script>
</head>
<body>

	
	
		<table style="padding-left: 200px;">
			<tr>
				<td colspan="1">
					<h1>Access Model Value</h1>
				</td>				
			</tr>
			<tr>
				<td>id</td>
				<td>${id}</td>
			</tr>
			<tr>
				<td>name</td>
				<td>${name}</td>
			</tr>
			<tr>
				<td>age</td>
				<td>${age}</td>
			</tr>
			<tr>
				<td>취미 Display1</td>
				<td>
					${hobby[0]} ${hobby[1]} ${hobby[2]}
				</td>
			</tr>
			<tr>
				<td>취미 Display2</td>
				<td>
					<c:forEach items="${hobby}" var="aHobby">
   						${aHobby}
   					</c:forEach>					
				</td>
			</tr>
		</table>
	
	
	
		<table style="padding-left: 200px;">
			<tr>
				<td colspan="1">
					<h1>Access Model Value (Map)</h1>
				</td>				
			</tr>
			<tr>
				<td>id</td>
				<td>${profile.id}</td>
			</tr>
			<tr>
				<td>name</td>
				<td>${profile.name}</td>
			</tr>
			<tr>
				<td>age</td>
				<td>${profile.age}</td>
			</tr>
			<tr>
				<td>취미 Display1</td>
				<td>
					${profile.hobby[0]} ${profile.hobby[1]} ${profile.hobby[2]}
				</td>
			</tr>
			<tr>
				<td>취미 Display2</td>
				<td>
					<c:forEach items="${profile.hobby}" var="aHobby">
   						${aHobby}
   					</c:forEach>					
				</td>
			</tr>
		</table>
	
</body>
</html>