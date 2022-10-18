package com.example.studentmanager.service;

import com.example.studentmanager.model.Course;
import com.example.studentmanager.model.CourseStudent;
import com.example.studentmanager.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseStudentService extends DatabaseConnect {

    public boolean insertCourseStudent(String student_id, int course_id) throws Exception {
        initConnection();
        String sql = "insert into course_student(student_id, course_id) values(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, student_id);
        ps.setInt(2, course_id);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean insertCourseStudent(String student_id, int course_id, int score) throws Exception {
        initConnection();
        String sql = "insert into course_student(student_id, course_id, score) values(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, student_id);
        ps.setInt(2, course_id);
        ps.setInt(3, score);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean deleteCourseStudent(int id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "delete from course_student where id='" + id + "'";
        int i = statement.executeUpdate(sql);
        closeConnection();
        return i == 1;
    }

    public boolean deleteCourse(int course_id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "delete from course_student where course_id='" + course_id + "'";
        int i = statement.executeUpdate(sql);
        closeConnection();
        return i == 1;
    }

    public boolean deleteStudent(String student_id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "delete from course_student where student_id='" + student_id + "'";
        int i = statement.executeUpdate(sql);
        closeConnection();
        return i == 1;
    }


    public void updateCourseStudent(String student_id, int course_id, int score) throws Exception {
        initConnection();
        String sql = "update course_student set score=? where student_id=? and course_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, score);
        ps.setString(2, student_id);
        ps.setInt(3, course_id);
        ps.executeUpdate();
        closeConnection();
    }

    public ArrayList<CourseStudent> findWithCourseId(int course_id) throws Exception {
        ArrayList<CourseStudent> courseStudents = new ArrayList<>();
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "select * from course_student where course_id = " + course_id;
        ResultSet rs = statement.executeQuery(sql);
        getMoreCourseStudents(courseStudents, rs);
        closeConnection();
        return courseStudents;
    }

    private CourseStudent getCourseStudent(ResultSet rs) throws Exception {
        CourseStudent courseStudent = null;
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        if (rs.next()) {
            courseStudent = new CourseStudent();
            courseStudent.setId(rs.getInt("id"));
            String student_id = rs.getString("student_id");
            Student student = studentService.findWithId(student_id);
            courseStudent.setStudent(student);
            int course_id = rs.getInt("course_id");
            Course course = courseService.findWithId(course_id);
            courseStudent.setCourse(course);
            int score = rs.getInt("score");
            courseStudent.setScore(score);
        }
        return courseStudent;
    }

    private void getMoreCourseStudents(ArrayList<CourseStudent> al, ResultSet rs) throws Exception {
        CourseStudent courseStudent;
        do {
            courseStudent = getCourseStudent(rs);
            if (courseStudent != null)
                al.add(courseStudent);
        } while (courseStudent != null);
    }
}