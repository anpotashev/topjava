<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="clientLocale" value="${pageContext.response.locale.language}"/>
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
        ${clientLocale}
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <a href="${url}?lang=en">English</a>
        </li>
        <li>
            <a href="${url}?lang=ru">Русский</a>
        </li>
    </ul>
</li>