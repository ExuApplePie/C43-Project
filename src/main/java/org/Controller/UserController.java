package org.Controller;

import org.Model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// Crud operations for user table
public class UserController {
    static Connection conn = JdbcSqlServerConnection.conn;
    public static User getUser(int userId) {
        User user = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE userId = " + userId);
            while (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static void addUser(User user) {
        try {
            Statement statement = conn.createStatement(); // ignore might also be suppressing other errors so be careful
            statement.executeUpdate("INSERT IGNORE INTO user VALUES (" + "NULL, '" + user.getOccupation() + "', '" +
                    user.getAddress() + "', '" + user.getDOB() + "', " + user.getSIN() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(int userId) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM user WHERE userId = " + userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editUser(User user) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE user SET occupation = '" + user.getOccupation() + "', address = '" +
                    user.getAddress() + "', DOB = '" + user.getDOB() + "', SIN = " + user.getSIN() + " WHERE userId = " + user.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
