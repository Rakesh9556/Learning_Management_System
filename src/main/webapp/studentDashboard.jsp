<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #6e48aa;
            color: white;
            padding: 20px;
            text-align: center;
        }
        .container {
            margin: 20px;
        }
        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
        }
        .card h3 {
            margin-top: 0;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>Welcome to the Student Dashboard</h1>
</div>

<div class="container">
    <div class="card">
        <h3>My Courses</h3>
        <p>Here you can find your enrolled courses.</p>
        <!-- List of courses will be dynamically generated -->
    </div>

    <div class="card">
        <h3>Assignments</h3>
        <p>Check your upcoming assignments and deadlines.</p>
        <!-- List of assignments will be dynamically generated -->
    </div>

    <div class="card">
        <h3>Messages</h3>
        <p>View your latest messages.</p>
        <!-- Messages will be dynamically generated -->
    </div>

    <div class="card">
    <h3>Profile</h3>
    <p>View and update your profile information.</p>
    <a href="profile.jsp">View Profile</a>
    <br>
    
</div>


</body>
</html>
