<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Trucks</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Trucks</h2>
    <c:url value="/truck/add" var="add"/>
    <div class="text-center">
        <form name="delete" method="get" action="${add}">
            <button class="btn btn-default" type="submit">Add new truck</button>
        </form>
    </div>
    <c:if test="${!trucks.isEmpty()}">
        <table class="table table-striped" id="cssTable">
            <tr>
                <td>Registration number</td>
                <td>Driver shift size</td>
                <td>Weight capacity</td>
                <td>Status</td>
                <td>City</td>
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
                        <c:url value="/truck/${truck.id}" var="getTruck"/>
                        <form name="edit" method="get" action="${getTruck}">
                            <button class="btn btn-default" type="submit">More information</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
