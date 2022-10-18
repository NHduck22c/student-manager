package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Generator;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet("/" + PageName.EXPORT_STUDENT_CSV)
public class ExportStudentCSV extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"students.csv\"");
        StudentService studentService = new StudentService();
        ArrayList<Student> students;
        try {
            students = studentService.getOnePage(1, 1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(Generator.exportStudentsCSV(students).getBytes());
        outputStream.flush();
        outputStream.close();
        response.sendRedirect(PageName.STUDENT_DETAIL);
    }
}
