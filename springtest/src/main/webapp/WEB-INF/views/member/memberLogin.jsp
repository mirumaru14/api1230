<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String msg="";
if(request.getAttribute("msg")!=null){
	msg = (String)request.getAttribute("msg"); 
}%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원로그인</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		//자동실행영역
	var msg = "<%=msg%>";
	if (msg!=""){
		alert(msg);
	}
	
	//컨트롤에서 msg에 아이디 비밀번호 확인 경고문구를 넣었고
	//getAttribute를 통해 msg를 가져왔고 그 문구를 msg에 담음.
});
function check(){
	//alert("각 값이 있는지 체크하는 구문을 만들어보세요");	
	
	if ($("#memberId").val() == ""){
		alert("아이디를 입력하세요");
		$("#memberId").focus();
		return;
	}else if ($("#memberPwd").val() == ""){
		alert("비밀번호를 입력하세요");
		$("#memberPwd").focus();
		return;
	}
	
	var fm = document.frm;
	//이 경로로 데이터를 감추어서 전송한다
	fm.action ="<%=request.getContextPath()%>/member/memberLoginAction.do";    
	fm.method = "post";
	fm.submit();   // 전송
	
	return;
}	


</script>
</head>
<body>
회원로그인 페이지
<form name="frm" id="frm">
<table style="border:1px solid #CCC;width:500px">
<tr>
<td>아이디</td>
<td>
<input type="text" name="memberId" id="memberId">
</td>
</tr>
<tr>
<td>비밀번호</td>
<td><input type="password" name="memberPwd" id="memberPwd"></td>
</tr>
<tr>
<td></td>
<td><input type="button" name="btn"  id="btn" value="확인" onclick="check();"></td>
</tr>
</table>
</form>
</body>
</html>