<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 16.01.2018
  Time: 17:21
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
    <title><fmt:message key="client.menu.page" bundle="${resourceBundle}"/></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="left-menu">
    <ul>
        <li><a href="/restaurant/client/home"><fmt:message key="client.home.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/client/menu"><fmt:message key="client.menu.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/client/order"><fmt:message key="client.order.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/client/bills"><fmt:message key="client.bill.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/logout"><fmt:message key="client.logout.page" bundle="${resourceBundle}"/></a></li>
    </ul>
</div>

<div>
    <form action="/restaurant/client/menu">
        <div class="styled-select">
            <fmt:message key="menu.select.category" bundle="${resourceBundle}"/>
            <select name="category">
                <option hidden value="" disabled selected>${sessionScope.currentCategory.name}</option>
                <c:forEach items="${requestScope.categories}" var="category">
                    <option value="${category.id}"> ${category.name} </option>
                </c:forEach>
            </select>
            <button class="button-green"><fmt:message key="menu.search" bundle="${resourceBundle}"/></button>
        </div>
    </form>
</div>

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${resourceBundle}"/></p>
    </div>
</c:if>

<c:forEach items="${sessionScope.menu}" var="meal">
    <div class="information-box">
        <form action="/restaurant/client/add_meal" method="post">
            <input type="hidden" name="meal.id" value="${meal.id}">
            <h3><fmt:message key="meal.title" bundle="${resourceBundle}"/>${meal.title}</h3>
            <p><fmt:message key="meal.price" bundle="${resourceBundle}"/><ct:price price="${meal.price}"/><br/>
                <fmt:message key="meal.weight" bundle="${resourceBundle}"/><ct:weight weight="${meal.weight}"/>
            </p>
            <input type="number" min="1" max="20" name="amount" value="1"  style="height: 35px;">
            <button class="button-green"><fmt:message key="menu.order" bundle="${resourceBundle}" /></button>
        </form>
    </div>
</c:forEach>
</body>
</html>
