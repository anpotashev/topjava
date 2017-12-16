<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="app.home" var="labelHome"/>
<spring:message code="app.title" var="labelTitle"/>

<header>
    <a href="<c:url value="/"/>">${labelHome}</a>&nbsp;|&nbsp;
    <a href="<c:url value="/meals"/>">${labelTitle}</a>
</header>