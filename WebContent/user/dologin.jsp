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
<title>로그인</title>
</head>
<body>
<%
	user u = new user();
	u.setEmail((String)request.getParameter("email"));
	u.setPassword((String)request.getParameter("pw"));
	
	if(userDAO.checkEmail(u)){
		session.setAttribute("email",u.getEmail());
		response.sendRedirect("./listUser.jsp");
	}else{
		%> <script> alert("로그인 실패"); history.go(-1);</script> <%
	}
%>
</body>
</html>