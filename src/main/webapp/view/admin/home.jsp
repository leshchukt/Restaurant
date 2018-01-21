<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 06.01.2018
  Time: 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom_tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="message" var="resourceBundle"/>
<html>
<head>
    <title><fmt:message key="admin.header.active.orders" bundle="${resourceBundle}"/></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="left-menu">
    <ul>
        <li><a href="#"><fmt:message key="admin.header.active.orders" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/logout"><fmt:message key="logout.page" bundle="${resourceBundle}"/></a></li>
    </ul>
</div>

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${resourceBundle}"/></p>
    </div>
</c:if>

<c:forEach var="order" items="${requestScope.activeOrders}">
    <div class="information-box">
        <fmt:message key="order.time" bundle="${resourceBundle}"/>
        <ct:dateTime dateTime="${order.timeOfOrder}"/>
        <form action="/restaurant/admin/order" method="post">
            <input type="hidden" name="order.id" value="${order.id}">
            <button class="button-green">
                <fmt:message key="admin.goto.order" bundle="${resourceBundle}"/>
            </button>
        </form>
    </div>
</c:forEach>
</body>
</html>
