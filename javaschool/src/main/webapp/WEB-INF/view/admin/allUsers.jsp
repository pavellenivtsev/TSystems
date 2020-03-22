<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Users</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container">
    <h2 class="text-center">Users table</h2>
    <table class="table table-striped">
        <tr>
            <th>first name</th>
            <th>last name</th>
            <th>phone number</th>
            <th>email</th>
            <th>role</th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.email}</td>
                <td>
                    <c:forEach items="${user.roles}" var="role">${role.name}</c:forEach>
                </td>
                <td>
                    <c:url value="/admin/appoint/admin" var="admin"/>
                    <form name="admin" method="post" action="${admin}">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="submit">Appoint as admin</button>
                    </form>
                </td>
                <td>
                    <c:url value="/admin/appoint/manager" var="manager"/>
                    <form name="manager" method="post" action="${manager}">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="submit">Appoint as manager</button>
                    </form>
                </td>
                <td>
                    <c:url value="/admin/appoint/driver" var="driver"/>
                    <form name="driver" method="get" action="${driver}">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="submit">Appoint as driver</button>
                    </form>
                </td>
                <td>
                    <c:url value="/admin/user/delete" var="delete"/>
                    <form name="delete" method="post" action="${delete}">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
