<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/common/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="/common/jquery-ui-1.11.0/jquery-ui.min.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.validate.js"></script>

</head>
<body>

	<form method="post" action="fileUpload.do" enctype="multipart/form-data">
		<table style="padding-left: 200px;">
			<tr>
				<td colspan="2"><c:out value="${uploadMessage}" />  ::   comment:<c:out value="${comment}" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td><b>comment: &nbsp;</b></td>
				<td><input type="text" name="comment" /></td>
			</tr>
			<tr>
				<td><b>Select the file to be uploaded: &nbsp;</b></td>
				<td><input type="file" name="myFileField1" /></td>
			</tr>
			<tr>
				<td><b>Select the file to be uploaded: &nbsp;</b></td>
				<td><input type="file" name="myFileField2" /></td>
			</tr>
			<tr>
				<td><b>Select the file to be uploaded: &nbsp;</b></td>
				<td><input type="file" name="myFileField3" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="Upload file" onclick="document.forms[0].submit();" /></td>
			</tr>
		</table>

	</form>
	
	<br/><br/><br/><br/>
	<form method="post" action="multifileUpload.do" enctype="multipart/form-data">
		<table style="padding-left: 200px;">
			<tr>
				<td colspan="2"><c:out value="${uploadMessage}" /> ::   comment:<c:out value="${comment}" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td><b>comment: &nbsp;</b></td>
				<td><input type="text" name="comment" /></td>
			</tr>
			<tr>
				<td><b>Select the file to be uploaded: &nbsp;</b></td>
				<td><input type="file" name="myFileField" /></td>
			</tr>
			<tr>
				<td><b>Select the file to be uploaded: &nbsp;</b></td>
				<td><input type="file" name="myFileField" /></td>
			</tr>
			<tr>
				<td><b>Select the file to be uploaded: &nbsp;</b></td>
				<td><input type="file" name="myFileField" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="Upload file" onclick="document.forms[1].submit();" /></td>
			</tr>
		</table>

	</form>
</body>
</html>