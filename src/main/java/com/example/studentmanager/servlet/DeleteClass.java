package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.service.ClassService;
import com.example.studentmanager.service.StudentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/" + PageName.DELETE_CLASS)
public class DeleteClass extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        ClassService classService = new ClassService();
        StudentService studentService = new StudentService();

        Integer id = Parser.toInt(request.getParameter("id"));
        try {
            if (id == null) {
                throw new Exception();
            }
            classService.deleteClass(id);
            ArrayList<Student> students = studentService.getStudentsInClass(id);

            for (Student student: students) {
                studentService.updateStudentInfo(student.getId(), student.getName(), student.getSex(), student.getMajor(), -1);
            }
            response.sendRedirect(PageName.CLASS_DETAIL);
        } catch (Exception e) {
            out.print(e);
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
