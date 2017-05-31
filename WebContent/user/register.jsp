<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
<form action=./doregister.jsp method=post>

            <div>개인 정보 입력</div>
        
            <div>email</div>
            <div><input type="email" name="email"
            placeholder ="abc@def.vfg" 
            required/></div>
            
            <!-- 비밀번호 정규식 
            조건1. 6~20 영문 대소문자
            조건2. 최소 1개의 숫자 혹은 특수 문자를 포함해야 함 
            pattern = "^(?=.*[0-9a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$"
            title = "6~20 영문 대소문자와 최소 1개의 숫자 혹은 특수문자를 포함시켜주세요."
            -->
            
            <div>비밀번호</div>
            <div><input type="password" name="pw"
            placeholder ="6~20 영문 대소문자와 최소 1개의 숫자 혹은 특수문자를 포함시켜주세요."
            pattern = "^(?=.*[0-9a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$"
            required></div> 
            
            <div>성</div>
            <div><input type="text" name="familyName" 
            placeholder = "성을 입력해주세요."
            required></div>
            
            <div>이름</div>
            <div><input type="text" name="firstName" 
            placeholder = "이름을 입력해주세요."
            required></div>
            
            <div>생일</div>
            <div><input type="date" name="birthday" 
            placeholder = "생일을 입력해주세요"
            required></div>
            
            <!-- 이름  
            <div class="selectName" id="userName">이름</div>
            <div><input type="text" class="select" id="name" name="name" 
            placeholder = "이름을 입력해주세요."
            required></div>
            성별  
            <div class="selectName" id="sexName">성별</div>
            <div><select class="select" id="sex" name="sex">
                    <option value="man">남자</option>
                    <option value="woman">여자</option>
                </select>
            </div>

             나이            
            <div class="selectName" id="ageName">나이</div>
            <div><input type="age"class="select" id="age" name="age" 
            placeholder ="나이를 입력해주세요."
            required></div>

           전화번호        
            <div class="selectName" id="phoneName">전화번호</div>
            <div><input type="text" class="select" id="telephoneNumber" name="telephoneNumber" 
            placeholder ="전화번호를 입력해주세요."
            pattern = "^\d{2,3}\-\d{3,4}\-\d{4}$"
            required></div>
    -->   
    <button type="submit">가입하기</button>
    </form>
</body>
</html>