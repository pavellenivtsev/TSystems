<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit driver</title>
</head>
<body>
<c:url value="/manager/driver/edit" var="var"/>
<form name="editTruck" action="${var}" method="post">
    <div class="form-element">
        <input type="hidden" name="id" id="id" value="${driver.id}"/>
    </div>
    <div class="form-element">
        <label for="personalNumber">Personal number</label>
        <input type="text" name="personalNumber" id="personalNumber" value="${driver.personalNumber}" required/>
    </div>
    <div class="form-element">
        <input type="submit" value="Edit driver">
    </div>
</form>
</body>
</html>
