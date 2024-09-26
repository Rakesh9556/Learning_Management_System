package com.lms.models;

public class IndividualStudent extends Student {
	private String username;

	
	public IndividualStudent(String fullname, String email, String password, Department department,
			Specialization specialization, Role role, String universityName, StudentType studentType, String username) {
		super(fullname, email, password, department, specialization, role, universityName, studentType);
		this.setUsername(username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if(!username.matches("^[a-zA-Z0-9_.]{3,12}$")) {
			throw new IllegalArgumentException("Username must be 3-12 characters and can include letters, digits, '_', and '.'!");		
		}
		this.username = username;
	}
	
}