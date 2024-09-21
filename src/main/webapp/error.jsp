<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Failed</title>
</head>
<body>
    <h2>Oops! Something went wrong during your registration process.</h2>
    <p>Please try again later or go back to the <a href="registration.jsp">registration page</a>.</p>

    <!-- Display the detailed error message if it's available -->
    <h3>Error Details:</h3>
    <p>
        <%= (request.getAttribute("errorMessage") != null) 
            ? request.getAttribute("errorMessage") 
            : "An unknown error occurred." 
        %>
    </p>
</body>
</html>
