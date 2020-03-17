<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <title>Edit truck</title>
</head>
<body>
<c:url value="/truck/edit" var="var"/>
<form name="editTruck" action="${var}" method="post">
    <div class="form-element">
        <input type="hidden" name="id" id="id" value="${truck.id}"/>
    </div>
    <div class="form-element">
        <label for="registrationNumber">Registration number</label>
        <input type="text" name="registrationNumber" id="registrationNumber" value="${truck.registrationNumber}" required/>
    </div>
    <div class="form-element">
        <label for="driverShiftSize">Driver shift size</label>
        <input type="text" name="driverShiftSize" id="driverShiftSize" value="${truck.driverShiftSize}" required/>
    </div>
    <div class="form-element">
        <label for="weightCapacity">Weight capacity</label>
        <input type="text" name="weightCapacity" id="weightCapacity" value="${truck.weightCapacity}" required/>
    </div>
    <div class="form-element">
        <label for="status">Status</label>
        <input type="text" name="status" id="status" value="${truck.status}" required/>
    </div>
    <div class="form-element">
        <label for="locationCity">City</label>
        <input type="text" name="locationCity" id="locationCity" value="${truck.location.city}" required/>
    </div>
    <div class="form-element">
         <input type="submit" value="Edit truck">
    </div>
</form>
</body>
</html>