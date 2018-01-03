package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

public class EventTypeDAO {
	public double getCost(int type_of_event, double cost) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		double answer = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			//We query the db for the Coverage amount
			String sql = "SELECT COVERAGE FROM EVENT_TYPE WHERE EVENT_TYPE_ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, type_of_event);
			double coverage = 0;
			rs = ps.executeQuery();
			while(rs.next()) {
				//coverage amount is a percentage so we convert it to a decimal
				coverage = rs.getInt("COVERAGE") / 100;
			}
			//cost for reimbursement is the coverage of the event type * cost of the total event
			answer = coverage * cost;
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
