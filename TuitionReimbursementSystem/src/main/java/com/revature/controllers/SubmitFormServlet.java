package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Allotment;
import com.revature.beans.Employee;
import com.revature.dao.AllotmentDAO;
import com.revature.dao.EmployeeDAO;

public class SubmitFormServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7604681980901047600L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//grabbing all fields from form to send to db
		int id = Integer.parseInt(request.getParameter("employeeID"));
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		int type_of_event = Integer.parseInt(request.getParameter("typeOfEvent"));
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		String justification = request.getParameter("justification");
		String date_of_event = request.getParameter("dateOfEvent");
		int grade_format = Integer.parseInt(request.getParameter("gradeFormat"));
		Double cost = Double.parseDouble(request.getParameter("cost"));
		Double timeMissed = Double.parseDouble(request.getParameter("missed"));
		
		//submit the form		
		EmployeeDAO ed = new EmployeeDAO();	
		Employee e = ed.submitReimbursement(id, fname, lname, type_of_event, description, location, justification,
				date_of_event, grade_format, cost, timeMissed);
		Employee sup, dept;
		sup = ed.getEmployee(e.getSupervisor_id());
		dept = ed.getEmployee(e.getDepthead_id());
		AllotmentDAO ad = new AllotmentDAO();
		Allotment a = ad.getAllotment(e.getA_id());
		String[] content = {Integer.toString(e.getE_id()), e.getFirstname(), e.getLastname(), e.getEmail(),
				sup.getFirstname() + " " + sup.getLastname(),dept.getFirstname() + " " + dept.getLastname(),
				Integer.toString(e.getEt_id()),Double.toString(a.getTotal()),
				Double.toString(a.getPending()),Double.toString(a.getAwarded())};
		
		request.setAttribute("userInfo", content);
		request.setAttribute("eid", e.getE_id());
		RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

}
