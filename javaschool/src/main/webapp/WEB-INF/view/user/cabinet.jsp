<html>
<head>
    <%@include file="../common/common.jsp" %>

    <title>User cabinet</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container">
    <h2 class="text-center">Hello, <sec:authentication property="firstName"/>!</h2>
    <h2 class="text-center">You haven't been appointed yet. Please wait.</h2>--%>
    <br><br><br>
    <h2 class="text-center"> Your information</h2>
    <ol>
        <li>First name: <sec:authentication property="firstName"/></li>
        <li>Last name: <sec:authentication property="lastName"/></li>
        <li>Phone number: <sec:authentication property="phoneNumber"/></li>
        <li>Email: <sec:authentication property="email"/></li>
        <br>
        <div class="text-center">
            <li><a href="/user/edit">Edit information</a></li>
        </div>
    </ol>
    <br><br><br>
    <div class="container">
        <form class="form-horizontal" action="" method="post">
            <div class="form-group">
                <label for="firstName" class="col-sm-2 control-label">First name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="<sec:authentication property="firstName"/>" required>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName" class="col-sm-2 control-label">Last name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="<sec:authentication property="lastName"/>" required>
                </div>
            </div>
            <div class="form-group">
                <label for="phoneNumber" class="col-sm-2 control-label">Phone number</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="<sec:authentication property="phoneNumber"/>" required>
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control" id="email" name="email" placeholder="<sec:authentication property="email"/>" required>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Edit information</button>
                </div>
            </div>
        </form>
    </div>
</div>

<%--<div class="container">--%>
<%--    <h2>Hello,!</h2>--%>

<%--    <div>--%>
<%--        <h2>You haven't been appointed yet. Please wait.</h2>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <h2>--%>
<%--            Your information:--%>
<%--        </h2>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <div>--%>
<%--            <span>Username: </span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span><c:out value="Имя пользователя"></c:out></span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span>First name: </span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span><c:out value="Имя"></c:out></span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span>Last name: </span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span><c:out value="Фамилия"></c:out></span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span>Phone number: </span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span><c:out value="телефон"></c:out></span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span>Email: </span>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <span><c:out value="email"></c:out></span>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <!--  добавь путь -->--%>
<%--    <c:url value="/" var="edit"/>--%>
<%--    <form>--%>
<%--        <input type="button" value="Edit information" onClick='location.href="${edit}"'>--%>
<%--    </form>--%>
<%--</div>--%>
</body>
</html>