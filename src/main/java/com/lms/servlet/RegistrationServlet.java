package com.lms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.lms.util.DbConnect;
import org.mindrot.jbcrypt.BCrypt; // Ensure to include BCrypt for password hashing
import java.util.logging.Level; // Import for logging
import java.util.logging.Logger; // Import for logging

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Logger declaration
    private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class.getName());

    private static final String[] ADMIN_NAMES = {"Pradeepta Mandal", "Rakesh Guru", "Prasada Kumar Gouda"};
    private static final String[] ADMIN_EMAILS = {"mandalpradeepta0@gmail.com", "rakeshguru9556@gmail.com", "prasadgouda2005@gmail.com"};
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$"); // Simple email validation pattern

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
        
        // Log the received parameters
        LOGGER.log(Level.INFO, "Received registration request with parameters: {0}", request.getParameterMap());

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String branch = request.getParameter("branch");
        String userType = request.getParameter("userType");
        String regdNo = request.getParameter("regd_no");
        String username = request.getParameter("username");
        String joiningDate = request.getParameter("joining_date");
        String designation = request.getParameter("designation");

        try (Connection conn = DbConnect.getConnnection()) {
            if (conn == null) {
                throw new SQLException("Connection to the database failed");
            }

            // Log successful connection
            LOGGER.info("Database connection established.");

            conn.setAutoCommit(false);

            if ("ADMIN".equals(userType)) {
                if (!isValidAdmin(fullname, email)) {
                    request.setAttribute("errorMessage", "Unauthorized Admin.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
                if (registerAdmin(conn, fullname, email, password)) {
                    conn.commit();
                    storeAdminInSession(request.getSession(), fullname, email, userType);
                    response.sendRedirect("login.jsp");
                } else {
                    conn.rollback();
                    request.setAttribute("errorMessage", "Failed to register Admin. Please try again.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                return;
            }

            if ("UNIVERSITY".equals(userType)) {
                if (!isValidUniversityEmail(email, regdNo)) {
                    request.setAttribute("errorMessage", "Invalid University Email or Registration Number.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else if ("INDIVIDUAL".equals(userType)) {
                if (username == null || username.isEmpty()) {
                    request.setAttribute("errorMessage", "Username is required for Individual Student.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else if ("FACULTY".equals(userType)) {
                if (joiningDate == null || designation == null || joiningDate.isEmpty() || designation.isEmpty()) {
                    request.setAttribute("errorMessage", "Joining Date and Designation are required for Faculty.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
                registerFaculty(conn, fullname, email, password, joiningDate, designation);
                conn.commit();
                response.sendRedirect("login.jsp");
                return;
            }

            registerStudent(conn, fullname, email, password, branch, userType, regdNo, username);
            conn.commit();
            response.sendRedirect("login.jsp");
           

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred", e);
            handleSQLException(request, response, e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class Not Found Exception occurred", e);
        }
    }

    


    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidUniversityEmail(String email, String regdNo) {
        return email.endsWith("@cutm.ac.in") && email.startsWith(regdNo);
    }

    private boolean isValidAdmin(String fullname, String email) {
        for (int i = 0; i < ADMIN_NAMES.length; i++) {
            if (fullname.equals(ADMIN_NAMES[i]) && email.equals(ADMIN_EMAILS[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean registerAdmin(Connection conn, String fullname, String email, String password) throws SQLException {
        String adminSql = "INSERT INTO admin (fullname, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement adminStmt = conn.prepareStatement(adminSql)) {
            adminStmt.setString(1, fullname);
            adminStmt.setString(2, email);
            adminStmt.setString(3, hashPassword(password));
            return adminStmt.executeUpdate() > 0;
        }
    }

    private void storeAdminInSession(HttpSession session, String fullname, String email, String userType) {
        session.setAttribute("fullname", fullname);
        session.setAttribute("email", email);
        session.setAttribute("userType", userType);
    }

    private void registerFaculty(Connection conn, String fullname, String email, String password, String joiningDate, String designation) throws SQLException {
        String facultySql = "INSERT INTO faculty (fullname, email, password, joining_date, designation) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement facultyStmt = conn.prepareStatement(facultySql)) {
            facultyStmt.setString(1, fullname);
            facultyStmt.setString(2, email);
            facultyStmt.setString(3, hashPassword(password));
            facultyStmt.setString(4, joiningDate);
            facultyStmt.setString(5, designation);
            facultyStmt.executeUpdate();
        }
    }

    private void registerStudent(Connection conn, String fullname, String email, String password, String branch, String userType, String regdNo, String username) throws SQLException {
        String sql = "INSERT INTO student (fullname, email, password, branch, student_type, regd_no, username, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setString(3, hashPassword(password));
            stmt.setString(4, branch);

            if ("UNIVERSITY".equals(userType)) {
                stmt.setString(5, "UNIVERSITY");
                stmt.setString(6, regdNo);
                stmt.setString(7, null);
            } else if ("INDIVIDUAL".equals(userType)) {
                stmt.setString(5, "INDIVIDUAL");
                stmt.setString(6, null);
                stmt.setString(7, username);
            }
            stmt.executeUpdate();
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void handleSQLException(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException {
        if ("23000".equals(e.getSQLState())) {
            request.setAttribute("errorMessage", "The email is already registered. Please use a different email.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } else {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
