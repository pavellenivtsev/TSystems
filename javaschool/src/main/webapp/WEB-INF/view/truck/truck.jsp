<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Truck information</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Truck information</h2>
    <table class="table table-striped" id="cssTable">
        <tr>
            <th></th>
            <th>Registration number</th>
            <th>Driver shift size</th>
            <th>Weight capacity</th>
            <th>Status</th>
            <th>Current address</th>
            <c:if test="${truck.userOrder==null}">
                <td></td>
            </c:if>
        </tr>
        <tr>
            <td><c:if test="${truck.userOrder!=null}">
                The truck fulfills the order
            </c:if></td>
            <td>${truck.registrationNumber}</td>
            <td>${truck.driverShiftSize}</td>
            <td>${truck.weightCapacity}</td>
            <td>${truck.status.name().toLowerCase().replaceAll("_"," ")}</td>
            <td>${truck.address}</td>
            <c:if test="${truck.userOrder==null}">
                <td>
                    <c:url value="/truck/edit" var="edit"/>
                    <form name="edit" method="get" action="${edit}">
                        <input type="hidden" name="id" value="${truck.id}">
                        <button class="btn btn-default" type="submit">Edit</button>
                    </form>

                    <c:if test="${truck.driverList.isEmpty()}">
                        <c:url value="/truck/delete" var="delete"/>
                        <form name="delete" method="post" action="${delete}">
                            <input type="hidden" name="id" value="${truck.id}">
                            <button class="btn btn-default" type="submit">Delete</button>
                        </form>
                    </c:if>
                </td>
            </c:if>
        </tr>
    </table>
    <br><br>
    <h2 class="text-center">List of drivers</h2>
    <table class="table table-striped" id="cssTable2">
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
                <fmt:formatNumber var="hoursThisMonth" value="${driver.hoursThisMonth}" pattern="###.##"/>
                <td>${hoursThisMonth}</td>
                <td>${driver.status.name().toLowerCase().replaceAll("_"," ")}</td>
                <td>${driver.user.phoneNumber}</td>
                <td>${driver.user.email}</td>

                <c:if test="${truck.userOrder==null}">
                    <td>
                        <c:url value="/truck/remove/driver" var="remove"/>
                        <form name="delete" method="post" action="${remove}">
                            <input type="hidden" name="truckId" value="${truck.id}">
                            <input type="hidden" name="driverId" value="${driver.id}">
                            <button class="btn btn-default" type="submit">Remove</button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${truck.userOrder==null}">
        <c:url value="/truck/add/driver" var="add"/>
        <div class="text-center">
            <form name="add" method="get" action="${add}">
                <input type="hidden" name="truckId" value="${truck.id}">
                <button class="btn btn-default" type="submit">Add driver</button>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
