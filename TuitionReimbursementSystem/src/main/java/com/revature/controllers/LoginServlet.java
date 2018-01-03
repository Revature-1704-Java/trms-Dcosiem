package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.revature.beans.Allotment;
import com.revature.beans.Employee;
import com.revature.dao.AllotmentDAO;
import com.revature.dao.EmployeeDAO;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeid"));
		String pw = request.getParameter("password");
		EmployeeDAO ed = new EmployeeDAO();		
		Employee e;
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
//				List dataList = new ArrayList();
				JSONObject jobj = new JSONObject();
				try {
					String[] content = {Integer.toString(e.getE_id()), e.getFirstname(), e.getLastname(), e.getEmail(),
							sup.getFirstname() + " " + sup.getLastname(),dept.getFirstname() + " " + dept.getLastname(),
							Integer.toString(e.getEt_id()),Double.toString(a.getTotal()),
							Double.toString(a.getPending()),Double.toString(a.getAwarded())};
//					request.setAttribute("listData", dataList);
//					request.setAttribute("ident", e.getE_id());
//					request.setAttribute("First Name", e.getFirstname());
//					request.setAttribute("Last Name", e.getLastname());
//					request.setAttribute("Email", e.getEmail());
//					request.setAttribute("Supervisor", sup.getFirstname() + " " + sup.getLastname());
//					request.setAttribute("Department Head", dept.getFirstname() + " " + dept.getLastname());
//					request.setAttribute("Title", e.getEt_id());
//					request.setAttribute("Allotment Total", a.getTotal());
//					request.setAttribute("Pending", a.getPending());
//					request.setAttribute("Awarded", a.getAwarded());
					request.setAttribute("userInfo", content);
					jobj.put("ID", e.getE_id());
					jobj.put("First Name", e.getFirstname());
					jobj.put("Last Name", e.getLastname());
					jobj.put("Email", e.getEmail());
					jobj.put("Supervisor", sup.getFirstname() + " " + sup.getLastname());
					jobj.put("Department Head", dept.getFirstname() + " " + dept.getLastname());
					jobj.put("Title", e.getEt_id());
					jobj.put("Allotment Total", a.getTotal());
					jobj.put("Pending", a.getPending());
					jobj.put("Awarded", a.getAwarded());
					out.print(jobj);
//					request.setAttribute("data", jobj);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("landing.html");
//					if(dispatcher != null) {
//						dispatcher.forward(request,response);
//					}
					RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
					if(dispatcher != null) {
						dispatcher.forward(request, response);
					}
//					RequestDispatcher rd = getServletContext().getRequestDispatcher("user.jsp");
//					rd.forward(request, response);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
					
//					response.sendRedirect("D:\\Vit\\TuitionReimbursementSystem\\src\\main\\webapp\\user.jsp");
//					request.getRequestDispatcher("D:\\Vit\\TuitionReimbursementSystem\\src\\main\\webapp\\user.jsp").forward(request, response);
					out.print("<h1>This is a dummy heading</h1>");
//					request.getRequestDispatcher("D:\\Vit\\TuitionReimbursementSystem\\src\\main\\webapp\\landing.html").forward(request, response);
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
			
		}//Login was bad, we tell them it was a bad login and return to the login page.
		
		out.print("<script>alert(\"The username or password you entered was invalid!\\nPlease try again.\");</script>");
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.include(request, response);
	}

}
