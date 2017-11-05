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
        <p>caloriesPerDay: <input type="number" class="caloriesPerDay" name="caloriesPerDay" value="${caloriesPerDay}"/><input type="submit"/></p>
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
                <c:forEach var="meal" items="${mealsWithExceeds}">
                    <tr class="${meal.exceed ? 'redcolor' : 'greencolor'}"/>
                        <td>${dateTimeFormatter.format(meal.dateTime)}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="editMeal?id=${meal.mealId}" class="button">edit</a></td>
                        <td><a href="deleteMeal?id=${meal.mealId}" class="button">delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <td><a href="editMeal" class="button">add</a></td>
        <hr>
        <c:if test="${not empty errorSaving}" >
            <h3 class="errormessage">Error saving meal</h3>
        </c:if>
        <c:if test="${not empty meal or newMeal}">
        <form action="meals" method="post">
            <c:if test="${not newMeal}">
                <input type="hidden" value="${meal.id}" name="id"/>
            </c:if>
            <table>
                <tr>
                    <td>datetime: </td>
                    <td><input type="text" value="${newMeal ? "" : dateTimeFormatter.format(meal.dateTime)}" name="datetime"
                        placeholder="дд.ММ.гггг чч:мм"/></td>
                </tr>
                <tr>
                    <td>description: </td>
                    <td><input type="text" value="${newMeal ? "" : meal.description}" name="description"
                        placeholder="описание"/></td>
                </tr>
                <tr>
                    <td>calories: </td>
                    <td><input type="number" value="${newMeal ? 0 : meal.calories}" name="calories"
                               placeholder="калории"/></td>
                </tr>
                <tr><td><input type="submit" value="${newMeal ? "create" : "save"}"/></td><td></td></tr>
            </table>

        </form>
        </c:if>

    </c:if>
</body>
</html>
