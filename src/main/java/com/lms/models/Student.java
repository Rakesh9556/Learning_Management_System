package com.lms.models;

import java.util.List;


public abstract class Student extends User{
	private boolean isEnrolled;
	private double progressPercentage;
	private List<Course> completedCourses; // we will handle this later section 
	private List<Course> currentCourses;
	private StudentType studentType;
	
	
	
	public Student(String fullname, String email, String password, Department department, Specialization specialization, Role role,
			String universityName, StudentType studentType) {
		super(fullname, email, password, department, specialization, role, universityName);
		this.setStudentType(studentType);
		
	}

	
	public boolean isEnrolled() {
		return isEnrolled;
	}
	public void setEnrolled(boolean isEnrolled) {
		this.isEnrolled = isEnrolled;
	}

	
	public double getProgressPercentage() {
		return progressPercentage;
	}
	public void setProgressPercentage(double progressPercentage) {
		if(progressPercentage < 0.0 || progressPercentage > 100.0) {
			throw new IllegalArgumentException("Progress percentage must be between 0 to 100");
		}
		this.progressPercentage = progressPercentage;
	}
	

	public List<Course> getCompletedCourses() {
		return completedCourses;
	}
	public void setCompletedCourses(List<Course> completedCourses) {
		this.completedCourses = completedCourses;
	}
	

	public List<Course> getCurrentCourses() {
		return currentCourses;
	}
	public void setCurrentCourses(List<Course> currentCourses) {
		this.currentCourses = currentCourses;
	}
	

	public StudentType getStudentType() {
		return studentType;
	}
	public void setStudentType(StudentType studentType) {
		if(studentType == null) {
			throw new IllegalArgumentException("Student type cannot be null");
		}
		this.studentType = studentType;
	}
	
	
}
