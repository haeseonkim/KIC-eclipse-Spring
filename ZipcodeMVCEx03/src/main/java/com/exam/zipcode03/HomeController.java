package com.exam.zipcode03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.exam.zipcode03.model1.ZipcodeDAO;
import com.exam.zipcode03.model1.ZipcodeTO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ZipcodeDAO dao;

	@RequestMapping(value = "/zipcode.do")
	public String zipcode() {
		return "zipcode";
	}
	
	// Datasource 사용
	@RequestMapping(value = "/zipcode1_ok.do")
	public String zipcode1(String dong, Locale locale, Model model) {
		ArrayList<ZipcodeTO> lists = dao.searchLists1( dong );
		
		System.out.println(dong);
		System.out.println(locale);
		System.out.println(model);
		
		model.addAttribute( "lists", lists );
		return "zipcode_ok";
	}
	
	// jdbcTemplate 사용
	@RequestMapping(value = "/zipcode2_ok.do")
	public String zipcode_ok(String dong, Locale locale, Model model) {
		
		ArrayList<ZipcodeTO> lists = dao.searchLists2( dong );
		
		model.addAttribute( "lists", lists );
		return "zipcode_ok";
	}
}
