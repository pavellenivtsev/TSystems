<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Your information</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Hello, ${driver.user.firstName}</h2>
    <br>
    <div>
        <h2 class="text-center">Your information</h2>
        <table class="table table-striped" id="cssTable">
            <tr>
                <td>Personal number</td>
                <td>Hours worked in this month</td>
                <td>Your status</td>
                <td></td>
            </tr>
            <tr>
                <td>${driver.personalNumber}</td>
                <fmt:formatNumber var="hoursThisMonth" value="${driver.hoursThisMonth}" pattern="###.##"/>
                <td>${hoursThisMonth}</td>
                <td>${driver.status.name().toLowerCase().replaceAll("_"," ")}</td>
                <td>
                    <c:if test="${!(driver.truck==null)}">
                        <c:if test="${driver.status.name().equals('REST')}">
                            <c:url value="/driver/status/start" var="start"/>
                            <form method="post" action="${start}">
                                <input type="hidden" name="driverId" value="${driver.id}">
                                <button class="btn btn-default" type="submit">Start shift</button>
                            </form>
                        </c:if>

                        <c:if test="${driver.status.name().equals('ON_SHIFT')}">
                            <c:url value="/driver/status/finish" var="finish"/>
                            <form method="post" action="${finish}">
                                <input type="hidden" name="driverId" value="${driver.id}">
                                <button class="btn btn-default" type="submit">Finish shift</button>
                            </form>
                        </c:if>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
    <c:if test="${(driver.truck.driverList.size()>1)}">
        <div>
            <h2 class="text-center">Your additional drivers</h2>
            <table class="table table-striped" id="cssTable2">
                <tr>
                    <td>Personal number</td>
                    <td>First name</td>
                    <td>Last name</td>
                </tr>
                <c:forEach var="anotherDriver" items="${driver.truck.driverList}">
                    <c:if test="${not (anotherDriver.id==driver.id)}">
                        <tr>
                            <td>${anotherDriver.personalNumber}</td>
                            <td>${anotherDriver.user.firstName}</td>
                            <td>${anotherDriver.user.lastName}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>
    </c:if>
</div>
</body>
</html>
