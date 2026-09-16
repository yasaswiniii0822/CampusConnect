package com.campusconnect.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST =
            System.getenv().getOrDefault("MYSQLHOST", "localhost");

    private static final String PORT =
            System.getenv().getOrDefault("MYSQLPORT", "3306");

    private static final String DATABASE =
            System.getenv().getOrDefault("MYSQLDATABASE", "campusconnect");

    private static final String USER =
            System.getenv().getOrDefault("MYSQLUSER", "root");

    private static final String PASSWORD =
            System.getenv().getOrDefault("MYSQLPASSWORD", "");

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&serverTimezone=UTC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "MySQL JDBC driver not found on the classpath.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}