package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.ReimbursementDAO;

public class TableReimbursementServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5020784871102357221L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeid"));
		ReimbursementDAO rdo = new ReimbursementDAO();
		request.setAttribute("content", rdo.getEmployeeReimbursements(id));
		request.setAttribute("employeeid", id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("reimbursementquery.jsp");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
		
		
	}
}
