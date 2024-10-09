package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lms.models.UniversityStudent;
import com.lms.util.DbConnect;

public class UniversityStudentDao {
	
	// Register a University Student
	public boolean registerUniversityStudent(UniversityStudent universityStudent) throws ClassNotFoundException, SQLException {
		
		// Step1: Prepare the query
		final String INSERT_UNIVERSITY_STUDENTS = "INSERT INTO university_students (role, studentType, fullname, email, password, studentId, universityName, department, specialization, universityEmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Step2: Establish connection and create prepared statement obj to execute query 
		// connection will be automatically closed after the try block ends
		try (Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(INSERT_UNIVERSITY_STUDENTS)) {
			
		// Step3: Setting the placeholder with actual values / binding values
			st.setString(1, universityStudent.getRole().name());
			st.setString(2, universityStudent.getStudentType().name());
			st.setString(3, universityStudent.getFullname());
			st.setString(4, universityStudent.getEmail());
			st.setString(5, universityStudent.getPassword());
			st.setString(6, universityStudent.getStudentId());
			st.setString(7, universityStudent.getUniversityName());
			st.setString(8, universityStudent.getDepartment().name());
			st.setString(9,universityStudent.getSpecialization().name());
			st.setString(10, universityStudent.getUniversityEmail());
			
			
		// Step4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				throw new SQLException("Failed to insert into database");
			}
			
			return rowsAffected > 0;
	
		} catch (SQLException e) {
			throw new SQLException(e.getMessage(), e);
			
		}
			
	}
}


