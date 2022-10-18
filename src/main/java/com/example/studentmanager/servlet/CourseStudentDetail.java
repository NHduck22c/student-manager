package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.model.CourseStudent;
import com.example.studentmanager.service.CourseStudentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/" + PageName.COURSE_STUDENT_DETAIL)
public class CourseStudentDetail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        int course_id = Parser.toInt(request.getParameter("id"), -1);
        CourseStudentService courseStudentService = new CourseStudentService();
        ArrayList<CourseStudent> courseStudents;
        try {
            courseStudents = courseStudentService.findWithCourseId(course_id);
            session.setAttribute("courseStudentDetail", courseStudents);
            session.setAttribute("courseDetailId", course_id);
            response.sendRedirect("teacher/courseDetail.jsp");
        } catch (Exception e) {
            response.sendRedirect("teacher/main.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
