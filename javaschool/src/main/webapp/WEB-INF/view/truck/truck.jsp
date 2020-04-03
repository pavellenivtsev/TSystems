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
            <td>registration number</td>
            <td>driver shift size</td>
            <td>weight capacity</td>
            <td>status</td>
            <td>current city</td>
            <td>action</td>
        </tr>
        <tr>
            <td>${truck.registrationNumber}</td>
            <td>${truck.driverShiftSize}</td>
            <td>${truck.weightCapacity}</td>
            <td>${truck.status.name().toLowerCase().replaceAll("_"," ")}</td>
            <td>${truck.location.city}</td>
            <td>
                <c:if test="${truck.userOrderList.isEmpty()}">
                    <c:url value="/truck/edit" var="edit"/>
                    <form name="edit" method="get" action="${edit}">
                        <input type="hidden" name="id" value="${truck.id}">
                        <button type="submit">Edit</button>
                    </form>
                <c:choose>
                    <c:when test="${truck.driverList.isEmpty()}">
                        <c:url value="/truck/delete" var="delete"/>
                        <form name="delete" method="post" action="${delete}">
                            <input type="hidden" name="id" value="${truck.id}">
                            <button type="submit">Delete</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        To delete this car
                        you must remove all drivers
                    </c:otherwise>
                </c:choose>
                </c:if>
                <c:if test="${!truck.userOrderList.isEmpty()}">
                    Carrying the order
                </c:if>
            </td>
        </tr>
    </table>
    <br><br>
    <h2 class="text-center">List of drivers</h2>
        <table class="table table-striped" id="cssTable2">
            <tr>
                <td>Personal number</td>
                <td>First name</td>
                <td>Last name</td>
                <td>Hours worked this month</td>
                <td>Status</td>
                <td>Phone number</td>
                <td>Email</td>
                <td></td>
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
                    <td>
                        <c:if test="${driver.truck.userOrderList.isEmpty()}">
                            <c:url value="/truck/delete/driver" var="delete"/>
                            <form name="delete" method="post" action="${delete}">
                                <input type="hidden" name="id" value="${truck.id}">
                                <input type="hidden" name="driverId" value="${driver.id}">
                                <button type="submit">Remove</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${truck.userOrderList.isEmpty()}">
            <c:url value="/truck/add/driver" var="add"/>
            <div class="text-center">
                <form name="add" method="get" action="${add}">
                    <input type="hidden" name="id" value="${truck.id}">
                    <button type="submit">Add driver</button>
                </form>
            </div>
        </c:if>
    </div>

</body>
</html>
