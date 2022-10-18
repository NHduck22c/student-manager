package com.example.studentmanager.service;

import com.example.studentmanager.model.Teacher;

import java.sql.*;

public class TeacherService extends DatabaseConnect {

    public Teacher checkAccount(String id, String password) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "select * from teachers where id = '" + id + "' and password = '" + password + "'";
        ResultSet rs = statement.executeQuery(sql);
        Teacher teacher = getTeacher(rs);
        closeConnection();
        return teacher;
    }

    public Teacher findWithId(String id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "select * from teachers where id = '" + id + "'";
        ResultSet rs = statement.executeQuery(sql);
        Teacher teacher = getTeacher(rs);
        closeConnection();
        return teacher;
    }

    public Teacher insertTeacher(String id, String password, String email) throws Exception {
        initConnection();
        String sql = "insert into teachers(id, password, email) values(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, password);
        ps.setString(3, email);
        ps.executeUpdate();
        Teacher teacher = findWithId(id);
        closeConnection();
        return teacher;
    }

    public Teacher updateTeacher(String id, String name, String sex, String email, String password) throws Exception {
        initConnection();
        String sql = "update teachers set name=?, sex=?, email=?, password=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, sex);
        ps.setString(3, email);
        ps.setString(4, password);
        ps.setString(5, id);
        ps.executeUpdate();
        Teacher teacher = findWithId(id);
        closeConnection();
        return teacher;
    }

    public void updateTeacherPassword(String id, String password) throws Exception {
        initConnection();
        String sql = "update teachers set password=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, password);
        ps.setString(2, id);
        ps.executeUpdate();
        closeConnection();
    }

    public void updateTeacherAvatar(String id, String path) throws Exception {
        initConnection();
        String sql = "update teachers set avatar_url=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, path);
        ps.setString(2, id);
        ps.executeUpdate();
        closeConnection();
    }

    private Teacher getTeacher(ResultSet rs) throws SQLException {
        Teacher teacher = null;
        if (rs.next()) {
            teacher = new Teacher();
            teacher.setId(rs.getString("id"));
            teacher.setPassword(rs.getString("password"));
            teacher.setName(rs.getString("name"));
            teacher.setEmail(rs.getString("email"));
            teacher.setSex(rs.getString("sex"));
            teacher.setAvatar_url(rs.getString("avatar_url"));
        }
        return teacher;
    }
}
