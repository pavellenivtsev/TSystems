<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Access Denied</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h1>HTTP Status 403 - Access is denied</h1>
    <h2>${msg}</h2>
</div>
</body>
</html>