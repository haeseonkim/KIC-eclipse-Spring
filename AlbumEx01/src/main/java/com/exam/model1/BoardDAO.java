package com.exam.model1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.exam.model1.BoardListTO;
import com.exam.model1.BoardTO;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSession sqlSession;

	// writer - dao 통과 안해도됨
	public void boardWrite() {
	}

	// writer_ok - flag 값있어야함
	public int boardWriteOk(BoardTO to) {
		int flag = 1;

		int result = sqlSession.insert("write_ok", to);
		if (result == 1) {
			flag = 0;
		}
		return flag;
	}

	// list
	public BoardListTO boardList(BoardListTO listTO) {

		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();

		ArrayList<BoardTO> lists = (ArrayList)sqlSession.selectList("list");

		listTO.setTotalRecord(lists.size());
		listTO.setTotalPage(((listTO.getTotalRecord() - 1) / recordPerPage) + 1);

		int skip = (cpage - 1) * recordPerPage;
		
		ArrayList<BoardTO> boardLists = new ArrayList();
		
		int cnt = 0;
		for (int i = skip; i < lists.size(); i++) {
			if(cnt == recordPerPage) {
				break;
			}
			if (lists.get(i) != null) {
				BoardTO to = lists.get(i);
				boardLists.add(to);
			}
			cnt++;
		}

		listTO.setBoardList(boardLists);

		listTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		listTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		if (listTO.getEndBlock() >= listTO.getTotalPage()) {
			listTO.setEndBlock(listTO.getTotalPage());
		}

		return listTO;
	}

	// view
	public BoardTO boardView(BoardTO to) {
		sqlSession.update("view_hit", to);
		to = sqlSession.selectOne("view", to);

		return to;
	}

	// delete
	public BoardTO boardDelete(BoardTO to) {
		BoardTO board = sqlSession.selectOne("delete", to);

		return board;
	}

	// delete_ok
	public int boardDeleteOk(BoardTO to) {
		int flag = 2;

		int result = sqlSession.delete("delete_ok", to);
		if (result == 1) {
			flag = 0;
		} else if (result == 0) {
			flag = 1;
		}

		return flag;
	}

	// modify
	public BoardTO boardModify(BoardTO to) {
		BoardTO board = sqlSession.selectOne("modify", to);

		return board;
	}

	// modify_ok
	public int boardModifyOk(BoardTO to) {
		int flag = 2;
		int result = sqlSession.update("modify_ok", to);
		if (result == 1) {
			flag = 0;
		} else if (result == 0) {
			flag = 1;
		}

		return flag;
	}
}
