package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.model.Teacher;
import com.example.studentmanager.service.TeacherService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.CHECK_REGISTER)
public class CheckRegister extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        String email = request.getParameter("email");
        String user = request.getParameter("user");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();


        TeacherService teacherService = new TeacherService();
        Teacher teacher = null;

        try {
            teacher = teacherService.insertTeacher(user, password, email);
        } catch (Exception e) {
            out.print(e);
        }

        if (teacher != null) {
            session.setAttribute("info", teacher);
            response.sendRedirect(PageName.STUDENT_DETAIL);
        } else {
            out.print("<script>alert(\"Tài khoản đã được đăng ký！\");location.href = \"register.jsp\";</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
