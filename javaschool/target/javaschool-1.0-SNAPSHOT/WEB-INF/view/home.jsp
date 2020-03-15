<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Home page</title>
    </head>
    <body>
        <c:url value="/truck/all" var="var1"/>
        <a href="${var1}">drivers</a>
        <br>
        <br>
        <br>
        <c:url value="/truck/all" var="var2"/>
        <a href="${var2}">trucks</a>
        <br>
        <br>
        <br>
        <c:url value="/registration" var="var3"/>
        <a href="${var3}">registration</a>
        <br>
        <br>
        <br>
        <c:url value="/truck/all" var="var4"/>
        <a href="${var4}">trucks</a>
    </body>
</html>
