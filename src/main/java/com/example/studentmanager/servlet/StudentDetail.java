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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet("/" + PageName.STUDENT_DETAIL)
public class StudentDetail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String key = request.getParameter("key");

        if (key == null || key.equals("")) {
            int currentIndex, count, size = 10;
            currentIndex = Parser.toInt(request.getParameter("index"), 1);

            try {
                StudentService studentService = new StudentService();
                ArrayList<Student> studentArrayList = studentService.getOnePage(currentIndex, size);
                count = studentService.getStudentCount();
                int sumIndex = count % size == 0 ? count / size : count / size + 1;
                session.setAttribute("studentDetail", studentArrayList);
                session.setAttribute("sumIndex", sumIndex);
                response.sendRedirect("teacher/main.jsp");
            } catch (Exception e) {
                out.print(e);
            }
        } else {
            StudentService studentService = new StudentService();
            String pattern = "^\\d+";
            boolean isMatch = Pattern.matches(pattern, key);
            if (isMatch) {
                try {
                    Student student = studentService.findWithId(key);
                    ArrayList<Student> students = new ArrayList<>();
                    students.add(student);
                    session.setAttribute("studentDetail", students);
                    session.setAttribute("sumIndex", 1);
                    response.sendRedirect("teacher/main.jsp");
                } catch (Exception e) {
                    out.print(e);
                }
            } else {
                try {
                    ArrayList<Student> studentArrayList = studentService.findWithName(key);
                    session.setAttribute("studentDetail", studentArrayList);
                    session.setAttribute("sumIndex", 1);
                    response.sendRedirect("teacher/main.jsp");
                } catch (Exception e) {
                    out.print(e);
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
