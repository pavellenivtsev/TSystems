<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Driver cabinet</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>

<div class="container" id="main-container">
    <h2 class="text-center">Hello, ${driver.user.firstName}</h2>
    <br>
    <c:if test="${!(driver.truck==null)}">
        <div>
            <h2 class="text-center">Truck information</h2>
            <table class="table" id="cssTable">
                <tr>
                    <td>Truck registration number</td>
                    <td>Truck status</td>
                    <td></td>
                </tr>
                <tr>
                    <td>${driver.truck.registrationNumber}</td>
                    <td>${driver.truck.status.name().toLowerCase().replaceAll("_"," ")}</td>
                    <td>
                        <c:if test="${driver.truck.status.name().equals('FAULTY')}">
                            <c:url value="/driver/truck/status/onDuty" var="truckOnDuty"/>
                            <form method="post" action="${truckOnDuty}">
                                <input type="hidden" name="driverId" value="${driver.id}">
                                <button type="submit">Set status on duty</button>
                            </form>
                        </c:if>

                        <c:if test="${driver.truck.status.name().equals('ON_DUTY')}">
                            <c:url value="/driver/truck/status/faulty" var="truckFaulty"/>
                            <form method="post" action="${truckFaulty}">
                                <input type="hidden" name="driverId" value="${driver.id}">
                                <button type="submit">Set faulty status</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
        <c:if test="${!driver.truck.userOrderList.isEmpty()}">
            <div>
                <h2 class="text-center">Order information</h2>
                <c:set var="flag" value="${true}"/>
                <c:forEach var="cargo" items="${driver.truck.userOrderList.get(0).cargoList}">
                    <c:if test="${(cargo.status.name().equals('PREPARED')||cargo.status.name().equals('SHIPPED'))}">
                        <c:set var="flag" value="${false}"/>
                    </c:if>
                </c:forEach>
                <table class="table" id="cssTable2">
                    <tr>
                        <c:if test="${flag}">
                            <td rowspan="2"></td>
                        </c:if>
                        <td rowspan="2">Order number</td>
                        <td rowspan="2">Place of departure</td>
                        <td rowspan="2">Delivery place</td>
                        <td colspan="2">Cargo</td>
                        <td rowspan="2"></td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td>Status</td>
                    </tr>
                    <tr>
                        <c:if test="${flag}">
                            <td rowspan="${driver.truck.userOrderList.get(0).cargoList.size()}">
                                <c:url value="/driver/order/status/completed" var="orderCompleted"/>
                                <form method="post" action="${orderCompleted}">
                                    <input type="hidden" name="userOrderId"
                                           value="${driver.truck.userOrderList.get(0).id}">
                                    <button type="submit">Complete the order</button>
                                </form>
                            </td>
                        </c:if>
                        <td rowspan="${driver.truck.userOrderList.get(0).cargoList.size()}">
                                ${driver.truck.userOrderList.get(0).uniqueNumber}</td>
                        <td rowspan="${driver.truck.userOrderList.get(0).cargoList.size()}">
                                ${driver.truck.userOrderList.get(0).waypointList.get(0).location.city}</td>
                        <td rowspan="${driver.truck.userOrderList.get(0).cargoList.size()}">
                                ${driver.truck.userOrderList.get(0).waypointList.get(1).location.city}</td>
                        <td>${driver.truck.userOrderList.get(0).cargoList.get(0).name}</td>
                        <td>${driver.truck.userOrderList.get(0).cargoList.get(0).status.name().toLowerCase().replaceAll("_"," ")}</td>
                        <td>
                            <c:if test="${driver.truck.userOrderList.get(0).cargoList.get(0).status.name().equals('PREPARED')}">
                                <c:url value="/cargo/shipped" var="cargoShipped"/>
                                <form method="post" action="${cargoShipped}">
                                    <input type="hidden" name="id"
                                           value="${driver.truck.userOrderList.get(0).cargoList.get(0).id}">
                                    <button type="submit">Cargo is shipped</button>
                                </form>
                            </c:if>

                            <c:if test="${driver.truck.userOrderList.get(0).cargoList.get(0).status.name().equals('SHIPPED')}">
                                <c:url value="/cargo/delivered" var="cargoDelivered"/>
                                <form method="post" action="${cargoDelivered}">
                                    <input type="hidden" name="id"
                                           value="${driver.truck.userOrderList.get(0).cargoList.get(0).id}">
                                    <button type="submit">Cargo is delivered</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                    <c:forEach var="cargo" items="${driver.truck.userOrderList.get(0).cargoList}" varStatus="loop">
                        <c:if test="${not loop.first}">
                            <tr>
                                <td>${cargo.name}</td>
                                <td>${cargo.status.name().toLowerCase().replaceAll("_"," ")}</td>
                                <td>
                                    <c:if test="${cargo.status.name().equals('PREPARED')}">
                                        <c:url value="/cargo/shipped" var="cargoShipped"/>
                                        <form method="post" action="${cargoShipped}">
                                            <input type="hidden" name="id" value="${cargo.id}">
                                            <button type="submit">Cargo is shipped</button>
                                        </form>
                                    </c:if>

                                    <c:if test="${cargo.status.name().equals('SHIPPED')}">
                                        <c:url value="/cargo/delivered" var="cargoDelivered"/>
                                        <form method="post" action="${cargoDelivered}">
                                            <input type="hidden" name="id" value="${cargo.id}">
                                            <button type="submit">Cargo is delivered</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </c:if>
</div>
</body>
</html>

