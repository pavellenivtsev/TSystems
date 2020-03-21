<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="../common/managerToolBar.jsp" %>
    <title>Add truck</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>

<div class="container">
    <c:url value="/truck/add" var="var"/>
    <form  action="${var}" method="post">
        <h2 class="text-center">Add new truck</h2>
        <div class="form row">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration number</label>
            <div class="col-sm-10">
                <input type="text" id="registrationNumber" name="registrationNumber" class="form-control" required/>
            </div>
        </div>
        <div class="form row">
            <label for="driverShiftSize" class="col-sm-2 col-form-label">Driver shift size</label>
            <div class="col-sm-10">
                <input type="text" id="driverShiftSize" name="driverShiftSize" class="form-control" required/>
            </div>
        </div>
        <div class="form row">
            <label for="weightCapacity" class="col-sm-2 col-form-label">Weight capacity</label>
            <div class="col-sm-10">
                <input type="text" id="weightCapacity" name="weightCapacity" class="form-control" required/>
            </div>
        </div>
        <div class="form row">
            <label for="locationCity" class="col-sm-2 col-form-label">City</label>
            <div class="col-sm-10">
                <input type="text" id="locationCity" name="locationCity" class="form-control" required/>
            </div>
        </div>
        <div class="text-center">
            <button type="submit">Add truck</button>
        </div>
    </form>
</div>
</body>
</html>
