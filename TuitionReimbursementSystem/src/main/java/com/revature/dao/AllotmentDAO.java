package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Allotment;
import com.revature.util.ConnectionUtil;

public class AllotmentDAO {
	public Allotment getAllotment(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Allotment a = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Allotment WHERE A_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int aid = rs.getInt("A_ID");
				double total = rs.getDouble("TOTAL");
				double pending = rs.getDouble("PENDING");
				double awarded = rs.getDouble("AWARDED");
				a = new Allotment(aid, total, pending, awarded);
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
		return a;
	}
	
	public void updateAwarded(int id, double cost) {
		PreparedStatement ps = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE ALLOTMENT SET AWARDED = ? WHERE A_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, getAllotment(id).getAwarded() + cost);
			ps.setInt(2, id);
			ps.execute();
			getAllotment(id).getPending();
			//we need to deduct the amount we just awarded from the pending field
			updatePending(id, getAllotment(id).getPending(), -cost);
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
		}
	}
	
	public void updatePending(int id, double current_pending, double projected_cost) {		
		PreparedStatement ps = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql ="UPDATE ALLOTMENT SET PENDING = ? WHERE A_ID= ?";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, current_pending + projected_cost);
			ps.setInt(2, id);
			ps.execute();
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
		}
	}

}
