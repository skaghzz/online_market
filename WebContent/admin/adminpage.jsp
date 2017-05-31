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
<form method="post" action="./clientHistory.jsp" id="emaillist">
<h4>a)고객의 구매 기록 보기</h4>
<div>기록을 볼 고객의 email을 입력하세요.</div>
<select name="email" form="emaillist">
<%
ArrayList<user> userList = userDAO.selectEmail();
for(user e : userList){
%>
	<option value="<%=e.getEmail() %>"><%=e.getEmail() %></option>
<%} %>
</select>
<input type="submit" value="해당 고객 구매 기록 보러가기">
</form>
<hr>

<form method="post" action="./sellerHistory.jsp" id="sellerList1">
<h4>b,c)판매자의 판매 기록, 제품 별 판매 개수를 확인</h4>
<div>기록을 볼 판매자 번호를 입력하세요.</div>
<select name="sellercode" form="sellerList1">
<%
ArrayList<seller> sellerList = sellerDAO.selectSellercode();
for(seller e : sellerList){
%>
	<option value="<%=e.getSellerCode() %>"><%=e.getSellerCode()+" - "+e.getSellerName() %></option>
<%} %>
</select>
<input type="submit" value="해당 판매자 판매 기록 보러가기">
</form>
<hr>

<form method="post" action="./bestItemDuringTime.jsp">
<h4>d)기간동안의 가장 많이 팔린 item 확인 => seller code까지 구별함 .seller code를 구분하지 말고, item으로만 구분한다.)</h4>
<div>시작 날짜</div>
<input type="datetime-local" name="startDate" value="2016-12-01T00:00">
<div>종료 날짜</div>
<input type="datetime-local" name="endDate" value="2016-12-01T00:00">
<input type="submit" value="best3 보러가기">
</form>
<hr>

<form method="post" action="./bestItemExceptOneSeller.jsp" id="sellerList2">
<h4>e)판매자가 안파는 item중 최고의 수익을 가져다 준 제품 best10 => 뭔가 이상하다?ㄴ</h4>
<div>기록을 볼 판매자 번호를 입력하세요.</div>
<select name="sellercode" form="sellerList2">
<%
for(seller e : sellerList){
%>
	<option value="<%=e.getSellerCode() %>"><%=e.getSellerCode()+" - "+e.getSellerName() %></option>
<%} %>
</select>
<input type="submit" value="보러가기">
</form>
<hr>

<form method="post" action="./cartItem.jsp">
<h4>f)item의 재고가 카트에 담긴 전체 양 보다 작은 item</h4>
<input type="submit" value="보러가기">
</form>
<hr>

<form method="post" action="./bestitem20to30.jsp">
<h4>g)20~30대에게 최고로 많이 팔린 상품 best 10 => 영어 잘못 해석함 -> 20대 best 10과 30대 best 10의 공통된 item은 무엇인가?</h4>
<input type="submit" value="보러가기">
</form>
</body>
</html>