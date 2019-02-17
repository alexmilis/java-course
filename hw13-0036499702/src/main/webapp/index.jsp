<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style>
	 	body {background-color: <%= session.getAttribute("pickedBgCol") %> ;}
	</style>
	<body>
		<a href = "/webapp2/colors.jsp">Background color chooser</a><br>
		<a href = "/webapp2/trigonometric?a=0&b=90">Calculate sin(x) and cos(x) for values from 0 to 90</a><br>
		<a href = "/webapp2/stories/funny.jsp">Funny stories</a><br>
		<a href = "/webapp2/report.jsp">Report</a><br>
		<a href = "/webapp2/power?a=1&b=100&n=3">Power a=1, b=100, n=3</a><br>
		<a href = "/webapp2/appinfo.jsp">App info</a><br>
		<a href = "/webapp2/glasanje">Glasanje</a>
		<form action="trigonometric" method="GET">
		 Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		 Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		 <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
		</form>
	</body>
</html>