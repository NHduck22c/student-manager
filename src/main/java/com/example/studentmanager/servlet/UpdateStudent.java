package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.service.StudentService;
import com.frameworkset.platform.security.authorization.impl.P;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.UPDATE_STUDENT)
public class UpdateStudent extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        StudentService studentService = new StudentService();

        String student_id = request.getParameter("student_id");
        String student_name = request.getParameter("student_name");
        String student_sex = request.getParameter("student_sex");
        String student_major = request.getParameter("student_major");
        Integer student_class_id = Parser.toInt(request.getParameter("student_class_id"));

        try {
            if (student_class_id != null) {
                studentService.updateStudentInfo(student_id, student_name, student_sex, student_major, student_class_id);
            } else {
                studentService.updateStudentInfo(student_id, student_name, student_sex, student_major);
            }
        } catch (Exception e) {
            out.print(e);
        }
        response.sendRedirect(PageName.STUDENT_DETAIL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
