<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Problemen bij ophalen data"/>
	</head>
	<body>
		<h2>Het cultuurhuis: problemen bij het ophalen van data.</h2>
		<div><vdab:menu/></div>
		<img src="<c:url value="/images/datafout.jpg"/>" alt="datafout" title="datafout">
		<p>Wegens een technische storing kan de gevraagde data niet opgehaald worden.
		Gelieve de helpdesk te contacteren.</p>
	</body>
</html>