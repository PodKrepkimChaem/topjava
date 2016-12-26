<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
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
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form method="post" action="meals">
        <table>
            <tr>Filter Date</tr>
            <tr>
                <td><label>Ot
                    <input type="date" value="${timeDate1}" name="startDate">
                </label></td>
                <td><label>Do
                    <input type="date" value="${timeDate2}" name="endDate">
                </label></td>
            </tr>
            <tr>
                <td><button type="submit">Find</button></td>
            </tr>
        </table>
    </form>
    <br>
    <form method="post" action="meals">
        <table>
            <tr>Filter Time</tr>
            <tr>
                <td><label>Ot
                    <input type="time" value="${timeFilter1}" name="startTime">
                </label></td>
                <td><label>Do
                    <input type="time" value="${timeFilter2}" name="endTime">
                </label></td>
            </tr>
            <tr>
                <td><button type="submit">Find</button></td>
            </tr>
        </table>
    </form>
</section>
</body>
</html>
