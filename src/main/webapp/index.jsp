<%@ page import="com.example.studentmanager.service.TeacherService" %>
<%@ page import="com.example.studentmanager.service.StudentService" %>
<%@ page import="com.example.studentmanager.model.Teacher" %>
<%@ page import="com.example.studentmanager.model.Student" %>
<%@ page import="com.example.studentmanager.constants.PageName" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%
    TeacherService teacherService = new TeacherService();
    StudentService studentService = new StudentService();
    Teacher teacher = null;
    Student student = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            String cookieName = c.getName();
            if ("name".equals(cookieName)) {
                String user = c.getValue();
                try {
                    teacher = teacherService.findWithId(user);
                    student = studentService.findWithId(user);
                } catch (Exception e) {
                    out.print(e);
                }
                if (teacher != null) {
                    session.setAttribute("info", teacher);
                    response.sendRedirect(PageName.STUDENT_DETAIL);
                    return;
                } else if (student != null) {
                    session.setAttribute("info", student);
                    response.sendRedirect("student/detail.jsp");
                    return;
                }
            }
        }
    }
    response.sendRedirect("login.jsp");
%>
</body>
</html>
