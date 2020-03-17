<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<h2>Orders</h2>
<table>
    <tr>
        <th>unique number</th>
        <th>order status</th>
        <th>cargo name</th>
        <th>cargo status</th>
        <th>place of departure</th>
        <th>delivery place</th>
        <th>action</th>
    </tr>
        <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.uniqueNumber}</td>
            <td>${order.status.name().toLowerCase().replaceAll("_"," ")}</td>

            <c:forEach var="cargo" items="${order.cargoList}">
                <td>${cargo.name}</td>
                <td>${cargo.status.name().toLowerCase()}</td>
                <c:forEach var="waypoint" items="${cargo.waypointList}">
                    <td>${waypoint.location.city}</td>
                </c:forEach>
            </c:forEach>

            <td>
                <c:url value="/order/edit" var="edit"/>
                <form name="edit" method="get" action="${edit}">
                    <input type="hidden" name="id" value="${order.id}">
                    <button type="submit">Edit</button>
                </form>
                <c:url value="/order/delete" var="delete"/>
                <form name="delete" method="post" action="${delete}">
                    <input type="hidden" name="id" value="${order.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        </c:forEach>
</table>
<h2>Add new userOrder</h2>
<c:url value="/order/add" var="add"/>
<a href="${add}">Add new userOrder</a>
</body>
</html>
