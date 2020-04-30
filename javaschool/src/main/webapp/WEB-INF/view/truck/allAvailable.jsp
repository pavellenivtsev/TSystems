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
            <th>Approximately distance</th>
            <th></th>
        </tr>
        <c:forEach var="truckPair" items="${truckPairs}">
            <tr>
                <td>${truckPair.truckDto.registrationNumber}</td>
                <td>${truckPair.truckDto.driverShiftSize}</td>
                <td>${truckPair.truckDto.weightCapacity}</td>
                <td>${truckPair.truckDto.status.name().toLowerCase().replaceAll("_"," ")}</td>
                <td>${truckPair.truckDto.address}</td>
                <td>${truckPair.approximatelyTotalDistanceForTruckAndOrder}</td>
                <td>
                    <c:url value="/truck/add/order" var="addTruck"/>
                    <form name="edit" method="post" action="${addTruck}">
                        <input type="hidden" name="orderId" value="${orderId}">
                        <input type="hidden" name="truckId" value="${truckPair.truckDto.id}">
                        <button class="btn btn-default" type="submit">Add truck</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>