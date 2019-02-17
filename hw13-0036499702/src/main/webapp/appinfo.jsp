<%@ page import = "java.util.Date, java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style>
	 	body {background-color: <%= session.getAttribute("pickedBgCol") %> ;}
	</style>
	<body>
		<h1>App info</h1>
		<p>This app has been running for</p>
		<% Calendar now = Calendar.getInstance(); 
			Calendar start = (Calendar) request.getServletContext().getAttribute("start");
			
			int day = now.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
			int hour = now.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
			int min = now.get(Calendar.MINUTE) - start.get(Calendar.MINUTE);
			int sec = now.get(Calendar.SECOND) - start.get(Calendar.SECOND);
			int mili = now.get(Calendar.MILLISECOND) - start.get(Calendar.MILLISECOND); 
			
			if(mili < 0) {
				mili += 1000;
				sec--;
			}
			
			if(sec < 0) {
				sec += 60;
				min--;
			}
			
			if(min < 0) {
				min += 60;
				hour--;
			}
			
			if(hour < 0) {
				hour += 24;
				day--;
			}
		%>
		
		<%= day %> days
		<%= hour %> hours
		<%= min %> minutes
		<%= sec %> seconds and
		<%= mili %> miliseconds.
		</body>
</html>