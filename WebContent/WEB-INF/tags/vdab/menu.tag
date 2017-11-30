<%@ tag description="menu" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test='${not pageContext.request.requestURI.contains("index")}'>
	<a href="<c:url value="/"/>" title="Voorstellingen">Voorstellingen</a>
</c:if>
<c:if test="${not empty reservatiemandje}">
	<c:if test='${not pageContext.request.requestURI.contains("reservatiemandje")}'>
		<a href="<c:url value="/reservatiemandje.htm"/>" title="Reservatiemandje">Reservatiemandje</a>
	</c:if>
	<c:if test='${not pageContext.request.requestURI.contains("bevestigingreservatie")}'>
		<a href="<c:url value="/bevestigingreservatie.htm"/>" title="Bevestiging reservatie">Bevestiging reservatie</a>
	</c:if>
</c:if>
