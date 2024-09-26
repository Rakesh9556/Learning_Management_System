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
import jakarta.servlet.http.HttpSession;

import com.lms.util.DbConnect;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Admin info
    private static final String[] ADMIN_NAMES = {"Pradeepta Mandal", "Rakesh Guru", "Prasada Kumar Gouda"};
    private static final String[] ADMIN_EMAILS = {"mandalpradeepta0@gmail.com", "rakeshguru9556@gmail.com", "prasadgouda2005@gmail.com"};

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
            // Establish connection to the database
            try {
				conn = DbConnect.getConnnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (conn == null) {
                throw new SQLException("Connection to database failed");
            }

            // Disable autocommit for manual transaction management
            conn.setAutoCommit(false);

            // Validate Admin registration
            if (userType.equals("ADMIN")) {
                boolean isValidAdmin = false;
                for (int i = 0; i < ADMIN_NAMES.length; i++) {
                    if (fullname.equals(ADMIN_NAMES[i]) && email.equals(ADMIN_EMAILS[i])) {
                        isValidAdmin = true;
                        break;
                    }
                }

                if (!isValidAdmin) {
                    request.setAttribute("errorMessage", "Unauthorized Admin.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                // Admin registration logic

                // Store user information in session
                HttpSession session = request.getSession();
                session.setAttribute("fullname", fullname);
                session.setAttribute("email", email);
                session.setAttribute("userType", userType);

                // Redirect to profile page
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Validate University Student registration
            if (userType.equals("UNIVERSITY")) {
                if (!email.endsWith("@cutm.ac.in") || !email.startsWith(regdNo)) {
                    request.setAttribute("errorMessage", "Invalid University Email or Registration Number.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            }

            // Validate Individual Student registration (Username required)
            if (userType.equals("INDIVIDUAL") && (username == null || username.isEmpty())) {
                request.setAttribute("errorMessage", "Username is required for Individual Student.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // Faculty registration logic
            if (userType.equals("FACULTY")) {
                if (joiningDate == null || designation == null || joiningDate.isEmpty() || designation.isEmpty()) {
                    request.setAttribute("errorMessage", "Joining Date and Designation are required for Faculty.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                // Insert into faculty table
                String facultySql = "INSERT INTO faculty (fullname, email, password, joining_date, designation) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement facultyStmt = conn.prepareStatement(facultySql)) {
                    facultyStmt.setString(1, fullname);
                    facultyStmt.setString(2, email);
                    facultyStmt.setString(3, password);
                    facultyStmt.setString(4, joiningDate);
                    facultyStmt.setString(5, designation);
                    facultyStmt.executeUpdate();
                }

                conn.commit(); // Commit transaction for Faculty

                // Store user information in session
                HttpSession session = request.getSession();
                session.setAttribute("fullname", fullname);
                session.setAttribute("email", email);
                session.setAttribute("userType", userType);

                // Redirect to profile page
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Insert University or Individual Student into student table
            String sql = "INSERT INTO student (fullname, email, password, branch, student_type, regd_no, username, created_at, updated_at) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, branch);

            if (userType.equals("UNIVERSITY")) {
                stmt.setString(5, "UNIVERSITY");
                stmt.setString(6, regdNo);
                stmt.setString(7, null);
            } else if (userType.equals("INDIVIDUAL")) {
                stmt.setString(5, "INDIVIDUAL");
                stmt.setString(6, null);
                stmt.setString(7, username);
            }

            stmt.executeUpdate();
            conn.commit();  // Commit transaction for students

            // Store user information in session
            HttpSession session = request.getSession();
            session.setAttribute("fullname", fullname);
            session.setAttribute("email", email);
            session.setAttribute("userType", userType);

            // Redirect to profile page
            request.getRequestDispatcher("profile.jsp").forward(request, response);

        } catch (SQLException e) {
            // Handle specific duplicate key error for email
            if (e.getSQLState().equals("23000")) {  // SQL state for integrity constraint violation
                request.setAttribute("errorMessage", "The email " + email + " is already registered. Please use a different email.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } else {
                e.printStackTrace();
                request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

            if (conn != null) {
                try {
                    conn.rollback();  // Rollback transaction if error occurs
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset autocommit to true
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
