package com.exam.board03;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.exam.board03.model1.BoardDAO;
import com.exam.board03.model1.BoardTO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping("/write.do")
	public ModelAndView write() {
		// TODO Auto-generated method stub
		System.out.println("write() 호출");
		return new ModelAndView("board_write1");
	}
	
	
	@RequestMapping("/write_ok.do")
	public ModelAndView write_ok(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		
		System.out.println("write_ok() 호출");
		
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
	
	@RequestMapping("/list.do")
	public ModelAndView list(){
		System.out.println("list() 호출");
		
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardTO> lists = dao.boardList();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_list1");
		modelAndView.addObject("lists", lists);
		
		return modelAndView;
	}
	
	@RequestMapping("/view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
		System.out.println("view() 호출");
		
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		
		BoardDAO dao = new BoardDAO();
		to = dao.boardView(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_view1");
		modelAndView.addObject("to", to);
		
		return modelAndView;
	}
	
	@RequestMapping("/modify.do")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response){
		System.out.println("modify() 호출");
		
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
	
	@RequestMapping("/modify_ok.do")
	public ModelAndView modify_ok(HttpServletRequest request, HttpServletResponse response){
		System.out.println("modify_ok() 호출");
		
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		to.setPassword(request.getParameter("password"));
		
		to.setSubject(request.getParameter("subject"));
		// 필수 입력 항목이 아닌 경우
		to.setMail("");
		if(!request.getParameter("mail1").equals("") && !request.getParameter("mail2").equals("")){
			to.setMail(request.getParameter("mail1") + "@" + request.getParameter("mail2"));
		}
		to.setContent(request.getParameter("content"));
		
		BoardDAO dao = new BoardDAO();
		int flag = dao.boardModifyOk(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_modify1_ok");
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/delete.do")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response){
		System.out.println("delete() 호출");
		
		String seq = request.getParameter("seq");

		BoardTO to = new BoardTO();
		to.setSeq(seq);	
		
		BoardDAO dao = new BoardDAO();
		to = dao.boardDelete(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_delete1");
		modelAndView.addObject("to", to);
		
		return modelAndView;
	}
	
	@RequestMapping("/delete_ok.do")
	public ModelAndView delete_ok(HttpServletRequest request, HttpServletResponse response){
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
