package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.model.Teacher;
import com.example.studentmanager.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.UPDATE_TEACHER)
public class UpdateTeacher extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        TeacherService teacherService = new TeacherService();

        String uid = request.getParameter("uid");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            Teacher teacher = teacherService.updateTeacher(uid, name, sex, email, password);
            session.setAttribute("info", teacher);
            out.print("<script>alert(\"Cập nhật thành công！\");location.href = \"teacher/detail.jsp\";</script>");
        } catch (Exception e) {
            out.print(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

