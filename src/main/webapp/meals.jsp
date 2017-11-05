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
</head>
<body>
    <h2>Meals</h2>
    <form action="meals" method="get">
        <p>caloriesPerDay: <input type="number" class="caloriesPerDay" name="caloriesPerDay" value="${caloriesPerDay}"/>
            <input type="submit" value="set"/></p>
    </form>
    <a href="resetToDefault" class="button">reset to default</a>
    <c:if test="${not empty mealsWithExceeds}" >
        <table>
            <thead>
                <tr>
                    <th>dateTime</th>
                    <th>descritpion</th>
                    <th>calories</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="meal1" items="${mealsWithExceeds}">
                    <c:set var="isEdit" value="${(not empty meal) and (meal1.mealId eq meal.id)}"/>
                    <c:choose>
                    <c:when test="${isEdit}" >
                        <tr>
                        <form action="meals" method="post">
                            <input type="hidden" value="${meal.id}" name="id"/>
                            <td><input type="text" value="${newMeal ? "" : dateTimeFormatter.format(meal1.dateTime)}" name="datetime"
                                       placeholder="дд.ММ.гггг чч:мм"/></td>
                            <td><input type="text" value="${newMeal ? "" : meal1.description}" name="description"
                                       placeholder="описание"/></td>
                            <td><input type="number" value="${newMeal ? 0 : meal1.calories}" name="calories"
                                       placeholder="калории"/></td>
                            <td><input type="submit" value="save"/><a href="meals" class="button">cancel</a></td>
                            <td><a href="deleteMeal?id=${meal1.mealId}" class="button">delete</a></td>
                        </form>
                        </tr>
                    </c:when>
                    <c:otherwise>
                    <tr class="${meal1.exceed ? 'redcolor' : 'greencolor'}"/>
                        <td>${dateTimeFormatter.format(meal1.dateTime)}</td>
                        <td>${meal1.description}</td>
                        <td>${meal1.calories}</td>
                        <td><a href="editMeal?id=${meal1.mealId}" class="button">edit</a></td>
                        <td><a href="deleteMeal?id=${meal1.mealId}" class="button">delete</a></td>
                    </tr>
                    </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${not empty newMeal}" >
                    <form action="meals" method="post">
                    <td><input type="text" value="${newMeal ? "" : dateTimeFormatter.format(meal.dateTime)}" name="datetime"
                               placeholder="дд.ММ.гггг чч:мм"/></td>
                        <td><input type="text" value="${newMeal ? "" : meal.description}" name="description"
                                   placeholder="описание"/></td>
                        <td><input type="number" value="${newMeal ? 0 : meal.calories}" name="calories"
                                   placeholder="калории"/></td>
                    <td><input type="submit" value="create"/></td><td></td>
                    </tr>
                    </form>
                </c:if>
            </tbody>
        </table>
        <c:if test="${empty newMeal}" >
            <a href="editMeal" class="button">add</a>
        </c:if>
        <hr>
        <c:if test="${not empty errorSaving}" >
            <h3 class="errormessage">Error saving meal</h3>
        </c:if>
    </c:if>
</body>
</html>
