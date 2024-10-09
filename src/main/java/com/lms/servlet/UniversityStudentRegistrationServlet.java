package com.lms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import com.lms.dao.UniversityStudentDao;
import com.lms.models.Department;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.models.StudentType;
import com.lms.models.UniversityStudent;
import com.lms.util.ApiError;

@WebServlet("/universityStudentRegister")
public class UniversityStudentRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Step 1: Create an instance of UniversityStudentDao to access its methods (register, login, etc)
	private UniversityStudentDao universityStudentDao;
	
	
    public UniversityStudentRegistrationServlet() {
    	// Step 2: Initialize the dao
        this.universityStudentDao = new UniversityStudentDao();
    }

    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// Step 3: Retrieve data/parameters from request (all data is coming from requests)
		try {
			
			// Ensure user select the role first
			String role = req.getParameter("role").toUpperCase();
			
			// validate if the user select its role or not
			if(role == null || role.trim().isEmpty()) {
				throw new ApiError(400, "Role is required");
			}
			
			role = role.toUpperCase();
			
			// if role exist then check if the user is student or not
			if(role.equals("STUDENT")) {
				
				// Ensure student select its role (individual or University)
				String studentType = req.getParameter("studentType");
				
				// validate if the student select its type or not
				if(studentType == null  || studentType.trim().isEmpty()) {
					throw new ApiError(400, "Student type is required");
				}
				
				studentType = studentType.toUpperCase();
				
				// if student role exist then check if the student is individual or university student
				if(studentType.equals("UNIVERSITY")) {
					// define the fields for the university student
					String firstname = req.getParameter("firstname");
					String lastname = req.getParameter("lastname");
					String email = req.getParameter("email");
					String password = req.getParameter("password");
					String universityName = req.getParameter("universityName");
					String studentId = req.getParameter("studentId");
					String universityEmail = req.getParameter("universityEmail");
					String department = req.getParameter("department");
					String specialization = req.getParameter("specialization");
					
					
					// validate all the fields
					if(firstname == null || firstname.trim().isEmpty()) {
						throw new ApiError(400, "First name is required");
					}
					
					if(lastname == null || lastname.trim().isEmpty()) {
						throw new ApiError(400, "Last name is required");
					}
					
					if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
						throw new ApiError(400, "Invalid email address");
					}
					
					if(password == null || password.trim().isEmpty()) {
						throw new ApiError(400, "Password is required");
					}
					
					if(universityName == null || universityName.trim().isEmpty()) {
						throw new ApiError(400, "University name is required");
					}
					
					if(studentId == null || studentId.trim().isEmpty()) {
						throw new ApiError(400, "Student id is required");
					}
					
					if(universityEmail == null || universityEmail.trim().isEmpty()) {
						throw new ApiError(400, "University mail id is required");
					}
					
					if(department == null) {
						throw new ApiError(400, "Department is required");
					}
					
					if(specialization == null) {
						throw new ApiError(400, "Specialization is required");
					}
					

					
					// Validating the enum fields and registering the student
					try {
					    Department departmentEnum = Department.valueOf(department.toUpperCase());
					    Specialization specializationEnum = Specialization.valueOf(specialization.toUpperCase());
					    Role roleEnum = Role.valueOf(role);
					    StudentType studentTypeEnum = StudentType.valueOf(studentType.toUpperCase());

					    // Create the UniversityStudent object
					    UniversityStudent universityStudent = new UniversityStudent();
					    universityStudent.setRole(roleEnum);
					    universityStudent.setStudentType(studentTypeEnum);
					    universityStudent.setFullname(firstname + " " + lastname);
					    universityStudent.setEmail(email);
					    universityStudent.setPassword(password);
					    universityStudent.setStudentId(studentId);
					    universityStudent.setUniversityName(universityName);
					    universityStudent.setDepartment(departmentEnum);
					    universityStudent.setSpecialization(specializationEnum); 
					    universityStudent.setUniversityEmail(universityEmail);
					    universityStudent.setUpdatedAt(LocalDateTime.now());
					    
					    
					    // Testing data
//					    System.out.println(universityStudent.getRole());
//					    System.out.println(universityStudent.getStudentType());
//					    System.out.println(universityStudent.getFullname());
//					    System.out.println(universityStudent.getEmail());
//					    System.out.println(universityStudent.getPassword());
//					    System.out.println(universityStudent.getStudentId());
//					    System.out.println(universityStudent.getUniversityName());
//					    System.out.println(universityStudent.getDepartment());
//					    System.out.println(universityStudent.getSpecialization());
//					    System.out.println(universityStudent.getUniversityEmail());
					    

					    // Calling the DAO class method to register the university student
					    boolean isRegistered = universityStudentDao.registerUniversityStudent(universityStudent);

					    if (isRegistered) {
					        res.getWriter().write("University student registered successfully");
					    } else {
					        throw new ApiError(500, "Failed to register university student");
					    }
					} catch (IllegalArgumentException e) {
					    throw new ApiError(400, "Invalid department or specialization");
					}
				} 
				else {
                    throw new ApiError(400, "Invalid student type selected");
				}

			}
			
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			res.getWriter().write(e.getMessage());
		}
		
	}

}
