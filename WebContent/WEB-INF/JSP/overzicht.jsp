<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Overzicht"/>
	</head>
	<body>
		<h2>Het Cultuurhuis: overzicht</h2><img src="<c:url value="/images/bevestig.png"/>" alt="Bevestig" title="Bevestig"/>
		<div><vdab:menu/></div>
		<c:if test="${not empty gelukteReserveringen}">
			<h3>Gelukte reserveringen</h3>
			<c:forEach var="entry" items="${gelukteReserveringen}" varStatus="status">
				<c:if test="${status.first}">
					<table>
						<tr>
							<th>Datum</th>
							<th>Titel</th>
							<th>Uitvoerders</th>
							<th>Prijs(&euro;)</th>
							<th>Plaatsen</th>
						</tr>
				</c:if>
				<fmt:parseDate value="${entry.key.datum}" pattern="yyyy-MM-dd'T'HH:mm" var="datum" type="both"/>
				<tr>
					<td><fmt:formatDate value="${datum}" type="both" dateStyle="short" timeStyle="short" pattern="dd/MM/yy HH:mm"/></td>
					<td>${entry.key.titel}</td>
					<td>${entry.key.uitvoerders}</td>
					<td class="getal"><fmt:formatNumber value="${entry.key.prijs}" minFractionDigits="2" maxFractionDigits="2"/></td>
					<td class="getal">${entry.value}</td>
				</tr>
				<c:if test="${status.last}">
					</table>
				</c:if>			
			</c:forEach>
		</c:if>
		
		<c:if test="${not empty mislukteReserveringen}">
			<h3>Mislukte reserveringen</h3>
			<c:forEach var="entry" items="${mislukteReserveringen}" varStatus="status">
				<c:if test="${status.first}">
					<table>
						<tr>
							<th>Datum</th>
							<th>Titel</th>
							<th>Uitvoerders</th>
							<th>Prijs(&euro;)</th>
							<th>Plaatsen</th>
							<th>Vrije plaatsen</th>
						</tr>
				</c:if>
				<fmt:parseDate value="${entry.key.datum}" pattern="yyyy-MM-dd'T'HH:mm" var="datum" type="both"/>
				<tr>
					<td><fmt:formatDate value="${datum}" type="both" dateStyle="short" timeStyle="short" pattern="dd/MM/yy HH:mm"/></td>
					<td>${entry.key.titel}</td>
					<td>${entry.key.uitvoerders}</td>
					<td class="getal"><fmt:formatNumber value="${entry.key.prijs}" minFractionDigits="2" maxFractionDigits="2"/></td>
					<td class="getal">${entry.value}</td>
					<td class="getal">${entry.key.aantalVrijePlaatsen}</td>
				</tr>
				<c:if test="${status.last}">
					</table>
				</c:if>			
			</c:forEach>
		</c:if>		
	
	</body>
</html>