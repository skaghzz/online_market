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
<div>20대 ~ 30대가 많이 구매한 best 10 제품</div>
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
	List<item> best20to30 = itemDAO.select20to30BestItem();
	for( item elem : best20to30 ){
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
	if(i == 11) break;
	}
	%>
	</table>
	

</body>
</html>

