package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.Teacher;
import com.example.studentmanager.service.StudentService;
import com.example.studentmanager.service.TeacherService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.CHECK_LOGIN)
public class CheckLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        TeacherService teacherService = new TeacherService();
        StudentService studentService = new StudentService();
        Teacher teacher = null;
        Student student = null;

        try {
            teacher = teacherService.checkAccount(user, password);
            student = studentService.checkAccount(user, password);
        } catch (Exception e) {
            out.print(e);
        }

        if (teacher != null) {
            session.setAttribute("info", teacher);

            if (remember != null) {
                Cookie userCookie = new Cookie("name", user);
                userCookie.setMaxAge(10);
                response.addCookie(userCookie);
            }
            response.sendRedirect(PageName.STUDENT_DETAIL);
        } else if (student != null) {
            session.setAttribute("info", student);

            if (remember != null) {
                Cookie userCookie = new Cookie("name", user);
                userCookie.setMaxAge(10);
                response.addCookie(userCookie);
            }
            response.sendRedirect("student/detail.jsp");
        }
        else {
            out.print("<script>alert(\"Sai tài khoản hoặc mật khẩu！\");location.href = \"login.jsp\";</script>");
        }
    }
}
