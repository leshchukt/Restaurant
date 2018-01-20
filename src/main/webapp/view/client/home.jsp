<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 16.01.2018
  Time: 17:20
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
    <title>Client Home Page</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="left-menu">
    <ul>
        <li><a href="/restaurant/client/home"><fmt:message key="client.home.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/client/menu"><fmt:message key="client.menu.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/client/order"><fmt:message key="client.order.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/client/bills"><fmt:message key="client.bills.page" bundle="${resourceBundle}"/></a></li>
        <li><a href="/restaurant/logout"><fmt:message key="logout.page" bundle="${resourceBundle}"/></a></li>
    </ul>
</div>
<div>
    <h3 style="text-align: center"><fmt:message key="client.home.information" bundle="${resourceBundle}"/></h3>
    <p><fmt:message key="client.home.name" bundle="${resourceBundle}"/>${sessionScope.user.nickname}</p><br/>
    <p><fmt:message key="client.home.birthday" bundle="${resourceBundle}"/>${sessionScope.user.birthDate}</p>
</div>

<c:if test="${not empty requestScope.message}">
    <div style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${resourceBundle}"/></p>
    </div>
</c:if>

<div class="information-box"
    <c:if test="${empty requestScope.orderMeals}">
        style="visibility: hidden"
    </c:if>>
    <table>
        <tr>
            <th><fmt:message key="meal.title" bundle="${resourceBundle}"/></th>
            <th><fmt:message key="meal.category" bundle="${resourceBundle}"/></th>
            <th><fmt:message key="meal.amount" bundle="${resourceBundle}"/></th>
            <th><fmt:message key="meal.price" bundle="${resourceBundle}"/></th>
        </tr>
        <c:forEach var="meal" items="${requestScope.orderMeals}">
            <tr>
                <td><c:out value="${meal.title}"/></td>
                <td><c:out value="${meal.category.name}"/></td>
                <td><c:out value="${meal.amount}"/></td>
                <td><ctg:price price="${meal.price}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="information-box"
        <c:if test="${empty requestScope.ordersHistory}">
            style="visibility: hidden;"
        </c:if>>
<h2><fmt:message key="client.home.history" bundle="${resourceBundle}"/></h2>
    <c:forEach var="order" items="${requestScope.ordersHistory}">
        <div class="information-box">
            <fmt:message key="order.time" bundle="${resourceBundle}"/>
            <c:out value=": ${order.timeOfOrder}"/>
            <br>
            <fmt:message key="order.done" bundle="${resourceBundle}"/>
            <c:choose>
                <c:when test="${order.accepted == 0}">
                    <fmt:message key="order.status.in.process" bundle="${resourceBundle}"/>
                </c:when>
                <c:when test="${order.accepted == 1}">
                    <fmt:message key="order.status.accepted" bundle="${resourceBundle}"/>
                </c:when>
                <c:when test="${order.accepted == -1}">
                    <fmt:message key="order.status.declined" bundle="${resourceBundle}"/>
                </c:when>
            </c:choose>
            <br>
            <form action="/restaurant/client/show_order" method="post" class="button-forms">
                <input type="hidden" name="order.id" value="${order.id}">
                <button class="button-green">
                    <fmt:message key="button.show" bundle="${resourceBundle}"/>
                </button>
            </form>
            <c:if test="${order.accepted == 0}">
                <form action="/restaurant/client/decline_order" method="post" class="button-forms">
                    <input hidden name="order.id" value="${order.id}">
                    <button class="button-red">
                        <fmt:message key="order.decline" bundle="${resourceBundle}"/>
                    </button>
                </form>
            </c:if>

        </div>
    </c:forEach>
</div>
</body>
</html>
