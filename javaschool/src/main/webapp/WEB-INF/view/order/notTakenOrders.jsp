<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Not taken orders</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Not taken orders</h2>
    <div class="text-center">
        <c:url value="/order" var="add"/>
        <form method="post" action="${add}">
            <button class="btn btn-default" type="submit" id="add_new_order">Add new order</button>
        </form>
    </div>
    <c:if test="${!orders.isEmpty()}">
        <table class="table table-striped" id="cssTable">
            <tr>
                <td rowspan="2"></td>
                <td rowspan="2">Order<br>Unique number</td>
                <td colspan="5">Cargo</td>
                <td rowspan="2"></td>
            </tr>
            <tr>
                <td>Name</td>
                <td>Weight</td>
                <td>Status</td>
                <td>Place of departure</td>
                <td>Delivery place</td>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <c:set var="rowspan" value="${order.cargoList.size()}"/>
                    <c:if test="${rowspan == 0}">
                        <c:set var="rowspan" value="1"/>
                    </c:if>
                    <td rowspan="${rowspan}">
                        <c:url value="/cargo/add" var="addCargo"/>
                        <form method="get" action="${addCargo}">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <button class="btn btn-default" type="submit" id="add_cargo">Add cargo</button>
                        </form>

                        <c:if test="${!order.cargoList.isEmpty()}">
                            <c:url value="/truck/add/order" var="addTruck"/>
                            <form method="get" action="${addTruck}">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <button class="btn btn-default" type="submit">Add truck</button>
                            </form>
                        </c:if>
                        <c:url value="/order/delete" var="delete"/>
                        <form method="post" action="${delete}">
                            <input type="hidden" name="id" value="${order.id}">
                            <button class="btn btn-default" type="submit" id="delete_order">Delete order</button>
                        </form>
                    </td>
                    <td rowspan="${rowspan}">${order.uniqueNumber}</td>
                    <c:if test="${!order.cargoList.isEmpty()}">
                        <td>${order.cargoList.get(0).name}</td>
                        <td>${order.cargoList.get(0).weight}</td>
                        <td>${order.cargoList.get(0).status.name().toLowerCase().replaceAll("_"," ")}</td>
                        <td>${order.cargoList.get(0).loadingAddress}</td>
                        <td>${order.cargoList.get(0).unloadingAddress}</td>
                        <td>
                            <c:url value="/cargo/edit" var="editCargo"/>
                            <form method="get" action="${editCargo}">
                                <input type="hidden" name="cargoId" value="${order.cargoList.get(0).id}">
                                <button class="btn btn-default" type="submit">Edit cargo</button>
                            </form>

                            <c:url value="/cargo/delete" var="deleteCargo"/>
                            <form method="post" action="${deleteCargo}">
                                <input type="hidden" name="cargoId" value="${order.cargoList.get(0).id}">
                                <button class="btn btn-default" type="submit">Delete cargo</button>
                            </form>
                        </td>
                    </c:if>
                </tr>
                <c:forEach var="cargo" items="${order.cargoList}" varStatus="loop">
                    <c:if test="${not loop.first}">
                        <tr>
                            <td>${cargo.name}</td>
                            <td>${cargo.weight}</td>
                            <td>${cargo.status.name().toLowerCase().replaceAll("_"," ")}</td>
                            <td>${cargo.loadingAddress}</td>
                            <td>${cargo.unloadingAddress}</td>
                            <td>
                                <c:url value="/cargo/edit" var="editCargo"/>
                                <form method="get" action="${editCargo}">
                                    <input type="hidden" name="cargoId" value="${cargo.id}">
                                    <button class="btn btn-default" type="submit">Edit cargo</button>
                                </form>

                                <c:url value="/cargo/delete" var="deleteCargo"/>
                                <form method="post" action="${deleteCargo}">
                                    <input type="hidden" name="cargoId" value="${cargo.id}">
                                    <button class="btn btn-default" type="submit">Delete cargo</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
