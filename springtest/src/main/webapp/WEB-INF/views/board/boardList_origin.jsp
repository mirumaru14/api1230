<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>  
 <%@ page import="com.myezen.myapp.domain.*" %> 
<% 
ArrayList<BoardVo> blist  = (ArrayList<BoardVo>)request.getAttribute("blist");
PageMaker   pm = (PageMaker)request.getAttribute("pm");
//out.println("전체갯수:"+pm.getTotalCount());
%>     
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
<form name="frm" action="<%=request.getContextPath() %>/board/boardList.do" method="post">
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
<% for(BoardVo bv : blist) {%>
<tr>
<td><%=bv.getBidx() %></td>
<td>
<% for (int i=1; i<=bv.getLevel_();i++) {
		out.println("&nbsp;&nbsp;");
		if (i == bv.getLevel_()){
			out.println("ㄴ");
		}		
}
%>
<a href="<%=request.getContextPath() %>/board/boardContents.do?bidx=<%=bv.getBidx() %>"><%=bv.getSubject() %></a></td>
<td><%=bv.getWriter() %></td>
<td><%=bv.getWriteday().substring(0, 10) %></td>
</tr>
<%} %>

</table>
<table border=0 style="width:300px;width:800px;">
<tr>
<td style="text-align:right;">
<% if (pm.isPrev()) {%>
<a href="<%=request.getContextPath() %>/board/boardList.do?page=<%=pm.getStartPage()-1%>&searchType=<%=pm.getScri().getSearchType()%>&keyword=<%=pm.encoding(pm.getScri().getKeyword()) %> "> ◀</a>
<%} %>
</td>
<td style="text-align:center;width:300px;">
<% 
for (int i = pm.getStartPage(); i<=pm.getEndPage(); i++){
%>	
	<a href="<%=request.getContextPath()%>/board/boardList.do?page=<%=i%>&searchType=<%=pm.getScri().getSearchType()%>&keyword=<%=pm.encoding(pm.getScri().getKeyword())%>"><%=i %></a>
<%	
}
%>

</td>
<td style="width:200px;text-align:left;">

<% if (pm.isNext() && pm.getEndPage() >0) {%>
<a href="<%=request.getContextPath()%>/board/boardList.do?page=<%=pm.getEndPage()+1%>&searchType=<%=pm.getScri().getSearchType()%>&keyword=<%=pm.encoding(pm.getScri().getKeyword())%>">▶</a>
<%} %>
</td>
</tr>
</table>


<a href="<%=request.getContextPath()%>/board/boardWrite.do">글쓰기</a>
</body>
</html>