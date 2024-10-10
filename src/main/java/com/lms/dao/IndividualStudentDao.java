package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.lms.models.Department;
import com.lms.models.IndividualStudent;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.models.StudentType;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

public class IndividualStudentDao {
	
	// Register an individual student
	public boolean registerStudent(IndividualStudent individualStudent) throws SQLException, ClassNotFoundException, DuplicateEntryException {
		
		// Step 1: Prepare the query
		final String INSERT_INDIVIDUAL_STUDENTS = "INSERT into individual_students (role, studentType, username, fullname, email, password, universityName, department, specialization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(INSERT_INDIVIDUAL_STUDENTS)){
			
			// Step 3: Setting the placeholder with actual values/ binding values
			st.setString(1, individualStudent.getRole().name());
			st.setString(2, individualStudent.getStudentType().name());
			st.setString(3, individualStudent.getUsername());
			st.setString(4, individualStudent.getFullname());
			st.setString(5, individualStudent.getEmail());
			st.setString(6, individualStudent.getPassword());
			st.setString(7, individualStudent.getUniversityName());
			st.setString(8, individualStudent.getDepartment().name());
			st.setString(9, individualStudent.getSpecialization().name());
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			// Step 5: Validate if database operation performed or not
			if(rowsAffected == 0) {
				throw new SQLException("Failed to insert into database!");
			}
			
			// Step 6: Return the result
			return rowsAffected > 0;
			
		} catch (SQLException e) {
			// SQLState '23' indicates integrity constraint violations
			if (e.getSQLState().startsWith("23")) {  
	            throw new DuplicateEntryException("Username or Email ID already exists!", e);
	        }
			throw new SQLException("Failed to connect to the database!", e.getMessage());
		}
	}
	
	
	// Check if user already present or registered
	public boolean findUser (String username, String email) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the query
		final String findUser = "SELECT COUNT(*) FROM individual_students WHERE username = ? OR email = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(findUser)) {
			
			// Step 3: Setting the placeholder with actual values
			st.setString(1, username);
			st.setString(2, email);
			
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
	public IndividualStudent loginUser(String usernameOrEmail, String password) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the query
		final String findUser = "SELECT * FROM individual_students WHERE (username = ? OR email = ?) AND password = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(findUser)) {
			
			// Step 3: Setting up the placeholder with actual values
			st.setString(1, usernameOrEmail);
			st.setString(2, usernameOrEmail);
			st.setString(3, password);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet rs = st.executeQuery();
			
			// Step 5: If the user is found create a user object
			if(rs.next()) {
				IndividualStudent individualStudent = new IndividualStudent();
				individualStudent.setRole(Role.valueOf(rs.getString("role")));
				individualStudent.setStudentType(StudentType.valueOf(rs.getString("studentType")));
				individualStudent.setUsername(rs.getString("username"));
				individualStudent.setFullname(rs.getString("fullname"));
				individualStudent.setEmail(rs.getString("email"));
				individualStudent.setPassword(rs.getString("password"));
				individualStudent.setUniversityName(rs.getString("universityName"));
				individualStudent.setDepartment(Department.valueOf(rs.getString("department")));
				individualStudent.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
				
				// Set login status related fields
				individualStudent.setLoggedIn(true);
				individualStudent.setUpdatedAt(LocalDateTime.now());
				
				
				// Step 6: Return the university student object
				return individualStudent;
				
			}
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e.getMessage());
		}
		
		return null;
	}

}
