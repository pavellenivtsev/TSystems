<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Completed orders</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Completed orders</h2>
    <c:if test="${!orders.isEmpty()}">
        <table class="table table-striped" id="cssTable">
            <tr>
                <th rowspan="2"></th>
                <th rowspan="2">Order<br>Unique number</th>
                <th colspan="5">Cargo</th>
            </tr>
            <tr>
                <th>Name</th>
                <th>Weight</th>
                <th>Status</th>
                <th>Place of departure</th>
                <th>Delivery place</th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td rowspan="${order.cargoList.size()}">
                        <c:url value="/order/delete" var="delete"/>
                        <form method="post" action="${delete}">
                            <input type="hidden" name="id" value="${order.id}">
                            <button class="btn btn-default" type="submit">Delete order</button>
                        </form>
                    </td>
                    <td rowspan="${order.cargoList.size()}">${order.uniqueNumber}</td>
                    <td>${order.cargoList.get(0).name}</td>
                    <td>${order.cargoList.get(0).weight}</td>
                    <td>${order.cargoList.get(0).status.name().toLowerCase().replaceAll("_"," ")}</td>
                    <td>${order.cargoList.get(0).loadingAddress}</td>
                    <td>${order.cargoList.get(0).unloadingAddress}</td>
                </tr>
                <c:forEach var="cargo" items="${order.cargoList}" varStatus="loop">
                    <c:if test="${not loop.first}">
                        <tr>
                            <td>${cargo.name}</td>
                            <td>${cargo.weight}</td>
                            <td>${cargo.status.name().toLowerCase().replaceAll("_"," ")}</td>
                            <td>${cargo.loadingAddress}</td>
                            <td>${cargo.unloadingAddress}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
