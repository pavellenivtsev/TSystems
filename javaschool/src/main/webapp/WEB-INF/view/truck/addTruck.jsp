<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add truck</title>
</head>
<body>
<c:url value="/truck/add" var="var"/>
<form name="addTruck" action="${var}" method="post">
    <div class="form-element">
        <label for="registrationNumber">Registration number</label>
        <input type="text" name="registrationNumber" id="registrationNumber" required/>
    </div>
    <div class="form-element">
        <label for="driverShiftSize">Driver shift size</label>
        <input type="text" name="driverShiftSize" id="driverShiftSize" required/>
    </div>
    <div class="form-element">
        <label for="weightCapacity">Weight capacity</label>
        <input type="text" name="weightCapacity" id="weightCapacity" required/>
    </div>
    <div class="form-element">
        <label for="status">Status</label>
        <input type="text" name="status" id="status" required/>
    </div>
    <div class="form-element">
        <label for="locationCity">City</label>
        <input type="text" name="locationCity" id="locationCity" required/>
    </div>
    <div class="form-element">
        <input type="submit" value="Add truck">
    </div>
</form>
</body>
</html>
