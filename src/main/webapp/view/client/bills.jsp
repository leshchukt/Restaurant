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
    <title><fmt:message key="client.bills.page" bundle="${resourceBundle}"/></title>
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

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${resourceBundle}"/></p>
    </div>
</c:if>

<c:forEach var="bill" items="${requestScope.userBills}" >
    <div class="information-box">
        <h2><fmt:message key="bill.order" bundle="${resourceBundle}"/>
            <ct:dateTime dateTime="${bill.order.timeOfOrder}"/></h2>
        <p><fmt:message key="bill.price" bundle="${resourceBundle}"/><ct:price price="${bill.price}"/></p>
        <p><fmt:message key="bill.admin" bundle="${resourceBundle}"/>${bill.admin.nickname}</p>
        <form action="/restaurant/client/show_order" method="post" class="button-forms">
            <input type="hidden" name="order.id" value="${bill.order.id}">
            <input type="hidden" name="query" value="get.order.items">
            <button class="button-green">
                <fmt:message key="button.show" bundle="${resourceBundle}"/>
            </button>
        </form>
        <c:if test="${empty bill.payment_datetime}">
            <form action="/restaurant/client/pay" method="post" class="button-forms">
                <input type="hidden" name="query" value="pay.bill">
                <input hidden name="bill.id" value="${bill.idOrder}">
                <button class="button-green">
                    <fmt:message key="bill.pay" bundle="${resourceBundle}"/>
                </button>
            </form>
        </c:if>
    </div>
</c:forEach>
</body>
</html>
