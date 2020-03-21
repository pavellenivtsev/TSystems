<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <title>Edit truck</title>
</head>
<body>
    <div class="container">
<c:url value="/truck/edit" var="var"/>
<form action="${var}" method="post">
    <h2 class="text-center">Edit the truck</h2>
    <div class="form row">
        <input type="hidden" name="id" id="id"  value="${truck.id}" class="form-control"/>
    </div>
    <div class="form row">
        <label for="registrationNumber" class="col-sm-2 col-form-label">Registration number</label>
        <div class="col-sm-10">
            <input type="text" name="registrationNumber" id="registrationNumber" value="${truck.registrationNumber}" class="form-control" required/>
        </div>
    </div>
    <div class="form row">
        <label for="driverShiftSize" class="col-sm-2 col-form-label">Driver shift size</label>
        <div class="col-sm-10">
            <input type="text" name="driverShiftSize" id="driverShiftSize" value="${truck.driverShiftSize}" class="form-control" required/>
        </div>
    </div>
    <div class="form row">
        <label for="weightCapacity" class="col-sm-2 col-form-label">Weight capacity</label>
        <div class="col-sm-10">
            <input type="text" name="weightCapacity" id="weightCapacity" value="${truck.weightCapacity}" class="form-control" required/>
        </div>
    </div>
    <div class="form row">
        <label for="status" class="col-sm-2 col-form-label">Status</label>
        <div class="col-sm-10">
            <input type="text" name="status" id="status" value="${truck.status}" class="form-control" required/>
        </div>
    </div>
    <div class="form row">
        <label for="locationCity" class="col-sm-2 col-form-label">City</label>
        <div class="col-sm-10">
            <input type="text" name="locationCity" id="locationCity" value="${truck.location.city}" class="form-control" required/>
        </div>
    </div>
    <div class="text-center">
        <button type="submit">Edit truck</button>
    </div>
</form>
</div>
</body>
</html>
