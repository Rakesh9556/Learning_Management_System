<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
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
        form {
            margin-top: 20px;
        }
        label {
            font-size: 14px;
            color: white;
            display: block;
            margin-bottom: 5px;
            text-align: left;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="date"],
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
        p {
            font-size: 14px;
            color: white;
            margin-top: 20px;
        }
        a {
            color: white;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        select {
            appearance: none;
            background-color: #fff;
            color: #6e48aa;
            border-radius: 5px;
        }
    </style>
    <script>
        function showFields() {
            var userType = document.getElementById("userType").value;
            
            document.getElementById("universityFields").style.display = "none";
            document.getElementById("individualFields").style.display = "none";
            document.getElementById("facultyFields").style.display = "none";
            document.getElementById("branchField").style.display = "block";  

            if (userType === "UNIVERSITY") {
                document.getElementById("universityFields").style.display = "block";
            } else if (userType === "INDIVIDUAL") {
                document.getElementById("individualFields").style.display = "block";
            } else if (userType === "FACULTY") {
                document.getElementById("facultyFields").style.display = "block";
                document.getElementById("branchField").style.display = "none"; 
            } else if (userType === "ADMIN") {
                document.getElementById("branchField").style.display = "none";  
            }
        }
    </script>
</head>
<body>

    <div class="container">
        <h2>Register</h2>
        <form action="RegistrationServlet" method="post">
            <label for="fullname">Full Name:</label>
            <input type="text" id="fullname" name="fullname" required><br><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>

            <div id="branchField">
                <label for="branch">Branch:</label>
                <input type="text" id="branch" name="branch"><br><br>
            </div>

            <label for="userType">Select User Type:</label>
            <select id="userType" name="userType" onchange="showFields()" required>
                <option value="">Select...</option>
                <option value="UNIVERSITY">University Student</option>
                <option value="INDIVIDUAL">Individual Student</option>
                <option value="FACULTY">Faculty</option>
                <option value="ADMIN">Admin</option>
            </select><br><br>

            <div id="universityFields" style="display:none;">
                <label for="regd_no">Registration No:</label>
                <input type="text" id="regd_no" name="regd_no"><br><br>
            </div>

            <div id="individualFields" style="display:none;">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username"><br><br>
            </div>

            <div id="facultyFields" style="display:none;">
                <label for="joining_date">Joining Date:</label>
                <input type="date" id="joining_date" name="joining_date"><br><br>

                <label for="designation">Designation:</label>
                <input type="text" id="designation" name="designation"><br><br>
            </div>

            <input type="submit" value="Register">
        </form>

        <p>Already registered? <a href="login.jsp">Login here</a></p>
    </div>
    
</body>
</html>
