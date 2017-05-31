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
String startDate = (String)request.getParameter("startDate")+":00";
String endDate = (String)request.getParameter("endDate")+":00";
startDate = startDate.replace("T", " ");
endDate = endDate.replace("T", " ");
%>
<div><%out.print(startDate + "  ~  "+ endDate); %>동안의 best 3 제품</div>
	<table border="1">
	<tr>
		<td>순위</td>
		<td>item code</td>
		<td>item name</td>
		<td>brand</td>
		<td>판매 개수</td>
	</tr>
	<%
	int i = 1;
	List<item> best3Selling = itemDAO.selectBest3Selling(startDate,endDate);
	for( item elem : best3Selling ){
	%>
		<tr>
		<td><%=i %>등</td>
		<td><%=elem.getItemCode() %></td>
		<td><%=elem.getItemName() %></td>
		<td><%=elem.getBrand() %></td>
		<td><%=elem.getCounter() %></td>
		</tr>
	<%
	i++;
	if(i == 4) break;
	}
	%>
	</table>
	

</body>
</html>

