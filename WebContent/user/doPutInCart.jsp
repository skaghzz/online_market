<%@ page import="term.*"
import="java.text.*"
import="java.util.*"
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>my cart</title>
<!-- 
구매하고자 하는 것들은 cart에 답는다.
shoppingcart 테이블에 eamil,itemcode,sellercode,numberofcart를 insert한다.
 -->

</head>
<body>
<%
	String email = (String)session.getAttribute("email");
	out.println(email);
%>
<%
	String[] itemCodeList = request.getParameterValues("itemcode");
	String[] sellerCodeList = request.getParameterValues("sellercode");
	String[] itemCountList = request.getParameterValues("itemcount");
	
	List<AllClass> itemList = new ArrayList<>();
	for(int i=0; i < itemCountList.length; i++){
		AllClass _AllClass = new AllClass();
		if(!itemCountList[i].equals("0")){
			user _user = new user();
			item _item = new item();
			seller _seller = new seller();
			_user.setEmail(email);
			_item.setItemCode(itemCodeList[i]);
			_item.setCounter(Integer.parseInt(itemCountList[i]));
			_seller.setSellerCode(sellerCodeList[i]);
			_AllClass.setItem(_item);
			_AllClass.setSeller(_seller);
			_AllClass.setUser(_user);
			itemList.add(_AllClass);
		}
	}
	int[] status = {0,};
	if(!itemList.isEmpty()) {
		status = AllClassDAO.insertCartData(itemList);
		if(status[0] > 0){
			out.println("success");
			%><script> alert("장바구니에 담기 성공"); history.go(-1);</script><%
		}
		else{
			out.println("fail : " + status[0]);
			%><script> alert("장바구니에 담기 실패"); history.go(-1);</script><%
		}
	}else {
		out.println("뭐 담을게 업네");
		%><script> alert("뭐 담을게 없네..."); history.go(-1);</script><%
	}
	%>

</body>
</html>