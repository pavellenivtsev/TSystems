<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Title</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Available drivers</h2>
    <table class="table table-striped" id="cssTable">
        <tr>
            <td>Personal number</td>
            <td>First name</td>
            <td>Last name</td>
            <td>Phone number</td>
            <td>Email</td>
            <td>City</td>
            <td></td>
        </tr>
        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td>${driver.personalNumber}</td>
                <td>${driver.user.firstName}</td>
                <td>${driver.user.lastName}</td>
                <td>${driver.user.phoneNumber}</td>
                <td>${driver.user.email}</td>
                <td>${driver.user.location.city}</td>
                <td>
                    <c:url value="/truck/add/driver" var="add"/>
                    <form name="delete" method="post" action="${add}">
                        <input type="hidden" name="id" value="${truck.id}">
                        <input type="hidden" name="driverId" value="${driver.id}">
                        <button type="submit">Add</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
