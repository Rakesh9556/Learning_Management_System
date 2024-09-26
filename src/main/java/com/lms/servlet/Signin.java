package com.lms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.lms.util.DbConnect;

@WebServlet("/Signin")
public class Signin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish connection to the database using DbConnect class
            conn = DbConnect.getConnnection();

            // Check if the connection is established
            if (conn == null) {
                throw new SQLException("Connection to database failed");
            }

            // SQL query to check user credentials based on userType
            String sql = null;
            if ("STUDENT".equals(userType)) {
                sql = "SELECT * FROM student WHERE email = ? AND password = ?";
            } else if ("FACULTY".equals(userType)) {
                sql = "SELECT * FROM faculty WHERE email = ? AND password = ?";
            } else if ("ADMIN".equals(userType)) {
                sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
                // Only allow specific admins
                if (!(email.equals("mandalpradeepta0@gmail.com") || email.equals("rakeshguru9556@gmail.com") || email.equals("prasadgouda2005@gmail.com"))) {
                    request.setAttribute("errorMessage", "You are not authorized to access admin functions.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "Invalid user type.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            // Check if user exists
            if (rs.next()) {
                // Set session attributes
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("fullname", rs.getString("fullname"));
                session.setAttribute("userType", userType);

                // Add specific session attributes for each user type
                if ("STUDENT".equals(userType)) {
                    session.setAttribute("branch", rs.getString("branch"));
                    session.setAttribute("regdNo", rs.getString("regd_no"));
                    session.setAttribute("username", rs.getString("username"));
                    response.sendRedirect("studentDashboard.jsp");  // Redirect to student dashboard
                } else if ("FACULTY".equals(userType)) {
                    session.setAttribute("joiningDate", rs.getString("joining_date"));
                    session.setAttribute("designation", rs.getString("designation"));
                    response.sendRedirect("facultyDashboard.jsp");  // Redirect to faculty dashboard
                } else if ("ADMIN".equals(userType)) {
                    response.sendRedirect("adminDashboard.jsp");  // Redirect to admin dashboard
                }
            } else {
                // If user does not exist, redirect to login page with error message
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
