package com.lms.models;

public class IndividualStudent extends User {
	private String username;
	
	public IndividualStudent(
			String username,
			String fullname,
			String email,
			String password,
			String branch,
			Role role) {
		super(fullname, email, password, branch, role);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannnot be emoty");
		}
		if(username.length() < 3 || username.length() > 12) {
			throw new IllegalArgumentException("Username must be betwwen 3 to 12 characters");
		}
		if(!username.matches("^[A-Za-z][A-Za-z0-9_]*$")) {
			throw new IllegalArgumentException("Username must start with a letter.");
		}
		this.username = username;
	}
	
}
