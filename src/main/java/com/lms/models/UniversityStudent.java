package com.lms.models;

public class UniversityStudent extends Student  {
	private String universityId;
	private String universityEmail;
	
	
	public UniversityStudent(String fullname, String email, String password, Department department,
			Specialization specialization, Role role, String universityName, StudentType studentType, String universityId, String universityEmail) {
		super(fullname, email, password, department, specialization, role, universityName, studentType);
		this.setUniversityId(universityId);
		this.setUniversityEmail(universityEmail);
	}
	
	
	
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		if(universityId == null || universityId.trim().isEmpty() || universityId.length() > 12) {
			throw new IllegalArgumentException("Invalid university id (must be 12 digit)!");
		}
		this.universityId = universityId;
	}
	public String getUniversityEmail() {
		return universityEmail;
		
	}
	public void setUniversityEmail(String universityEmail) {
		if(universityEmail == null || universityEmail.trim().isEmpty() || !universityEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Email is invalid");
		}
		this.universityEmail = universityEmail;
	}
	
	
}
