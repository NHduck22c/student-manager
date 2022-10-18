package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.helper.Parser;
import com.example.studentmanager.model.ClassInfo;
import com.example.studentmanager.service.ClassService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet("/" + PageName.CLASS_DETAIL)
public class ClassDetail extends HttpServlet {

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
                ClassService classService = new ClassService();
                ArrayList<ClassInfo> classInfoArrayList = classService.getOnePage(currentIndex, size);
                count = classService.getClassCount();
                int sumIndex = count % size == 0 ? count / size : count / size + 1;
                session.setAttribute("classDetail", classInfoArrayList);
                session.setAttribute("sumIndex", sumIndex);
                response.sendRedirect("teacher/class.jsp");
            } catch (Exception e) {
                out.print(e);
            }
        } else {
            ClassService classService = new ClassService();
            String pattern = "^\\d+";
            boolean isMatch = Pattern.matches(pattern, key);
            if (isMatch) {
                try {
                    ClassInfo classInfo = classService.findWithId(Parser.toInt(key, -1));
                    ArrayList<ClassInfo> classInfos = new ArrayList<>();
                    classInfos.add(classInfo);
                    session.setAttribute("classDetail", classInfos);
                    session.setAttribute("sumIndex", 1);
                    response.sendRedirect("teacher/class.jsp");
                } catch (Exception e) {
                    out.print(e);
                }
            } else {
                try {
                    ArrayList<ClassInfo> classInfos = classService.findWithName(key);
                    session.setAttribute("classDetail", classInfos);
                    session.setAttribute("sumIndex", 1);
                    response.sendRedirect("teacher/class.jsp");
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
