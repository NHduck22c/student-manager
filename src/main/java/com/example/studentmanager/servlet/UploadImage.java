package com.example.studentmanager.servlet;

import com.example.studentmanager.constants.PageName;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.Teacher;
import com.example.studentmanager.service.StudentService;
import com.example.studentmanager.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;

@WebServlet("/" + PageName.UPLOAD_IMAGE)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class UploadImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String uploadPath = getServletContext().getRealPath("/images");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        Part filePart = request.getPart("img");
        String fileName = filePart.getSubmittedFileName();
        String filePath = uploadPath + File.separator + fileName;
        for (Part part : request.getParts()) {
            part.write(filePath);
        }
        TeacherService teacherService = new TeacherService();
        StudentService studentService = new StudentService();
        Teacher teacher = null;
        Student student = null;

        try {
            teacher = teacherService.findWithId(id);
            student = studentService.findWithId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession();

        try {
            if (teacher != null) {
                teacherService.updateTeacherAvatar(id, "images/" + fileName);
                teacher = teacherService.findWithId(id);
                session.setAttribute("info", teacher);
                response.sendRedirect("teacher/detail.jsp");

            } else if (student != null) {
                studentService.updateStudentAvatar(id, "images/" + fileName);
                student = studentService.findWithId(id);
                session.setAttribute("info", student);
                response.sendRedirect("student/detail.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
