<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload file example</title>
</head>
<body>
<center>
<form action="UploadServlet" method="post">
<table>
	<tr>
		<td>File:</td>
		<td><input type="file" name="filedata"></td>
	</tr>
	<tr>
		<td>filename:</td>
		<td align="left"><input name="filename"></td>
	</tr>
	<tr>
		<td>siteid:</td>
		<td align="left"><input name="siteid"></td>
	</tr>
	<tr>
		<td>containerid:</td>
		<td align="left"><input name="containerid"></td>
	</tr>
	<tr>
		<td>uploaddirectory:</td>
		<td align="left"><input type="text" name="uploaddirectory"></td>
	</tr>
	<tr>
		<td>contenttype:</td>
		<td align="left"><input type="text" name="contenttype"></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" name="submit" value="Upload"></td>
	</tr>

</table>
</form>
</center>
</body>
</html>