<%@ page import="java.util.Map" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style>
	 	body {background-color: <%= session.getAttribute("pickedBgCol") %> ;}
	</style>
	<body>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja!</p>
		<table border = "1" class = "rez">
			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
 			<tbody>
 			<% 	@SuppressWarnings("unchecked")
 				Map<String, Integer> results = (Map<String, Integer>) session.getAttribute("results");
 				for(Map.Entry<String, Integer> entry : results.entrySet()) {
	 				out.print("<tr><td>");
	 				out.print(entry.getKey());
	 				out.print("</td><td>");
	 				out.print(entry.getValue());
	 				out.print("</td></tr>");
	 			}
 			%>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt = "Pie-chart" src = "/webapp2/glasanje-grafika" width = "400" height = "400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="/webapp2/glasanje-xls">ovdje</a></p>
		
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova</p>
		<ul>
			<% 	@SuppressWarnings("unchecked")
				Map<String, String> winners = (Map<String, String>) session.getAttribute("winners");
				for(Map.Entry<String, String> entry : winners.entrySet()) {
	 				out.print("<li><a href=\"");
	 				out.print(entry.getValue());
	 				out.print("\" target=\"_blank\">");
	 				out.print(entry.getKey());
	 				out.print("</a></li>");
	 			}
 			%>
		</ul>
	</body>
</html>