<%--
  Created by IntelliJ IDEA.
  User: leshchuk.t
  Date: 08.01.2018
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration form</title>
</head>
<body>
<form action="/RegistrationServlet" method="post">
    Nickname<<input type="text" name="nickname" /><br>
    Date of birth<<input type="date" name="birthDate"><br>
    Email<input type="text" name="email"/><br>
    Password<input type="text" name="password">
    <input type="submit" value="Sign up">
</form>
</body>
</html>
