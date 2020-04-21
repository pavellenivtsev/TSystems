<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Users</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Users table</h2>
    <table class="table table-striped" id="cssTable">
        <tr>
            <td>First name</td>
            <td>Last name</td>
            <td>Phone number</td>
            <td>Email</td>
            <td>Address</td>
            <td>Role</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.email}</td>
                <td>${user.address}</td>
                <td>
                    <c:forEach items="${user.roles}" var="role">${role.name}</c:forEach>
                </td>
                <c:if test="${user.driver==null||user.driver.truck==null}">
                    <td>
                        <c:url value="/admin/appoint/admin" var="admin"/>
                        <form name="admin" method="post" action="${admin}">
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-default" type="submit">Appoint<br/>as admin</button>
                        </form>
                    </td>
                    <td>
                        <c:url value="/admin/appoint/manager" var="manager"/>
                        <form name="manager" method="post" action="${manager}">
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-default" type="submit">Appoint<br/>as manager</button>
                        </form>
                    </td>
                    <td>
                        <c:url value="/admin/appoint/driver" var="driver"/>
                        <form name="driver" method="post" action="${driver}">
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-default" type="submit">Appoint<br/>as driver</button>
                        </form>

                    </td>
                    <td>
                        <c:url value="/admin/user/delete" var="delete"/>
                        <form name="delete" method="post" action="${delete}">
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-default" type="submit">Delete</button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
