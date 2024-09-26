<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to bottom, #9d50bb, #6e48aa);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
            width: 320px;
            text-align: center;
            background: linear-gradient(to bottom, #a18cd1, #fbc2eb);
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            padding: 40px 30px;
            box-sizing: border-box;
        }

        h2 {
            color: white;
            font-size: 24px;
            margin-bottom: 20px;
        }

        .error-message {
            color: red;
            margin-bottom: 20px;
            font-size: 14px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 10px;
            border-radius: 5px;
        }

        label {
            font-size: 14px;
            color: white;
            display: block;
            margin-bottom: 5px;
            text-align: left;
        }

        input[type="text"], 
        input[type="password"], 
        select {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: white;
            color: #6e48aa;
            border: none;
            border-radius: 5px;
            padding: 10px;
            width: 100%;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }

        input[type="submit"]:hover {
            background-color: #6e48aa;
            color: white;
        }

        select {
            background-color: white;
            color: #6e48aa;
        }

        .register-link {
            margin-top: 20px;
            color: white;
            font-size: 14px;
        }

        .register-link a {
            color: black; /* Set initial link color to black */
            text-decoration: none;
        }

        .register-link a:hover {
            color: white; /* Change to white on hover */
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Login</h2>

    <!-- Display error message if login fails -->
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <form action="Signin" method="post">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <label for="userType">User Type:</label>
        <select id="userType" name="userType" required>
            <option value="">Select...</option>
            <option value="STUDENT">Student</option>
            <option value="FACULTY">Faculty</option>
            <option value="ADMIN">Admin</option>
        </select>

        <input type="submit" value="Login">
    </form>

    <!-- Link to Registration page -->
    <div class="register-link">
        Don't have an account? <a href="registration.jsp">Register Here</a>
    </div>
</div>

</body>
</html>
