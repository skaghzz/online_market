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
	item i = new item();
	i.setItemCode(request.getParameter("itemcode"));
	i.setItemName(request.getParameter("itemname"));
	i.setBrand(request.getParameter("brand"));
	i.setPrice(Integer.parseInt(request.getParameter("price")));
	i.setSales(0);
	i.setStock(Integer.parseInt(request.getParameter("numberofstock")));
	i.setSellerCode((String)session.getAttribute("sellercode"));
	
	int status = itemDAO.insertItem(i);
	if(status > 0) out.println("success");
	else out.println("fail : " + status);
%>
</body>
<a href="./sellerStart.jsp">메인화면으로 돌아가기</a>
</html>