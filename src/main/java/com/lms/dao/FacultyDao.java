package com.lms.dao;

import com.lms.models.Faculty;
import com.lms.models.Department;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FacultyDao {

    // Register a faculty member
    public boolean registerFaculty(Faculty faculty) throws SQLException, ClassNotFoundException, DuplicateEntryException {

        // Step 1: Prepare the query
        final String INSERT_FACULTY = "INSERT into faculty (role, fullname, email, password, universityName, department, specialization, facultyId, joiningDate, permissions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(INSERT_FACULTY)) {

            // Step 3: Set the placeholder with actual values/binding values
            st.setString(1, faculty.getRole().name());
            st.setString(2, faculty.getFullname());
            st.setString(3, faculty.getEmail());
            st.setString(4, faculty.getPassword());
            st.setString(5, faculty.getUniversityName());
            st.setString(6, faculty.getDepartment().name());
            st.setString(7, faculty.getSpecialization().name());
            st.setString(8, faculty.getFacultyId());
            st.setDate(9, Date.valueOf(faculty.getJoiningDate()));
            st.setString(10, String.join(",", faculty.getPermissions()));

            // Step 4: Execute the prepared statement
            int rowsAffected = st.executeUpdate();

            // Step 5: Check if insertion was successful
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert into database!");
            }

            // Step 6: Return the result
            return rowsAffected > 0;

        } catch (SQLException e) {
            // SQLState '23' indicates integrity constraint violations
            if (e.getSQLState().startsWith("23")) {
                throw new DuplicateEntryException("Faculty ID or Email ID already exists!", e);
            }
            throw new SQLException("Failed to connect to the database!", e.getMessage());
        }
    }

    // Check if a faculty member already exists
    public boolean findFaculty(String facultyId, String email) throws SQLException, ClassNotFoundException {

        // Step 1: Prepare the query
        final String FIND_FACULTY = "SELECT COUNT(*) FROM faculty WHERE facultyId = ? OR email = ?";

        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(FIND_FACULTY)) {

            // Step 3: Set the placeholder with actual values
            st.setString(1, facultyId);
            st.setString(2, email);

            // Step 4: Execute the query and store the result into result set
            ResultSet rs = st.executeQuery();

            // Step 5: Check if the faculty exists
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database!", e.getMessage());
        }

        return false;
    }

    // Login faculty member
    public Faculty loginFaculty(String facultyIdOrEmail, String password) throws SQLException, ClassNotFoundException {

        // Step 1: Prepare the query
        final String LOGIN_FACULTY = "SELECT * FROM faculty WHERE (facultyId = ? OR email = ?) AND password = ?";

        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(LOGIN_FACULTY)) {

            // Step 3: Set the placeholder with actual values
            st.setString(1, facultyIdOrEmail);
            st.setString(2, facultyIdOrEmail);
            st.setString(3, password);

            // Step 4: Execute the query and store the result into result set
            ResultSet rs = st.executeQuery();

            // Step 5: If the faculty is found, create a Faculty object
            if (rs.next()) {
            	
//                Role role = Role.valueOf(rs.getString("role"));
//                String fullname = rs.getString("fullname");
//                String email = rs.getString("email");
//                String facultyId = rs.getString("facultyId");
//                String universityName = rs.getString("universityName");
//                Department department = Department.valueOf(rs.getString("department"));
//                Specialization specialization = Specialization.valueOf(rs.getString("specialization"));
//                LocalDate joiningDate = rs.getDate("joiningDate").toLocalDate();
//                List<String> permissions = List.of(rs.getString("permissions").split(","));
            	
//              return new Faculty(role, fullname, email, rs.getString("password"), universityName, department, specialization, facultyId, joiningDate, permissions);

            	
            	Faculty faculty = new Faculty();
            	faculty.setRole(Role.valueOf(rs.getString("role")));
            	faculty.setFullname(rs.getString("fullname"));
            	faculty.setEmail(rs.getString("email"));
            	faculty.setFacultyId(rs.getString("facultyId"));
            	faculty.setUniversityName(rs.getString("universityName"));
            	faculty.setDepartment(Department.valueOf(rs.getString("department")));
            	faculty.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
            	faculty.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
            	faculty.setPermissions(List.of(rs.getString("permissions").split(",")));
            	
            	//Set login status related fields
            	faculty.setLoggedIn(true);
                faculty.setUpdatedAt(LocalDateTime.now());
                
                //Step 6: Return the university student object
            	return faculty;

            }
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database!", e.getMessage());
        }

        return null;
    }
}
