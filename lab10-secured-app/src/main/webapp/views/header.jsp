<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
<header class="topbar">
    <div><strong>Lab 10 - Ứng dụng quản lý</strong></div>
    <div>
        <c:if test="${not empty sessionScope.currentUser}">
            Xin chào, ${sessionScope.currentUser.fullName} (${sessionScope.currentUser.role})
            &nbsp;|&nbsp;
            <a href="${pageContext.request.contextPath}/auth?action=logout">Đăng xuất</a>
        </c:if>
    </div>
</header>
<nav class="menu">
    <a href="${pageContext.request.contextPath}/dashboard.jsp">Trang chủ</a>
    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/admin/users">Quản lý tài khoản</a>
        <a href="${pageContext.request.contextPath}/staff/products">Quản lý sản phẩm</a>
    </c:if>
    <c:if test="${sessionScope.currentUser.role == 'STAFF'}">
        <a href="${pageContext.request.contextPath}/staff/products">Quản lý sản phẩm</a>
    </c:if>
    <c:if test="${not empty sessionScope.currentUser}">
        <a href="${pageContext.request.contextPath}/user/profile">Hồ sơ cá nhân</a>
    </c:if>
</nav>
