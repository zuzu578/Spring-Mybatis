<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<form method="POST" action="member_check">
<table border="1">
<tr><td align="right">User ID</td><td><input type="text" name="userid" size="12"></td></tr>
<tr><td align="right">Password</td><td><input type="password" name="passcode" size="12"></td></tr>
<tr><td colspan="2" align="center">
	<input type="reset" value="reset" name="reset" id="reset">
	<input type="submit" value="login" name="submit" id="submit">
	


</table>



</form>
</body>
</html>