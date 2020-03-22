<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Truck information</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
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
