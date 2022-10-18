<%@ page import="com.example.studentmanager.model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>main</title>
    <link rel="stylesheet" href="../resources/css/jquery-ui-1.10.4.custom.min.css">
    <script src="../resources/js/jquery-1.10.2.js"></script>
    <script src="../resources/js/jquery-ui-1.10.4.custom.min.js"></script>
    <link href="../resources/css/default.css" rel="stylesheet"/>
</head>
<body>
<%
    Student student = null;
    try {
        student = (Student) session.getAttribute("info");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("../login.jsp");
        return;
    }

    if (student == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<div id="page" class="container">
    <div id="header">
        <div id="logo">
            <img src="../<%=student.getAvatar_url()%>"/>
            <h1><%=student.getName()%>
            </h1>
        </div>
        <div id="menu">
            <ul>
                <li class="current_page_item"><a href="detail.jsp">Thông tin cá nhân</a></li>
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
            <img src="../<%=student.getAvatar_url()%>" class="detail-img"><br>
            <form action="../upload_image" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%=student.getId()%>">
                <input type="file" name="img">
                <input type="submit" value="Tải lên">
            </form>

            <table width="600" frame="box" align="center" style="margin-top: 30px;">
                <tr>
                    <td style="font-size: 25px;font-weight: bold">Mã sinh viên</td>
                    <td style="font-size: 25px;font-weight: bold"><%=student.getId()%>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 25px;font-weight: bold">Tên</td>
                    <td style="font-size: 25px;font-weight: bold"><%=student.getName()%>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 25px;font-weight: bold">Giới tính</td>
                    <td style="font-size: 25px;font-weight: bold"><%=student.getSex()%>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 25px;font-weight: bold">Chuyên ngành</td>
                    <td style="font-size: 25px;font-weight: bold"><%=student.getMajor()%>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 25px;font-weight: bold">Lớp học</td>
                    <td style="font-size: 25px;font-weight: bold"><%=student.getClassInfo() != null ? student.getClassInfo().getClassName() : ""%>
                    </td>
                </tr>
            </table>

            <button class="password-btn" style="margin-top: 30px; height: 40px">Thay đổi mật khẩu</button>
        </div>
    </div>
</div>

<div id="password-dialog" title="Thay đổi mật khẩu">
    <form id="password-form" method="post">
        <input type="hidden" name="id" value="<%=student.getId()%>">
        Mật khẩu:<br><input type="password" name="password"><br>
        <hr>
        <input style="float: right" type="submit" value="Hủy" onclick="function x() {
          $('#add-dialog').dialog('close');
        }">
        <input style="float: right; margin-right: 25px" type="submit" value="Lưu"
               onclick="this.form.action='../update_student_password'">
    </form>
</div>

<script>
    $('#password-dialog').dialog({
        width: 500,
        autoOpen: false,
        draggable: false,
        modal: true,
        resizable: false
    });
    $('.password-btn').click(function () {
        $('#password-dialog').dialog('open');
    });
</script>

<style>
    .ui-dialog-titlebar-close {
        display: none
    }
</style>
</body>
</html>

