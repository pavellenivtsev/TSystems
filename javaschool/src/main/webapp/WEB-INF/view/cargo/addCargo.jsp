<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Add cargo</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/cargo/add" var="var"/>
    <form action="${var}" method="post" id="form">
        <h2 class="text-center">Add new cargo</h2>
        <div class="form row">
            <label for="name" class="col-sm-2 col-form-label">Cargo name</label>
            <div class="col-sm-10">
                <input type="text" id="name" name="name" class="form-control"
                       placeholder="Cargo name"
                       required/>
            </div>
        </div>
        <div class="form row">
            <label for="weight" class="col-sm-2 col-form-label">Cargo weight</label>
            <div class="col-sm-10">
                <input type="text" id="weight" name="weight" class="form-control"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <input type="hidden" name="orderId" value="${order.id}" required/>
        <div class="text-center">
            <button type="submit">Add cargo</button>
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
