package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.ReimbursementDAO;

public class SubmitStatusChangeServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Manager pressed submit decision on one of the reimbursement
		//Will now update db with decision they made and update their queue and return that view after updating the db
		ReimbursementDAO rd = new ReimbursementDAO();
		boolean approve_or_deny = false;
		if (request.getParameter("decision").equals("approve")) {
			approve_or_deny = true;
		}
		String reason = request.getParameter("deny_reason");
		int eid = Integer.parseInt(request.getParameter("eid"));
		int rid = Integer.parseInt(request.getParameter("reimb_id"));
		//Send update to database by changing status of reimbursement, return to manager view of reimbursement
		//Should see 1 less reimbursement
		rd.changeStatus(rid, approve_or_deny, eid, reason);
		int manager_id = Integer.parseInt(request.getParameter("managerid"));
		request.setAttribute("content", rd.getManagerReimbursements(manager_id));
		request.setAttribute("e_id", manager_id);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("managereimbursement.jsp");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}
}
