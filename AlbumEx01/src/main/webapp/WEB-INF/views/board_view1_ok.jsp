<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	int flag = (Integer)request.getAttribute("flag");

	String cpage = request.getParameter("cpage");
	String seq = request.getParameter("seq");
		
	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('댓글작성에 성공했습니다.');");
		out.println("location.href='view.do?cpage=" + cpage + "&seq=" + seq + "';");
	}else{
		out.println("alert('댓글작성에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
%>








