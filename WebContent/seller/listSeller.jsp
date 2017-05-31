<%@ page 
import="term.*"
import="java.util.*" 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PNUIPS seller</title>
</head>
<body>
<body>
<%
String sellercode = (String)session.getAttribute("sellercode");
%>
<%
String sellername = sellerDAO.selectSellerName(sellercode).getSellerName();

%>
<div>seller code : <%=sellercode %></div>
<div><%=sellername %>님 환영합니다.</div>
<div>현재 판매 item 현황</div>
<table border="1">
<tr>
	<td>item code</td>
	<td>제품명</td>
	<td>브랜드</td>
	<td>가격</td>
	<td>재고량</td>
	<td>판매량</td>
</tr>
<%
ArrayList<item> listitem = itemDAO.selectSellerItem(sellercode);
for(item i : listitem){
%>
	<tr>
	<td><%=i.getItemCode()%></td>
	<td><%=i.getItemName()%></td>
	<td><%=i.getBrand()%></td>
	<td><%=i.getPrice()%></td>
	<td><%=i.getStock()%></td>
	<td><%=i.getSales()%></td>
	</tr>
<%
}
%>
</table>
</div>
<a href="./registerItem.jsp"><button>물건 등록하기</button></a>
</body>
</body>
</html>