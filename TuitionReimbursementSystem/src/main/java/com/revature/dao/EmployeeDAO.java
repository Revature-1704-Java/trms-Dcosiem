package com.revature.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Allotment;
import com.revature.beans.Employee;
import com.revature.util.ConnectionUtil;

public class EmployeeDAO {
	
	public Employee getEmployee(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee e = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEE WHERE E_ID = ?";
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
				
				e = new Employee(eid, f_name, l_name, email, sid, did, etid, pw, aid);
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
		return e;
	}
	
	public Employee submitReimbursement(int id, String fname,
			String lname, int type_of_event, String description, String location, 
			String justification, String date_of_event, int grade_format, Double cost, Double time_missed) {
		PreparedStatement ps = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Employee e = null;
		AllotmentDAO a = new AllotmentDAO();
		GradeDAO gd = new GradeDAO();
		EventTypeDAO etd = new EventTypeDAO();
		try(Connection conn = ConnectionUtil.getConnection()) {
			//Create a new Grade Entry, IDs are created automatically server side
			gd.insertGrade(grade_format);
			//We need to grab the ID (primary key) of the Grade so that we can add it into the Reimbursement table
			//We query grade table for the ID with the highest value to grab the most recently added entry
			int grade_id = gd.getRecentID();
						
			//Update Allotment
			//Grab the coverage amount from db and calculate actual reimbursement with coverage amount
			double projected_cost = etd.getCost(type_of_event, cost);
			
			//Grab the allotment table for the specified user
			e = getEmployee(id);
			int a_id = e.getA_id();
			Allotment al = a.getAllotment(a_id);
			//Grab their pending amount to add this reimbursement to
			
			double available = al.getTotal() - al.getPending() - al.getAwarded();
			
			//check to see if projected cost exceeds available funds
			if(projected_cost > available) {
				projected_cost = available;
			}
			
			//update allotment table with new amount
			a.updatePending(id, al.getPending(), projected_cost);
			
			
			//Create a new Reimbursement by calling stored procedure
			String sql = "{CALL SP_SUBMIT_REIM(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, id);
			cs.setInt(2, type_of_event);
			cs.setString(3, description);
			cs.setString(4, location);
			cs.setString(5, justification);
			cs.setString(6, date_of_event);			
			cs.setInt(7, grade_id);
			cs.setDouble(8,  cost);
			cs.setDouble(9, time_missed);
			
			Boolean result = cs.execute();
			if(!result) {
				System.out.println("Submitted!");
			} else {
				System.out.println("FAILED TO SUBMIT!");
			}
			cs.close();
			e = getEmployee(id);
			
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
		return e;
	}
	

}
