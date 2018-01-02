<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/mealsDatatable.js" defer></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <div class="jumbotron">
        <div class="container">
            <h3><spring:message code="meal.title"/></h3>


            <!-- filter form -->
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <button class="btn btn-primary" data-toggle="collapse" data-target="#filter">filter</button>
                </div>
                <div class="panel-body collapse" id="filter">
                    <form method="post" action="meals/filter" id="filterForm">
                        <div class="form-group">
                            <label for="startDateFilter"><spring:message code="meal.startDate"/>:</label>
                            <input name="startDate" value="${param.startDate}" id="startDateFilter" autocomplete="off"
                                   onfocus="showDatePicker(this)">
                        </div>
                        <div class="form-group">
                            <label for="endDateFilter"><spring:message code="meal.endDate"/>:</label>
                            <input name="endDate" value="${param.startDate}" id="endDateFilter" autocomplete="off"
                                   onfocus="showDatePicker(this)">
                        </div>
                        <div class="form-group">
                            <label for="startTimeFilter"><spring:message code="meal.startTime"/>:</label>
                            <input name="startTime" value="${param.startTime}" id="startTimeFilter" autocomplete="off"
                                   onfocus="showTimePicker(this)">
                        </div>
                        <div class="form-group">
                            <label for="endTimeFilter"><spring:message code="meal.endTime"/>:</label>
                            <input name="endTime" value="${param.startTime}" id="endTimeFilter" autocomplete="off"
                                   onfocus="showTimePicker(this)">
                        </div>

                        <a type="button" class="btn btn-primary" onclick="setFilter()"><span
                                class="glyphicon glyphicon-filter"></span></a>
                        <a type="button" class="btn btn-danger" onclick="clearFilter()"><span
                                class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                    </form>
                </div>
            </div>
            <!-- filter form -->

            <hr>
            <!-- Add button -->
            <a class="btn btn-primary" onclick="add()">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <spring:message code="meal.add"/>
            </a>
            <!-- Add button -->


            <hr>

            <!-- Data table -->
            <table class="table table-striped display" id="dataTable">
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                    <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                        <td>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a></td>
                        <td><a class="delete"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <!-- Data table -->


        </div>
    </div>
</section>


<!-- New meal panel -->
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="meal.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message
                                code="meal.description"/></label>

                        <div class="col-xs-9">
                            <input class="form-control" id="description" name="description"
                                   placeholder="<spring:message code="meal.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="datetime" class="control-label col-xs-3"><spring:message
                                code="meal.dateTime"/></label>

                        <div class="col-xs-9">
                            <input class="form-control" id="datetime" name="datetime"
                                   placeholder="<spring:message code="meal.dateTime"/>"
                                   onfocus="showDateTimePicker(this)" autocomplete="off">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message
                                code="meal.calories"/></label>

                        <div class="col-xs-9">
                            <input type="input" class="form-control" id="calories" name="calories"
                                   placeholder="<spring:message code="meal.calories"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- New meal panel -->

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>