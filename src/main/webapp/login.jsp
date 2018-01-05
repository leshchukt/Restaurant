<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 04.01.2018
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
hello
<form name="log" method="post" action="LoginServlet">
    Login <input type="text" name="login">
    <br>
    Password <input type="text" name="password">
    <br>
    <input type="submit" value="Log in">
</form>
<a href="/LoginServlet">Your menu</a>
</body>
</html>
