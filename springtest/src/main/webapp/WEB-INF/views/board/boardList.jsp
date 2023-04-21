<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>   

     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
a:link{
text-decoration:none;
}

</style>
</head>
<body>
게시판 목록
<form name="frm" action="${pageContext.request.contextPath }/board/boardList.do" method="post">
<table border=0 style="text-align:left;width:800px;">
<tr>
<td style="width:600px;"></td>
<td>
<select name="searchType">
<option value="subject">제목</option>
<option value="writer">작성자</option>
</select>
</td>
<td><input type="text" name="keyword" size="10"></td>
<td><input type="submit" name="submit" value="검색"></td>
</tr>
</table>
</form>
<table border=1 style="text-align:left;width:800px;">
<tr>
<td>게시물번호</td>
<td>제목</td>
<td>작성자</td>
<td>날짜</td>
</tr>
<c:forEach var="bv" items="${blist}">
<tr>
<td>${bv.bidx}</td>
<td>

<c:forEach var="i"  begin="1"  end="${bv.level_ }"  step="1">
	&nbsp;&nbsp;
<c:if test="${ i ==bv.level_  }"></c:if>
	ㄴ	
</c:forEach>
 
<a href="${pageContext.request.contextPath }/board/boardContents.do?bidx=${bv.bidx}">${bv.subject }</a></td>
<td>${ bv.writer }</td>
<td>${bv.writeday.substring(0, 10)}</td>
</tr>
</c:forEach>

</table>

<table border=0 style="width:300px;width:800px;">
<tr>
<td style="text-align:right;">
<c:if test="${ pm.prev == true }">
<a href="${pageContext.request.contextPath }/board/boardList.do?page=${pm.startPage()-1}&searchType=${ pm.scri.searchType}&keyword=${ pm.encoding(pm.scri.keyword) } "> ◀</a>
</c:if>
</td>
<td style="text-align:center;width:300px;">
<c:forEach var="i"  begin="${pm.startPage}" end="${pm.endPage}"  step="1" >
	<a href="${pageContext.request.contextPath }/board/boardList.do?page=${ i }&searchType=${pm.scri.searchType}&keyword=${ pm.encoding(pm.scri.keyword) } ">${ i }</a>
</c:forEach>	
</td>
<td style="width:200px;text-align:left;">
<c:if test="${pm.next&&pm.endPage >0 }">
<a href="${pageContext.request.contextPath }/board/boardList.do?page=${pm.endPage+1}&searchType=${pm.scri.searchType}&keyword=${ pm.encoding(pm.scri.keyword) } ">▶</a>
</c:if>
</td>
</tr>
</table>

<a href="${pageContext.request.contextPath }/board/boardWrite.do">글쓰기</a>
<a href="${pageContext.request.contextPath }/">홈</a>
</body>
</html>