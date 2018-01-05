package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

public class EmployeeTypeDAO {
	public String getEmployeeType(int id) {
		String answer = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT EMPLOYEE_TYPE FROM EMPLOYEE_TYPE WHERE ET_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				answer = rs.getString("EMPLOYEE_TYPE");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}	catch (IOException ex) {
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
		}		
		return answer;
	}
}
