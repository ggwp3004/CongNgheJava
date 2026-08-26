<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Dashboard - Lab 10</title></head>
<body>
<jsp:include page="/views/header.jsp" />
<div class="container">
    <c:choose>
        <c:when test="${empty sessionScope.currentUser}">
            <p>Bạn chưa đăng nhập. <a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập ngay</a></p>
        </c:when>
        <c:otherwise>
            <h2>Trang chủ (${sessionScope.currentUser.role})</h2>
            <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                <p>Bạn có toàn quyền quản trị: quản lý tài khoản, sản phẩm và toàn bộ dữ liệu hệ thống.</p>
            </c:if>
            <c:if test="${sessionScope.currentUser.role == 'STAFF'}">
                <p>Bạn có thể thực hiện nghiệp vụ: thêm/sửa sản phẩm, không quản lý được tài khoản admin.</p>
            </c:if>
            <c:if test="${sessionScope.currentUser.role == 'USER'}">
                <p>Bạn có thể xem/cập nhật hồ sơ cá nhân và đổi mật khẩu.</p>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/views/footer.jsp" />
</body>
</html>
