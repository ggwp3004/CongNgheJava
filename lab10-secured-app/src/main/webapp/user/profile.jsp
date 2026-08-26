<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Hồ sơ cá nhân</title></head>
<body>
<jsp:include page="/views/header.jsp" />
<div class="container">
    <h2>Hồ sơ cá nhân</h2>
    <c:if test="${not empty error}"><p class="msg-error">${error}</p></c:if>
    <c:if test="${not empty success}"><p class="msg-success">${success}</p></c:if>

    <h3>Thông tin cá nhân</h3>
    <form method="post" action="${pageContext.request.contextPath}/user/profile">
        <input type="hidden" name="formType" value="updateInfo">
        <label>Email</label>
        <input type="email" value="${sessionScope.currentUser.email}" readonly>
        <label>Họ tên</label>
        <input type="text" name="fullName" value="${sessionScope.currentUser.fullName}" required>
        <button type="submit">Cập nhật</button>
    </form>

    <h3>Đổi mật khẩu</h3>
    <form method="post" action="${pageContext.request.contextPath}/user/profile">
        <input type="hidden" name="formType" value="changePassword">
        <label>Mật khẩu cũ</label>
        <input type="password" name="oldPassword" required>
        <label>Mật khẩu mới</label>
        <input type="password" name="newPassword" required>
        <label>Xác nhận mật khẩu mới</label>
        <input type="password" name="confirmPassword" required>
        <button type="submit">Đổi mật khẩu</button>
    </form>
</div>
<jsp:include page="/views/footer.jsp" />
</body>
</html>
