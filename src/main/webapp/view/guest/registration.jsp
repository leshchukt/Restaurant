<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 08.01.2018
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="message" var="resourceBundle" />
<html>
<head>
    <title>Registration Form</title>
    <base href="${pageContext.request.contextPath}">
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login">
<form action="/restaurant/registration/register">
    <c:forEach items="${requestScope.registrationErrors}" var="error">
        <p style="color: red">
            <fmt:message key="${error}" bundle="${resourceBundle}"/>
        </p><br>
    </c:forEach>
    <input type="email"
           pattern="[A-Za-z0-9._]+@[a-z]+\.(com|net|ru)"
           title="<fmt:message key="email.description" bundle="${resourceBundle}"/>"
           placeholder="<fmt:message key="email" bundle="${resourceBundle}"/>"
           name="email" required/><br/>
    <input type="password"
           pattern=".{6,}"
           title="<fmt:message key="password.description" bundle="${resourceBundle}"/>"
           placeholder="<fmt:message key="password" bundle="${resourceBundle}"/>"
           name="password" required /><br/>
    <input type="password"
           pattern=".{6,}"
           title="<fmt:message key="password.description" bundle="${resourceBundle}"/>"
           placeholder="<fmt:message key="password.validation" bundle="${resourceBundle}"/>"
           name="repeatedPassword" required /><br/>
    <input type="text"
           pattern="([A-Z][a-z]{1,13} ?){1,5}"
           title="<fmt:message key="registration.name.description" bundle="${resourceBundle}"/> "
           placeholder="<fmt:message key="registration.name" bundle="${resourceBundle}"/> "
           name="nickname" required/><br/>
    <input type="date"
           title="<fmt:message key="registration.birth.date.description" bundle="${resourceBundle}"/>"
           placeholder="<fmt:message key="registration.birth.date" bundle="${resourceBundle}"/>"
           onfocus="(this.type='date')"
           name = "birthDate" required/><br/>
    <button class="login_button">
        <fmt:message key="registration.register" bundle="${resourceBundle}"/>
    </button>
    <p class="message">
        <fmt:message key="registration.have.account" bundle="${resourceBundle}"/>
        <a href="/restaurant/login_page">
            <fmt:message key="registration.login" bundle="${resourceBundle}"/>
        </a>
    </p>
</form>
<div class="language-box">
    <form action="/restaurant/change_locale" method="post">
        <input type="hidden" name="page" value="/view/guest/registration.jsp"/>
        <input id="ua" type="submit" name="language" value="UA"/>
        <input id="en" type="submit" name="language" value="EN"/>
    </form>
</div>
</body>
</html>
