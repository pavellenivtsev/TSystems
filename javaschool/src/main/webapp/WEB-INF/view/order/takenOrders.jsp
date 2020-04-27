<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Orders in progress</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Orders in progress</h2>
    <c:if test="${!orders.isEmpty()}">
        <table class="table table-striped" id="cssTable">
            <tr>
                <td rowspan="2">Order<br>Unique number</td>
                <td colspan="5">Cargo</td>
            </tr>
            <td>Name</td>
            <td>Weight</td>
            <td>Status</td>
            <td>Place of departure</td>
            <td>Delivery place</td>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
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