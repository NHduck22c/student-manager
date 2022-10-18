package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.service.CourseService;
import com.example.studentmanager.service.CourseStudentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.DELETE_COURSE_STUDENT)
public class DeleteCourseStudent extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        CourseStudentService courseStudentService = new CourseStudentService();
        Integer id = Parser.toInt(request.getParameter("id"));
        Integer course_id = Parser.toInt(request.getParameter("course_id"));
        try {
            if (id == null || course_id == null) {
                throw new Exception();
            }
            courseStudentService.deleteCourseStudent(id);
            response.sendRedirect(PageName.COURSE_STUDENT_DETAIL + "?id=" + course_id);
        } catch (Exception e) {
            out.print(e);
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
