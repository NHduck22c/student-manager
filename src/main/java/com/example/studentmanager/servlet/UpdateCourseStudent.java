package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.service.CourseStudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.UPDATE_COURSE_STUDENT)
public class UpdateCourseStudent extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        CourseStudentService courseStudentService = new CourseStudentService();

        String student_id = request.getParameter("student_id");
        Integer course_id = Parser.toInt(request.getParameter("course_id"));
        Integer courseStudentScore = Parser.toInt(request.getParameter("course_student_score"));
        try {
            if (course_id == null) {
                throw new Exception();
            }
            if (courseStudentScore != null) {
                courseStudentService.updateCourseStudent(student_id, course_id, courseStudentScore);
            }
            response.sendRedirect(PageName.COURSE_STUDENT_DETAIL + "?id=" + course_id);
        } catch (Exception e) {
            out.print(e);
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
