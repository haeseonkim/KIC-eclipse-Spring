package model2;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import model1.ZipcodeDAO;
import model1.ZipcodeTO;

public class ZipcodeOkAction implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		
		System.out.println("ZipcodeAction 호출");
		
		
		// model1 부분 호출
		String strDong = arg0.getParameter("dong");
		
		ZipcodeDAO dao = new ZipcodeDAO();
		ArrayList<ZipcodeTO> lists = dao.searchLists(strDong);
		
		System.out.println(lists.size());
		
		// 데이터를 ok 페이지로 넘겨주는 부분
		// 뷰페이지명 말고도 넘겨줄 데이터가 존재하므로
		// ModelAndView를 기본 생성자를 만든 후에 내재된 함수로 view를 set하고 data를 담는다.
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("zipcode_ok");
		modelAndView.addObject("lists", lists);
		
		return modelAndView;
	}

	

}
