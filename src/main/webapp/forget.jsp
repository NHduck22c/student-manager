<%@ page import="com.example.studentmanager.service.TeacherService" %>
<%@ page import="com.example.studentmanager.service.StudentService" %>
<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page import="com.example.studentmanager.model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quên mật khẩu</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link href="resources/css/forget.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%
    String id = request.getParameter("user");
    TeacherService teacherService = new TeacherService();
    StudentService studentService = new StudentService();
    Teacher teacher = null;
    Student student = null;

    try {
        teacher = teacherService.findWithId(id);
        student = studentService.findWithId(id);
    } catch (Exception e) {
        out.print(e);
    }

    if (teacher != null) {
        response.sendRedirect("teacher/resetPassword.jsp?id=" + teacher.getId());
    } else if (student != null) {
        response.sendRedirect("student/resetPassword.jsp?id=" + student.getId());
    } else {
%>
<script>alert("Tên đăng nhập không tồn tại!");
window.location.href = 'forget.jsp';</script>
<%
    }
%>
<h1 style="margin: 50px 80px; color: darkgray; font-family: cursive;">Hệ thống quản lý sinh viên</h1>
<div class="main">
    <form role="form" action="" method="post">
        <div class="form-group" align="center">
            <input class="form-control" type="text" name="user" placeholder="Tên người dùng"><br>
            <input type="submit" class="btn btn-success" value="Tiếp">
            <input type="button" class="btn btn-info" value="Hủy" style="margin-left: 20px"
                   onclick="window.location.href='login.jsp'">
        </div>
    </form>
</div>
<script src="resources/js/jquery-3.2.1.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>
