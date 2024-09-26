<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Faculty Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .navbar {
            background-color: #333;
            padding: 15px;
            color: white;
            text-align: center;
        }
        .navbar h1 {
            margin: 0;
        }
        .container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        }
        .welcome-message {
            text-align: center;
            margin-bottom: 30px;
        }
        .actions {
            display: flex;
            justify-content: space-around;
        }
        .action-card {
            background-color: #5cb85c;
            color: white;
            padding: 20px;
            border-radius: 8px;
            width: 30%;
            text-align: center;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .action-card:hover {
            background-color: #4cae4c;
        }
        .logout-button {
            display: block;
            margin: 30px auto;
            padding: 10px 20px;
            background-color: #d9534f;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .logout-button:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>

<div class="navbar">
    <h1>Faculty Dashboard</h1>
</div>

<div class="container">
    <div class="welcome-message">
        <h2>Welcome, Faculty Member!</h2>
        <p>You are logged in as <strong>${sessionScope.email}</strong></p>
    </div>

    <div class="actions">
        <div class="action-card" onclick="window.location.href='viewCourses.jsp'">
            <h3>View Courses</h3>
            <p>View and manage the courses you teach.</p>
        </div>

        <div class="action-card" onclick="window.location.href='manageStudents.jsp'">
            <h3>Manage Students</h3>
            <p>Manage student records and performance.</p>
        </div>

        <div class="action-card" onclick="window.location.href='profile.jsp'">
            <h3>Your Profile</h3>
            <p>View and update your profile information.</p>
        </div>
    </div>

    <form action="LogoutServlet" method="post">
        <button class="logout-button" type="submit">Logout</button>
    </form>
</div>

</body>
</html>
