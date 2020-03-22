<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Title</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>

<div class="container">
    <h2 class="text-center">Appoint as driver</h2>
    <c:url value="/admin/appoint/driver" var="var"/>
    <form class="form-horizontal" method="post" action="${var}">
        <input type="hidden" name="userId" value="${userId}">
        <div class="form-group">
            <label for="personalNumber" class="col-sm-2 control-label">Personal number</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="personalNumber" name="personalNumber" autofocus="autofocus" required>
            </div>
        </div>
        <div class="form-group">
            <label for="currentCity" class="col-sm-2 control-label">Current city</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="currentCity" name="currentCity" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Appoint</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
