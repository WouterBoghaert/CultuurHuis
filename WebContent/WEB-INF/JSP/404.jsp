<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags"%>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Pagina niet gevonden"/>
	</head>
	<body>
		<h2>Het Cultuurhuis: pagina niet gevonden</h2>
		<div><vdab:menu/></div>
		<img src="<c:url value="/images/fout.jpg"/>" alt="fout" title="fout">
		<p>De pagina die u zocht bestaat niet op deze website.</p>
	</body>
</html>