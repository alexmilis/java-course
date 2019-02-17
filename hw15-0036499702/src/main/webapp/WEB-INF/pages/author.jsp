<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../style.css">		
	</head>
	<body>
		<% if(session.getAttribute("currentId") == null){ %>
			<h2>Not logged in</h2>
		<% } else { %>
			<h2>Currently logged in: ${currentFn } ${currentLn }</h2>
			<a href = "../logout">Logout</a><br> <br>
		<%} %>
			
		<h1>Blog of user ${blogNick }</h1>
		
			<p>${error } </p>
			
			<h3>List of entries</h3>
			<c:forEach var="entry" items="${entries }">
				<a href = "${blogNick }/${entry.id }">${entry.title }</a><br>
			</c:forEach>
			
			<% if(session.getAttribute("blogNick").equals(session.getAttribute("currentNick"))){ %>
				<br><a href = "${blogNick }/new">Add new blog entry</a><br>
				<a href = "../logout">Logout</a><br><br>
			<% } %>
			
			<br><a href = "../main">Back</a>
			
	</body>
</html>