<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<body>
		<h1>${poll.title }</h1>
		<p>${poll.message }</p>
		<ol>
			<c:forEach var="option" items="${options }">
				<a href = "glasanje-glasaj?pollID=${poll.pollID }&id=${option.id }">${option.optionName }</a><br>
			</c:forEach>
		</ol>
		<a href="index.html">Povratak na odabir ankete</a>
	</body>
</html>