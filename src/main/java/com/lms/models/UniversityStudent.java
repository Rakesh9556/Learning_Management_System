package com.lms.models;

public class UniversityStudent extends User  {
	private String regdNo;
	
	public UniversityStudent(
			String regdNo,
			String fullname,
			String email,
			String password,
			String branch,
			Role role) {
		super(fullname, email, password, branch, role);
		this.regdNo = regdNo;	
	}

	public String getRegdNo() {
		return regdNo;
	}
	public void setRegdNo(String regdNo) {
		if(regdNo == null || regdNo.trim().isEmpty() || regdNo.length() > 12) {
			throw new IllegalArgumentException("Username must be 12 digit");
		}
		this.regdNo = regdNo;
	}
	
	
	
}
