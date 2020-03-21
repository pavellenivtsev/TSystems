<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<h2 class="text-center">Orders</h2>
<table class="table table-striped">
    <tr>
        <th>unique number</th>
        <th>order status</th>
        <th>cargo name</th>
        <th>cargo status</th>
        <th>place of departure</th>
        <th>delivery place</th>
        <th></th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.uniqueNumber}</td>
            <td>${order.status.name().toLowerCase().replaceAll("_"," ")}</td>

            <c:forEach var="cargo" items="${order.cargoList}">
                <td>${cargo.name}</td>
                <td>${cargo.status.name().toLowerCase()}</td>
                <c:forEach var="waypoint" items="${cargo.waypointList}">
                    <td>${waypoint.location.city}</td>
                </c:forEach>
            </c:forEach>
            <td>
                <c:url value="/order/edit" var="edit"/>
                <c:url value="/order/delete" var="delete"/>
                <c:url value="/order/add/truck" var="addTruck"/>
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Action
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">

                        <form class="dropdown-item" name="edit" method="get" action="${edit}">
                            <input type="hidden" name="id" value="${order.id}">
                            <button class="dropdown-item" type="submit">Edit order</button>
                        </form>

                        <form class="dropdown-item" name="delete" method="post" action="${delete}">
                            <input type="hidden" name="id" value="${order.id}">
                            <button class="dropdown-item" type="submit">Delete order</button>
                        </form>

                        <form class="dropdown-item" name="addTruck" method="get" action="${addTruck}">
                            <input type="hidden" name="id" value="${order.id}">
                            <button class="dropdown-item" type="submit">Add/edit truck</button>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<c:url value="/order/add" var="add"/>
<div class="text-center">
    <form method="get" action="${add}">
        <button type="submit">Add new userOrder</button>
    </form>
</div>
</body>
</html>
