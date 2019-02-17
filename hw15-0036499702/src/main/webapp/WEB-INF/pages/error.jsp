<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../../../style.css">		
	</head>
	<body>
		<% if(session.getAttribute("currentId") == null){ %>
			<h2>Not logged in</h2>
		<% } else { %>
			<h2>Currently logged in: ${currentFn } ${currentLn }</h2>
			<a href = "../logout">Logout</a><br> <br>
		<%} %>
		
		<h1>ERROR</h1>
			<p> ${error } </p>
			
			<a href = "../main">Back to blog</a>
	</body>
</html>