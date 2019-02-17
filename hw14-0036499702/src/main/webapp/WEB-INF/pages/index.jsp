<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<body>
		<h1>Polls</h1>
			<c:forEach var="poll" items="${polls }">
				<a href = "glasanje?pollID=${poll.pollID }">${poll.title }</a><br>
			</c:forEach>
	</body>
</html>