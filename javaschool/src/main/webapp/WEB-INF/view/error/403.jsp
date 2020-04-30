<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Access Denied</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <div class="text-center">
        <h1>HTTP Status 403 - Access is denied</h1>
        <br><br>
        <sec:authorize access="isAuthenticated()">
        <h2>Hi <sec:authentication property="name"/>, you do not have permission to access this page!</h2>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <h2>You do not have permission to access this page!</h2>
        </sec:authorize>
    </div>
</div>
</body>
</html>