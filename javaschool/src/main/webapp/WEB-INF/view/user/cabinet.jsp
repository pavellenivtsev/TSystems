<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User cabinet</title>
</head>
<body>
<div>
    <h2>Hello, <c:out value="Имя"></c:out>!</h2>
</div>
<div>
    <h2>You haven't been appointed yet. Please wait.</h2>
</div>
<div>
    <h2>
        Your information:
    </h2>
</div>
<div>
    <div>
        <span>Username: </span>
    </div>
    <div>
        <span ><c:out value="Имя пользователя"></c:out></span>
    </div>
    <div>
        <span>First name: </span>
    </div>
    <div>
        <span ><c:out value="Имя"></c:out></span>
    </div>
    <div>
        <span>Last name: </span>
    </div>
    <div>
        <span ><c:out value="Фамилия"></c:out></span>
    </div>
    <div>
        <span>Phone number: </span>
    </div>
    <div>
        <span ><c:out value="телефон"></c:out></span>
    </div>
    <div>
        <span>Email: </span>
    </div>
    <div>
        <span ><c:out value="email"></c:out></span>
    </div>
</div>
<!--  добавь путь -->
<c:url value="/" var="edit"/>
<form>
    <input type="button" value="Edit information" onClick='location.href="${edit}"'>
</form>
</body>
</html>