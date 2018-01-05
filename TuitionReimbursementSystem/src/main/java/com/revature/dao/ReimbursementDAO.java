package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.Allotment;
import com.revature.beans.Employee;
import com.revature.beans.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDAO {
	public Reimbursement getReimbursement(int id) {
		Reimbursement r = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM REIMBURSEMENT WHERE REIM_ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				int rid = rs.getInt("REIM_ID");
				int eid = rs.getInt("E_ID");
				int etid = rs.getInt("EVENT_TYPE_ID");
				String description = rs.getString("REIM_DESC");
				String location = rs.getString("REIM_LOC");
				String justification = rs.getString("JUSTIFICATION");
				String date = rs.getDate("DATE_OF_EVENT").toString();
				int gid = rs.getInt("GRADE_ID");
				double cost = rs.getDouble("REIM_COST");
				String time_sub = rs.getTime("TIME_SUBMITTED").toString();
				double time_miss = rs.getDouble("TIME_MISSED");
				int status = rs.getInt("STATUS");
				double exceed_amount = rs.getDouble("EXCEED_AMOUNT");
				String exceed_reason = rs.getString("EXCEED_REASON");
				String deny = rs.getString("DENIED_REASON");

				r = new Reimbursement (rid, eid, etid, description, location,
						justification, date, gid, cost, time_sub, time_miss, status, exceed_amount, exceed_reason, deny);
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
		return r;
	}
	
	public void denyReimbursement(int reim_id, String reason) {
		PreparedStatement ps = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE REIMBURSEMENT SET DENIED_REASON = ? WHERE REIM_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, reason);
			ps.setInt(2,  reim_id);
			ps.execute();
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
		}
		
	}
	
	//If the reimbursement was approved, the boolean will be true, otherwise it will be false
	public void changeStatus(int reim_id, Boolean approve_or_deny, int employee_id, String reason) {
		PreparedStatement ps = null;
		AllotmentDAO ad = new AllotmentDAO();
		EmployeeDAO ed = new EmployeeDAO();
		Employee e = ed.getEmployee(employee_id);
		ReimbursementDAO rd = new ReimbursementDAO();
		Reimbursement r = rd.getReimbursement(reim_id);
		try(Connection conn = ConnectionUtil.getConnection()) {
			int new_status = 0;
			if(approve_or_deny == false) {
				new_status = -1;
				denyReimbursement(reim_id, reason);
				//call deny, update reimbursement with deny reason
				//also we need to update allotment and remove the cost from pending field for reimbursement we just denied
				ad.updatePending(e.getA_id(), ad.getAllotment(e.getA_id()).getPending(), -r.getCost());
			} else {
				//If it was true, then we increase the status which moves the reimbursement down the line to the next manager up
				new_status = getReimbursement(reim_id).getStatus() + 1;
			}

			String sql = "UPDATE REIMBURSEMENT SET STATUS = ? WHERE REIM_ID = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, new_status);
			ps.setInt(2, reim_id);
			ps.execute();

			//benco has approved this reimbursement, so we should award money to the employee and update their tables accordingly
			if(new_status == 3) {				
				//Call method that updates awarded and corresponding pending field in allotment table
				ad.updateAwarded(e.getA_id(), r.getCost());
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
		}

	}
	public ArrayList<String[]> getManagerReimbursements(int id) {
		ArrayList<String[]>content = new ArrayList<String[]>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		EventTypeDAO etd = new EventTypeDAO();
		GradeDAO gd = new GradeDAO();
		EmployeeDAO ed = new EmployeeDAO();
		Employee e = ed.getEmployee(id);
		int employee_type = e.getEt_id();
		try(Connection conn = ConnectionUtil.getConnection()) {
			//We tailor the sql statement based on the user's employee type
			String flexsql = "";
			String sql = "";

			//We change ID we are searching for based on the type of employee that is managing the current reimbursements
			if(employee_type == 2) {
				flexsql = "SUPERVISOR_ID = ? AND REIMBURSEMENT.E_ID=EMPLOYEE.E_ID AND REIMBURSEMENT.STATUS=?";
			} else if (employee_type == 3) {
				flexsql = "DEPTHEAD_ID = ? AND REIMBURSEMENT.E_ID=EMPLOYEE.E_ID AND REIMBURSEMENT.STATUS=?" ;
				//The benco will be the last to approve or deny a reimbursement, so he only needs to query reimbursements that have a status
				//of 2(meaning a department head has approved a reimbursement) we are also relying on the fact that a benco will only approve
				//a reimbursement if he sees a passing grade, so there is no check to see if there is a passing grade.
				//Left to benco to determine
			} else if (employee_type == 4) {
				flexsql = "E_ID = REIMBURSEMENT.E_ID AND REIMBURSEMENT.STATUS=2"; 
			}
			//			sql = "select employee.E_ID, employee.SUPERVISOR_ID, employee.DEPTHEAD_ID, \r\n" + 
			//					"reimbursement.REIM_ID, reimbursement.event_type_id,\r\n" + 
			//					"reimbursement.reim_desc, reimbursement.REIM_loc, reimbursement.justification,\r\n" + 
			//					"reimbursement.date_of_event, reimbursement.grade_id, reimbursement.REIM_cost,\r\n" + 
			//					"reimbursement.time_submitted, reimbursement.time_missed, reimbursement.status,\r\n" + 
			//					"reimbursement.denied_reason from EMPLOYEE join REIMBURSEMENT ON EMPLOYEE."
			//					+ flexsql + " = ? AND REIMBURSEMENT.E_ID=EMPLOYEE.E_ID AND REIMBURSEMENT.STATUS=?";
			sql = "select employee.E_ID, employee.SUPERVISOR_ID, employee.DEPTHEAD_ID, \r\n" + 
					"reimbursement.REIM_ID, reimbursement.event_type_id,\r\n" + 
					"reimbursement.reim_desc, reimbursement.REIM_loc, reimbursement.justification,\r\n" + 
					"reimbursement.date_of_event, reimbursement.grade_id, reimbursement.REIM_cost,\r\n" + 
					"reimbursement.time_submitted, reimbursement.time_missed, reimbursement.status,\r\n" + 
					"reimbursement.denied_reason from EMPLOYEE join REIMBURSEMENT ON EMPLOYEE."
					+ flexsql;

			ps = conn.prepareStatement(sql);
			if(employee_type != 4) {
				ps.setInt(1, id);
				//Status of 0 is default for Supervisor level, we check and change this variable for higher level management
				int status_check = 0;
				//We only want to see reimbursements that are pending and in our queue, if that status changes because of an approval or deny
				//then they should disappear and move onto the next manager to deal with
				if(employee_type == 3) {
					status_check = 1;
				}
				//We input the manager's id to search the database for all reimbursements from employees with that manager
				ps.setInt(2,  status_check);
			}			
			rs = ps.executeQuery();
			while (rs.next()) {
				//When a user wants to approve/deny a reimbursement they will click submit next to the reimbursement they want to approve/deny
				//This will submit the update for that particular reimbursement and then return them to the manage reimbursement page with
				//one less reimbursement (because we just processed it and it should no longer be in their pending reimbursements)

				String beginTable = "<tr>";
				int rid = rs.getInt("REIM_ID");
				int eid = rs.getInt("E_ID");
				int sid = rs.getInt("SUPERVISOR_ID");
				int did = rs.getInt("DEPTHEAD_ID");
				String event_type = etd.getEventType(rs.getInt("EVENT_TYPE_ID"));
				String description = rs.getString("REIM_DESC");
				String location = rs.getString("REIM_LOC");
				String justification = rs.getString("JUSTIFICATION");
				String date = rs.getDate("DATE_OF_EVENT").toString();
				String grade = gd.getLetterGrade(rs.getInt("GRADE_ID"));
				double cost = rs.getDouble("REIM_COST");
				String time = rs.getTime("TIME_SUBMITTED").toString();
				double  time_missed = rs.getDouble("TIME_MISSED");
				int status = rs.getInt("STATUS");
				//				int exceed_amount = rs.getInt("EXCEED_AMOUNT");
				//				String exceed_reason = rs.getString("EXCEED_REASON");
				String deny = "<textarea name=\"deny_reason\"></textarea>";
				String select = "<select name=\"decision\"><option value=\"\" disabled selected>Select an option</option><option value=\"approve\">Approve</option><option value=\"deny\">Deny</option></select>";
				//				String approval = "<input type=\"radio\" name=\"approve\" value=\"true\">Approve";
				//				String denial = "<input type=\"radio\" name=\"denial\" value=\"false\">Deny";
				String submit = "<input type=\"submit\" value=\"Submit\">";
				String endTable = "</tr>";
				String startForm = "<form method=\"get\" action=\"SubmitStatusChangeServlet\">";
				String endForm = "</form>";
				String hidden_id = "<input type=\"hidden\" name=\"reimb_id\"value=\"" + Integer.toString(rid) + "\">";
				String hidden_manager = "<input type=\"hidden\" name=\"managerid\"value=\"" + Integer.toString(id) + "\">";
				String employee_reimbursement_id = "<input type=\"hidden\" name=\"eid\" value=\"" + Integer.toString(eid) + "\">";
				String[] tablerow = {startForm, employee_reimbursement_id, hidden_id, hidden_manager, beginTable, Integer.toString(eid), Integer.toString(sid), Integer.toString(did), 
						Integer.toString(rid), event_type, description, location, justification, date, grade, 
						Double.toString(cost), time, Double.toString(time_missed), Integer.toString(status), deny, select, submit, endTable, endForm };
				content.add(tablerow);
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
		return content;
	}

	public ArrayList<String[]> getEmployeeReimbursements(int id) {
		ArrayList<String[]>content = new ArrayList<String[]>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		GradeDAO gd = new GradeDAO();
		EventTypeDAO etd = new EventTypeDAO();
		try(Connection conn = ConnectionUtil.getConnection()) {
			//We grab every reimbursement in the table from the specified employee
			String sql = "SELECT * FROM REIMBURSEMENT WHERE E_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);


			rs = ps.executeQuery();
			//We loop through every result and grab the fields for each reimbursement
			while (rs.next()) {
				String beginTable = "<tr>";
				int rid = rs.getInt("REIM_ID");
				//				int eid = rs.getInt("E_ID");
				String event_type = etd.getEventType(rs.getInt("EVENT_TYPE_ID"));
				String description = rs.getString("REIM_DESC");
				String location = rs.getString("REIM_LOC");
				String justification = rs.getString("JUSTIFICATION");
				String date = rs.getDate("DATE_OF_EVENT").toString();
				String grade = gd.getLetterGrade(rs.getInt("GRADE_ID"));
				//				int gid = rs.getInt("GRADE_ID");
				double cost = rs.getDouble("REIM_COST");
				String time = rs.getTime("TIME_SUBMITTED").toString();
				double  time_missed = rs.getDouble("TIME_MISSED");
				int status = rs.getInt("STATUS");
				//				int exceed_amount = rs.getInt("EXCEED_AMOUNT");
				//				String exceed_reason = rs.getString("EXCEED_REASON");
				String deny = rs.getString("DENIED_REASON");
				String endTable = "</tr>";

				String[] table = {beginTable, Integer.toString(rid),  event_type, description, location, 
						justification, date, grade, Double.toString(cost), time, Double.toString(time_missed),
						Integer.toString(status), deny, endTable};

				content.add(table);
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
		return content;
	}
}
