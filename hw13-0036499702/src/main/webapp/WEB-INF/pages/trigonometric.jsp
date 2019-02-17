<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style>
	 	body {background-color: <%= session.getAttribute("pickedBgCol") %> ;}
	</style>
	<body>
		<h1>Table of sin(x) and cos(x) values</h1>
		<table>
			<thead>
				<tr><th>Number</th><th>sin</th><th>cos</th></tr>
			</thead>
			<tbody>
				<c:forEach var="entry" items="${entries }">
					<tr><td>${entry.number}</td><td>${entry.sin }</td><td>${entry.cos }</td></tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>