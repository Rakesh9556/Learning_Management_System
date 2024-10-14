<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #333;
        }
        .dashboard-section {
            padding: 10px;
            margin-bottom: 15px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

<h1>Welcome, Admin!</h1>

<div class="dashboard-section">
    <h2>Manage Users</h2>
    <p>Here you can add, update, or remove users.</p>
    <a href="manageUsers.jsp">Go to User Management</a>
</div>

<div class="dashboard-section">
    <h2>View Reports</h2>
    <p>Access detailed reports of the system's performance and user activity.</p>
    <a href="viewReports.jsp">View Reports</a>
</div>

<div class="dashboard-section">
    <h2>Settings</h2>
    <p>Change system settings and preferences.</p>
    <a href="settings.jsp">Go to Settings</a>
</div>

</body>
</html>
