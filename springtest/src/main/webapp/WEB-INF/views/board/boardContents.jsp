<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.myezen.myapp.domain.BoardVo" %>   
 
 <% BoardVo bv   = (BoardVo)request.getAttribute("bv"); %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script> <!-- jqurey cdn주소 -->
<script type="text/javascript">
$(document).ready(function(){
	
	var originalFileName = getOriginalFileName("<%=bv.getFilename()%>");	//파일이름을 뽑아내기위한 변수
	//alert(originalFileName);	
	
	var str2 = getImageLink("<%=bv.getFilename()%>");
	
	var str = "";		//파일다운로드 경로 변수선언	
	str = "<div><a href='<%=request.getContextPath()%>/board/displayFile.do?fileName="+str2+"&down=1'>"+originalFileName+"</a></div>"; //다운로드 경로 설정
	//경로는 displayFile.do 로 링크를 했고, 컨트롤러에 의해 displayFile 메소드가 실행됨.
	//해당 메소드는 Filename을 매개변수로 받고, ?FileName을 추가하여 bv.getFilename에 담겨있는 원본파일이름을 보냄
	
	$.boardCommentList(0);
	
	$("#download").html(str);
	//.html은 해당 요소안의 내용을 다른내용으로 바꾸거나 (id가 download인 영역 내용을 str로 변경)
	//요소안의 내용을 가져온다. 가져올때는 .html(); , 바꿀때는 .html(변수);
	
	$("#save").click(function(){		//댓글등록버튼
		
		var bidx = <%=bv.getBidx()%>
		var cwriter = $("#cwriter").val();
		var ccontents = $("#ccontents").val();
		var nextBlock = $("#nextBlock").val();
		var midx = <%=session.getAttribute("midx")%>;
		
		$.ajax({
			type : "post",
			url: "<%=request.getContextPath()%>/comment/commentWrite.do",
			datType : "json",
			data : {
				"bidx" : bidx,
				"cwriter": cwriter,
				"ccontents" : ccontents,
				"nextBlock" : nextBlock,
				"midx" : midx
				},
			cache: false,
			success : function(data){
			//	alert("등록성공");	
				$("#cwriter").val("");
				$("#ccontents").val("");
				$.boardCommentList();	//등록시 재호출
			},
			error:function(){
				alert("등록실패");
			}			
		});		
	});
	
	$("#more").click(function(){	//더보기버튼 클릭
		
		var nextBlock = $("#nextBlock").val();
	
		$.ajax({
			
			type:"get",			//더보기 컨트롤러 경로로.
			url:"<%=request.getContextPath()%>/comment/<%=bv.getBidx()%>/"+nextBlock+"/more.do",		
			dataType : "json",
			cache : false,
			success : function(data){
			//	alert("성공");
			//	console.log(data);	//브라우저의 f12(개발자도구) 콘솔에 로그를남김
			//	alert(JSON.stringify(data))
				$("#nextBlock").val(data.nextBlock);	//데이터로 넘어온 nextBlock으로 값 변경
				
			//	 if(nextBlock != data.nextBlock){		//넘어온 nextBlock과 기존 nextBlock값이 다르면
			//		$("#more").css("display","block");	//버튼 보임
			//	}else{										//값이 서로 같으면
			//		$("#morebtn").css("display","none");	//버튼을 감싸는 영역을 감춤.
					//boardCommentList 함수에의해 버튼은 보이게됨. 그러므로 감추려면 아예 영역을 감춤. 
			//	}	boardCommentList에 이미 더보기버튼 활성여부가 포함되어있으므로 필요없음.
												
				$.boardCommentList($("#nextBlock").val());
			},
			error : function(){
				alert("실패");
			}						
		});	
	});
		
		
});

$.boardCommentList = function(nb){
	
	var nextBlock;
	
	if(nb == 0){
		nextBlock = 1;
	}else{
		nextBlock = nb;
	}
	
	
	$.ajax({
		type : "get",
		url: "<%=request.getContextPath()%>/comment/<%=bv.getBidx()%>/"+nextBlock+"/commentList.do",
		dataType : "json",		
		cache: false,
		success : function(data){
		//	alert("성공");	
			console.log(data);
		//	alert(JSON.stringify(data))
			commentList(data.alist);
		
			if(data.moreView == "N"){	//더보기버튼 활성여부 받아옴.
				$("#more").css("display","none");
			}else{
				$("#more").css("display","block");
			}
		},
		error:function(){
			alert("등록실패");
		}
		
	});
}

	


function getOriginalFileName(fileName){
	
	var idx = fileName.indexOf("_")+1;	
	//fileName.lastIndexOf와 fileName.indexOf 2가지가 있다.
	//lastIndexOf는 해당 문자열이 마지막으로 보인위치값을 리턴하고, indexOf는 처음으로 보인위치값을 리턴
	//파일 원본이름은 _(언더바) 이후에 나오므로, indexOf를 하는것이 좋다. 수업시간엔 lastIndexOf를 사용
		
	return fileName.substr(idx);
}

