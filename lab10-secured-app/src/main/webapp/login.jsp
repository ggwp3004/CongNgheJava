<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Đăng nhập - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
<div class="login-box">
    <h2>Đăng nhập hệ thống</h2>
    <c:if test="${not empty error}">
        <p class="msg-error">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/auth">
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Mật khẩu</label>
        <input type="password" name="password" required>
        <button type="submit">Đăng nhập</button>
    </form>
    <p style="margin-top:16px;font-size:13px;color:#666">
        Tài khoản mẫu: admin@eaut.edu.vn / admin123, staff@eaut.edu.vn / staff123, user@eaut.edu.vn / user123
    </p>
</div>
</body>
</html>
