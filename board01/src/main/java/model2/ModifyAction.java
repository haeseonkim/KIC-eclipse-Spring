package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import model1.BoardDAO;
import model1.BoardTO;

public class ModifyAction implements Controller {


	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ModifyAction 호출");
		
		String seq = request.getParameter("seq");

		BoardTO to = new BoardTO();
		to.setSeq(seq);	
		
		BoardDAO dao = new BoardDAO();
		to = dao.boardView(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_modify1");
		modelAndView.addObject("to", to);
		
		return modelAndView;
	}

}
