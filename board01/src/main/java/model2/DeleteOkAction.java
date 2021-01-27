package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import model1.BoardDAO;
import model1.BoardTO;

public class DeleteOkAction implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("DeleteOkAction 호출");
		
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		to.setPassword(request.getParameter("password"));
		
		BoardDAO dao = new BoardDAO();
		int flag = dao.boardDeleteOk(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_delete1_ok");
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}

}
