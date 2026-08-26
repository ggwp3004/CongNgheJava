<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Quản lý tài khoản</title></head>
<body>
<jsp:include page="/views/header.jsp" />
<div class="container">
    <h2>Quản lý tài khoản người dùng</h2>

    <c:if test="${not empty error}"><p class="msg-error">${error}</p></c:if>
    <c:if test="${not empty success}"><p class="msg-success">${success}</p></c:if>

    <form method="get" action="${pageContext.request.contextPath}/admin/users" style="margin-bottom:16px;">
        <input type="text" name="q" placeholder="Tìm theo email" value="${keyword}" style="width:250px;display:inline-block;">
        <button type="submit">Tìm kiếm</button>
    </form>

    <h3>${empty product ? 'Thêm tài khoản mới' : 'Sửa tài khoản'}</h3>
    <form method="post" action="${pageContext.request.contextPath}/admin/users">
        <input type="hidden" name="id" value="${editUser.id}">
        <label>Email</label>
        <input type="email" name="email" value="${editUser.email}" ${not empty editUser ? 'readonly' : ''} required>
        <label>Họ tên</label>
        <input type="text" name="fullName" value="${editUser.fullName}" required>
        <label>Mật khẩu ${not empty editUser ? '(để trống nếu không đổi)' : ''}</label>
        <input type="password" name="password" ${empty editUser ? 'required' : ''}>
        <label>Vai trò</label>
        <select name="role">
            <c:forEach var="r" items="${roles}">
                <option value="${r}" ${editUser.role == r ? 'selected' : ''}>${r}</option>
            </c:forEach>
        </select>
        <button type="submit">Lưu</button>
    </form>

    <h3>Danh sách tài khoản</h3>
    <table>
        <tr><th>ID</th><th>Email</th><th>Họ tên</th><th>Vai trò</th><th>Trạng thái</th><th>Hành động</th></tr>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.email}</td>
                <td>${u.fullName}</td>
                <td>${u.role}</td>
                <td>${u.active ? 'Đang hoạt động' : 'Đã khóa'}</td>
                <td>
                    <a class="btn-link" href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}">Sửa</a>
                    &nbsp;|&nbsp;
                    <a class="btn-link" href="${pageContext.request.contextPath}/admin/users?action=toggle&id=${u.id}">
                        ${u.active ? 'Khóa' : 'Mở khóa'}
                    </a>
                    &nbsp;|&nbsp;
                    <a class="btn-link" href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.id}"
                       onclick="return confirm('Xóa tài khoản này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/views/footer.jsp" />
</body>
</html>
