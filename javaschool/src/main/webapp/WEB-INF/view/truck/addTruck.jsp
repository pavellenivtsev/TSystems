<html>
<head>
    <%@ include file="../common/common.jsp" %>
    <title>Add truck</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/truck/add" var="var"/>
    <form action="${var}" method="post" id="form">
        <h2 class="text-center">Add new truck</h2>
        <c:if test="${(addTruckError!=null)}">
            <div class="text-center" id="warning-message">${addTruckError}</div>
        </c:if>
        <div class="form row">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration number</label>
            <div class="col-sm-10">
                <input type="text" id="registrationNumber" name="registrationNumber" autofocus="autofocus"
                       class="form-control"
                       pattern="[a-zA-Z]{2}[0-9]{5}"
                       placeholder="AA12345" required/>
            </div>
        </div>
        <div class="form row">
            <label for="driverShiftSize" class="col-sm-2 col-form-label">Driver shift size, hours</label>
            <div class="col-sm-10">
                <input type="text" id="driverShiftSize" name="driverShiftSize" class="form-control"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <div class="form row">
            <label for="weightCapacity" class="col-sm-2 col-form-label">Weight capacity, kg</label>
            <div class="col-sm-10">
                <input type="text" id="weightCapacity" name="weightCapacity" class="form-control"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <input type="hidden" name="officeId" value="${officeId}"/>
        <div class="text-center">
            <button class="btn btn-default" type="submit">Add truck</button>
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
