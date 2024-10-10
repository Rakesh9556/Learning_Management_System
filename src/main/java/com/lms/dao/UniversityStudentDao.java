package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.lms.models.Department;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.models.StudentType;
import com.lms.models.UniversityStudent;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

public class UniversityStudentDao {
	
	// Register a University Student
	public boolean registerStudent(UniversityStudent universityStudent) throws ClassNotFoundException, SQLException, DuplicateEntryException {
		
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
			
			// Step 5: Validate if database operation performed or not
			if(rowsAffected == 0) {
				throw new SQLException("Failed to insert into database");
			}
			
			// Step 6: Return the result
			return rowsAffected > 0;
	
		} catch (SQLException e) {
			// SQLState '23' indicates integrity constraint violations
			if (e.getSQLState().startsWith("23")) {  
				throw new DuplicateEntryException("Email or student ID already exists!", e);
	        }
			throw new SQLException(e.getMessage(), e);
			
		}
			
	}
	
	
	// Check if user already present or registered
	public boolean findUser (String email, String studentId) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the query
		final String findUser = "SELECT COUNT(*) FROM university_students WHERE email = ? OR studentId = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(findUser)) {
			
			// Step 3: Setting the placeholder with actual values
			st.setString(1, email);
			st.setString(2, studentId);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet rs = st.executeQuery();
			
			// Step 5: Checking if multiple user exist or not
			if(rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e.getMessage());
		}
		
		return false;
	}
	
	
	// Login the user
	public UniversityStudent loginUser(String universityEmailOrStudentId, String password) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the query
		final String findUser = "SELECT * FROM university_students WHERE (universityEmail = ? OR studentId = ?) AND password = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(findUser)) {
			
			// Step 3: Setting up the placeholder with actual values
			st.setString(1, universityEmailOrStudentId);
			st.setString(2, universityEmailOrStudentId);
			st.setString(3, password);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet rs = st.executeQuery();
			
			// Step 5: If the user is found create a user object
			if(rs.next()) {
				UniversityStudent universityStudent = new UniversityStudent();
				universityStudent.setRole(Role.valueOf(rs.getString("role")));
				universityStudent.setStudentType(StudentType.valueOf(rs.getString("studentType")));
				universityStudent.setFullname(rs.getString("fullname"));
				universityStudent.setEmail(rs.getString("email"));
				universityStudent.setPassword(rs.getString("password"));
				universityStudent.setStudentId(rs.getString("studentId"));
				universityStudent.setUniversityName(rs.getString("universityName"));
				universityStudent.setDepartment(Department.valueOf(rs.getString("department")));
				universityStudent.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
				universityStudent.setUniversityEmail(rs.getString("universityEmail"));
				
				// Set login status related fields
				universityStudent.setLoggedIn(true);
				universityStudent.setUpdatedAt(LocalDateTime.now());
				
				
				// Step 6: Return the university student object
				return universityStudent;
				
			}
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e.getMessage());
		}
		
		return null;
	}
}




