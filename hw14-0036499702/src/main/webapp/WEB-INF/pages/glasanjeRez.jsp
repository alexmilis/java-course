<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<body>
		<h1>Rezultati glasanja ankete ${poll.title }</h1>
		<p>Ovo su rezultati glasanja!</p>
		<table border = "1" class = "rez">
			<thead><tr><th>Opcija</th><th>Broj glasova</th></tr></thead>
 			<tbody>
 			<c:forEach var="option" items="${results }">
				<tr><td>${option.optionName }</td><td>${option.votes }</td></tr>
			</c:forEach>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt = "Pie-chart" src = "glasanje-grafika?id=${poll.pollID }" width = "400" height = "400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?id=${poll.pollID }">ovdje</a></p>
		
		<h2>Razno</h2>
		<p>Opisni linkovi pobjednika ankete</p>
		<ul>
			<c:forEach var="option" items="${winners }">
				<li><a href="${option.link }" target="blank">${option.optionName }</a></li>
			</c:forEach>
		</ul>
		<a href = "glasanje?pollID=${poll.pollID }">Povratak na glasanje</a>
	</body>
</html>