<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Available Trucks</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Available trucks</h2>
    <table class="table table-striped" id="cssTable">
        <tr>
            <td>Registration number</td>
            <td>Driver shift size</td>
            <td>Weight capacity</td>
            <td>Status</td>
            <td>Current city</td>
            <td></td>
        </tr>
        <c:forEach var="truck" items="${trucks}">
            <tr>
                <td>${truck.registrationNumber}</td>
                <td>${truck.driverShiftSize}</td>
                <td>${truck.weightCapacity}</td>
                <td>${truck.status.name().toLowerCase().replaceAll("_"," ")}</td>
                <td>${truck.location.city}</td>
                <td>
                    <c:url value="/order/add/truck" var="addTruck"/>
                    <form name="edit" method="post" action="${addTruck}">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="truckId" value="${truck.id}">
                        <button class="btn btn-default" type="submit">Add truck</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
