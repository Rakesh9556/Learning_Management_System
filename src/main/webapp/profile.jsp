<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<html>
<head>
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        p {
            font-size: 16px;
            color: #666;
        }
    </style>
</head>
<body>

<% 
    String userType = (String) session.getAttribute("userType");
    String fullname = (String) session.getAttribute("fullname");
    String email = (String) session.getAttribute("email");
%>

<h1>User Profile</h1>
<p><strong>Name:</strong> <%= fullname %></p>
<p><strong>Email:</strong> <%= email %></p>

<% if ("STUDENT".equals(userType)) { %>
    <h2>Student Details</h2>
    <p><strong>Branch:</strong> <%= session.getAttribute("branch") %></p>
    <p><strong>Registration No:</strong> <%= session.getAttribute("regdNo") %></p>
    <p><strong>Username:</strong> <%= session.getAttribute("username") %></p>

<% } else if ("FACULTY".equals(userType)) { %>
    <h2>Faculty Details</h2>
    <p><strong>Joining Date:</strong> <%= session.getAttribute("joiningDate") %></p>
    <p><strong>Designation:</strong> <%= session.getAttribute("designation") %></p>

<% } else if ("ADMIN".equals(userType)) { %>
    <h2>Admin Details</h2>
    <p>Welcome, Admin! You have full access to the system.</p>

<% } else { %>
    <p>User type is not recognized.</p>
<% } %>

</body>
</html>
