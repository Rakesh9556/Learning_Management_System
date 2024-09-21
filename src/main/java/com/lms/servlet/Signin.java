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

            // SQL query to check user credentials
            String sql;
            if (userType.equals("STUDENT")) {
                sql = "SELECT * FROM student WHERE email = ? AND password = ?";
            } else if (userType.equals("FACULTY")) {
                sql = "SELECT * FROM faculty WHERE email = ? AND password = ?";
            } else if (userType.equals("ADMIN")) {
                sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
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
                session.setAttribute("userType", userType);

                // Redirect to respective dashboard based on user type
                if (userType.equals("STUDENT")) {
                    response.sendRedirect("studentDashboard.jsp");
                } else if (userType.equals("FACULTY")) {
                    response.sendRedirect("facultyDashboard.jsp");
                } else if (userType.equals("ADMIN")) {
                    response.sendRedirect("adminDashboard.jsp");
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
