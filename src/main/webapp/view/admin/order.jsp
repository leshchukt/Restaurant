<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 16.01.2018
  Time: 17:16
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
    <title><fmt:message key="admin.header.order" bundle="${resourceBundle}"/></title>
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

<div class="information-box">
    <h2><fmt:message key="admin.client.information" bundle="${resourceBundle}"/></h2>
    <p2><fmt:message key="client.home.name" bundle="${resourceBundle}"/>${sessionScope.currentOrder.client.nickname}</p2>
    <br>
    <p2><fmt:message key="client.home.birthday" bundle="${resourceBundle}"/>
        <ct:date date="${sessionScope.currentOrder.client.birthDate}"/></p2>
</div>

<c:forEach items="${sessionScope.currentOrder.menu}" var="meal">
    <div class="information-box">
        <h3><fmt:message key="meal.title" bundle="${resourceBundle}"/>${meal.title}</h3>
        <p><fmt:message key="meal.category" bundle="${resourceBundle}"/>${meal.category.name}</p>
        <p><fmt:message key="meal.amount" bundle="${resourceBundle}"/>${meal.amount}</p>
        <p><fmt:message key="meal.price" bundle="${resourceBundle}"/><ct:price price="${meal.price}"/><br>
            <fmt:message key="meal.weight" bundle="${resourceBundle}"/><ct:weight weight="${meal.weight}"/></p>
    </div>
</c:forEach>
<div class="information-box">
    <form action="/restaurant/admin/accept_order" class="button-forms">
        <button class="button-green"><fmt:message key="order.accept" bundle="${resourceBundle}"/></button>
    </form>
    <form action="/restaurant/admin/decline_order" class = "button-forms">
        <button class="button-red"><fmt:message key="order.decline" bundle="${resourceBundle}" /></button>
    </form>
</div>
</body>
</html>
