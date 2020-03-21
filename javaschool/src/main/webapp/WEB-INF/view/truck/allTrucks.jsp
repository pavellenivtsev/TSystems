<!DOCTYPE html>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Trucks</title>

</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container">
<h2 class="text-center">Trucks</h2>
<table class="table table-striped">
    <tr>
        <th>registration number</th>
        <th>driver shift size</th>
        <th>weight capacity</th>
        <th>status</th>
        <th>current city</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="truck" items="${trucks}">
        <tr>
            <td>${truck.registrationNumber}</td>
            <td>${truck.driverShiftSize}</td>
            <td>${truck.weightCapacity}</td>
            <td>${truck.status}</td>
            <td>${truck.location.city}</td>
            <td>
                <c:url value="/truck/" var="getTruck"/>
               <form name="edit" method="get" action="${getTruck}">
                  <input type="hidden" name="id" value="${truck.id}">
                   <button type="submit">More information</button>
                </form>
            </td>
            <td>
                <c:url value="/truck/delete" var="delete"/>
                <form name="delete" method="post" action="${delete}">
                  <input type="hidden" name="id" value="${truck.id}">
                   <button type="submit">Delete</button>
                </form>
            </td>
<%--                <!-- <div class="dropdown">--%>
<%--                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2"--%>
<%--                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                        Action--%>
<%--                    </button>--%>
<%--                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">--%>

<%--                        <form class="dropdown-item" name="edit" method="get" action="${getTruck}">--%>
<%--                            <input type="hidden" name="id" value="${truck.id}">--%>
<%--                            <button class="dropdown-item" type="submit">More information</button>--%>
<%--                        </form>--%>

<%--                        <form class="dropdown-item" name="delete" method="post" action="${delete}">--%>
<%--                            <input type="hidden" name="id" value="${truck.id}">--%>
<%--                            <button class="dropdown-item" type="submit">Delete</button>--%>
<%--                        </form>--%>
<%--                    </div>--%>
<%--                </div> -->--%>

        </tr>
    </c:forEach>
</table>
<c:url value="/truck/add" var="add"/>
<div class="text-center">
    <form name="delete" method="get" action="${add}">
        <button type="submit">Add new truck</button>
    </form>
</div>
</div>
</body>
</html>
