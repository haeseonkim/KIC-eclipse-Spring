package com.exam.album01;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exam.config.SqlMapperInter;
import com.exam.model1.BoardDAO;
import com.exam.model1.BoardListTO;
import com.exam.model1.BoardTO;
import com.exam.model1.CommentDAO;
import com.exam.model1.CommentTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private BoardDAO dao;
	@Autowired
	private CommentDAO cdao;
	private String uploadPath = "C:\\eGovFrameDev-3.9.0-64bit\\workspace\\AlbumEx01\\src\\main\\webapp\\upload";
	
	@Autowired
	private SqlMapperInter sqlMapperInter;
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request, Model model) {
		
		BoardListTO listTO = new BoardListTO();
		listTO.setCpage( Integer.parseInt( request.getParameter( "cpage" ) == null || request.getParameter( "cpage" ).equals( "" ) ? "1" : request.getParameter( "cpage" ) ) );
		listTO = dao.boardList(listTO);
		
		model.addAttribute( "listTO", listTO );
		
		return "board_list1";
	}
	
	@RequestMapping(value = "/write.do")
	public String write(Model model) {
		return "board_write1";
	}
	
	@RequestMapping(value = "/write_ok.do")
	public String write_ok(HttpServletRequest request, Model model) {
		
	    int maxFileSize = 1024 * 1024 * 6;
	    String encType = "utf-8";
	    
	    MultipartRequest multi = null;
		
	    try {
			multi = new MultipartRequest(request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy());
			
			BoardTO to = new BoardTO();
			to.setSubject(multi.getParameter("subject"));
			to.setWriter(multi.getParameter("writer"));
			// 필수 입력 항목이 아닌 경우
			to.setMail("");
			
			if(!multi.getParameter("mail1").equals("") && !multi.getParameter("mail2").equals("")){
				to.setMail(multi.getParameter("mail1") + "@" + multi.getParameter("mail2"));
			}
			to.setPassword(multi.getParameter("password"));
			to.setContent(multi.getParameter("content"));
			
			to.setFilename(multi.getFilesystemName("upload"));
			long filesize = 0;
			File file = multi.getFile("upload");
			if(file != null) {
				filesize = file.length();
			}
			to.setFilesize(filesize);
			
			to.setLongitude(multi.getParameter("longitude"));
			to.setLatitude(multi.getParameter("latitude"));
			
			to.setWip(request.getRemoteAddr());
			
			int flag = dao.boardWriteOk(to);
			
			model.addAttribute("flag", flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "board_write1_ok";
	}
	
	@RequestMapping(value = "/view.do")
	public String view(HttpServletRequest request, Model model) {
		BoardTO to = new BoardTO();
		ArrayList<CommentTO> clist = new ArrayList();
		
		to.setSeq(request.getParameter("seq"));
		
		to = dao.boardView(to);
		clist = cdao.commentList(to);
		
		model.addAttribute("to", to);
		model.addAttribute("clist", clist);
		
		return "board_view1";
	}
	
	@RequestMapping(value = "/view_ok.do")
	public String view_ok(HttpServletRequest request, Model model) {
		
		CommentTO cto = new CommentTO();
		cto.setPseq(request.getParameter("seq"));
		
		cto.setWriter(request.getParameter("cwriter"));
		cto.setPassword(request.getParameter("cpassword"));
		cto.setContent(request.getParameter("ccontent"));
		
		int flag = cdao.commentWriteOk(cto);
		
		model.addAttribute("flag", flag);
		
		return "board_view1_ok";
	}
	
	@RequestMapping(value = "/modify.do")
	public String modify(HttpServletRequest request, Model model) {
		String seq = request.getParameter("seq");

		BoardTO to = new BoardTO();
		to.setSeq(seq);
		
		to = dao.boardModify(to);
		
		model.addAttribute("to",to);
		
		return "board_modify1";
	}
	
	@RequestMapping(value = "/modify_ok.do")
	public String modify_ok(HttpServletRequest request, Model model) {

		int maxFileSize = 1024 * 1024 * 6;
	    String encType = "utf-8";
	    
	    MultipartRequest multi = null;
		
	    try {
			multi = new MultipartRequest(request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy());
			
			BoardTO to = new BoardTO();
			to.setSubject(multi.getParameter("subject"));
			to.setWriter(multi.getParameter("writer"));
			// 필수 입력 항목이 아닌 경우
			to.setMail("");
			
			if(!multi.getParameter("mail1").equals("") && !multi.getParameter("mail2").equals("")){
				to.setMail(multi.getParameter("mail1") + "@" + multi.getParameter("mail2"));
			}
			to.setPassword(multi.getParameter("password"));
			to.setContent(multi.getParameter("content"));
			
			to.setFilename(multi.getFilesystemName("upload"));
			long filesize = 0;
			File file = multi.getFile("upload");
			if(file != null) {
				filesize = file.length();
			}
			to.setFilesize(filesize);
			
			to.setLongitude(multi.getParameter("longitude"));
			to.setLatitude(multi.getParameter("latitude"));
			
			to.setWip(request.getRemoteAddr());
			
			int flag = dao.boardModifyOk(to);
			
			model.addAttribute("flag", flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "board_modify1_ok";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(HttpServletRequest request, Model model) {
		String seq = request.getParameter("seq");
		
		BoardTO to = new BoardTO();
		to.setSeq(seq);
		
		to = dao.boardDelete(to);
		
		model.addAttribute("to",to);
		
		return "board_delete1";
	}
	
	@RequestMapping(value = "/delete_ok.do")
	public String delete_ok(HttpServletRequest request, Model model) {

		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		to.setPassword(request.getParameter("password"));
		
		int flag = dao.boardDeleteOk(to);
		
		model.addAttribute("flag",flag);
		
		return "board_delete1_ok";
	}
}
