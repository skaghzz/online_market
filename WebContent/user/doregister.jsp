<%@ page import="term.*"
import="java.text.*"
import="java.util.*"
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
<%
	user u = new user();
	u.setEmail(request.getParameter("email"));
	u.setFamilyName(request.getParameter("familyName"));
	u.setFirstName(request.getParameter("firstName"));
	u.setbirthDate(request.getParameter("birthday"));
	u.setPassword(request.getParameter("pw"));
	out.println(request.getParameter("email"));
	out.println(request.getParameter("familyName"));
	out.println(request.getParameter("firstName"));
	out.println(request.getParameter("birthday"));
	
	int status = userDAO.insertUser(u);
	if(status > 0) out.println("회원가입 성공");
	else out.println("회원가입 실패 : " + status);
%>
</body>
<a href="../index.jsp">로그인화면으로 돌아가기</a>
</html>