<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
    <link href="../resources/css/default.css" rel="stylesheet"/>
</head>
<body>
<%
    Teacher teacher = null;
    try {
        teacher = (Teacher) session.getAttribute("info");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("../login.jsp");
        return;
    }

    if (teacher == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<div id="page" class="container">
    <div id="header">
        <div id="logo">
            <img src="../<%=teacher.getAvatar_url()%>"/>
            <h1><%=teacher.getId()%>
            </h1>
        </div>
        <div id="menu">
            <ul>
                <li class="current_page_item"><a href="detail.jsp">Thông tin cá nhân</a></li>
                <li><a href="../student_detail">Quản lý sinh viên</a></li>
                <li><a href="../class_detail">Quản lý lớp học</a></li>
                <li><a href="../course_detail">Quản lý khóa học</a></li>
                <li><a onclick="return confirm('Xác nhận thoát?');" href="../exit">Đăng xuất</a></li>
            </ul>
        </div>
    </div>
    <div id="main">
        <div class="top">
            <h2>Thông tin cá nhân</h2>
            <hr/>
        </div>
        <div class="info">
            <img src="../<%=teacher.getAvatar_url()%>" class="detail-img"><br>
            <form action="../upload_image" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%=teacher.getId()%>">
                <input type="file" name="img">
                <input type="submit" value="Gửi">
            </form>
            <form method="post" action="../update_teacher" class="detail-form">
                <input name="uid" value="<%=teacher.getId()%>" type="hidden">
                Tên: <br> <input type="text" name="name" value="<%=teacher.getName()%>" class="detail-input"><br>
                Giới tính: <br> <input type="text" name="sex" value="<%=teacher.getSex()%>" class="detail-input"><br>
                Email: <br> <input type="text" name="email" value="<%=teacher.getEmail()%>" class="detail-input"><br>
                Mật khẩu: <br><input type="text" name="password" value="<%=teacher.getPassword()%>"
                                     class="detail-input"><br>
                <input type="submit" value="Lưu" style="margin-top: 20px">
            </form>
        </div>
    </div>
</div>
</body>
</html>

