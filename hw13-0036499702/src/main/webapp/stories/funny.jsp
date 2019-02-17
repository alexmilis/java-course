<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
String[] colors = {"yellow", "orange", "red", "violet", "pink", "blue", "cyan", "green", "brown", "black"};
%>
<html>
	<style>
	 	body {background-color: <%= session.getAttribute("pickedBgCol") %> ;}
	 	body {color: <%= colors[(int) (Math.random() * 10)] %> ;}
	</style>
	<body>
		<p>	I went to this girl’s party the week after she beat the shit out of my friend.<br> 
			While everyone was getting trashed, I went around putting tuna inside all the curtain rods <br> 
			and so like weeks went by and they couldn’t figure out why the house smelled like festering death.<br> 
			They caught me through this video where these guys at the party were singing Beyoncé <br> 
			while I was in the background with a can of tuna.
		</p>
		<p>	When I was about 5/6 my mom and stepdad bought my sister and I bikes for Easter.<br> 
			After church they were like “do you wanna learn how to ride them?”<br> 
			And I was like? Duh? I had finally gotten the hang of it and I was riding<br> 
			around the circle showing off, and my mom was like “say cheese” <br>
			so I look over at her for a second and I RAM INTO A CAR AT FULL SPEED.<br> 
			A parked car that I didn’t even see, like at all, so I just rammed into this car<br> 
			and I fell off my bike and I was crying and all I could think about was <br> 
			“this must be how bugs feel” like they’re flying around living life and then SPLAT.<br> 
			Looking back that was my first existential crisis.
		</p> A navy captain is alerted by his First Mate that there is a pirate ship coming towards his position.<br> 
			He asks a sailor to get him his red shirt.<br>
			The captain was asked, “Why do you need a red shirt?”<br>
			The Captain replies, “So that when I bleed, you guys don’t notice and aren’s discouraged.”<br> 
			They fight off the pirates eventually. <br>
			The very next day, the Captain is alerted that 50 pirate ships are coming towards their boat.<br> 
			He yells, “Get me my brown pants!”
		<p>	
	</body>
</html>