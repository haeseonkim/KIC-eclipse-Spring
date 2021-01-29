package com.exam.zipcode03.model1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ZipcodeDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataSource dataSource;
	
	// DataSource 사용
	public ArrayList<ZipcodeTO> searchLists1(String strDong) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ZipcodeTO> lists = new ArrayList<ZipcodeTO>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select zipcode, sido, gugun, dong, ri, bunji from zipcode where dong like ?";
			pstmt = conn.prepareStatement( sql );
			pstmt.setString( 1, strDong + "%" );
				
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				ZipcodeTO to = new ZipcodeTO();
				to.setZipcode( rs.getString( "zipcode" ) );
				to.setSido( rs.getString( "sido" ) );
				to.setGugun( rs.getString( "gugun" ) );
				to.setDong( rs.getString( "dong" ) );
				to.setRi( rs.getString( "ri" ) );
				to.setBunji( rs.getString( "bunji" ) );
				
				lists.add(to);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println( "[예외] : " + e.getMessage() );
		} finally {
			if( rs != null ) try { rs.close(); } catch(SQLException e) {}
			if( pstmt != null ) try { pstmt.close(); } catch(SQLException e) {}
			if( conn != null ) try { conn.close(); } catch(SQLException e) {}
		}
		return lists;
	}
	
	// jdbcTemplate 사용
	public ArrayList<ZipcodeTO> searchLists2(String dong) {
		String sql = "select zipcode, sido, gugun, dong, ri, bunji from zipcode where dong like ?";
		ArrayList<ZipcodeTO> lists = (ArrayList<ZipcodeTO>)jdbcTemplate.query(sql, new BeanPropertyRowMapper<ZipcodeTO>(ZipcodeTO.class), dong + "%");
		return lists;
	}
}
