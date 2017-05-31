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
<div>seller code : <%=sellercode %>의 판매 기록 확인</div>
<%
String sellername = sellerDAO.selectSellerName(sellercode).getSellerName();
%>
<div>판매자 이름:<%=sellername %> </div>
	<table border="1">
	<tr>
		<td>item code</td>
		<td>item name</td>
		<td>brand</td>
		<td>판매 개수</td>
		<td>판매 일시</td>
		<td>구매자 email</td>
		<td>사용한 할인 쿠폰</td>
		<td>결제된 금액(쿠폰할인 적용됨.)</td>
	</tr>
	<%
	List<AllClass> sellerHistoryList = sellHistoryDAO.selectSellerHistory(sellercode);
	for( AllClass elem : sellerHistoryList ){
	%>
		<tr>
		<td><%=elem.getItem().getItemCode() %></td>
		<td><%=elem.getItem().getItemName() %></td>
		<td><%=elem.getItem().getBrand() %></td>
		<td><%=elem.getSellHistory().getNumberOfPurchase() %></td>
		<td><%=elem.getSellHistory().getSelltime() %></td>
		<td><%=elem.getSellHistory().getEmail() %></td>
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
<hr>
<div>seller code : <%=sellercode %>의 판매 개수 확인</div>
	<table border="1">
	<tr>
		<td>item code</td>
		<td>item name</td>
		<td>brand</td>
		<td>총 판매 개수</td>
		<td>재고 개수</td>
	</tr>
	<%
	
	List<item> sellCountList = itemDAO.selectSellCount(sellercode);
	for( item elem : sellCountList ){
		
	%>
		<tr>
		<td><%=elem.getItemCode() %></td>
		<td><%=elem.getItemName() %></td>
		<td><%=elem.getBrand() %></td>
		<td><%=elem.getSales() %></td>
		<td><%=elem.getStock() %></td>
		</tr>
	<%
	}
	%>
	</table>

</body>
</html>

