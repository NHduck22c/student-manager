package com.example.studentmanager.service;

import com.example.studentmanager.model.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseService extends DatabaseConnect {

    public boolean insertCourse(String name) throws Exception {
        initConnection();
        String sql = "insert into courses(name) values(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean insertCourse(String name, String description) throws Exception {
        initConnection();
        String sql = "insert into courses(name, description) values(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, description);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean deleteCourse(int id) throws Exception {
        CourseStudentService courseStudentService = new CourseStudentService();
        courseStudentService.deleteCourse(id);
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "delete from courses where id='" + id + "'";
        int i = statement.executeUpdate(sql);
        closeConnection();
        return i == 1;
    }

    public void updateCourse(int id, String name) throws Exception {
        initConnection();
        String sql = "update courses set name=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, id);
        ps.executeUpdate();
        closeConnection();
    }

    public void updateCourse(int id, String name, String description) throws Exception {
        initConnection();
        String sql = "update courses set name=?, description=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setInt(3, id);
        ps.executeUpdate();
        closeConnection();
    }

    public Course findWithId(int id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "select * from courses where id = " + id;
        ResultSet rs = statement.executeQuery(sql);
        Course course = getCourse(rs);
        closeConnection();
        return course;
    }

    public ArrayList<Course> findWithName(String course_name) throws Exception {
        ArrayList<Course> courses = new ArrayList<>();
        initConnection();
        Statement stat = conn.createStatement();
        String sql = "select * from courses where name LIKE '%" + course_name + "%'";
        ResultSet rs = stat.executeQuery(sql);
        getMoreCourses(courses, rs);
        closeConnection();
        return courses;
    }

    public ArrayList<Course> getOnePage(int page, int size) throws Exception {
        ArrayList<Course> courses = new ArrayList<>();
        initConnection();
        String sql = "SELECT * FROM courses limit ?, ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, (page - 1) * size);
        ps.setInt(2, size);
        ResultSet rs = ps.executeQuery();
        getMoreCourses(courses, rs);
        closeConnection();
        return courses;
    }

    public int getCoursesCount() throws Exception {
        initConnection();
        String sql = "select count(*) from courses";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        closeConnection();
        return count;
    }

    public int getTotalStudentsInCourse(int id) throws Exception {
        initConnection();
        String sql = "select count(*) from courses c, course_student cs where c.id = cs.course_id and c.id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        closeConnection();
        return count;
    }

    private Course getCourse(ResultSet rs) throws SQLException {
        Course course = null;
        if (rs.next()) {
            course = new Course();
            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            String description = rs.getString("description");
            if (description != null)
                course.setDescription(description);
        }
        return course;
    }

    private void getMoreCourses(ArrayList<Course> al, ResultSet rs) throws SQLException {
        Course course;
        do {
            course = getCourse(rs);
            if (course != null)
                al.add(course);
        } while (course != null);
    }
}