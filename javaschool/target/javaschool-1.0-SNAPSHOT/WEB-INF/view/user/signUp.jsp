<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
    <div>Please Sign Up!</div>
    <c:url value="/signup" var="var"
    <form name="signUp" method="post" action="${var}">
        <label for="username">Username
            <input class="input-field" type="text" id="username" name="username">
        </label>
        <label for="password">Password
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <label for="firstName">First Name
            <input class="input-field" type="text" id="firstName" name="firstName">
        </label>
        <label for="lastName">Last Name
            <input class="input-field" type="text" id="lastName" name="lastName">
        </label>
        <label for="phoneNumber">First Name
            <input class="input-field" type="text" id="phoneNumber" name="firstName" required >
        </label>
        <input type="submit" value="Add user">
    </form>

</body>
</html>
