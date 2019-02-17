<%@ page import="java.util.Map" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style>
	 	body {background-color: <%= session.getAttribute("pickedBgCol") %> ;}
	</style>
	<body>
		<h1>Glasanje za omiljeni bend:</h1>
		<p>Kliknite na link da biste glasali!</p>
		<ol>
			<% 	@SuppressWarnings("unchecked")
				Map<String, String> bands = (Map<String, String>) session.getAttribute("bands");
				for(Map.Entry<String, String> entry : bands.entrySet()) {
	 				out.print("<li><a href=\"glasanje-glasaj?id=");
	 				out.print(entry.getKey());
	 				out.print("\">");
	 				out.print(entry.getValue());
	 				out.print("</a></li>");
	 			}
 			%>
		</ol>
	</body>
</html>