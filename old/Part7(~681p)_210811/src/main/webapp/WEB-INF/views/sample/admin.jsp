<%--
  Created by IntelliJ IDEA.
  User: DAL
  Date: 2021-07-28
  Time: 오후 8:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>/sample/admin page</h1>

    <p>principal : <sec:authentication property="principal" /></p>
    <p>MemberVO : <sec:authentication property="principal.member" /></p>
    <p>사용자이름 : <sec:authentication property="principal.member.userName" /></p>
    <p>사용자아이디 : <sec:authentication property="principal.username" /></p>
    <p>사용자 권한 리스트 : <sec:authentication property="principal.member.authList" /></p>

    <a href="/customLogout">Logout</a>
</body>
</html>
