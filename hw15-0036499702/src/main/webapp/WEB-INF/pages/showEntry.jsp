<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../../style.css">		
	</head>
	<body>
		<% if(session.getAttribute("currentId") == null){ %>
			<h2>Not logged in</h2>
		<% } else { %>
			<h2>Currently logged in: ${currentFn } ${currentLn }</h2>
			<a href = "../../logout">Logout</a><br> <br>
		<% session.setAttribute("usersemail", session.getAttribute("currentEmail"));
			} %>
		
		<h1>Blog user: ${blogNick }</h1>
		<h2>Blog entry: ${entry.title }</h2>
		<p>${entry.text } </p>
		
		<% if(session.getAttribute("blogNick").equals(session.getAttribute("currentNick"))){ %>
			<br><a href = "edit/${entry.id }">Edit blog entry</a><br><br>
		<% } %>
			
		<a href = "../../main">Back</a>
		
		<h3>Comments</h3>
		
		<c:forEach var="comment" items="${entry.comments }">
			<br>${comment.usersEMail }   ${comment.postedOn }
			<br>${comment.message }<br>
		</c:forEach>
		
		<p>${error }</p>
		
		<form action="../../comment" method="GET">
			 E-mail:<br><input type="email" name="usersemail" value = "${usersemail }"><br>
			 Comment:<br><textarea name="comment">${comment }</textarea><br>
			 <input type="submit" value="Add comment">
			 <input type="hidden" name="entry" value=${entry.id }>
			 <input type="hidden" name="entryNick" value=${blogNick }>
		</form>
			
	</body>
</html>