function getImageLink(fileName){
	
	
	
	if(!checkImageType(fileName)){
		return fileName; // 이미지파일이 아닌경우는 그대로 파일이름을 리턴
	}
	
	//db상 파일명의 처음 12글자는 저장된 날짜가 기록된다. (/2023/04/14/)
	
	//폴더위치. 변수는 front
	var front = fileName.substr(0,12);
	
	//파일이름. 변수는 end
	var end = fileName.substr(14);		//15번째 글자. 즉 13,14번째 글자를 제외한다. 이 자리에 s-가 들어감
	return front+end;
	
	//이미지 파일의 경우, 파일이 업로드될 때, 썸네일 이미지가 하나 추가로 생성된다.
	//이 썸네일 이미지파일은 s-가 추가로 붙는데, 다운로드시에 이 s-를 제외해야 원본파일이 다운로드됨.
	//front+end 는 이 s-를 뺀 파일이름 즉, 원본파일의 이름이다.
}

function checkImageType(fileName){
	
	//파일 확장자를 검사. 확장자가 jpg, gif, png, jpeg인지를 확인
	//이 함수의 리턴값은 true, false이다.
	
	var pattern = /jpg&|gif&|png&|jpeg&/i;
	
	return fileName.match(pattern);
}

function commentList(data){
	
	var str = "";
	
	str = "<tr><td>이름</td><td>내용</td></tr>"
	
	$(data).each(function(){	
		str= str+ "<tr><td>"+this.cwriter+"</td><td>"+this.ccontents+"<input type='button' id='del' value='삭제'"
				+ "onclick=commentDelete("+this.cidx+")	>"		
		});
	
	
	//alert(str);
	$("#tbl").html("<table>"+str+"</table>");
		
	return;		
}

function commentDelete(cidx){
	
	var flag = confirm("삭제하시겠습니까?")
	if(flag == false){
		return;		
	}
	
	$.ajax({
		type : "get",
		url: "<%=request.getContextPath()%>/comment/<%=bv.getBidx()%>/"+cidx+"/commentDelete.do",
		dataType : "json",		
		cache: false,
		success : function(data){
				alert("삭제되었습니다.");	
			//	console.log(data);
			//	alert(JSON.stringify(data))
				$.boardCommentList($("#nextBlock").val())	
			},
			error:function(){
				alert("등록실패");
			}
	});
		
}


</script>
</head>
<body>
내용보기
<table  border=1 style="width:500px;">
<tr>
<td style="width:50px;">제목</td>
<td> <%=bv.getSubject() %> &nbsp;&nbsp;&nbsp;&nbsp;  조회수(<%=bv.getViewcnt() %>) </td>
</tr>
<tr>
<td>파일다운로드</td>
<td id="download">
<!-- jquery를 통해 다른내용으로 변경했음. 
<%if (bv.getFilename() ==null){
}else{ %>
<a href="<%=request.getContextPath()%>/board/fileDownload.do?filename=<%=bv.getFilename()%>"><%=bv.getFilename() %></a></td>
<%} %>
 -->
</tr>
<tr>
<td>이미지</td>
<td>
<%
if (bv.getFilename() ==null){
}else{
String exp =  bv.getFilename().substring(bv.getFilename().length()-3, bv.getFilename().length());

if (exp.equals("jpg") || exp.equals("gif") || exp.equals("png")   ) { %>
<img src="<%=request.getContextPath()%>/board/displayFile.do?fileName=<%=bv.getFilename() %>"  width="400px" height="100px"></td>
<%} 
}
%>
</tr>


<tr>
<td style="height:200px;">내용</td>
<td><%=bv.getContents() %>
<%if(bv.getSubject().contains("홍진호")){
	%><br><%=bv.getContents() %><% 
}
%>
</td>
</tr>
<tr>
<td>작성자</td>
<td><%=bv.getWriter()%></td>
</tr>
<tr>
<td colspan=2 style="text-align:right;">
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardModify.do?bidx=<%=bv.getBidx() %>'   ">수정</button>
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardDelete.do?bidx=<%=bv.getBidx() %>'   ">삭제</button>
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardReply.do?bidx=<%=bv.getBidx() %>&originbidx=<%=bv.getOriginbidx()%>&depth=<%=bv.getDepth()%>&level_=<%=bv.getLevel_()%>'   ">답변</button>
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardList.do'">목록</button>
</td>
</tr>
</table>

<table>
<tr>
<td>이름</td>
<td><input type="text" id="cwriter" size="10"></td>
<td></td>
</tr>
<tr>
<td>내용</td>
<td><textarea id="ccontents" cols=50 rows=3></textarea></td>
<td><input type="button" name="btn" id="save" value="등록"></td>
</tr>
</table>
<input id="nextBlock" type="hidden" value=1 />

<div id="tbl"></div>
<div id="morebtn">
<button id="more">더보기</button>
</div>
</body>
</html>