package com.lms.models;


public abstract class Student extends User{
	private StudentType studentType;
	private boolean isEnrolled;
	
	
	public Student() {
        super(); // Call the no-argument constructor of the User class (if it exists)
    }
	
	public Student(Role role, StudentType studentType, String fullname, String email, String password, String universityName, Department department, Specialization specialization) {
		super(role, fullname, email, password, universityName, department, specialization);
		this.setStudentType(studentType);
		this.setEnrolled(isEnrolled);
		
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
	
	
	public boolean isEnrolled() {
		return isEnrolled;
	}
	public void setEnrolled(boolean isEnrolled) {
		this.isEnrolled = isEnrolled;
	}
	
	
}
