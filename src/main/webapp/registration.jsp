<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
     <script>
        function showFields() {
            var userType = document.getElementById("userType").value;
            
            // Hide all conditional fields by default
            document.getElementById("universityFields").style.display = "none";
            document.getElementById("individualFields").style.display = "none";
            document.getElementById("facultyFields").style.display = "none";
            document.getElementById("branchField").style.display = "block";  // Show branch by default

            // Show or hide fields based on user type selection
            if (userType === "UNIVERSITY") {
                document.getElementById("universityFields").style.display = "block";
            } else if (userType === "INDIVIDUAL") {
                document.getElementById("individualFields").style.display = "block";
            } else if (userType === "FACULTY") {
                document.getElementById("facultyFields").style.display = "block";
                document.getElementById("branchField").style.display = "none";  // Hide branch for Faculty
            } else if (userType === "ADMIN") {
                document.getElementById("branchField").style.display = "none";  // Hide branch for Admin
            }
        }
    </script>
   
</head>
<body>

    <h2>Register</h2>

    <form action="RegistrationServlet" method="post">
        <label for="fullname">Full Name:</label>
        <input type="text" id="fullname" name="fullname" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <!-- Branch field, initially visible -->
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

        <!-- Fields for University Student -->
        <div id="universityFields" style="display:none;">
            <label for="regd_no">Registration No:</label>
            <input type="text" id="regd_no" name="regd_no"><br><br>
        </div>

        <!-- Fields for Individual Student -->
        <div id="individualFields" style="display:none;">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"><br><br>
        </div>

        <!-- Fields for Faculty -->
        <div id="facultyFields" style="display:none;">
            <label for="joining_date">Joining Date:</label>
            <input type="date" id="joining_date" name="joining_date"><br><br>

            <label for="designation">Designation:</label>
            <input type="text" id="designation" name="designation"><br><br>
        </div>

        <input type="submit" value="Register">
    </form>

</body>
</html>
