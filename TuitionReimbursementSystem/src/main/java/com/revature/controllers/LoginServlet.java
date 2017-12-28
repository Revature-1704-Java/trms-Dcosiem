package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDAO;

public class LoginServlet extends HttpServlet {
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//response.getWriter().append("Served at: ").append(request.getContextPath());
//		String fname = request.getParameter("firstName");
//		String lname = request.getParameter("lastName");
//		String fcolor = this.getInitParameter("favColor");
//		
//		//System.out.println(fname + " " + lname);
//		
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<p>Your name is: ");
//		out.println(fname + " " + lname + "</p>");
//		out.println("</br><p>Your favorite color: " + fcolor + "</p>");
//		out.close();
//	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeid"));
		String pw = request.getParameter("password");
		EmployeeDAO ed = new EmployeeDAO();
		Employee e;
		if((e = ed.getEmployee(id)) != null) {
			if(e.getPassword().equals(pw)) {
			//Login was good
			//Display user homepage 
			//change display based on the user's employee type
				
			}
			
		} else {
			//Login was bad, we tell them it was a bad login and return to the login page.
			PrintWriter out = response.getWriter();
			out.print("<script>alert(\"The username or password you entered was invalid!\\nPlease try again.\");</script>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
//			response.sendRedirect("D:\\Vit\\TuitionReimbursementSystem\\src\\main\\webapp\\index.html");
//			response.sendRedirect("index.html");
			
			
			
		}
	}

}
