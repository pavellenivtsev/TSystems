<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit order</title>
</head>
<body>
<c:url value="/order/edit" var="var"/>
<form name="editOrder" action="${var}" method="post">
    <div class="form-element">
        <input type="hidden" name="id" id="id" value="${order.id}"/>
    </div>
    <div class="form-element">
        <label for="uniqueNumber">uniqueNumber</label>
        <input type="text" name="uniqueNumber" id="uniqueNumber" value="${order.uniqueNumber}" required/>
    </div>
    <div class="form-element">
        <label for="cargoName">cargoName</label>
        <input type="text" name="cargoName" id="cargoName" value="${order.cargoList.get(0).name}" required/>
    </div>
    <div class="form-element">
        <label for="cargoWeight">cargoWeight</label>
        <input type="text" name="cargoWeight" id="cargoWeight" value="${order.cargoList.get(0).weight}" required/>
    </div>
    <div class="form-element">
        <label for="locationFromCity">place of departure</label>
        <input type="text" name="locationFromCity" id="locationFromCity" value="${order.cargoList.get(0).waypointList.get(0).location.city}" required/>
    </div>
    <div class="form-element">
        <label for="locationToCity">delivery place</label>
        <input type="text" name="locationToCity" id="locationToCity" value="${order.cargoList.get(0).waypointList.get(1).location.city}" required/>
    </div>
    <div class="form-element">
        <input type="submit" value="Edit truck">
    </div>
</form>
</body>
</html>
