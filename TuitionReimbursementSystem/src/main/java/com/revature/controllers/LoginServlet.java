package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.revature.beans.Allotment;
import com.revature.beans.Employee;
import com.revature.dao.AllotmentDAO;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeTypeDAO;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeid"));
		String pw = request.getParameter("password");
		EmployeeDAO ed = new EmployeeDAO();		
		Employee e;
		EmployeeTypeDAO emptd = new EmployeeTypeDAO();
		PrintWriter out = response.getWriter();
		if((e = ed.getEmployee(id)) != null) {
			if(e.getPassword().equals(pw)) {
				//Login was good
				//Display user homepage 
				//change display based on the user's employee type
				AllotmentDAO ad = new AllotmentDAO();
				Allotment a = ad.getAllotment(e.getA_id());
				Employee sup, dept;
				sup = ed.getEmployee(e.getSupervisor_id());
				dept = ed.getEmployee(e.getDepthead_id());
				String sup_name = "";
				String dep_name = "";
				if(sup != null) {
					sup_name = sup.getFirstname() + " " + sup.getLastname();
				}
				if(dept != null) { 
					dep_name = dept.getFirstname() + " " + dept.getLastname();
				}
				
				

				String[] content = {Integer.toString(e.getE_id()), e.getFirstname(), e.getLastname(), e.getEmail(),
						sup_name, dep_name, emptd.getEmployeeType(e.getEt_id()),Double.toString(a.getTotal()),
						Double.toString(a.getPending()),Double.toString(a.getAwarded())};

				request.setAttribute("userInfo", content);
				request.setAttribute("eid", e.getE_id());
				String manage = "";
				
				//Add more functionality if the user has higher employee type
				if(e.getEt_id() > 1) {
					manage = "<input type=\"submit\" value=\"Manage Reimbursements\" />";					
				}	
				request.setAttribute("manage", manage);
				RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
				if(dispatcher != null) {
					dispatcher.forward(request, response);
				}

			}

		}//Login was bad, we tell them it was a bad login and return to the login page.

		out.print("<script>alert(\"The username or password you entered was invalid!\\nPlease try again.\");</script>");
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.include(request, response);
	}

}
