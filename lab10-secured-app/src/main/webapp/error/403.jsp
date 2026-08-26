<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>403 - Không có quyền truy cập</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head>
<body>
<div class="container" style="text-align:center;">
    <h1>403</h1>
    <h2>Bạn không có quyền truy cập trang này</h2>
    <p>Tài khoản của bạn không đủ quyền hạn để xem nội dung này.</p>
    <a href="${pageContext.request.contextPath}/dashboard.jsp"><button>Quay về trang chủ</button></a>
</div>
</body>
</html>
