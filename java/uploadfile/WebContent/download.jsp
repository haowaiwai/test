<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>download file</title>
<script type="text/javascript">
function downloadSubmit(){
	var form=document.getElementById("downloadForm");
	var nodeRef=form.nodeRef.value;
	var url;
	url="http://localhost:8080/alfresco/d/a/";
	url+=nodeRef;
	url+="?username='admin'&password='admin'";
	form.action=url;
	form.submit();
}
</script>
</head>
<body>
<center>
<%=request.getAttribute("result") %>
<form action="DownloadServlet" method="get" id="downloadForm" name="downloadForm">
<table>
	<tr>
		<td>nodeRef:</td>
		<td><input name="nodeRef"/></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="button" name="download" value="scriptSubmit" onclick="downloadSubmit()"></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" name="sdd" value="servletSubmit"></td>
	</tr>
</table>
</form>
</center>
</body>
</html>