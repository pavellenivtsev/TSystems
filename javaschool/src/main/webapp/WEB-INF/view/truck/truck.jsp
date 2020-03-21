<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="../common/managerToolBar.jsp" %>
    <title>Truck information</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h2 class="text-center">Truck information</h2>
    <table class="table table-striped">
        <tr>
            <th>registration number</th>
            <th>driver shift size</th>
            <th>weight capacity</th>
            <th>status</th>
            <th>current city</th>
            <th>action</th>
        </tr>
        <tr>
            <td>${truck.registrationNumber}</td>
            <td>${truck.driverShiftSize}</td>
            <td>${truck.weightCapacity}</td>
            <td>${truck.status}</td>
            <td>${truck.location.city}</td>
            <td>
                <c:url value="/truck/edit" var="edit"/>
                <form name="edit" method="get" action="${edit}">
                    <input type="hidden" name="id" value="${truck.id}">
                    <button type="submit">Edit</button>
                </form>
            </td>
        </tr>
    </table>
    <br><br>
    <h2 class="text-center">List of drivers</h2>
    <div class="text-center">
        <table class="table table-striped">
            <tr>
                <th>Personal number</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Hours worked this month</th>
                <th>Status</th>
                <th>Phone number</th>
                <th>Email</th>
                <th></th>
            </tr>
            <c:forEach var="driver" items="${truck.driverList}">
                <tr>
                    <td>${driver.personalNumber}</td>
                    <td>${driver.user.firstName}</td>
                    <td>${driver.user.lastName}</td>
                    <td>${driver.hoursThisMonth}</td>
                    <td>${driver.status}</td>
                    <td>${driver.user.phoneNumber}</td>
                    <td>${driver.user.email}</td>
                    <td>
                        <c:url value="/truck/delete/driver" var="delete"/>
                        <form name="delete" method="post" action="${delete}">
                            <input type="hidden" name="id" value="${truck.id}">
                            <input type="hidden" name="driverId" value="${driver.id}">
                            <button type="submit">Edit</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:url value="/truck/add/driver" var="add"/>
        <div class="text-center">
            <form name="add" method="get" action="${add}">
                <input type="hidden" name="id" value="${truck.id}">
                <button type="submit">Add driver</button>
            </form>
        </div>
    </div>
</body>
</html>
