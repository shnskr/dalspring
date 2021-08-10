<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <h1>/sample/all page</h1>

    <sec:authorize access="isAnonymous()">
        <a href="/customLogin">로그인</a>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <a href="/customLogout">로그아웃</a>
    </sec:authorize>
</body>
</html>
