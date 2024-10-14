<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.lms.models.User" %>  <!-- Import your User class -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <style>
        .user-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .user-table th, .user-table td {
            padding: 10px;
            border: 1px solid #ccc;
        }
        .user-table th {
            background-color: #eee;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
        .actions button {
            padding: 5px 10px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .actions button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Manage Users</h1>
    
    <table class="user-table">
        <thead>
            <tr>
                <th>Full Name</th>
                <th>Email</th>
                <th>User Type</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
    <%
        // Check if the userList attribute is available in the request scope
        List<User> userList = (List<User>) request.getAttribute("userList");
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
    %>
            <tr>
                <td><%= user.getFullname() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getRole().toString() %></td> <!-- Assuming Role class has a proper toString() method -->
                <td class="actions">
                    <form action="EditUserServlet" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="<%= user.getUserId() %>"> <!-- Updated to use getUserId() -->
                        <button type="submit">Edit</button>
                    </form>
                    <form action="DeleteUserServlet" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="<%= user.getUserId() %>"> <!-- Updated to use getUserId() -->
                        <button type="submit" onclick="return confirm('Are you sure you want to delete this user?');">Delete</button>
                    </form>
                </td>
            </tr>
    <%
            }
        } else {
    %>
            <tr>
                <td colspan="4" style="text-align:center;">No users found.</td>
            </tr>
    <%
        }
    %>
</tbody>

    </table>
</body>
</html>
