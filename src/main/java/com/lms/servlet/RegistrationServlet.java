package com.lms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.lms.util.DbConnect;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String branch = request.getParameter("branch");
        String userType = request.getParameter("userType");

        // Additional fields for University Student, Individual Student, and Faculty
        String regdNo = request.getParameter("regd_no");
        String username = request.getParameter("username");
        String joiningDate = request.getParameter("joining_date");
        String designation = request.getParameter("designation");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establish connection to the database using DbConnect class
            conn = DbConnect.getConnnection();

            // Check if the connection is established
            if (conn == null) {
                //System.out.println("Failed to establish database connection");
                throw new SQLException("Connection to database failed");  // Force the error handling
            } 
            //else {
//                System.out.println("Database connection established successfully");
//            }
         // Test if the connection is null and throw a custom SQLException
//            if (conn == null) {
//                throw new SQLException("Testing error message handling: Connection is null");
//            }

            //conn.setAutoCommit(false);  // Start transaction

            // SQL query to insert student or admin based on userType
//            String sql = "INSERT INTO student (fullname, email, password, branch, role, student_type, regd_no, username, created_at, updated_at) " +
//                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
            String sql = "INSERT INTO student (fullname, email, password, branch, student_type, regd_no, username, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";


         // Check userType and set parameters accordingly
            if (userType.equals("UNIVERSITY")) {
                stmt.setString(1, fullname);  
                stmt.setString(2, email);  
                stmt.setString(3, password);  
                stmt.setString(4, branch);  
                stmt.setString(5, "UNIVERSITY");  // Set student_type
                stmt.setString(6, regdNo);  
                stmt.setString(7, null);  // Username can be null for UNIVERSITY

            } else if (userType.equals("INDIVIDUAL")) {
                stmt.setString(1, fullname);  
                stmt.setString(2, email);  
                stmt.setString(3, password);  
                stmt.setString(4, branch);  
                stmt.setString(5, "INDIVIDUAL");  // Set student_type
                stmt.setString(6, null);  // Registration number can be null for INDIVIDUAL
                stmt.setString(7, username);  

            } else if (userType.equals("FACULTY")) {
                // Faculty entry into a separate table
                String facultySql = "INSERT INTO faculty (fullname, email, password, joining_date, designation) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement facultyStmt = conn.prepareStatement(facultySql)) {
                    facultyStmt.setString(1, fullname);
                    facultyStmt.setString(2, email);
                    facultyStmt.setString(3, password);
                    facultyStmt.setString(4, joiningDate);
                    facultyStmt.setString(5, designation);
                    facultyStmt.executeUpdate();
                }
                // No need to insert into student table for faculty
                request.getRequestDispatcher("success.jsp").forward(request, response);
                return;

            } else if (userType.equals("ADMIN")) {
                // Optionally handle ADMIN similarly
                // You may need an admin table for this
                request.getRequestDispatcher("success.jsp").forward(request, response);

                return;
            }

            // Execute the SQL query only for student types
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();

            // Redirect to success page
            request.getRequestDispatcher("success.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            // Log the actual error message
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());  // Pass the error message to error.jsp
            request.getRequestDispatcher("error.jsp").forward(request, response);
            
            // Rollback in case of failure
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // Close resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}