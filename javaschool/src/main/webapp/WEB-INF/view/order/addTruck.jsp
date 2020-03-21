<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Available Trucks</title>
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
            <td>${truck.location.city}</td>
            <td>
                <c:url value="/order/add/truck" var="addTruck"/>
                <form name="edit" method="post" action="${addTruck}">
                    <input type="hidden" name="id" value="${order.id}">
                    <input type="hidden" name="truckId" value="${truck.id}">
                    <button type="submit">Add/edit truck</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
