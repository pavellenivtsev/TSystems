<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trucks</title>
</head>
<body>

<h2>Trucks</h2>
<table>
    <tr>
        <th>registration number</th>
        <th>driver shift size</th>
        <th>weight capacity</th>
        <th>status</th>
        <th>current city</th>
        <th>action</th>
    </tr>
    <c:forEach var="truck" items="${trucks}">
        <tr>
            <td>${truck.registrationNumber}</td>
            <td>${truck.driverShiftSize}</td>
            <td>${truck.weightCapacity}</td>
            <td>${truck.status}</td>
            <td>${truck.locationDto}</td>
            <td>
                <c:url value="/truck/edit/${truck.id}" var="edit"/>
                <form name="edit" method="get" action="${edit}">
                    <button type="submit">Edit</button>
                </form>
                <c:url value="/truck/delete" var="delete"/>
                <form name="delete" method="post" action="${delete}">
                    <input type="hidden" name="id" value="${truck.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<h2>Add new truck</h2>
<c:url value="/truck/add" var="add"/>
<a href="${add}">Add new truck</a>
</body>
</html>