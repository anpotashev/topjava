<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Please login</h2>
    <p>
    <form action="users" method="post">
        Choose user: <select name="userId"> <!--  onchange="this.form.submit()"> -->
            <c:forEach var="user" items="${users}">
                <option value="${user.id}">${user.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="ok"/>
    </form>
</body>
</html>
