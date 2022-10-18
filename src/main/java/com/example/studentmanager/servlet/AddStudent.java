package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.service.StudentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.ADD_STUDENT)
public class AddStudent extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        StudentService studentService = new StudentService();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String major = request.getParameter("major");
        String school_date = request.getParameter("school_date");
        Integer class_id = Parser.toInt(request.getParameter("class_id"));

        try {
            if (class_id != null) {
                studentService.insertStudent(id, name, sex, school_date, major, class_id);
            } else {
                studentService.insertStudent(id, name, sex, school_date, major);
            }
            response.sendRedirect(PageName.STUDENT_DETAIL);
        } catch (Exception e) {
            out.print(e);
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
