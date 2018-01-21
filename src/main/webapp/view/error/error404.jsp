<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 15.01.2018
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="message" var="resourceBundle"/>

<html>
<head>
    <title>Error 404</title>
</head>
<body>
<div style="text-align: center">
    <p style="font-size: 32px"><fmt:message key="error.404" bundle="${resourceBundle}"/></p>
</div>
</body>
</html>
