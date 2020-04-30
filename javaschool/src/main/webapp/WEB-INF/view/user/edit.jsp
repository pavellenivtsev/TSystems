<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Edit information</title>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Edit your information</h2>
    <c:url var="edit" value="/user/edit"/>
    <form class="form-horizontal" action="${edit}" method="post" id="form">
        <input type="hidden" id="id" name="id" value="${user.id}" required>
        <div class="form-group">
            <label for="firstName" class="col-sm-2 control-label">First name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="col-sm-2 control-label">Last name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="phoneNumber" class="col-sm-2 control-label">Phone number</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Edit information</button>
            </div>
        </div>
    </form>
</div>
<script>
    $(function () {
        $('#form').validate({
            errorPlacement: function (error, element) {
                element.after(error);
            },
        });
    });
</script>
</body>
</html>
