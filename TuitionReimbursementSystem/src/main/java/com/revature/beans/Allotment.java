package com.revature.beans;

public class Allotment {
	
	private int a_id;
	private double total;
	private double pending;
	private double awarded;
	
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getPending() {
		return pending;
	}
	public void setPending(double pending) {
		this.pending = pending;
	}
	public double getAwarded() {
		return awarded;
	}
	public void setAwarded(double awarded) {
		this.awarded = awarded;
	}
	@Override
	public String toString() {
		return "Allotment [a_id=" + a_id + ", total=" + total + ", pending=" + pending + ", awarded=" + awarded + "]";
	}
	public Allotment(int a_id, double total, double pending, double awarded) {
		super();
		this.a_id = a_id;
		this.total = total;
		this.pending = pending;
		this.awarded = awarded;
	}
	public Allotment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getAvailable() {
		return this.total - this.pending - this.awarded;
	}
	
	
}
