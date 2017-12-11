<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><spring:message code="meals"/></h3>
    <form method="post" action="<c:url value="/meals/filter"/>">
        <dl>
            <dt><spring:message code="filter.from_date"/>:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="filter.to_date"/>:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="filter.from_time"/>:</dt>
            <dd><input type="text" name="startTime" value="${param.startTime}" pattern="([01][0-9]|2[0-3]):[0-5][0-9]" placeholder="HH:MM"></dd>
            <%--<dd><input type="time" name="startTime" value="${param.startTime}"></dd>--%>
        </dl>
        <dl>
            <dt><spring:message code="filter.to_time"/>:</dt>
            <dd><input type="text" name="endTime" value="${param.endTime}" pattern="([01][0-9]|2[0-3]):[0-5][0-9]" placeholder="HH:MM"></dd>
            <%--<dd><input type="time" name="endTime" value="${param.endTime}"></dd>--%>
        </dl>
        <button type="submit"><spring:message code="filter"/></button>
    </form>
    <hr/>
    <a href="<c:url value="/meals/add"/>"><spring:message code="meal.add"/></a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.date"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="<c:url value="/meals/edit/${meal.id}"/>"><spring:message code="update"/></a></td>
                <td><a href="<c:url value="/meals/delete/${meal.id}"/>"><spring:message code="delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>