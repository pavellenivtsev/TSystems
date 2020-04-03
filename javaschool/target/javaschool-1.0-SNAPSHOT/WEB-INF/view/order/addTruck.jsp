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
            <th>Registration number</th>
            <th>Driver shift size</th>
            <th>Weight capacity</th>
            <th>Status</th>
            <th>Current city</th>
            <th></th>
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
                        <button type="submit">Add truck</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
