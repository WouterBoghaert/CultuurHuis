<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Nieuwe klant"/>
	</head>
	<body>
		<h2>Het Cultuurhuis: nieuwe klant</h2><img src="<c:url value="/images/nieuweklant.png"/>" alt="Nieuwe klant" title="Nieuwe klant"/>
		<div><vdab:menu/></div>
		<div>
			<form method="post" id="nieuweKlantForm">
				<label for="voornaam">Voornaam:</label>
				<input type="text" name="voornaam" value="${param.voornaam}" maxlength = "50" required autofocus>
				<label for="familienaam">Familienaam:</label>
				<input type="text" name="familienaam" value="${param.familienaam}" maxlength = "50" required> 
			 	<label for="straat">Straat:</label>
				<input type="text" name="straat" value="${param.straat}" maxlength = "50" required> 
			 	<label for="huisnr">Huisnr:</label>
				<input type="text" name="huisnr" value="${param.huisnr}" maxlength = "50" required>
				<label for="postcode">Postcode:</label>
				<input type="text" name="postcode" value="${param.postcode}" maxlength = "50" required> 
				<label for="gemeente">Gemeente:</label>
				<input type="text" name="gemeente" value="${param.gemeente}" maxlength = "50" required> 
				<label for="gebruikersnaam">Gebruikersnaam:</label>
				<input type="text" name="gebruikersnaam" value="${param.gebruikersnaam}" maxlength = "50" required>
				<label for="paswoord">Paswoord:</label>
				<input type="password" name="paswoord" maxlength = "50" required> 
				<label for="herhaalpaswoord">Herhaal paswoord:</label>
				<input type="password" name="herhaalpaswoord" maxlength = "50" required>
				<input type="submit" name="bevestig" id="bevestigKnop" value="OK">			 
			</form>
		</div>
		<c:if test="${not empty fouten}">
			<ul>
				<c:forEach var="foutEntry" items="${fouten}">
					<li>${foutEntry.value}</li>
				</c:forEach>
			</ul>
		</c:if>		
		<script type="text/javascript">
			document.getElementById("nieuweKlantForm").addEventHandler("submit", function(){
				if(!navigator.cookieEnabled) {
					alert("Dit werkt enkel als cookies aanstaan!");
				}
				document.getElementById("bevestigKnop").disabled = true;
			});
		</script>
	</body>
</html>

<!-- 
TO DO:
* deze jsp afwerken
* alles testen en nalezen
* checken of entity classes hashcode en equals hebben
* checken of overal javascript staat en html validatie waar nodig
* checken op overbodige code en efficiëntie
* foutopvang, fouten loggen

 -->