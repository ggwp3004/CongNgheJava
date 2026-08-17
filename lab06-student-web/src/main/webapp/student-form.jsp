<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Them sinh vien</title>
</head>
<body>
<h2>Them sinh vien</h2>
<form action="${pageContext.request.contextPath}/students" method="post">
    <label>Ma sinh vien:</label><br>
    <input type="text" name="id"><br><br>
    <label>Ho ten:</label><br>
    <input type="text" name="name"><br><br>
    <label>Lop:</label><br>
    <input type="text" name="className"><br><br>
    <label>Email:</label><br>
    <input type="email" name="email"><br><br>
    <button type="submit">Luu sinh vien</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/students">Quay lai danh sach</a>
</body>
</html>
