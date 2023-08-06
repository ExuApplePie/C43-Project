package org.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class JdbcSqlServerConnection {
    protected static Connection conn;
    public static void connectToServer() {
        String dbURL = "jdbc:mysql://localhost:3306/assignment2";
        String username="root";
        String password="";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("Connected");
//                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
//                System.out.println("Driver name: " + dm.getDriverName());
//                System.out.println("Driver version: " + dm.getDriverVersion());
//                System.out.println("Product name: " + dm.getDatabaseProductName());
//                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void initDatabase() {
        try {
            Scanner sc = new Scanner(new File("./src/main/java/org/Controller/InitDB.sql"));
            sc.useDelimiter("@@@");
            String str = "";
            while (sc.hasNext()) {
                str = sc.next();
                Statement statement = conn.createStatement();
                statement.executeUpdate(str);
            };
            sc.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
