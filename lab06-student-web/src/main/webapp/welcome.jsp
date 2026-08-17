<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trang quan tri</title>
</head>
<body>
<h2>Xin chao, ${sessionScope.username}</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/students">Quan ly sinh vien</a></li>
    <li><a href="${pageContext.request.contextPath}/logout">Dang xuat</a></li>
</ul>
</body>
</html>
