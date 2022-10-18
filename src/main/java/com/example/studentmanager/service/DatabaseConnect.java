package com.example.studentmanager.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnect {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "password";
    public static final String DATABASE_NAME = "student-manager";
    public static final String HOST = "localhost";
    public static final int PORT = 3306;

    protected Connection conn = null;

    protected void initConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME + "?allowPublicKeyRetrieval=true&useSSL=false";
            conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
        }
    }

    protected void closeConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
