<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Title</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container">
    <h2 class="text-center">Available drivers</h2>
    <table class="table table-striped">
        <tr>
            <th>Personal number</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Phone number</th>
            <th>Email</th>
            <th></th>
        </tr>
        <c:forEach var="driver" items="${drivers}">
            <tr>
                <td>${driver.personalNumber}</td>
                <td>${driver.user.firstName}</td>
                <td>${driver.user.lastName}</td>
                <td>${driver.user.phoneNumber}</td>
                <td>${driver.user.email}</td>
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
