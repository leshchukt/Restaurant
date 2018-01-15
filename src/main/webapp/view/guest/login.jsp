<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 04.01.2018
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<form name="log" method="post" action="">
    Email <input type="text" name="email" /><br>
    Password <input type="password" name="password"><br>
    <input type="radio" name="language" value="UA">UA
    <input type="radio" name="language" value="EN">EN
    <br>
    <input type="submit" value="Log in">
</form>
<a href="/view/guest/registration.jsp">Sign up</a>
</body>
</html>
