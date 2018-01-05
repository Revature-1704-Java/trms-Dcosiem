package com.revature.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Allotment;
import com.revature.beans.Employee;
import com.revature.dao.AllotmentDAO;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeTypeDAO;

public class UserRedirectServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("eid"));
		EmployeeDAO ed = new EmployeeDAO();
		Employee e = ed.getEmployee(id);
		Employee sup = ed.getEmployee(e.getSupervisor_id());
		Employee dept = ed.getEmployee(e.getDepthead_id());
		AllotmentDAO ad = new AllotmentDAO();
		Allotment a = ad.getAllotment(e.getA_id());
		EmployeeTypeDAO etd = new EmployeeTypeDAO();
		String title = etd.getEmployeeType(e.getEt_id());
		String sup_name = "";
		String dep_name = "";
		if(e.getEt_id() == 2) {
			dep_name = dept.getFirstname() + " " + dept.getLastname();
	
		}
		String[] content = {Integer.toString(e.getE_id()), e.getFirstname(), e.getLastname(), e.getEmail(),
				sup_name ,dep_name, title, Double.toString(a.getTotal()),
				Double.toString(a.getPending()),Double.toString(a.getAwarded())};

		request.setAttribute("userInfo", content);
		request.setAttribute("eid", e.getE_id());
		String manage = "";
		if(e.getEt_id() != 1) {
			manage = "<input type=\"submit\" value=\"Manage Reimbursements\" />";
			request.setAttribute("manage",  manage);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

}
