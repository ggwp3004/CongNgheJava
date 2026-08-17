<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh sach sinh vien</title>
</head>
<body>
<h2>Danh sach sinh vien</h2>
<a href="${pageContext.request.contextPath}/student-form.jsp">Them sinh vien</a>
&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/logout">Dang xuat</a>
<br><br>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Ma SV</th>
        <th>Ho ten</th>
        <th>Lop</th>
        <th>Email</th>
    </tr>
    <c:forEach var="sv" items="${students}">
        <tr>
            <td>${sv.id}</td>
            <td>${sv.name}</td>
            <td>${sv.className}</td>
            <td>${sv.email}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
