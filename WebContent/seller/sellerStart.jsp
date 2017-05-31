<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>판매자로그인</title>
</head>
<body>
<div>판매자 전용입니다.</div>
<form name="login" method="post" action="./dologin.jsp">
	<label >seller code</label><input id="email" type="text" name="sellercode" placeholder="seller code를 입력해주세요">
	<br>
	<label>Password</label><input type="password" name="pw" placeholder="pw를 입력해주세요.">
	<input id="submit" type="submit" name="submit" value="Login">
</form>
  <!--   <a href="./register.jsp">회원가입하기</a>  -->
</body>
</html>