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
<title>seller 로그인</title>
</head>
<body>
<%
	seller _seller = new seller();
	_seller.setSellerCode(request.getParameter("sellercode"));
	_seller.setPassword(request.getParameter("pw"));

	if(sellerDAO.checkAccount(_seller)){
		session.setAttribute("sellercode",_seller.getSellerCode());
		//session.setAttribute("pw",_seller.getPassword());
		response.sendRedirect("./listSeller.jsp");
	}else{
		%> <script> alert("로그인 실패"); history.go(-1);</script> <%
	}
%>
</body>
<a href="./sellerStart">로그인화면으로 돌아가기</a>
</html>