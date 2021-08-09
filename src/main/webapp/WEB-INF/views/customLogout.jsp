<%--
  Created by IntelliJ IDEA.
  User: DAL
  Date: 2021-07-28
  Time: 오후 8:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Logout Page</h1>

    <form method="post" action="/customLogout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button>로그아웃</button>
    </form>
</body>
</html>
