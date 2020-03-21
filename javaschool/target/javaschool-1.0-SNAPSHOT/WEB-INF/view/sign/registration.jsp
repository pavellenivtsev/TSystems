<!DOCTYPE html>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Registration</title>
</head>

<body>
<%@include file="../common/navbar.jsp" %>
<div class="container">

    <h2 class="text-center">Registration</h2>
    <c:url value="/registration" var="var"/>
    <form name="userForm" class="form-horizontal" method="post" action="${var}">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="username" name="username" autofocus="autofocus" required>
            </div>
        </div>
        <div class="form-group">
        <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
            <input class="form-control" type="password" id="password" name="password" required>
            </div>
        </div>
        <div class="form-group">
        <label for="passwordConfirm" class="col-sm-2 control-label">Confirm your password</label>
            <div class="col-sm-10">
            <input class="form-control" type="password" id="passwordConfirm" name="passwordConfirm" required>
            </div>
        </div>
        <div class="form-group">
        <label for="firstName" class="col-sm-2 control-label">First Name</label>
            <div class="col-sm-10">
            <input class="form-control" type="text" id="firstName" name="firstName" required>
            </div>
        </div>
        <div class="form-group">
        <label for="lastName" class="col-sm-2 control-label">Last Name</label>
            <div class="col-sm-10">
            <input class="form-control" type="text" id="lastName" name="lastName" required>
            </div>
        </div>
        <div class="form-group">
        <label for="phoneNumber" class="col-sm-2 control-label">Phone number</label>
            <div class="col-sm-10">
            <input class="form-control" type="text" id="phoneNumber" name="phoneNumber" required>
            </div>
        </div>
        <div class="form-group">
        <label for="email" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
            <input class="form-control" type="email" id="email" name="email" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Sign up</button>
            </div>
        </div>
    </form>
    <c:url value="/" var="home"/>
    <h3 class="text-center"><a href="${home}">Home page</a></h3>
</div>
</body>
</html>
