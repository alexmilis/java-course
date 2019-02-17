<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../style.css">		
	</head>
	<body>
		<h1>Blog</h1>
			<p> ${error } </p>
			
			<% if(session.getAttribute("currentId") == null){ %>
				<h2>Not logged in</h2>
				<form action="login" method="POST">
					 Nick:<br><input type="text" name="nick" value = "${nick }"><br>
					 Password:<br><input type="password" name="password"><br>
					 <input type="submit" value="Login"><input type="reset" value="Reset">
				</form>
				<a href = "register">Registration for new users</a><br>
			<% } else { %>
				<h2>Currently logged in: ${currentFn } ${currentLn }</h2>
				<a href = "logout">Logout</a><br> <br>
				<a href = "author/${currentNick }/new">Add new entry</a><br><br>
			<%} %>
						
			<h2>List of authors</h2>
			<c:forEach var="author" items="${authors }">
				<a href = "author/${author.nick }">${author.nick } </a><br>
			</c:forEach>
			
	</body>
</html>