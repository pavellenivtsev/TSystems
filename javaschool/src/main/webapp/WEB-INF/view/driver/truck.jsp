<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Truck</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Truck information</h2>
    <c:choose>
        <c:when test="${!(driver.truck==null)}">
            <table class="table" id="cssTable">
                <tr>
                    <th>Truck registration number</th>
                    <th>Truck status</th>
                    <th></th>
                </tr>
                <tr>
                    <td>${driver.truck.registrationNumber}</td>
                    <td>${driver.truck.status.name().toLowerCase().replaceAll("_"," ")}</td>
                    <td>
                        <c:if test="${driver.truck.status.name().equals('FAULTY')}">
                            <c:url value="/driver/truck/status/onDuty" var="truckOnDuty"/>
                            <form method="post" action="${truckOnDuty}"
                                  style="margin-bottom: 0;">
                                <input type="hidden" name="driverId" value="${driver.id}">
                                <button class="btn btn-default" type="submit">Set status on duty</button>
                            </form>
                        </c:if>
                        <c:if test="${driver.truck.userOrder==null}">
                            <c:if test="${driver.truck.status.name().equals('ON_DUTY')}">
                                <c:url value="/driver/truck/status/faulty" var="truckFaulty"/>
                                <form method="post" action="${truckFaulty}"
                                      style="margin-bottom: 0;">
                                    <input type="hidden" name="driverId" value="${driver.id}">
                                    <button class="btn btn-default" type="submit">Set faulty status</button>
                                </form>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <br>
            <div class="text-center">
                You don't have a truck yet, please wait
            </div>
            <br>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
