package com.revature.beans;

public class Reimbursement {
	//primary key
	private int reim_id;;
	private int e_id;
	private int event_type_id;
	private String reim_desc;
	private String reim_loc;
	private String justification;
	private String date_of_event;
	private int grade_id;
	private double cost;
	private String time_submitted;
	private double time_missed;
	private int status;
	private double exceed_amount;
	private String exceed_reason;
	private String denied_reason;
	public int getReim_id() {
		return reim_id;
	}
	public void setReim_id(int reim_id) {
		this.reim_id = reim_id;
	}
	public int getE_id() {
		return e_id;
	}
	public void setE_id(int e_id) {
		this.e_id = e_id;
	}
	public int getEvent_type_id() {
		return event_type_id;
	}
	public void setEvent_type_id(int event_type_id) {
		this.event_type_id = event_type_id;
	}
	public String getReim_desc() {
		return reim_desc;
	}
	public void setReim_desc(String reim_desc) {
		this.reim_desc = reim_desc;
	}
	public String getReim_loc() {
		return reim_loc;
	}
	public void setReim_loc(String reim_loc) {
		this.reim_loc = reim_loc;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getDate_of_event() {
		return date_of_event;
	}
	public void setDate_of_event(String date_of_event) {
		this.date_of_event = date_of_event;
	}
	public int getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(int grade_id) {
		this.grade_id = grade_id;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getTime_submitted() {
		return time_submitted;
	}
	public void setTime_submitted(String time_submitted) {
		this.time_submitted = time_submitted;
	}
	public double getTime_missed() {
		return time_missed;
	}
	public void setTime_missed(double time_missed) {
		this.time_missed = time_missed;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getExceed_amount() {
		return exceed_amount;
	}
	public void setExceed_amount(double exceed_amount) {
		this.exceed_amount = exceed_amount;
	}
	public String getExceed_reason() {
		return exceed_reason;
	}
	public void setExceed_reason(String exceed_reason) {
		this.exceed_reason = exceed_reason;
	}
	public String getDenied_reason() {
		return denied_reason;
	}
	public void setDenied_reason(String denied_reason) {
		this.denied_reason = denied_reason;
	}
	@Override
	public String toString() {
		return "Reimbursement [reim_id=" + reim_id + ", e_id=" + e_id + ", event_type_id=" + event_type_id
				+ ", reim_desc=" + reim_desc + ", reim_loc=" + reim_loc + ", justification=" + justification
				+ ", date_of_event=" + date_of_event + ", grade_id=" + grade_id + ", cost=" + cost + ", time_submitted="
				+ time_submitted + ", time_missed=" + time_missed + ", status=" + status + ", exceed_amount="
				+ exceed_amount + ", exceed_reason=" + exceed_reason + ", denied_reason=" + denied_reason + "]";
	}
	public Reimbursement(int reim_id, int e_id, int event_type_id, String reim_desc, String reim_loc,
			String justification, String date_of_event, int grade_id, double cost, String time_submitted,
			double time_missed, int status, double exceed_amount, String exceed_reason, String denied_reason) {
		super();
		this.reim_id = reim_id;
		this.e_id = e_id;
		this.event_type_id = event_type_id;
		this.reim_desc = reim_desc;
		this.reim_loc = reim_loc;
		this.justification = justification;
		this.date_of_event = date_of_event;
		this.grade_id = grade_id;
		this.cost = cost;
		this.time_submitted = time_submitted;
		this.time_missed = time_missed;
		this.status = status;
		this.exceed_amount = exceed_amount;
		this.exceed_reason = exceed_reason;
		this.denied_reason = denied_reason;
	}
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
