<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Quản lý sản phẩm</title></head>
<body>
<jsp:include page="/views/header.jsp" />
<div class="container">
    <h2>Quản lý sản phẩm</h2>
    <c:if test="${not empty error}"><p class="msg-error">${error}</p></c:if>

    <h3>${empty product ? 'Thêm sản phẩm mới' : 'Sửa sản phẩm'}</h3>
    <form method="post" action="${pageContext.request.contextPath}/staff/products">
        <input type="hidden" name="id" value="${product.id}">
        <label>Tên sản phẩm</label>
        <input type="text" name="name" value="${product.name}" required>
        <label>Giá</label>
        <input type="number" step="0.01" min="0" name="price" value="${product.price}" required>
        <label>Số lượng</label>
        <input type="number" min="0" name="quantity" value="${product.quantity}" required>
        <label>Mô tả</label>
        <textarea name="description" rows="3">${product.description}</textarea>
        <button type="submit">Lưu</button>
    </form>

    <h3>Danh sách sản phẩm</h3>
    <table>
        <tr><th>ID</th><th>Tên</th><th>Giá</th><th>Số lượng</th><th>Mô tả</th><th>Hành động</th></tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td>${p.quantity}</td>
                <td>${p.description}</td>
                <td>
                    <a class="btn-link" href="${pageContext.request.contextPath}/staff/products?action=edit&id=${p.id}">Sửa</a>
                    &nbsp;|&nbsp;
                    <a class="btn-link" href="${pageContext.request.contextPath}/staff/products?action=delete&id=${p.id}"
                       onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/views/footer.jsp" />
</body>
</html>
