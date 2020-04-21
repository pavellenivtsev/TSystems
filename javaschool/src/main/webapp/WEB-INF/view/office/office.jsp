<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Office</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Office information</h2>
    <table class="table table-striped" id="cssTable">
        <tr>
            <td>Name</td>
            <td>Address</td>
            <td></td>
        </tr>
        <tr>
            <td>${office.title}</td>
            <td>${office.address}</td>
            <c:if test="${office.truckList.isEmpty()}">
            <td>
                <c:url value="/office/delete" var="deleteOffice"/>
                <form name="deleteOffice" method="post" action="${deleteOffice}">
                    <input type="hidden" name="id" value="${office.id}">
                    <button class="btn btn-default" type="submit">Delete</button>
                </form>
            </td>
            </c:if>
        </tr>
    </table>
    <br><br>
    <h2 class="text-center">Trucks registered in this office</h2>
    <table class="table table-striped" id="cssTable2">
        <tr>
            <td></td>
            <td>Registration number</td>
            <td>Driver shift size</td>
            <td>Weight capacity</td>
            <td>Status</td>
            <td>Current address</td>
            <td></td>
        </tr>
        <c:forEach var="truck" items="${office.truckList}">
            <tr>
                <td>
                    <c:if test="${truck.userOrder!=null}">
                        The truck fulfills the order
                    </c:if>
                </td>
                <td>${truck.registrationNumber}</td>
                <td>${truck.driverShiftSize}</td>
                <td>${truck.weightCapacity}</td>
                <td>${truck.status.name().toLowerCase().replaceAll("_"," ")}</td>
                <td>${truck.address}</td>
                <td>
                    <c:url value="/truck/${truck.id}" var="getTruck"/>
                    <form name="edit" method="get" action="${getTruck}">
                        <button class="btn btn-default" type="submit">More information</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:url value="/truck/add" var="add"/>
    <div class="text-center">
        <form name="add" method="get" action="${add}">
            <input type="hidden" name="officeId" value="${office.id}">
            <button class="btn btn-default" type="submit">Add new truck</button>
        </form>
    </div>
</div>
</body>
</html>