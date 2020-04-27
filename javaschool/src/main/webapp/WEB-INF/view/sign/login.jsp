<!DOCTYPE html>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Login</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>

<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/cabinet"); %>
</sec:authorize>
<div class="container" id="main-container">
    <h2 class="text-center">Login</h2>
    <c:url value="/login" var="login"/>
    <form id="form" class="form-horizontal" method="post" action="${login}">
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
                <button class="btn btn-default" type="submit" id="form_login">Log in</button>
            </div>
        </div>
        <c:url value="/registration" var="registration"/>
        <h3 class="text-center"><a href="${registration}">Sign up</a></h3>
    </form>
</div>
<script>
    $(function () {
        var validator = $("#form").validate({
            // Specify validation rules
            rules: {
                password: {
                    minlength: 5,
                },
            },
            massages: {
                username: "Please enter username.",
                password:{
                    required: "Please provide a password.",
                    minlength: "Your password must be at least 5 characters long.",
                },
            },
            errorPlacement: function (error, element) {
                if (element.is('#latitude')) {
                    error.appendTo("#confirmLocationError")
                } else {
                    element.after(error);
                }
            },
        });
    });
</script>
</body>
</html>
