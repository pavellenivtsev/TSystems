<html>
<head>
    <%@include file="../common/common.jsp" %>

    <title>User cabinet</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container">
    <h2>Hello, ${}!</h2>

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
            <span><c:out value="Имя пользователя"></c:out></span>
        </div>
        <div>
            <span>First name: </span>
        </div>
        <div>
            <span><c:out value="Имя"></c:out></span>
        </div>
        <div>
            <span>Last name: </span>
        </div>
        <div>
            <span><c:out value="Фамилия"></c:out></span>
        </div>
        <div>
            <span>Phone number: </span>
        </div>
        <div>
            <span><c:out value="телефон"></c:out></span>
        </div>
        <div>
            <span>Email: </span>
        </div>
        <div>
            <span><c:out value="email"></c:out></span>
        </div>
    </div>
    <!--  добавь путь -->
    <c:url value="/" var="edit"/>
    <form>
        <input type="button" value="Edit information" onClick='location.href="${edit}"'>
    </form>
</div>
</body>
</html>