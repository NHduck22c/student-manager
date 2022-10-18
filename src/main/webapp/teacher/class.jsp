<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page import="com.example.studentmanager.model.ClassInfo" %>
<%@ page import="com.example.studentmanager.service.StudentService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../resources/css/jquery-ui-1.10.4.custom.min.css">
    <script src="../resources/js/jquery-1.10.2.js"></script>
    <script src="../resources/js/jquery-ui-1.10.4.custom.min.js"></script>
    <title>Trang chủ</title>
    <link href="../resources/css/default.css" rel="stylesheet"/>
</head>
<body>
<%
    Teacher teacher = null;
    ArrayList<ClassInfo> classInfos = new ArrayList<>();
    int sumIndex = 0;
    try {
        teacher = (Teacher) session.getAttribute("info");
        classInfos = (ArrayList<ClassInfo>) session.getAttribute("classDetail");
        sumIndex = (int) session.getAttribute("sumIndex");
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
                <li><a href="detail.jsp">Thông tin cá nhân</a></li>
                <li><a href="../student_detail">Quản lý sinh viên</a></li>
                <li class="current_page_item"><a href="../class_detail">Quản lý lớp học</a></li>
                <li><a href="../course_detail">Quản lý khóa học</a></li>
                <li><a onclick="return confirm('Xác nhận thoát?');" href="../exit">Đăng xuất</a></li>
            </ul>
        </div>
    </div>
    <div id="main">
        <div class="top">
            <h2>Quản lý lớp học</h2>
            <hr/>
            <button class="btn-add">Thêm lớp học</button>
            <div class="find">
                <form action="../class_detail" method="post">
                    <input id="find-text" type="text" name="key" placeholder="Nhập tên lớp để tìm kiếm">
                    <input class="find-btn" type="submit" value="Tìm kiếm">
                </form>
            </div>
        </div>
        <div class="table">
            <table id="table" width="100%">
                <tr>
                    <th height="35">Mã lớp</th>
                    <th>Tên lớp</th>
                    <th>Số sinh viên</th>
                    <th>Thao tác</th>
                </tr>
                <%
                    StudentService studentService = new StudentService();
                    for (ClassInfo classInfo : classInfos) {
                        int totalStudents = studentService.getTotalStudentsInClass(classInfo.getId());
                %>
                <tr>
                    <form method="post" action="../update_class">
                        <td height="35"><%=classInfo.getId()%>
                        </td>
                        <td><input value="<%=classInfo.getClassName()%>" name="class_name" class="table-input"></td>
                        <td><%=totalStudents%>
                        </td>
                        <input value="<%=classInfo.getId()%>" name="class_id" type="hidden">
                        <td>
                            <a
                                    class="btn-delete"
                                    href=<%="'../class_student_detail?id=" + classInfo.getId() + "'"%>
                            >Xem</a>
                            <input type="submit" class="update-btn" value="Cập nhật">&nbsp;
                            <a
                                    class="btn-delete"
                                    onclick="return confirm('Bạn có chắc muốn xóa lớp này?');"
                                    href=<%="'../delete_class?id=" + classInfo.getId() + "'"%>
                            >Xóa</a>
                        </td>
                    </form>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <%
            if (sumIndex > 1) {
        %>
        <div id="index">
            <a href="../class_detail?index=1">Trang đầu</a>
            <%
                for (int i = 1; i <= sumIndex; i++) {
            %>
            <a href="../class_detail?index=<%=i%>"><%=i%>
            </a>
            <%
                }
            %>
            <a href="../class_detail?index=<%=sumIndex%>">Trang cuối</a>
        </div>
        <%
            }
        %>
    </div>
</div>

<div id="add-dialog" title="Thêm lớp học">
    <form id="add-form" method="post">
        Tên lớp:<br><input name="class_name" type="text" class="form-input"><br>
        <hr>
        <input style="float: right" type="submit" value="Hủy" onclick="function x() {
          $('#add-dialog').dialog('close');
        }">
        <input style="float: right; margin-right: 25px" type="submit" value="Xác nhận"
               onclick="this.form.action='../add_class'">
    </form>
</div>

<style>
    .ui-dialog-titlebar-close {
        display: none
    }

    .form-input {
        width: 100%;
    }
</style>

<script>
    $('#add-dialog').dialog({
        width: 600,
        autoOpen: false,
        draggable: false,
        modal: true,
        resizable: false
    });
    $('.btn-add').click(function () {
        $('#add-dialog').dialog('open');
    });
</script>

</body>
</html>

