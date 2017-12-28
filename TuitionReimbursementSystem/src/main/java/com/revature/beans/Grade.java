package com.revature.beans;

public class Grade {
	private int grade_id;
	private String gradeType;
	private String letterGrade;
	private String gradeCutoff;
	public int getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(int grade_id) {
		this.grade_id = grade_id;
	}
	public String getGradeType() {
		return gradeType;
	}
	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}
	public String getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	public String getGradeCutoff() {
		return gradeCutoff;
	}
	public void setGradeCutoff(String gradeCutoff) {
		this.gradeCutoff = gradeCutoff;
	}
	@Override
	public String toString() {
		return "Grade [grade_id=" + grade_id + ", gradeType=" + gradeType + ", letterGrade=" + letterGrade
				+ ", gradeCutoff=" + gradeCutoff + "]";
	}
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Grade(int grade_id, String gradeType, String letterGrade, String gradeCutoff) {
		super();
		this.grade_id = grade_id;
		this.gradeType = gradeType;
		this.letterGrade = letterGrade;
		this.gradeCutoff = gradeCutoff;
	}
	
	

}
