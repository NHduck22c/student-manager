package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/" + PageName.EXIT)
public class Exit extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if ("name".equals(cookieName)) {
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("info", null);
        response.sendRedirect("index.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
