package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.service.StudentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/" + PageName.CLASS_STUDENT_DETAIL)
public class ClassStudentDetail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        Integer class_id = Parser.toInt(request.getParameter("id"));
        StudentService studentService = new StudentService();
        ArrayList<Student> students;
        try {
            if (class_id == null) {
                throw new Exception();
            }
            students = studentService.getStudentsInClass(class_id);
            session.setAttribute("classStudentDetail", students);
            session.setAttribute("classDetailId", class_id);
            response.sendRedirect("teacher/classDetail.jsp");
        } catch (Exception e) {
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
