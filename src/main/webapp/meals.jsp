<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Title</title>
    <%--<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">--%>
    <style type="text/css">
        <%@include file="css/styles.css"%>
    </style>

    <c:set var="labelDateTime" value="Datetime"/>
    <c:set var="labelDescription" value="Description"/>
    <c:set var="labelCalories" value="Calories"/>
    <c:set var="labelResetToDefault" value="Reset to default"/>
    <c:set var="labelEdit" value="Edit"/>
    <c:set var="labelSave" value="Save"/>
    <c:set var="labelCancel" value="Cancel"/>
    <c:set var="labelCreate" value="Create"/>
    <c:set var="labelCaloriesPerDay" value="Calories per day"/>
    <c:set var="labelSet" value="Set"/>
    <c:set var="labelDelete" value="Delete"/>
    <c:set var="labelAdd" value="Add"/>
    <c:set var="labelErrorSavingMeal" value="Error Saving meal!"/>
    <c:set var="labelDateTimeFormat" value="dd.MM.yyyy hh:mm"/>




</head>
<body>
    <h2>Meals</h2>
    <%--show reset to default form--%>
    <form action="meals" method="get">
        <p>${labelCaloriesPerDay}: <input type="number" class="caloriesPerDay" name="caloriesPerDay" value="${caloriesPerDay}"/>
            <input type="submit" value="${labelSet}"/></p>
    </form>

    <%--end show reset to default form--%>
    <%--show reset to default--%>
    <a href="meals?action=resetToDefault" class="button">${labelResetToDefault}</a>
    <p/>
    <%--end show reset to default--%>
    <c:if test="${not empty mealsWithExceeds or param.action eq 'create'}" >
        <table>
            <thead>
                <tr>
                    <th>${labelDateTime}</th>
                    <th>${labelDescription}</th>
                    <th>${labelCalories}</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="meal1" items="${mealsWithExceeds}">
                    <c:set var="isEdit" value="${(not empty meal) and (meal1.mealId eq meal.id)}"/>
                    <c:choose>

                        <%--show edited row--%>
                    <c:when test="${isEdit}" >
                        <tr>
                            <form action="meals" method="post">
                                <input type="hidden" value="${meal.id}" name="id"/>
                                <td><input type="text" value="${newMeal ? "" : dateTimeFormatter.format(meal1.dateTime)}" name="datetime"
                                           placeholder="${labelDateTimeFormat}"/></td>
                                <td><input type="text" value="${newMeal ? "" : meal1.description}" name="description"
                                           placeholder="${labelDescription}"/></td>
                                <td><input type="number" value="${newMeal ? 0 : meal1.calories}" name="calories"
                                           placeholder="${labelCalories}"/></td>
                                <td><input type="submit" value="${labelSave}"/></td>
                                <td><a href="meals" class="button">${labelCancel}</a></td>
                                <%--<td><a href="meals?action=delete&id=${meal1.mealId}" class="button">delete</a></td>--%>
                            </form>
                        </tr>
                    </c:when>
                        <%--end show edited row--%>
                    <c:otherwise>
                        <%--show nonedited row--%>
                        <tr class="${meal1.exceed ? 'redcolor' : 'greencolor'}"/>
                            <td>${dateTimeFormatter.format(meal1.dateTime)}</td>
                            <td>${meal1.description}</td>
                            <td>${meal1.calories}</td>
                            <td><a href="meals?action=edit&id=${meal1.mealId}" class="button">${labelEdit}</a></td>
                            <td><a href="meals?action=delete&id=${meal1.mealId}" class="button">${labelDelete}</a></td>
                        </tr>
                        <%--end show nonedited row--%>
                    </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--show new meal form--%>
                <c:if test="${not empty newMeal}" >
                    <form action="meals" method="post">
                    <td><input type="text" value="${newMeal ? "" : dateTimeFormatter.format(meal.dateTime)}" name="datetime"
                               placeholder="${labelDateTimeFormat}"/></td>
                        <td><input type="text" value="${newMeal ? "" : meal.description}" name="description"
                                   placeholder="${labelDescription}"/></td>
                        <td><input type="number" value="${newMeal ? 0 : meal.calories}" name="calories"
                                   placeholder="${labelCalories}"/></td>
                        <td><input type="submit" value="${labelCreate}"/></td>
                        <td><a href="meals" class="button">${labelCancel}</a></td>
                    </tr>
                    </form>
                </c:if>
                <%--end show meal form--%>

            </tbody>
        </table>
    </c:if>
        <%--show add button--%>
        <c:if test="${empty create}" >
            <a href="meals?action=create" class="button">${labelAdd}</a>
        </c:if>
        <%--end show add button--%>
        <hr>

        <%--show error--%>
        <c:if test="${not empty errorSaving}" >
            <h3 class="errormessage">${labelErrorSavingMeal}</h3>
        </c:if>
        <%--end show error--%>

</body>
</html>
