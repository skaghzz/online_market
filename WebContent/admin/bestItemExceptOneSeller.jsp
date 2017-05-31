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
String sellercode = (String)request.getParameter("sellercode");
%>
<div>seller code : <%=sellercode %>가 판매하는 제품을 제외한 최고의 수익을 준 item best 10</div>
	<table border="1">
	<tr>
	<td>순위</td>
	<td>seller code</td>
	<td>item code</td>
	<td>item name</td>
	<td>brand</td>
	<td>판매 개수</td>
	</tr>
	<%
	int i = 1;
	List<item> best10Item = itemDAO.selectBest10Item(sellercode);
	for( item elem : best10Item ){
	%>
	<tr>
	<td><%=i %>위</td>
	<td><%=elem.getSellerCode() %></td>
	<td><%=elem.getItemCode() %></td>
	<td><%=elem.getItemName() %></td>
	<td><%=elem.getBrand() %></td>
	<td><%=elem.getSales() %></td>
	</tr>
	<%
	i++;
	if(i == 11) break;
	}
	%>
	</table>
	<hr>
	<div>수익 best 10 item중, <%=sellercode %>가 판매하는 제품을 제외</div>
	<table border="1">
	<tr>
	<td>seller code</td>
	<td>item code</td>
	<td>item name</td>
	<td>brand</td>
	<td>가격</td>
	<td>판매 개수</td>
	</tr>
	<%
	List<item> best10Item2 = itemDAO.selectBest10ItemExceptSeller(sellercode);
	for( item elem : best10Item2 ){
	%>
	<tr>
	<td><%=elem.getSellerCode() %></td>
	<td><%=elem.getItemCode() %></td>
	<td><%=elem.getItemName() %></td>
	<td><%=elem.getBrand() %></td>
	<td><%=elem.getPrice() %></td>
	<td><%=elem.getSales() %></td>
	</tr>
	<%
	}
	%>
	</table>
	
</body>
</html>

