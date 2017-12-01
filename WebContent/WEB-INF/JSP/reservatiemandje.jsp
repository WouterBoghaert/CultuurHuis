<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
<head>
	<vdab:head title="Reservatiemandje"/>
</head>
<body>
	<h2>Het Cultuurhuis: reservatiemandje</h2><img src="<c:url value="/images/mandje.png"/>" alt="Reservatiemandje" title="Reservatiemandje"/>
	<div><vdab:menu/></div>
	<c:choose>
		<c:when test="${not empty reservatiemandje}">
			<c:set var="teBetalen" value="0"/>
			<c:forEach var="voorstelling" items="${voorstellingen}" varStatus="status">
				<c:if test="${status.first}">
					<form method="post">
						<table>
							<tr>
								<th>Datum</th>
								<th>Titel</th>
								<th>Uitvoerders</th>
								<th>Prijs</th>
								<th>Plaatsen</th>
								<th><input type="submit" value="Verwijderen" id="verwijderKnop" name=verwijderKnop></th>
							</tr>
				</c:if>
				<fmt:parseDate value="${voorstelling.datum}" pattern="yyyy-MM-dd'T'HH:mm" var="datum" type="both"/>
				<tr>
					<td><fmt:formatDate value="${datum}" type="both" dateStyle="short" timeStyle="short" pattern="dd/MM/yy HH:mm"/></td>
					<td>${voorstelling.titel}</td>
					<td>${voorstelling.uitvoerders}</td>
					<td class="getal">&euro;<fmt:formatNumber value="${voorstelling.prijs}" minFractionDigits="2" maxFractionDigits="2"/></td>
					<td class="getal">${reservatiemandje[voorstelling.id]}</td>
					<td><input type="checkbox" name="verwijderen" value="${voorstelling.id}"></td>
				</tr>
				<c:if test="${status.last}">
					</table>
					</form>
				</c:if>
				<c:set var="teBetalen" value="${teBetalen + (voorstelling.prijs * reservatiemandje[voorstelling.id])}"/>			
			</c:forEach>
			<p>Te betalen: &euro;<fmt:formatNumber value="${teBetalen}" minFractionDigits="2" maxFractionDigits="2"/>
		</c:when>
		<c:otherwise><p>U heeft nog geen reservaties in uw mandje. Op de voorstellingspagina kan u reservaties plaatsen.</p></c:otherwise>
	</c:choose>
</body>
</html>

<!--  check doen op empty reservatiemandje, melding geven als mandje leeg is -->