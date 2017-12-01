<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Bevestiging reservaties"/>
	</head>
	<body>
		<h2>Het Cultuurhuis: bevestiging reservaties</h2><img src="<c:url value="/images/bevestig.png"/>" alt="Bevestig" title="Bevestig"/>
		<div><vdab:menu/></div>
		<c:url var="zoekURL" value="/bevestigingreservatie.htm">
				<c:param name="zoek" value="true"/>
			</c:url>
			<c:url var="nieuwURL" value="/bevestigingreservatie.htm">
				<c:param name="nieuw" value="true"/>
			</c:url>
			<c:url var="bevestigenURL" value="/bevestigingreservatie.htm">
				<c:param name="bevestigen" value="true"/>
			</c:url>
			<c:set var="zoekDisabled" value='${not empty klantId ?"disabled":""}'/>
			<c:set var="bevestigenDisabled" value='${not empty klantId ?"":"disabled"}'/>
		<form method="post" action="${zoekURL}" id="zoekForm">
			<h3>Stap 1: Wie ben je?</h3>
			<label for="gebruikersnaam">Gebruikersnaam:<span>${fouten.gebruikersnaam}</span></label>
			<input type="text" name="gebruikersnaam" required autofocus value="${param.gebruikersnaam}" ${zoekDisabled}>
			<label for="paswoord">Paswoord:<span>${fouten.paswoord}</span></label>
			<input type="password" name="paswoord" ${zoekDisabled} required>
			<input type="submit" name="zoek" value="Zoek me op" ${zoekDisabled}>
		</form>
		<form method="post" action="${nieuwURL}" id="nieuwForm">	
			<input type="submit" name="nieuw" value="Ik ben nieuw" ${zoekDisabled} id="nieuwKnop">
		</form>	
		<c:choose>
			<c:when test="${empty fouten}">
				${klant.voornaam} ${klant.familienaam} ${klant.straat} ${klant.huisnr} ${klant.postcode} ${klant.gemeente}
			</c:when>
			<c:otherwise>
				${fouten.zoek}
			</c:otherwise>
		</c:choose>
		<form method="post" action="${bevestigenURL}" id="bevestigenForm">
			<h3>Stap 2: Bevestigen</h3>
			<input type="submit" name="bevestigen" value="Bevestigen" ${bevestigenDisabled} id="bevestigenKnop">
		</form>
		<script>
 			document.getElementById("zoekForm").addEventListener("submit", function(){ 
				document.getElementById("zoekKnop").disabled; 
			}); 
			
			document.getElementById("nieuwForm").addEventListener("submit", function(){ 
				document.getElementById("nieuwKnop").disabled; 
			}); 
			
			document.getElementById("bevestigenForm").addEventListener("submit", function(){ 
				document.getElementById("bevestigenKnop").disabled; 
 			}); 
		</script> 
	</body>
</html>