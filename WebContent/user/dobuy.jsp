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
<title>구매처리</title>
<!-- 
- 여기서 할 일
1. shoppingcart에 있는 데이터 삭제, sellhistory에 그 기록 적기
2. item의 판매 개수 증가, 재고 개수 낮추기
3. user의 totalpurchase 증가, 등급 승급을 검사한다.
4. 등급이 올라가면 쿠폰을 지급
-->
</head>
<body>
<%
    String email = (String)session.getAttribute("email");
	String strPrice = request.getParameter("price");
	int totalprice = Integer.parseInt(strPrice);//결제된 가격
	String couponNumber = request.getParameter("couponNumber");
	
	out.println("<div>계정 : "+email+"</div>");
	out.println("<div>구매가격 : "+totalprice);
	out.println("사용쿠폰 : "+couponNumber+"%</div>");
    
	//update(물건사기)이전의 구매 비용
	int beforeTotalOfPurchase = userDAO.selectUser(email).getAmountOfPurchase();
	
    //sellhistory에 기록.
    int status1 = sellHistoryDAO.insertCartToSellHistory(email,couponNumber);
	if(status1 > 0) out.println("success1");
	else out.println("<div>fail1 : " + status1+"</div>");
	
	//item의 재고와 판매 개수를 변경
	int status2 = itemDAO.updateSellCount(email);
	if(status2 > 0) out.println("success2");
	else out.println("<div>fail2 : " + status2+"</div>");
	
	//사용자 총 구매비용 증가
	int status3 = userDAO.updateTotalBuyPrice(email, totalprice);
	if(status3 > 0) out.println("success3");
	else out.println("<div>fail3 : " + status3+"</div>");
	
	//shoppingcart data는 제거
	int status4 = cartItemDAO.deleteCartItem(email);
	if(status4 > 0) out.println("success4");
	else out.println("<div>fail4 : " + status4+"</div>");
		
	//update한 후 구매 비용 확인, 쿠폰 증정
	int afterAmountOfPurchase = userDAO.selectUser(email).getAmountOfPurchase();
	if(beforeTotalOfPurchase < 200000 && 200000 <= afterAmountOfPurchase && afterAmountOfPurchase < 500000){
		//Regular -> VIP, 10%
		userDAO.updateGiveOneCoupon(email,10);
		out.println("<div>VIP가 되었네요 10%쿠폰 증정</div>");
	}else if(beforeTotalOfPurchase < 200000 && 500000 <= afterAmountOfPurchase){
		//Regualr -> VVIP, 10%,30%
		userDAO.updateGiveOneCoupon(email,10);
		userDAO.updateGiveOneCoupon(email,30);
		out.println("<div>VVIP가 되었네요 10%,30%쿠폰 증정</div>");
	}else if(beforeTotalOfPurchase >= 200000 && beforeTotalOfPurchase < 500000 && afterAmountOfPurchase >= 500000){
		//VIP -> VVIP, 30%
		userDAO.updateGiveOneCoupon(email,30);
		out.println("<div>VVIP가 되었네요 30%쿠폰 증정</div>");
	}
	
	//사용자의 쿠폰 개수 감소
	int status5 = userDAO.updateCouponNumber(email,couponNumber);
	if(status5 > 0) out.println("success5");
	else out.println("fail5 : " + status5);
	
%>
</body>
<div><a href="./listUser.jsp">메인화면으로 돌아가기</a></div>
<script>alert("구입완료");</script>
<script>location.replace('./listUser.jsp');</script>
</html>