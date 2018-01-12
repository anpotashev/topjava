<%@page contentType="text/javascript" pageEncoding="UTF-8"%>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%--var i18n = [];--%>

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","lang"}%>'>
    i18n["<spring:message text='${key}'/>"] = "<spring:message code='${key}'/>";
    </c:forEach>