<%--
  Created by IntelliJ IDEA.
  User: aleks
  Date: 12.02.2020
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3>Meal list</h3>
<a href="meals?action=create">Add meal</a>
<table border="1">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
<%--    <c:forEach items="${requestScope.meals}" var="meal">--%>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:choose>
            <c:when test="${meal.excess==true}">
                <c:set var="excessColor" scope="request" value="red"/>
            </c:when>
            <c:otherwise>
                <c:set var="excessColor" scope="request" value="green"/>
            </c:otherwise>
        </c:choose>

        <tr bgcolor="${excessColor}">
                <%--            <td>${meal.dateTime}</td>--%>
            <td>${meal.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            <td>${meal.description}</td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>

