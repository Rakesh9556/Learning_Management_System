<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Settings</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #333;
        }
        .settings-section {
            padding: 10px;
            margin-bottom: 15px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .settings-section label {
            font-weight: bold;
        }
        .settings-section input[type="text"], .settings-section input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .settings-section button {
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .settings-section button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Settings</h1>

<div class="settings-section">
    <h2>Change Password</h2>
    <form action="changePasswordServlet" method="POST">
        <label for="currentPassword">Current Password</label>
        <input type="password" id="currentPassword" name="currentPassword" required>

        <label for="newPassword">New Password</label>
        <input type="password" id="newPassword" name="newPassword" required>

        <label for="confirmPassword">Confirm New Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>

        <button type="submit">Update Password</button>
    </form>
</div>

<div class="settings-section">
    <h2>Profile Settings</h2>
    <form action="updateProfileServlet" method="POST">
        <label for="email">Email</label>
        <input type="text" id="email" name="email" value="<%= session.getAttribute("email") %>" required>

        <label for="fullname">Full Name</label>
        <input type="text" id="fullname" name="fullname" value="<%= session.getAttribute("fullname") %>" required>

        <button type="submit">Update Profile</button>
    </form>
</div>

</body>
</html>
