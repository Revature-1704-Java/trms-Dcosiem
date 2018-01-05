package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

public class GradeDAO {
	public void insertGrade(int grade_format) {

		PreparedStatement ps = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			//Create a new Grade Entry, IDs are created automatically server side
			String sql = "INSERT INTO GRADE (GRADETYPE_ID, LETTERGRADE) VALUES (?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, grade_format);
			ps.setString(2, "NULL");
			ps.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
		}
	}
	public int getRecentID() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int g_id = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT MAX(GRADE_ID) FROM GRADE";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			while(rs.next()) {
				g_id = rs.getInt("MAX(GRADE_ID)");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
		}
		return g_id;
	}
	public String getLetterGrade(int id) {
		PreparedStatement ps = null;
		String letterGrade = "";
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT LETTERGRADE FROM GRADE WHERE GRADE_ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				letterGrade = rs.getString("LETTERGRADE");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
		}
		return letterGrade;
	}
}
