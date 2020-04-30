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
                <th>Personal number</th>
                <th>Hours worked in this month</th>
                <th>Your status</th>
                <th></th>
            </tr>
            <tr>
                <td>${driver.personalNumber}</td>
                <fmt:formatNumber var="hoursThisMonth" value="${driver.hoursThisMonth}" pattern="###.##"/>
                <td>${hoursThisMonth}</td>
                <td>${driver.status.name().toLowerCase().replaceAll("_"," ")}</td>
                <td>
                    <c:if test="${driver.truck!=null}">
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
    <br><br>
    <c:if test="${(driver.truck.driverList.size()>1)}">
        <div>
            <h2 class="text-center">Your additional drivers</h2>
            <table class="table table-striped" id="cssTable2">
                <tr>
                    <th>Personal number</th>
                    <th>First name</th>
                    <th>Last name</th>
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
