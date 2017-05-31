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
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 <style>
   /* Remove the navbar's default rounded borders and increase the bottom margin */ 
   .navbar {
     margin-bottom: 50px;
     border-radius: 0;
   }
   
   /* Remove the jumbotron's default bottom margin */ 
    .jumbotron {
     margin-bottom: 0;
   }
  
   /* Add a gray background color and some padding to the footer */
   footer {
     background-color: #f2f2f2;
     padding: 25px;
   }
 </style>
 <script type="text/javascript">
//쿠폰 선택에 따라 비용이 달라진다.
$(document).ready(function() {
	$('#price').val(parseInt($("#totalprice").text()));
	$("input[name=coupon]").change(function(){
		var realprice;
		var radioValue = $(this).val();
		if(radioValue == "nocoupon"){//쿠폰사용X
			alert("nocoupon");
			realprice = $("#totalprice").text();
			$("#couponNumber").val(0);
		}else if(radioValue == "tencoupon"){//10%쿠폰사용
			alert("10%");
			realprice = $("#totalprice").text() * 0.9;
			$("#couponNumber").val(10);
		}else if(radioValue == "thirtycoupon"){//30%쿠폰사용
			alert("30%");
			realprice = $("#totalprice").text() * 0.7;
			$("#couponNumber").val(30);
		}
		$("#price").val(parseInt(realprice));
		$("#finalPrice").text(parseInt(realprice)+"원");
	})
});
 </script>
<title>my cart</title>
<!-- 
(session)email을 이용해서 client의 쿠폰정보를 가져옴
shoppingcart 테이블에서 email의 구매 정보 가져옴, item 테이블과 조인하여 item현황도 같이 가져옴
 -->

</head>
<body>
<%
	String email = (String)session.getAttribute("email");
	user _user = userDAO.selectUser(email);
	int finalPrice = 0;
%>
<%

//사용자는 구매량에 따라서 Regula, VIP, VVIP의 3가지로 분류됨
int amountOfPurchase = userDAO.selectUser(email).getAmountOfPurchase();
String level = null;
if(0 <= amountOfPurchase && amountOfPurchase < 200000){
	level = "Regular";
}else if(200000 <= amountOfPurchase && amountOfPurchase < 500000){
	level = "VIP";
}else if(500000 <= amountOfPurchase){
	level = "VVIP";
}

%>
<div class="jumbotron">
  <div class="container text-center">
    <a href="./listUser.jsp"><h1>PNUIPS</h1></a>
    <p>My Cart</p>
  </div>
</div>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
		  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>                        
		  </button>
		  <a class="navbar-brand" href="./listUser.jsp">PNUIPS</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
		  <ul class="nav navbar-nav">
		    <li><a href="#"><%=email %></a></li>
		    <li><a href="#"><%=_user.getAmountOfPurchase() %>원</a></li>
		    <li><a href="#">회원등급 : <%=level %></a></li>
		    <li><a href="#"><%=_user.getFamilyName()+" "+_user.getFirstName() %>님 반갑습니다.</a></li>
		  </ul>
		  <ul class="nav navbar-nav navbar-right">
		  	<li><a href="./myhistory.jsp"><span class="glyphicon glyphicon-user"></span> History</a></li>
		    <li><a href="./mycart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
		    <li><a href="./logout.jsp">Logout</a></li>
		  </ul>
		</div>
  </div>
</nav>

<div class="container">제거된 항목 - 현재 재고가 카트에 담긴 양보다 작은 것들은 장바구니에서 제거됩니다. 다시 담아주세요.</div>

<%
List<item> itemList = cartItemDAO.selectLessStock(email);
for(int i = 0; i < itemList.size(); i++){
	item elem = itemList.get(i);
	if(i%4 == 0){%>
	<div class="container">
  		<div class="row">
  	<%}%>
  	
    <div class="col-sm-3">
      <div class="panel panel-danger">
        <div class="panel-heading">
        	<div><%=elem.getItemName() %></div>
        	<div><%=elem.getBrand() %></div>
        </div>
        <div class="panel-body">
        	<div>판매자 : <%=sellerDAO.selectSellerName(elem.getSellerCode()).getSellerName() %></div>
        	<div>재고 : <%=elem.getStock() %>개</div>
        </div>
        <div class="panel-footer">제거된 항목</div>
        </div>

     </div>
   
  <%if(i%4 == 3 || i == itemList.size()-1){%>
    </div>
  	</div><br>
  <%} %>
<%}
cartItemDAO.deleteLessStock(email);
%>
<hr>
<div class="container">구매할 제품</div>
<%
List<item> listitem2 = itemDAO.selectCartItem(email);
for(int i = 0; i < listitem2.size(); i++){
	item elem = listitem2.get(i);
	if(i%4 == 0){%>
	<form method="post" action="./dobuy.jsp">
	<div class="container">
  		<div class="row">
  	<%}%>
  	
    <div class="col-sm-3">
      <div class="panel panel-primary">
        <div class="panel-heading">
        	<div><%=elem.getItemName() %></div>
        	<div><%=elem.getBrand() %></div>
        </div>
        <div class="panel-body">
        	<div>개당 가격 : ￦<%=elem.getPrice() %></div>
        	<div>판매자 : <%=sellerDAO.selectSellerName(elem.getSellerCode()).getSellerName() %></div>
        	<div>재고 : <%=elem.getStock() %>개</div>
        	<div>담긴 개수 : <%=elem.getCounter() %>개</div>
        </div>
        <div class="panel-footer">
        	합계 가격 : ￦<%=elem.getTotalPrice()%>	
        	<% finalPrice += elem.getTotalPrice(); %>
        </div>
        </div>

      </div>
   
  <%if(i%4 == 3 || i == listitem2.size()-1){%>
    </div>
  	</div><br>
  <%} %>
<%}%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<div class="panel panel-success">
				<div class="panel-heading">
				결제 정보
				</div>
				<div class="panel-body">
				<%
				int[] coupon = new int[100];
				coupon = couponDAO.getCoupon(email);
				for(int i = 1; i < 100; i++){
					if(coupon[i] > 0){%>
				<div><%=i%>% 할인쿠폰개수 : <%=coupon[i] %></div>
				  <%}
				}%>
				<div>구매 가격 : <span id="totalprice"><%=finalPrice %></span>원</div>
				<div>쿠폰 사용안함 :<input id="nocoupon" type="radio" name="coupon" value="nocoupon"></div>
				<%if(coupon[10] > 0){%>
				<div>10%쿠폰 사용 <input id="tencoupon" type="radio" name="coupon" value="tencoupon"></div>
				<%}
				if(coupon[30] > 0){%>
				<div>30%쿠폰 사용 <input id="thirtycoupon" type="radio" name="coupon" value="thirtycoupon"></div>
				<%}%>
				<div>최종 가격</div>
				<div id=finalPrice><%=finalPrice %>원</div>
				<input type="hidden" id="price" name="price" value="0">
				<input type="hidden" id="couponNumber" name="couponNumber" value="0">
				</div>
				<div class="panel-footer">
				<input type="submit" class="btn btn-info btn-block" value="BUY">
				</div>
			</div>
		</div>
	</div>
</div>
</form>
<footer class="container-fluid text-center">
  <p>2016 DataBases term project : trading system</p> 
</footer>
<script>
 </script>
</body>
</html>
