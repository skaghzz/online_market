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
<title>PNUIPS user</title>
</head>
<body>
<%
String searchkey = null;
String searchvalue = null;
if(request.getParameter("searchkey") != null){
	searchkey = (String)request.getParameter("searchkey");
	searchvalue = (String)request.getParameter("searchvalue");
}


String email = (String)session.getAttribute("email");
user _user = userDAO.selectUser(email);
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
    <p>Select it! Put it!</p>
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

<div class="container">
	<div class = row>
	<form method="get" action="./listUser.jsp" id="searchform">
	<select name="searchkey" form="searchform">
		<option value="itemname">제품명</option>
		<option value="brand">brand</option>
		<option value="sellername">판매자명</option>
	</select>
	<input type="text" name="searchvalue">
	<input type="submit" class="btn btn-info" value="검색">
	</form>
	<br>
	</div>
</div>

<!-- 
장바구니에 답기 listuser.jsp => registercart.jsp 
장바구니 보기 listuser.jsp => mycart.jsp
-->
<%
ArrayList<AllClass> listitem = null;
if(searchkey != null){
	listitem = itemDAO.selectSearchitem(searchkey,searchvalue);
}else{
	listitem = itemDAO.selectAllitem();
}
for(int i = 0; i < listitem.size(); i++){
	AllClass elem = listitem.get(i);
	if(i%4 == 0){%>
	<form method="post" action="./doPutInCart.jsp">
	<div class="container">
  		<div class="row">
  	<%}%>
  	
    <div class="col-sm-3">
      <div class="panel panel-primary">
        <div class="panel-heading">
        	<div><%=elem.getItem().getItemName() %></div>
        	<div><%=elem.getItem().getBrand() %></div>
        </div>
        <div class="panel-body">
        	<div>가격 : ￦<%=elem.getItem().getPrice() %></div>
        	<div>판매자 : <%=elem.getSeller().getSellerName() %></div>
        	<div>재고 : <%=elem.getItem().getStock() %></div>
        	<div>총 판매개수 : <%=elem.getItem().getSales() %></div>
        </div>
        <div class="panel-footer">
        	구입 희망 개수 : <input type="number" name="itemcount" value=0 min=0 max=<%=elem.getItem().getStock()%>>	
        </div>
        </div>
        <input type="hidden" name ="itemcode" value=<%=elem.getItem().getItemCode() %>>
		<input type="hidden" name ="sellercode" value=<%=elem.getItem().getSellerCode() %>>
      </div>
   
  <%if(i%4 == 3 || i == listitem.size()-1){%>
    </div>
  	</div><br>
  <%} %>
<%
}
%>
<div class="container">
	<div class = row>
	<input type="submit" class="btn btn-info btn-block" value="장바구니에 넣기">
	</div>
</div>
</form>

<footer class="container-fluid text-center">
  <p>2016 DataBases term project : trading system</p> 
</footer>

</body>
</html>