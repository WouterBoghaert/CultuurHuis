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
		<form method="post">
			<c:set var="zoekDisabled" value='${klantId?"disabled":""}'/>
			<c:set var="bevestigenDisabled" value='${klantId?"":"disabled"}'/>
			<h3>Stap 1: Wie ben je?</h3>
			<label for="gebruikersnaam">Gebruikersnaam:<span>${fouten.gebruikersnaam}</span></label>
			<input type="text" name="gebruikersnaam" required autofocus value="${param.gebruikersnaam}" ${zoekDisabled}>
			<label for="paswoord">Paswoord:<span>${fouten.paswoord}</span></label>
			<input type="password" name="paswoord" ${zoekdDisabled} required >
			<input type="submit" name="zoek" value="Zoek me op" ${zoekdDisabled}>
			<input type="submit" name="nieuw" value="Ik ben nieuw" ${zoekdDisabled}>
			
			${klant.voornaam} ${klant.familienaam} ${klant.straat} ${klant.huisnr} ${klant.postcode} ${klant.gemeente}
			
			<h3>Stap 2: Bevestigen</h3>
			<input type="submit" name="bevestigen" value="Bevestigen" ${bevestigenDisabled}>
		</form>
	</body>
</html>