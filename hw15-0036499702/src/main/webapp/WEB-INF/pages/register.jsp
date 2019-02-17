<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../style.css">		
	</head>
	<body>
		<% if(session.getAttribute("currentId") == null){ %>
			<h2>Not logged in</h2>
		<% } else { %>
			<h2>Currently logged in: ${currentFn } ${currentLn }</h2>
			<a href = "../logout">Logout</a><br> <br>
		<%} %>
		
		<h1>Registration</h1>
			<p> ${error } </p>
			
			<h2>Input requested data. All fields must be filled in order to register new user.</h2>
			<form action="registration" method="POST">
				 First name:<br><input type="text" name="fn" value = "${fn }"><br>
				 Last name:<br><input type="text" name="ln" value = "${ln }"><br>
				 e-mail:<br><input type="email" name="email" value = "${email }"><br>
				 Nick:<br><input type="text" name="nick" value = "${nick }"><br>
				 Password:<br><input type="password" name="password"><br>
				 <input type="submit" value="Register">
			</form>
			
			<a href = "main">Back to blog</a>
	</body>
</html>