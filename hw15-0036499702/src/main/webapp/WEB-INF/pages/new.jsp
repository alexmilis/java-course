<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<%if("new".equals(request.getAttribute("action"))){ %>
			<link rel="stylesheet" type="text/css" href="../../../style.css">		
		<%} else { %>
			<link rel="stylesheet" type="text/css" href="../../../../style.css">		
		<%} %>
	</head>
	<body>
		<% if(session.getAttribute("currentId") == null){ %>
			<h2>Not logged in</h2>
		<% } else { %>
			<h2>Currently logged in: ${currentFn } ${currentLn }</h2>
			<%if("new".equals(request.getAttribute("action"))){ %>
				<a href = "../../logout">Logout</a><br> <br>		
			<%} else { %>
				<a href = "../../../logout">Logout</a><br> <br>		
			<%} %>
		<%} %>
		
		<h1>New entry by ${currentNick }</h1>
			<p> ${error } </p>
			
			<h2>Input requested data. All fields must be filled in order to add new entry.</h2>
			<form action="${action }" method="GET">
				 Title:<br><input type="text" name="title" value = "${title }"><br>
				 Text<br><textarea name="text">${text }</textarea><br>
				 <input type="submit" value="Add Entry">
			</form>
			
			<a href = "../../../main">Back to blog</a>
	</body>
</html>