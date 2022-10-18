package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.model.Course;
import com.example.studentmanager.service.CourseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet("/" + PageName.COURSE_DETAIL)
public class CourseDetail extends HttpServlet {

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
                CourseService courseService = new CourseService();
                ArrayList<Course> courseArrayList = courseService.getOnePage(currentIndex, size);
                count = courseService.getCoursesCount();
                int sumIndex = count % size == 0 ? count / size : count / size + 1;
                session.setAttribute("courseDetail", courseArrayList);
                session.setAttribute("sumIndex", sumIndex);
                response.sendRedirect("teacher/course.jsp");
            } catch (Exception e) {
                out.print(e);
            }
        } else {
            CourseService courseService = new CourseService();
            String pattern = "^\\d+";
            boolean isMatch = Pattern.matches(pattern, key);
            if (isMatch) {
                try {
                    Course course = courseService.findWithId(Parser.toInt(key, -1));
                    ArrayList<Course> courses = new ArrayList<>();
                    courses.add(course);
                    session.setAttribute("courseDetail", courses);
                    session.setAttribute("sumIndex", 1);
                    response.sendRedirect("teacher/course.jsp");
                } catch (Exception e) {
                    out.print(e);
                }
            } else {
                try {
                    ArrayList<Course> courses = courseService.findWithName(key);
                    session.setAttribute("courseDetail", courses);
                    session.setAttribute("sumIndex", 1);
                    response.sendRedirect("teacher/course.jsp");
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
