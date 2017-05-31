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
String email = (String)request.getParameter("email");
%>
<div><%out.print(email); %>고객의 구매 기록 확인</div>
	<table border="1">
	<tr>
		<td>item code</td>
		<td>제품이름</td>
		<td>brand</td>
		<td>seller code</td>
		<td>판매자 이름</td>
		<td>구매 개수</td>
		<td>구입 시간</td>
		<td>사용한 할인 쿠폰</td>
		<td>결제된 금액(쿠폰할인 적용됨)</td>
	</tr>
	<%
	List<AllClass> _Allclass = sellHistoryDAO.selectUserHistory(email);
	for( AllClass elem : _Allclass ){
		
	%>
		<tr>
		<td><%=elem.getItem().getItemCode() %></td>
		<td><%=elem.getItem().getItemName() %></td>
		<td><%=elem.getItem().getBrand() %></td>
		<td><%=elem.getSeller().getSellerCode() %></td>
		<td><%=elem.getSeller().getSellerName() %></td>
		<td><%=elem.getSellHistory().getNumberOfPurchase() %></td>
		<td><%=elem.getSellHistory().getSelltime() %></td>
		<td><%=elem.getSellHistory().getCoupon() %>%</td>
		<%
		int price = elem.getItem().getPrice();
		int count = elem.getSellHistory().getNumberOfPurchase();
		double discount = 100 - Integer.parseInt(elem.getSellHistory().getCoupon()) ;
		int totalprice = (int)((price*count)*((discount)/100));
		%>
		<td><%=totalprice %>원</td>
		</tr>
	<%
	}
	%>
	</table>

</body>
</html>

