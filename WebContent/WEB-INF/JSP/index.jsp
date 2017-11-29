<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
<head>
	<vdab:head title="Voorstellingen"/>
</head>
<body>
	<h1>Het Cultuurhuis</h1>
	<p>Welkom op de website van Het Cultuurhuis!</p>
	<p>Hieronder kan u onze voorstellingen bekijken, geselecteerd per genre. 
	U kan per voorstelling tickets reserveren, die opgeslagen worden in een reservatiemandje.
	Nadien kan u uw reservatie bevestigen en betalen.<br>
	Wij zijn u graag binnenkort in Het Cultuurhuis!</p>
	
	<div>
	<h2>Het Cultuurhuis: voorstellingen</h2><img src="<c:url value="/images/voorstellingen.png"/>" alt="Voorstellingen" title="Voorstellingen"/>
	</div>
	<h3>Genres</h3>
	<c:forEach var="genre" items="${genres}">
		<c:url value="/" var="genreURL">
				<c:param name="genreId" value="${genre.id}"/>
		</c:url>
		<a href="${genreURL}" title="${genre.naam}">${genre.naam}</a>
	</c:forEach>
	<c:if test="${not empty param.genreId}">
		<c:forEach var="voorstelling" items="${voorstellingen}" varStatus="status">
			<c:if test="${status.first}">
				<h3>${voorstelling.genre.naam} voorstellingen</h3>
				<table>
					<tr>
						<th>Datum</th>
						<th>Titel</th>
						<th>Uitvoerders</th>
						<th>Prijs</th>
						<th>Vrije plaatsen</th>
						<th>Reserveren</th>
					</tr>
			</c:if>
			<fmt:parseDate value="${voorstelling.datum}" pattern="yyyy-MM-dd HH:mm" var="datum" type="both"/>
			<tr>
				<td><fmt:formatDate value="${datum}" dateStyle="short" timeStyle="short"/></td>
				<td>${voorstelling.titel}</td>
				<td>${voorstelling.uitvoerders}</td>
				<td>&euro;<fmt:formatNumber value="${voorstelling.prijs}"/></td>
				<td>${voorstelling.aantalVrijePlaatsen}</td>
				<td><c:if test="${voorstelling.aantalVrijePlaatsen > 0}">
						<c:url value="/reserveren.htm" var="reserverenURL">
							<c:param name="voorstellingId" value="${voorstelling.id}"/>
						</c:url>
						<a href="${reserverenURL}" title="reserveren">Reserveren</a>
					</c:if>
				</td>
			</tr>
			<c:if test="${status.last}">
				</table>
			</c:if>			
		</c:forEach>
	</c:if>	
</body>
</html>

<!-- Checken voor parsen van datum, juiste patroon opzoeken
	checken voor css tabel
	prijs decimalen formatten -->