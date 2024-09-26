<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="styles.css"> <!-- Assuming you have a CSS file -->
</head>
<body>
    <div class="container">
        <h1 style="color: red;">Registration Error</h1>
        <p>There was an issue with your registration:</p>
        <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <a href="registration.jsp">Go back to Registration Page</a>
    </div>
</body>
</html>
