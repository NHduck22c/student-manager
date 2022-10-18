package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.service.ClassService;
import com.example.studentmanager.service.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.UPDATE_COURSE)
public class UpdateCourse extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        CourseService courseService = new CourseService();

        Integer course_id = Parser.toInt(request.getParameter("course_id"));
        String course_name = request.getParameter("course_name");
        String description = request.getParameter("course_description");
        try {
            if (course_id == null) {
                throw new Exception();
            }
            if (description != null) {
                courseService.updateCourse(course_id, course_name, description);
            } else {
                courseService.updateCourse(course_id, course_name);
            }
            response.sendRedirect(PageName.COURSE_DETAIL);
        } catch (Exception e) {
            out.print(e);
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
