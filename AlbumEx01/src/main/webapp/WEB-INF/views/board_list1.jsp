<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="com.exam.model1.BoardTO" %>
<%@ page import="com.exam.model1.BoardListTO" %>
<%@page import="java.util.ArrayList"%>

<%
	BoardListTO listTO = (BoardListTO)request.getAttribute( "listTO" );
	
	int cpage = listTO.getCpage();
	int recordPerPage = listTO.getRecordPerPage();
	int totalRecord = listTO.getTotalRecord();
	int totalPage = listTO.getTotalPage();
	int blockPerPage = listTO.getBlockPerPage();
	
	int startBlock = listTO.getStartBlock();
	int endBlock = listTO.getEndBlock();
	
	
	ArrayList<BoardTO> boardLists = listTO.getBoardLists();
	StringBuffer sbHtml = new StringBuffer();
	
	int rowCount = 0;
	for(BoardTO to : boardLists) {
		String seq = to.getSeq();
		String subject = to.getSubject();
		String writer = to.getWriter();
		String filename = "./upload/" + to.getFilename();
		int cmt = to.getCmt();
		String wdate = to.getWdate();
		String hit = to.getHit();
		int wgap = to.getWgap();
			
		if(rowCount%5 == 0) {
			sbHtml.append( "<tr>" );
		}
		sbHtml.append( "	<td class='last2'>" );
		sbHtml.append( "		<div class='board'>" );
		sbHtml.append( "			<table class='boardT'>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td class='boardThumbWrap'>" );
		sbHtml.append( "					<div class='boardThumb'>" );
		sbHtml.append( "						<a href='view.do?cpage=" + cpage + "&seq=" + seq + "'><img src='" + filename + "' border='0' width='100%' /></a>" );
		sbHtml.append( "					</div>" );
		sbHtml.append( "				</td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td>" );
		sbHtml.append( "					<div class='boardItem'>" );	
		sbHtml.append( "						<strong>" + subject +"</strong>" );
		sbHtml.append( "						<span class='coment_number'><img src='./images/icon_comment.png' alt='commnet'>" + cmt + "</span>" );
		if(wgap == 0) {
			sbHtml.append( "						<img src='./images/icon_new.gif' alt='NEW'>" );
		}
		sbHtml.append( "					</div>" );
		sbHtml.append( "				</td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td><div class='boardItem'><span class='bold_blue'>" + writer + "</span></div></td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td><div class='boardItem'>" + wdate + " <font>|</font> Hit " + hit + "</div></td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "		</table>" );
		sbHtml.append( "	</div>" );
		sbHtml.append( "</td>" );
		
		if( rowCount%5 == 4 ) {
			sbHtml.append( "</tr>" );
		}
		
		rowCount++;
	}
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/board_list.css">
<style type="text/css">
<!--
	.board_pagetab { text-align: center; }
	.board_pagetab a { text-decoration: none; font: 12px verdana; color: #000; padding: 0 3px 0 3px; }
	.board_pagetab a:hover { text-decoration: underline; background-color:#f2f2f2; }
	.on a { font-weight: bold; }
-->
</style>
</head>

<body>
<!-- 상단 디자인 -->
<div class="contents1"> 
	<div class="con_title"> 
		<p style="margin: 0px; text-align: right">
			<img style="vertical-align: middle" alt="" src="./images/home_icon.gif" /> &gt; 커뮤니티 &gt; <strong>여행지리뷰</strong>
		</p>
	</div> 
	<div class="contents_sub">	
		<div class="board_top">
			<div class="bold">
				<p>총 <span class="txt_orange"><%=totalRecord %></span>건</p>
			</div>
		</div>	
		
		<table class="board_list">
			
			<%= sbHtml %>
			
		</table>
		
		<div class="align_right">		
			<input type="button" value="쓰기" class="btn_write btn_txt01" style="cursor: pointer;" onclick="location.href='write.do?cpage=<%=cpage %>'" />
		</div>
		
		<!--페이지넘버-->
		<div class="paginate_regular">
			<div class="board_pagetab">
			<%
				if(startBlock == 1){
					out.println("<span class='off'>&lt;&lt;&nbsp;&nbsp;</span>");
				}else{
					out.println("<span class='off'><a href='list.do?cpage="+ (startBlock - blockPerPage) +"'>&lt;&lt;</a>&nbsp;&nbsp;</span>");
				}
				
				if(cpage == 1){
					out.println("<span class='off'>&lt;&nbsp;&nbsp;</span>");
				}else{
					out.println("<span class='off'><a href='list.do?cpage="+ (cpage-1) +"'>&lt;</a>&nbsp;&nbsp;</span>");
				}
				
				for(int i=startBlock; i<=endBlock; i++){
					if(i == cpage){
						out.println("<span class='off'>[ "+ i +" ]</span>");
					}else{
						out.println("<span class='off'><a href='list.do?cpage="+ i +"'>["+ i +"]</a></span>");
					}
				}
				
				if(cpage == totalPage){	
					out.println("<span class='off'>&nbsp;&nbsp;&gt;</span>");
				}else{
					out.println("<span class='off'>&nbsp;&nbsp;<a href='list.do?cpage="+ (cpage+1) +"'>&gt;</a></span>");
				}
				
				if(endBlock == totalPage){
					out.println("<span class='off'>&nbsp;&nbsp;&gt;&gt;</span>");
				} else{
					out.println("<span class='off'>&nbsp;&nbsp;<a href='list.do?cpage="+ (startBlock + blockPerPage) +"'>&gt;&gt;</a></span>");
				}
				
			%>
			</div>
		</div>
		<!--//페이지넘버-->	
  	</div>
</div>
<!--//하단 디자인 -->

</body>
</html>
