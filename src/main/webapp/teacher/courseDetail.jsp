<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page import="com.example.studentmanager.model.Student" %>
<%@ page import="com.example.studentmanager.model.CourseStudent" %>
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
    ArrayList<CourseStudent> courseStudents = new ArrayList<>();
    int courseDetailId = 1;
    try {
        teacher = (Teacher) session.getAttribute("info");
        courseDetailId = (int) session.getAttribute("courseDetailId");
        courseStudents = (ArrayList<CourseStudent>) session.getAttribute("courseStudentDetail");
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
                <li><a href="../class_detail">Quản lý lớp học</a></li>
                <li class="current_page_item"><a href="../course_detail">Quản lý khóa học</a></li>
                <li><a onclick="return confirm('Xác nhận thoát?');" href="../exit">Đăng xuất</a></li>
            </ul>
        </div>
    </div>
    <div id="main">
        <div class="top">
            <h2>Chi tiết khóa học</h2>
            <hr/>
            <button class="btn-add">Thêm sinh viên vào lớp</button>
<%--            <div class="find">--%>
<%--                <form action="../course_student_detail" method="post">--%>
<%--                    <input id="find-text" type="text" name="key" placeholder="Nhập mã hoặc tên sinh viên để tìm kiếm">--%>
<%--                    <input class="find-btn" type="submit" value="Tìm kiếm">--%>
<%--                </form>--%>
<%--            </div>--%>
        </div>
        <div class="table">
            <table id="table" width="100%" style="display: block; height: 100%; overflow-y: auto">
                <tr>
                    <th height="35">Mã sinh viên</th>
                    <th>Tên</th>
                    <th>Lớp</th>
                    <th>Điểm số</th>
                    <th>Thao tác</th>
                </tr>
                <%
                    for (CourseStudent courseStudent : courseStudents) {
                %>
                <tr>
                    <form method="post" action="../update_course_student">
                        <td height="35"><%=courseStudent.getStudent().getId()%>
                        </td>
                        <td><%=courseStudent.getStudent().getName()%>
                        </td>
                        <td><%=courseStudent.getStudent().getClassInfo() != null ? courseStudent.getStudent().getClassInfo().getClassName() : ""%>
                        </td>
                        <td><input value="<%=courseStudent.getScore()%>" name="course_student_score" class="table-input"
                                   style="width: 110px">
                        </td>
                        <input value="<%=courseDetailId%>" name="course_id" type="hidden">
                        <input value="<%=courseStudent.getStudent().getId()%>" name="student_id" type="hidden">
                        <td>
                            <input type="submit" class="update-btn" value="Cập nhật">&nbsp;
                            <a
                                    class="btn-delete"
                                    onclick="return confirm('Bạn có chắc muốn xóa sinh viên này khỏi lớp?');"
                                    href=<%="'../delete_course_student?course_id=" + courseDetailId + "&id=" + courseStudent.getId() + "'"%>
                            >Xóa</a>
                        </td>
                    </form>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>

<div id="add-dialog" title="Thêm sinh viên vào lớp">
    <form id="add-form" method="post">
        Sinh viên: <br><select type="text" name="student_id">
        <%
            StudentService studentService = new StudentService();
            ArrayList<Student> students = studentService.getOnePage(1, 1000);
            for (Student student : students) {
        %>
        <option value=<%=student.getId()%>><%= student.getName()%> | <%=student.getId()%>
        </option>
        <%
            }
        %>
    </select><br>
        Điểm số:<br><input name="score" type="number" class="form-input"><br>
        <input name="course_id" type="hidden" value=<%=courseDetailId%>>
        <hr>
        <input style="float: right" type="submit" value="Hủy" onclick="function x() {
          $('#add-dialog').dialog('close');
        }">
        <input style="float: right; margin-right: 25px" type="submit" value="Xác nhận"
               onclick="this.form.action='../add_course_student'">
    </form>
</div>

<style>
    .ui-dialog-titlebar-close {
        display: none
    }

    .form-input {
        width: 100%;
    }

    tbody {
        display: table;
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

