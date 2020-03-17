<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add order</title>
</head>
<body>
<c:url value="/order/add" var="var"/>
<form name="addTruck" action="${var}" method="post">
    <div class="form-element">
        <label for="orderUniqueNumber">orderUniqueNumber</label>
        <input type="text" name="uniqueNumber"  id="orderUniqueNumber" required/>
    </div>
    <div class="form-element">
        <label for="cargoName">cargoName</label>
        <input type="text" name="cargoName"  id="cargoName" required/>
    </div>
    <div class="form-element">
        <label for="cargoWeight">cargoWeight</label>
        <input type="text" name="cargoWeight" id="cargoWeight" required/>
    </div>
    <div class="form-element">
        <label for="locationFromCity">locationFromCity</label>
        <input type="text" name="locationFromCity" id="locationFromCity" required/>
    </div>
    <div class="form-element">
        <label for="locationToCity">locationToCity</label>
        <input type="text" name="locationToCity" id="locationToCity" required/>
    </div>
    <div class="form-element">
        <input type="submit" value="Create userOrder">
    </div>
</form>
</body>
</html>
