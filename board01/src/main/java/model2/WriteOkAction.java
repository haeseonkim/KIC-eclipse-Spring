package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import model1.BoardDAO;
import model1.BoardTO;

public class WriteOkAction implements Controller{

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("WriteOkAction 호출");
		
		// -------------- write할 값을 to객체 안에 모두 set하기
		BoardTO to = new BoardTO();
		to.setSubject(request.getParameter("subject"));
		to.setWriter(request.getParameter("writer"));
		// 필수 입력 항목이 아닌 경우
		to.setMail("");
		
		if(!request.getParameter("mail1").equals("") && !request.getParameter("mail2").equals("")){
			to.setMail(request.getParameter("mail1") + "@" + request.getParameter("mail2"));
		}
		to.setPassword(request.getParameter("password"));
		to.setContent(request.getParameter("content"));
		
		to.setWip(request.getRemoteAddr());
		
		
		// -------------- to 객체를 가지고 실제 db와 실행시킨후 제대로 됐는지 flag값 받아오기
		BoardDAO dao = new BoardDAO();
		int flag = dao.boardWriteOk(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_write1_ok");
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}

}
