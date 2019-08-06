package com.objectfrontier.training.java.jdbc.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionService {

    private static Connection connection = null;

//    static HikariConfig config = new HikariConfig("resources\\properties\\db.properties");
//    static { config.setMaximumPoolSize(3); }
//    static HikariDataSource ds = new HikariDataSource(config);

    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://rpc65:3306/arunkumar?useSSL=true","arun","demo");
        return connection;
//       connection =  ds.getConnection();
//       connection.setAutoCommit(false);

//        if (connection != null) {
////            log("%s", "Connected!\n");
//            return connection;
//        } else {
////            log("%s", "Not Connected!");
//            return null;
//        }
    }

//    public static void manageTransaction(Connection conn, boolean isSuccess) throws SQLException {
//
//        if (isSuccess) {
//            System.out.println("connection-commit");
//            conn.commit();
//        } else {
//            System.out.println("connection-rollback");
//            conn.rollback();
//        }
////        conn.close();
//    }
//
    private static void log(String format, String vals) {
        System.out.format(format, vals);
    }
}
