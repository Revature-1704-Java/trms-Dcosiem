package com.revature.beans;

public class Employee {
	
	private int e_id;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private int supervisor_id;
	private int depthead_id;	
	//different et_id correspond to different job titles i.e. 0-employee, 1-supervisor, etc
	private int et_id;
	private int a_id;
	public int getE_id() {
		return e_id;
	}
	public void setE_id(int e_id) {
		this.e_id = e_id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSupervisor_id() {
		return supervisor_id;
	}
	public void setSupervisor_id(int supervisor_id) {
		this.supervisor_id = supervisor_id;
	}
	public int getDepthead_id() {
		return depthead_id;
	}
	public void setDepthead_id(int depthead_id) {
		this.depthead_id = depthead_id;
	}
	public int getEt_id() {
		return et_id;
	}
	public void setEt_id(int et_id) {
		this.et_id = et_id;
	}
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	@Override
	public String toString() {
		return "Employee [e_id=" + e_id + ", firstname=" + firstname + ", lastname=" + lastname + ", password="
				+ password + ", email=" + email + ", supervisor_id=" + supervisor_id + ", depthead_id=" + depthead_id
				+ ", et_id=" + et_id + ", a_id=" + a_id + "]";
	}
	public Employee(int e_id, String firstname, String lastname, String password, String email, int supervisor_id,
			int depthead_id, int et_id, int a_id) {
		super();
		this.e_id = e_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.supervisor_id = supervisor_id;
		this.depthead_id = depthead_id;
		this.et_id = et_id;
		this.a_id = a_id;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
