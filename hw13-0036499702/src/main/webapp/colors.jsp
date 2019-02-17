<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style>
	 	body { background-color: <%= session.getAttribute("pickedBgCol")%>; }
	</style>
	<body>
		<a href = "/webapp2/setcolor?pickedBgCol=white">WHITE</a><br>
		<a href = "/webapp2/setcolor?pickedBgCol=red">RED</a><br>
		<a href = "/webapp2/setcolor?pickedBgCol=green">GREEN</a><br>
		<a href = "/webapp2/setcolor?pickedBgCol=cyan">CYAN</a>
	</body>
</html>