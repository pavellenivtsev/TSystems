<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h2>Users table</h2>
<table>
    <tr>
        <th>first name</th>
        <th>last name</th>
        <th>phone number</th>
        <th>email</th>
        <th>role</th>
        <th>action</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.email}</td>
            <td>${user.roles}</td>
            <td>
                <a href="/appointAsAdmin/${user.id}">Appoint as admin</a><br>
                <a href="">Appoint as manager</a><br>
                <a href="">Appoint as driver</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
