<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>item등록</title>
</head>
<body>
<form action=./doregisterItem.jsp method=post>

            <div>물건 정보 입력</div>
        
            <div>item code</div>
            <div><input type="text" name="itemcode"
            placeholder ="6자리 숫자" 
            pattern = "[0-9]{6}"
            required/></div>
            
            <!-- 비밀번호 정규식 
            조건1. 6~20 영문 대소문자
            조건2. 최소 1개의 숫자 혹은 특수 문자를 포함해야 함 
            pattern = "^(?=.*[0-9a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$"
            title = "6~20 영문 대소문자와 최소 1개의 숫자 혹은 특수문자를 포함시켜주세요."
            -->
            
            <div>item name</div>
            <div><input type="text" name="itemname"
            required></div> 
            
            <div>brand</div>
            <div><input type="text" name="brand" 
            required></div>
            
            <div>price</div>
            <div><input type="text" name="price"
            pattern = "[0-9]{1,}"
            required></div>
            
            <div>재고 개수</div>
            <div><input type="text" name="numberofstock" 
            pattern = "[0-9]{1,}"
            required></div>
            

    <button type="submit">제품 등록하기</button>
    </form>
</body>
</html>