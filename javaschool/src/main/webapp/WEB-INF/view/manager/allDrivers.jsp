<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Drivers</title>
</head>
<body>
<h2>Drivers</h2>
<table>
    <tr>
        <th>Personal number</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Hours worked this month</th>
        <th>Status</th>
        <th>Phone number</th>
        <th>Email</th>
        <th>Action</th>
    </tr>
    <c:forEach var="driver" items="${drivers}">
        <tr>
            <td>${driver.personalNumber}</td>
            <td>${driver.user.firstName}</td>
            <td>${driver.user.lastName}</td>
            <td>${driver.hoursThisMonth}</td>
            <td>${driver.status}</td>
            <td>${driver.user.phoneNumber}</td>
            <td>${driver.user.email}</td>
            <td>
                <c:url value="/manager/driver/edit" var="edit"/>
                <form name="edit" method="get" action="${edit}">
                    <input type="hidden" name="id" value="${driver.id}">
                    <button type="submit">Edit</button>
                </form>
                <c:url value="/manager/driver/delete" var="delete"/>
                <form name="edit" method="post" action="${delete}">
                    <input type="hidden" name="id" value="${driver.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
