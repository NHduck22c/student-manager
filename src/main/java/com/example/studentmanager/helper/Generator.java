package com.example.studentmanager.helper;

import com.example.studentmanager.model.Student;

import java.util.ArrayList;

public class Generator {
    public static String exportStudentsCSV(ArrayList<Student> students) {
        String header = "Mã sinh viên, Tên, Email, Giới tính, Ngày nhập học, Chuyên ngành, Tên lớp\n";
        StringBuilder output = new StringBuilder(header);
        for (Student student : students) {
            output.append(student.toString());
        }
        return output.toString();
    }
}
