<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>User cabinet</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Hello, ${user.firstName}</h2>
    <h2 class="text-center">You haven't been appointed yet. Please wait.</h2>
    <br><br><br>
    <h2 class="text-center"> Your information</h2>
    <div class="text-center">
        <ol>
            <li>First name: ${user.firstName}</li>
            <li>Last name: ${user.lastName}</li>
            <li>Phone number: ${user.phoneNumber}</li>
            <li>Email: ${user.email}</li>
        </ol>
        <div class="text-center">
            <c:url value="/user/edit" var="edit"/>
            <form action="${edit}" method="get">
                <input type="hidden" name="id" value="${user.id}">
                <button class="btn btn-default" type="submit">Edit information</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
