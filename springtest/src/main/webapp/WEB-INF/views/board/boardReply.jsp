<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import ="com.myezen.myapp.domain.BoardVo" %>   
 <% BoardVo bv = (BoardVo)request.getAttribute("bv"); %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글답변화면</title>
<script type="text/javascript">
function check(){	

	var fm = document.frm;	
	if (fm.subject.value == "" ){
		alert("제목을 입력하세요");
		fm.subject.focus();
		return;
	}else if (fm.contents.value == ""){
		alert("내용을 입력하세요");
		fm.contents.focus();
		return;
	}else if (fm.writer.value == ""){
		alert("작성자를 입력하세요");
		fm.writer.focus();
		return;
	}
	
	fm.action = "<%=request.getContextPath()%>/board/boardReplyAction.do";
	fm.enctype ="multipart/form-data";
	fm.method="post";
	fm.submit();
	return;
}


</script>
</head>
<body>
게시판 글답변
<form name="frm">
<input type="hidden" name="bidx" value="<%=bv.getBidx() %>">
<input type="hidden" name="originbidx" value="<%=bv.getOriginbidx()%>">
<input type="hidden" name="depth" value="<%=bv.getDepth()%>">
<input type="hidden" name="level_" value="<%=bv.getLevel_()%>">
<table  border=1 style="width:500px;">
<tr>
<td>제목</td>
<td><input type="text" name="subject"></td>
</tr>
<tr>
<td>내용</td>
<td><textarea name="contents" cols="50"  rows="5"></textarea></td>
</tr>
<tr>
<td>작성자</td>
<td><input type="text" name="writer" maxlength=10></td>
</tr>
<tr>
<td>비밀번호</td>
<td><input type="password" name="pwd" ></td>
</tr>
<tr>
<td>파일첨부</td>
<td><input type="file" name="filename"></td>
</tr>

<tr><td colspan=2>
<input type="button" name="btn"  value="확인" onclick="check();">
<input type="reset" name="rst" value="리셋">
</td></tr>

</table>
</form>
</body>
</html>