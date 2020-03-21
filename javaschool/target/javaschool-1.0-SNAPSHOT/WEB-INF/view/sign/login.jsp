<!DOCTYPE html>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Login</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>

<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/cabinet"); %>
</sec:authorize>
<div class="container">
    <h2 class="text-center">Login</h2>
    <c:url value="/login" var="login"/>
    <form class="form-horizontal" method="post" action="${login}">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required autofocus/>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Log in</button>
            </div>
        </div>
        <c:url value="/registration" var="registration"/>
        <h3 class="text-center"><a href="${registration}">Sign up</a></h3>
    </form>
</div>

</body>
</html>
