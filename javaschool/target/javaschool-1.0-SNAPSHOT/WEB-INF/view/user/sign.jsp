<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign</title>
</head>
<body>
    <h2>Please sign in</h2>
    <form method="post" action="/cabinet">
        <div class="form-element">
            <label for="username">User name</label>
            <input type="text" id="username" required/>
        </div>
        <div class="form-element">
            <label for="password">Password:
            <input type="password" id="password" required/>
            </label>
        </div>
        <div class="form-element">
            <input type="submit" value="Log in" />
        </div>
    </form>
    <h2>if you are not registered</h2>
    <c:url value="/signup" var="signUp"/>
    <form>
        <input type="button" value="Sign up" onClick='location.href="${signUp}"'>
    </form>
</body>
</html>
