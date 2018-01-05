package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.ReimbursementDAO;

public class ManageReimbursementServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int manager_id = Integer.parseInt(request.getParameter("managerid"));
		ReimbursementDAO r = new ReimbursementDAO();
		request.setAttribute("content", r.getManagerReimbursements(manager_id));
		request.setAttribute("e_id", manager_id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("managereimbursement.jsp");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

}
