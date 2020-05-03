<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>All offices</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Offices</h2>
    <c:url value="/office/add" var="add"/>
    <div class="text-center">
        <form name="delete" method="get" action="${add}">
            <button class="btn btn-default" type="submit">Add new office</button>
        </form>
    </div>
    <c:if test="${!offices.isEmpty()}">
        <table class="table table-striped" id="cssTable">
            <tr>
                <th>Name</th>
                <th>Address</th>
                <th>Count of trucks</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="office" items="${offices}">
                <tr>
                    <td>${office.title}</td>
                    <td>${office.address}</td>
                    <td>${office.truckList.size()}</td>
                    <td>
                        <c:url value="/office/${office.id}" var="getOffice"/>
                        <form name="edit" method="get" action="${getOffice}"
                              style="margin-bottom: 0;">
                            <button class="btn btn-default" type="submit">More information</button>
                        </form>
                    </td>
                    <td>
                        <c:url value="/truck/add" var="addTruck"/>
                        <form name="edit" method="get" action="${addTruck}"
                              style="margin-bottom: 0;">
                            <input type="hidden" name="officeId" value="${office.id}">
                            <button class="btn btn-default" type="submit">Add new truck</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
