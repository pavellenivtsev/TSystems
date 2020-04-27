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
        <tr class="text-center-row">
            <th>First name</th>
            <th>Last name</th>
            <th>Phone number</th>
            <th>Email</th>
            <th>Address</th>
            <th>Role</th>
            <th></th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr class="text-center-row">
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.email}</td>
                <td>${user.address}</td>
                <td>
                    <c:set var="isNotDisable"
                           value="${user.driver==null||user.driver.truck==null||user.driver.truck.userOrder==null}"/>
                    <c:forEach items="${user.roles}" var="role">
                        <select class="form-control"
                                <c:if test="${!isNotDisable}">disabled</c:if>
                                onchange="changeRole(this);">
                            <option value="${user.id}" id="role-user"
                                    <c:if test="${role.name=='ROLE_USER'}">selected</c:if>>User
                            </option>
                            <option value="${user.id}" id="role-driver"
                                    <c:if test="${role.name=='ROLE_DRIVER'}">selected</c:if>>Driver
                            </option>
                            <option value="${user.id}" id="role-manager"
                                    <c:if test="${role.name=='ROLE_MANAGER'}">selected</c:if>>Manager
                            </option>
                            <option value="${user.id}" id="role-admin"
                                    <c:if test="${role.name=='ROLE_ADMIN'}">selected</c:if>>Admin
                            </option>
                        </select>
                    </c:forEach>
                </td>
                <td>
                    <c:url value="/admin/user/delete" var="delete"/>
                    <form name="delete" method="post" action="${delete}">
                        <input type="hidden" name="id" value="${user.id}">
                        <button style="margin-top: 15px"
                                <c:if test="${!isNotDisable}">disabled</c:if>
                                class="btn btn-default" type="submit" id="delete_user">Delete
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script type="text/javascript">
    function changeRole(obj) {
        switch (obj.options[obj.selectedIndex].id) {
            case 'role-user':
                $.ajax({
                    type: "post",
                    url: "/admin/appoint/user",
                    data: {
                        id: obj.options[obj.selectedIndex].value,
                    },
                }).done(function (response) {
                    if (!response) {
                        alert("Something wrong")
                    }
                });
                break;
            case 'role-driver':
                $.ajax({
                    type: "post",
                    url: "/admin/appoint/driver",
                    data: {
                        id: obj.options[obj.selectedIndex].value,
                    },
                }).done(function (response) {
                    if (!response) {
                        alert("Something wrong")
                    }
                });
                break;
            case 'role-manager':
                $.ajax({
                    type: "post",
                    url: "/admin/appoint/manager",
                    data: {
                        id: obj.options[obj.selectedIndex].value,
                    },
                }).done(function (response) {
                    if (!response) {
                        alert("Something wrong")
                    }
                });
                break;
            case 'role-admin':
                $.ajax({
                    type: "post",
                    url: "/admin/appoint/admin",
                    data: {
                        id: obj.options[obj.selectedIndex].value,
                    },
                }).done(function (response) {
                    if (!response) {
                        alert("Something wrong")
                    }
                });
                break;
            default:
                alert("Incorrect data")
        }
    }
</script>
</body>
</html>
