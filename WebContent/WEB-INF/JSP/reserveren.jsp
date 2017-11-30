<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
<head>
	<vdab:head title="Reserveren"/>
</head>
<body>
	<h1>Het Cultuurhuis: reserveren</h1>
	<div><vdab:menu/></div>
	<div>
		<dl>
			<dt>Voorstelling:</dt>
			<dd>${voorstelling.titel}</dd>
			<dt>Uitvoerders:</dt>
			<dd>${voorstelling.uitvoerders}</dd>
			<dt>Datum:</dt>
			<fmt:parseDate value="${voorstelling.datum}" pattern="yyyy-MM-dd'T'HH:mm" var="datum" type="both"/>
			<dd><fmt:formatDate value="${datum}" type="both" dateStyle="short" timeStyle="short" pattern="dd/MM/yyyy HH:mm"/></dd>
			<dt>Prijs:</dt>
			<dd>&euro;<fmt:formatNumber value="${voorstelling.prijs}" minFractionDigits="2" maxFractionDigits="2"/></dd>
			<dt>Vrije plaatsen:</dt>
			<dd>${voorstelling.aantalVrijePlaatsen}</dd>
		</dl>
		<form method = "post">
			<label for="plaatsen">Plaatsen:<span>${fout}</span></label>
			<input type="number" min="1" max="${voorstelling.aantalVrijePlaatsen}" name="plaatsen" id="plaatsen" value="${reservatiemandje[voorstelling.id]}" required>
		</form>
	</div>
</body>
</html>