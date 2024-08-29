package com.lms.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
	private String fullname;
	private String email;
	private String password;
	private String branch;
	private Role role;
	private String avatar;
	private boolean isLoggedIn;
	private LocalDate createdAt;
	private LocalDateTime updatedAt;
	
	
	
	// Constructor
	public User(String fullname, String email, String password, String branch,Role role) {
		this.setFullname(fullname);
		this.setEmail(email);
		this.setPassword(password);
		this.setBranch(branch);
		this.setRole(role);
		this.isLoggedIn = false;
		this.createdAt = LocalDate.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		if(fullname == null || fullname.trim().isEmpty()) {
			throw new IllegalArgumentException("Full name cannot be empty");
		}
		this.fullname = fullname;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Email is invalid");
		}
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password == null || password.trim().isEmpty() || password.trim().length() < 8) {
			throw new IllegalArgumentException("Password must be atleast 8 characters");
		}
		this.password = password;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		if(branch == null || branch.trim().isEmpty()) {
			throw new IllegalArgumentException("Bracnh cannot be empty");
		}
		this.branch = branch;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		if (role == null) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        this.role = role;
	}
	
	// we will set avatar after successful login
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		if(isLoggedIn) {
			this.avatar = avatar;
			this.updatedAt = LocalDateTime.now();
		}
		else {
			throw new IllegalArgumentException("User must be logged in");
		}
		
	}
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}
