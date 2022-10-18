<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nhập mật khẩu mới</title>
    <link rel="stylesheet" href="../resources/css/bootstrap.min.css">
    <link href="../resources/css/forget.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<h1 style="margin: 50px 80px; color: darkgray; font-family: cursive;">Hệ thống quản lý sinh viên</h1>
<%
    String id = request.getParameter("id");
%>
<div class="main">
    <form role="form" action="../update_student_password" method="post">
        <div class="form-group" align="center">
            <input class="form-control" type="text" name="password" placeholder="Mật khẩu mới"><br>
            <input type="hidden" name="id" value="<%=id%>">
            <input type="submit" class="btn btn-success" value="Gửi">
            <input type="button" class="btn btn-info" value="Huỷ" style="margin-left: 20px"
                   onclick="window.location.href='../login.jsp'">
        </div>
    </form>
</div>
<script src="../resources/js/jquery-3.2.1.min.js"></script>
<script src="../resources/js/popper.min.js"></script>
<script src="../resources/js/bootstrap.min.js"></script>
</body>
</html>
