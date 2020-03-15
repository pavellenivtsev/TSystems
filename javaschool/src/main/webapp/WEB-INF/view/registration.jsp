<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Registration</title>
</head>

<body>
<div>
    <c:url value="/registration" var="var"/>
    <form name="userForm" method="post" action="${var}">
        <h2>Registration</h2>
        <label for="username">Username
            <input class="input-field" type="text" id="username" name="username" autofocus="autofocus" required>
        </label>
        <br>
        <label for="password">Password
            <input class="input-field" type="password" id="password" name="password" required>
        </label>
        <br>
        <label for="passwordConfirm">Confirm your password
            <input class="input-field" type="password" id="passwordConfirm" name="passwordConfirm" required>
        </label>
        <br>
        <label for="firstName">First Name
            <input class="input-field" type="text" id="firstName" name="firstName" required>
        </label>
        <br>
        <label for="lastName">Last Name
            <input class="input-field" type="text" id="lastName" name="lastName" required>
        </label>
        <br>
        <label for="phoneNumber">Phone number
            <input class="input-field" type="text" id="phoneNumber" name="phoneNumber" required>
        </label>
        <br>
        <label for="email">Email
            <input class="input-field" type="email" id="email" name="email" required>
        </label>
        <br>
        <button type="submit">Sign up</button>
    </form>

    <a href="/">Home page</a>
</div>
</body>
</html>
