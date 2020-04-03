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
            <td>City</td>
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
                <td>${user.location.city}</td>
                <td>
                    <c:forEach items="${user.roles}" var="role">${role.name}</c:forEach>
                </td>
                <td>
                    <c:if test="${(user.driver.truck==null)||(user.driver.truck.userOrderList.isEmpty())}">
                        <c:url value="/admin/appoint/admin" var="admin"/>
                        <form name="admin" method="post" action="${admin}">
                            <input type="hidden" name="id" value="${user.id}">
                            <button type="submit">Appoint as admin</button>
                        </form>
                    </c:if>
                </td>
                <td>
                    <c:if test="${(user.driver.truck==null)||(user.driver.truck.userOrderList.isEmpty())}">
                        <c:url value="/admin/appoint/manager" var="manager"/>
                        <form name="manager" method="post" action="${manager}">
                            <input type="hidden" name="id" value="${user.id}">
                            <button type="submit">Appoint as manager</button>
                        </form>
                    </c:if>
                </td>
                <td>
                    <c:if test="${(user.driver.truck==null)||(user.driver.truck.userOrderList.isEmpty())}">
                        <c:url value="/admin/appoint/driver" var="driver"/>
                        <form name="driver" method="post" action="${driver}">
                            <input type="hidden" name="userId" value="${user.id}">
                            <button type="submit">Appoint as driver</button>
                        </form>
                    </c:if>
                </td>
                <td>
                    <c:if test="${(user.driver.truck==null)||(user.driver.truck.userOrderList.isEmpty())}">
                            <c:url value="/admin/user/delete" var="delete"/>
                            <form name="delete" method="post" action="${delete}">
                                <input type="hidden" name="id" value="${user.id}">
                                <button type="submit">Delete</button>
                            </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
