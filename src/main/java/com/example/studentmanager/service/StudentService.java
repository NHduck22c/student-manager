package com.example.studentmanager.service;

import com.example.studentmanager.model.ClassInfo;
import com.example.studentmanager.model.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentService extends DatabaseConnect {

    public Student checkAccount(String user, String password) throws Exception {
        initConnection();
        Statement stat = conn.createStatement();
        String sql = "select * from students where id = '" + user + "' and password = '" + password + "'";
        ResultSet rs = stat.executeQuery(sql);
        Student stu = getStudent(rs);
        closeConnection();
        return stu;
    }

    public Student findWithId(String id) throws Exception {
        initConnection();
        Statement stat = conn.createStatement();
        String sql = "select * from students where id = '" + id + "'";
        ResultSet rs = stat.executeQuery(sql);
        Student stu = getStudent(rs);
        closeConnection();
        return stu;
    }

    public ArrayList<Student> findWithName(String name) throws Exception {
        ArrayList<Student> al = new ArrayList<>();
        initConnection();
        Statement stat = conn.createStatement();
        String sql = "select * from students where name LIKE '%" + name + "%'";
        ResultSet rs = stat.executeQuery(sql);
        getMoreStudent(al, rs);
        closeConnection();
        return al;
    }

    public boolean insertStudent(String id, String name, String sex, String school_date, String major, int class_id) throws Exception {
        initConnection();
        String sql = "insert into students(id, password, name, sex, school_date, major, class_id) values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, id);
        ps.setString(3, name);
        ps.setString(4, sex);
        ps.setString(5, school_date);
        ps.setString(6, major);
        ps.setInt(7, class_id);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean insertStudent(String id, String name, String sex, String school_date, String major) throws Exception {
        initConnection();
        String sql = "insert into students(id, password, name, sex, school_date, major) values(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, id);
        ps.setString(3, name);
        ps.setString(4, sex);
        ps.setString(5, school_date);
        ps.setString(6, major);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean deleteStudent(String id) throws Exception {
        CourseStudentService courseStudentService = new CourseStudentService();
        courseStudentService.deleteStudent(id);
        initConnection();
        Statement stat = conn.createStatement();
        String sql = "delete from students where id='" + id + "'";
        int i = stat.executeUpdate(sql);
        closeConnection();
        return i == 1;
    }

    public ArrayList<Student> getOnePage(int page, int size) throws Exception {
        ArrayList<Student> al = new ArrayList<>();
        initConnection();
        String sql = "SELECT * FROM students limit ?, ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, (page - 1) * size);
        ps.setInt(2, size);
        ResultSet rs = ps.executeQuery();
        getMoreStudent(al, rs);
        closeConnection();
        return al;
    }

    public ArrayList<Student> getStudentsInClass(int class_id) throws Exception {
        ArrayList<Student> al = new ArrayList<>();
        initConnection();
        String sql = "SELECT * FROM students where class_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, class_id);
        ResultSet rs = ps.executeQuery();
        getMoreStudent(al, rs);
        closeConnection();
        return al;
    }

    public int getTotalStudentsInClass(int class_id) throws Exception {
        initConnection();
        String sql = "select count(*) from students WHERE class_id=" + class_id;
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        closeConnection();
        return count;
    }

    public int getStudentCount() throws Exception {
        initConnection();
        String sql = "select count(*) from students";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        closeConnection();
        return count;
    }

    public void updateStudentInfo(String id, String name, String sex, String major, int class_id) throws Exception {
        initConnection();
        String sql = "update students set name=?, sex=?, major=?, class_id=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, sex);
        ps.setString(3, major);
        ps.setInt(4, class_id);
        ps.setString(5, id);
        ps.executeUpdate();
        closeConnection();
    }

    public void updateStudentInfo(String id, String name, String sex, String major) throws Exception {
        initConnection();
        String sql = "update students set name=?, sex=?, major=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, sex);
        ps.setString(3, major);
        ps.setString(4, id);
        ps.executeUpdate();
        closeConnection();
    }

    public void updateStudentPassword(String id, String password) throws Exception {
        initConnection();
        String sql = "update students set password=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, password);
        ps.setString(2, id);
        ps.executeUpdate();
        closeConnection();
    }

    public void updateStudentAvatar(String id, String path) throws Exception {
        initConnection();
        String sql = "update students set avatar_url=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, path);
        ps.setString(2, id);
        ps.executeUpdate();
        closeConnection();
    }

    private Student getStudent(ResultSet rs) throws Exception {
        Student student = null;
        ClassService classService = new ClassService();
        if (rs.next()) {
            student = new Student();
            student.setId(rs.getString("id"));
            student.setPassword(rs.getString("password"));
            student.setName(rs.getString("name"));
            student.setSex(rs.getString("sex"));
            student.setSchool_date(rs.getString("school_date"));
            student.setMajor(rs.getString("major"));
            student.setEmail(rs.getString("email"));
            ClassInfo classInfo = classService.findWithId(rs.getInt("class_id"));
            student.setClassInfo(classInfo);
            student.setAvatar_url(rs.getString("avatar_url"));
        }
        return student;
    }

    private void getMoreStudent(ArrayList<Student> al, ResultSet rs) throws Exception {
        Student student;
        do {
            student = getStudent(rs);
            if (student != null)
                al.add(student);
        } while (student != null);

    }
}
