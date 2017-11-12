<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<jsp:useBean id="dateTimeFilter" class="ru.javawebinar.topjava.DateTimeFilter" scope="request"/>
<table>
    <tbody>
    <form action="meals" method="get">
        <input type="hidden" name="action" value="filter"/>
        <tr>
            <th>Start Date</th>
            <th><input type="text" name="startDate" value="${fn:formatDate(dateTimeFilter.startDate)}" placeholder="гггг-мм-дд"/></th>
        </tr>
        <tr>
            <th>End Date</th>
            <th><input type="text" name="endDate" value="${fn:formatDate(dateTimeFilter.endDate)}" placeholder="гггг-мм-дд"/></th>
        </tr>
        <tr>
            <th>Start Time</th>
            <th><input type="text" name="startTime" value="${fn:formatTime(dateTimeFilter.startTime)}" placeholder="ЧЧ:мм"/></th>
        </tr>
        <tr>
            <th>End Time</th>
            <th><input type="text" name="endTime" value="${fn:formatTime(dateTimeFilter.endTime)}" placeholder="ЧЧ:мм"/></th>
        </tr>
        <tr>
            <td><input type="submit" value="filter"/></td>
    </form>
    <%--TODO некорректное закрытие формы--%>
    <form action="/meals" method="post">
        <td>
            <input type="hidden" name="action" value="filter"/>
            <input type="hidden" name="startTime" value=""/>
            <input type="hidden" name="endTime" value=""/>
            <input type="hidden" name="startDate" value=""/>
            <input type="hidden" name="endDate" value=""/>
            <input type="submit" value="clear" />

        </td>
    </form>
    </tr>

    </tbody>
</table>
