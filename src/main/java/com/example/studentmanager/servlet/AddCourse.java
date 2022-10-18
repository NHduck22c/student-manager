package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.service.CourseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/" + PageName.ADD_COURSE)
public class AddCourse extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        CourseService courseService = new CourseService();

        String course_name = request.getParameter("course_name");
        String description = request.getParameter("course_description");

        try {
            if (description != null) {
                courseService.insertCourse(course_name, description);
            } else {
                courseService.insertCourse(course_name);
            }
        } catch (Exception e) {
            out.print(e);
        }
        response.sendRedirect(PageName.COURSE_DETAIL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
