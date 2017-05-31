<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PNUIPS for administrator</title>
</head>
<body>
<div>PNUIPS</div>
<div>관리자 전용입니다.</div>
<div>정해진 ID와 PW를 입력해주세요</div>
<form name="login" method="post" action="./adminpage.jsp">
	<label >관리자ID</label><input id="email" type="text" name="email" placeholder="admin임" pattern="admin">
	<br>
	<label>Password</label><input id="password" type="password" name="pw" placeholder="1234임" pattern="1234">
	<input id="submit" type="submit" name="submit" value="Login">
</form>
</body>
</html>