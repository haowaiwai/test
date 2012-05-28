<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>search files from alfresco</title>
</head>
<body>
<center>
<form action="SearchServlet" method="get" name="searchForm" id="searchForm">
<table>
	<tr>
		<td>query keyword:</td>
		<td><input type="text" name="queryItem"></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" name="search" value="search"/></td>
	</tr>
</table>
</form>
</center>
</body>
</html>