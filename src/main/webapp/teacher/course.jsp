<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page import="com.example.studentmanager.model.Course" %>
<%@ page import="com.example.studentmanager.service.CourseService" %>
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
    ArrayList<Course> courses = new ArrayList<>();
    int sumIndex = 0;
    try {
        teacher = (Teacher) session.getAttribute("info");
        courses = (ArrayList<Course>) session.getAttribute("courseDetail");
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
                <li><a href="../class_detail">Quản lý lớp học</a></li>
                <li class="current_page_item"><a href="../course_detail">Quản lý khóa học</a></li>
                <li><a onclick="return confirm('Xác nhận thoát?');" href="../exit">Đăng xuất</a></li>
            </ul>
        </div>
    </div>
    <div id="main">
        <div class="top">
            <h2>Quản lý khóa học</h2>
            <hr/>
            <button class="btn-add">Thêm khóa học</button>
            <div class="find">
                <form action="../course_detail" method="post">
                    <input id="find-text" type="text" name="key" placeholder="Nhập tên khóa học để tìm kiếm">
                    <input class="find-btn" type="submit" value="Tìm kiếm">
                </form>
            </div>
        </div>
        <div class="table">
            <table id="table" width="100%">
                <tr>
                    <th height="35">Mã khóa học</th>
                    <th>Tên khóa học</th>
                    <th>Thông tin chung</th>
                    <th>Số sinh viên</th>
                    <th>Thao tác</th>
                </tr>
                <%
                    CourseService courseService = new CourseService();
                    for (Course course : courses) {
                        int totalStudents = courseService.getTotalStudentsInCourse(course.getId());
                %>
                <tr>
                    <form method="post" action="../update_course">
                        <td height="35"><%=course.getId()%>
                        </td>
                        <td><input value="<%=course.getName()%>" name="course_name" class="table-input"></td>
                        <td><input value="<%=course.getDescription()%>" name="course_description" class="table-input">
                        </td>
                        <td><%=totalStudents%>
                        </td>
                        <input value="<%=course.getId()%>" name="course_id" type="hidden">
                        <td>
                            <a
                                    class="btn-delete"
                                    href=<%="'../course_student_detail?id=" + course.getId() + "'"%>
                            >Xem</a>
                            <input type="submit" class="update-btn" value="Cập nhật">&nbsp;
                            <a
                                    class="btn-delete"
                                    onclick="return confirm('Bạn có chắc muốn xóa khóa học này?');"
                                    href=<%="'../delete_course?id=" + course.getId() + "'"%>
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
            <a href="../course_detail?index=1">Trang đầu</a>
            <%
                for (int i = 1; i <= sumIndex; i++) {
            %>
            <a href="../course_detail?index=<%=i%>"><%=i%>
            </a>
            <%
                }
            %>
            <a href="../course_detail?index=<%=sumIndex%>">Trang cuối</a>
        </div>
        <%
            }
        %>
    </div>
</div>

<div id="add-dialog" title="Thêm khóa học">
    <form id="add-form" method="post">
        Tên khóa học:<br><input name="course_name" type="text" class="form-input"><br>
        Thông tin chung:<br><input name="course_description" type="text" class="form-input"><br>
        <hr>
        <input style="float: right" type="submit" value="Hủy" onclick="function x() {
          $('#add-dialog').dialog('close');
        }">
        <input style="float: right; margin-right: 25px" type="submit" value="Xác nhận"
               onclick="this.form.action='../add_course'">
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

