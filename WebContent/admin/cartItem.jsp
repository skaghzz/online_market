<%@ page 
import="term.*"
import="java.util.*" 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PNUIPS admin Page</title>
</head>
<body>
<%
//각 고객의 구매 기록 확인
%>
<div>현재 모두의 장바구니에 담긴 개수보다 재고가 부족한 item들</div>
	<table border="1">
	<tr>
	<td>seller code</td>
	<td>item code</td>
	<td>item name</td>
	<td>brand</td>
	<td>현재 재고 개수</td>
	<td>장바구니에 담긴 개수</td>
	</tr>
	<%
	List<item> cartItem = cartItemDAO.selectCartItem();
	for( item elem : cartItem ){
	%>
	<tr>
	<td><%=elem.getSellerCode() %></td>
	<td><%=elem.getItemCode() %></td>
	<td><%=elem.getItemName() %></td>
	<td><%=elem.getBrand() %></td>
	<td><%=elem.getStock() %></td>
	<td><%=elem.getCounter() %></td>
	</tr>
	<%
	}
	%>
	</table>
</body>
</html>

