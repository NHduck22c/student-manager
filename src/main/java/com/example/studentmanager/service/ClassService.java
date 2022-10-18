package com.example.studentmanager.service;

import com.example.studentmanager.model.ClassInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClassService extends DatabaseConnect {

    public boolean insertClass(String class_name) throws Exception {
        initConnection();
        String sql = "insert into classes(class_name) values(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, class_name);
        int i = ps.executeUpdate();
        closeConnection();
        return i == 1;
    }

    public boolean deleteClass(int id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "delete from classes where id='" + id + "'";
        int i = statement.executeUpdate(sql);
        closeConnection();
        return i == 1;
    }

    public void updateClassInfo(int id, String class_name) throws Exception {
        initConnection();
        String sql = "update classes set class_name=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, class_name);
        ps.setInt(2, id);
        ps.executeUpdate();
        closeConnection();
    }

    public ClassInfo findWithId(int id) throws Exception {
        initConnection();
        Statement statement = conn.createStatement();
        String sql = "select * from classes where id = " + id;
        ResultSet rs = statement.executeQuery(sql);
        ClassInfo classInfo = getClassInfo(rs);
        closeConnection();
        return classInfo;
    }

    public ArrayList<ClassInfo> findWithName(String class_name) throws Exception {
        ArrayList<ClassInfo> classInfos = new ArrayList<>();
        initConnection();
        Statement stat = conn.createStatement();
        String sql = "select * from classes where class_name LIKE '%" + class_name + "%'";
        ResultSet rs = stat.executeQuery(sql);
        getMoreClasses(classInfos, rs);
        closeConnection();
        return classInfos;
    }

    public ArrayList<ClassInfo> getOnePage(int page, int size) throws Exception {
        ArrayList<ClassInfo> classInfos = new ArrayList<>();
        initConnection();
        String sql = "SELECT * FROM classes limit ?, ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, (page - 1) * size);
        ps.setInt(2, size);
        ResultSet rs = ps.executeQuery();
        getMoreClasses(classInfos, rs);
        closeConnection();
        return classInfos;
    }

    public int getClassCount() throws Exception {
        initConnection();
        String sql = "select count(*) from classes";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        closeConnection();
        return count;
    }

    private ClassInfo getClassInfo(ResultSet rs) throws SQLException {
        ClassInfo classInfo = null;
        if (rs.next()) {
            classInfo = new ClassInfo();
            classInfo.setId(rs.getInt("id"));
            classInfo.setClassName(rs.getString("class_name"));
        }
        return classInfo;
    }

    private void getMoreClasses(ArrayList<ClassInfo> al, ResultSet rs) throws SQLException {
        ClassInfo classInfo;
        do {
            classInfo = getClassInfo(rs);
            if (classInfo != null)
                al.add(classInfo);
        } while (classInfo != null);
    }
}
