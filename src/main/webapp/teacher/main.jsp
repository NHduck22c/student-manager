<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page import="com.example.studentmanager.model.Student" %>
<%@ page import="com.example.studentmanager.model.ClassInfo" %>
<%@ page import="com.example.studentmanager.service.ClassService" %>
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
    ArrayList<Student> students = new ArrayList<>();
    int sumIndex = 0;

    try {
        teacher = (Teacher) session.getAttribute("info");
        students = (ArrayList<Student>) session.getAttribute("studentDetail");
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
                <li class="current_page_item"><a href="../student_detail">Quản lý sinh viên</a></li>
                <li><a href="../class_detail">Quản lý lớp học</a></li>
                <li><a href="../course_detail">Quản lý khóa học</a></li>
                <li><a onclick="return confirm('Xác nhận thoát?');" href="../exit">Đăng xuất</a></li>
            </ul>
        </div>
    </div>
    <div id="main">
        <div class="top">
            <h2>Quản lý sinh viên</h2>
            <hr/>
            <button class="btn-add">Thêm sinh viên</button>
            <form method="get" action="../export_student_csv" class="export-form">
                <input type="submit" value="Xuất file CSV">&nbsp;
            </form>
            <div class="find">
                <form action="../student_detail" method="post">
                    <input id="find-text" type="text" name="key" placeholder="Nhập mã hoặc tên sinh viên để tìm kiếm">
                    <input class="find-btn" type="submit" value="Tìm kiếm">
                </form>
            </div>
        </div>
        <div class="table">
            <table id="table" width="100%">
                <tr>
                    <th height="35">Mã sinh viên</th>
                    <th>Tên</th>
                    <th>Giới tính</th>
                    <th>Ngày nhập học</th>
                    <th>Chuyên ngành</th>
                    <th>Lớp</th>
                    <th>Thao tác</th>
                </tr>
                <%
                    for (Student stu : students) {
                %>
                <tr>
                    <form method="post" action="../update_student">
                        <td height="35"><%=stu.getId()%>
                        </td>
                        <td><input value="<%=stu.getName()%>" name="student_name" class="table-input"></td>
                        <td><input value="<%=stu.getSex()%>" name="student_sex" class="table-input"></td>
                        <td><%=stu.getSchool_date()%>
                        </td>
                        <td><input value="<%=stu.getMajor()%>" name="student_major" class="table-input"
                                   style="width: 110px">
                        </td>
                        <td>
                            <select type="text" name="student_class_id" class="table-input">
                                <%
                                    ClassService classService = new ClassService();
                                    ArrayList<ClassInfo> classInfos = classService.getOnePage(1, 1000);
                                    for (ClassInfo classInfo : classInfos) {
                                %>
                                <option value=<%=classInfo.getId()%> <%= (stu.getClassInfo() != null && classInfo.getId() == stu.getClassInfo().getId()) ? "selected" : "" %>><%= classInfo.getClassName() %>
                                </option>

                                <%
                                    }
                                %>
                            </select><br>
                        </td>
                        <input value="<%=stu.getId()%>" name="student_id" type="hidden">
                        <td>
                            <input type="submit" class="update-btn" value="Cập nhật">&nbsp;
                            <a
                                    class="btn-delete"
                                    onclick="return confirm('Bạn có chắc muốn xóa sinh viên này?');"
                                    href=<%="'../delete_student?id=" + stu.getId() + "'"%>
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
            <a href="../student_detail?index=1">Trang đầu</a>
            <%
                for (int i = 1; i <= sumIndex; i++) {
            %>
            <a href="../student_detail?index=<%=i%>"><%=i%>
            </a>
            <%
                }
            %>
            <a href="../student_detail?index=<%=sumIndex%>">Trang cuối</a>
        </div>
        <%
            }
        %>
    </div>
</div>

<div id="add-dialog" title="Thêm sinh viên">
    <form id="add-form" method="post">
        Mã sinh viên:<br><input name="id" type="text" class="form-input"><br>
        Tên:<br><input name="name" type="text" class="form-input"><br>
        Giới tính:<br><input name="sex" type="text" class="form-input"><br>
        Chuyên ngành:<br><input name="major" type="text" class="form-input"><br>
        Ngày nhập học:<br><input name="school_date" type="date" style="width: 190px" class="form-input"><br>
        Lớp: <br>
        <select type="text" name="class_id">
            <%
                ClassService classService = new ClassService();
                ArrayList<ClassInfo> classInfos = classService.getOnePage(1, 1000);
                for (ClassInfo classInfo : classInfos) {
            %>
            <option value=<%=classInfo.getId()%>><%= classInfo.getClassName() %>
            </option>
            <%
                }
            %>
        </select><br>
        <hr>
        <input style="float: right" type="submit" value="Hủy" onclick="function x() {
          $('#add-dialog').dialog('close');
        }">
        <input style="float: right; margin-right: 25px" type="submit" value="Xác nhận"
               onclick="this.form.action='../add_student'">
    </form>
</div>

<style>
    .ui-dialog-titlebar-close {
        display: none
    }

    .form-input {
        width: 100%;
    }

    .export-form {
        float: left;
        margin-top: 29px;
        margin-left: 2px;
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

