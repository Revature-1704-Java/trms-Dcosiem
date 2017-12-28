package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Employee;
import com.revature.util.ConnectionUtil;

public class EmployeeDAO {
	
	public Employee getEmployee(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee e = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEE WHERE EMPLOYEEID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int eid = rs.getInt("E_ID");
				String f_name = rs.getString("FIRSTNAME");
				String l_name = rs.getString("LASTNAME");
				String email = rs.getString("EMAIL");
				String pw = rs.getString("PW");
				int sid = rs.getInt("SUPERVISOR_ID");
				int did = rs.getInt("DEPTHEAD_ID");
				int etid = rs.getInt("ET_ID");
				int aid = rs.getInt("A_ID");
				
				e = new Employee(eid, f_name, l_name, email, pw, sid, did, etid, aid);
			}
		} catch (Exception ex) {
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
		return e;
	}
	

}
