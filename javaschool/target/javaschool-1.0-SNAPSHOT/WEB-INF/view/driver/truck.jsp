<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        </c:when>
        <c:otherwise>
            <br>
            <div class="text-center">
                You don't have a truck yet, please wait
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
