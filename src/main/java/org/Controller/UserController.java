package org.Controller;

import org.Model.Rating;
import org.Model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
                        resultSet.getString(4), resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(8));
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public static void addUser(User user) {
    	try {
            Statement statement = conn.createStatement(); // ignore might also be suppressing other errors so be careful
            statement.executeUpdate("INSERT IGNORE INTO user VALUES (" + "NULL, '" + user.getOccupation() + "', '" +
                    user.getAddress() + "', '" + user.getDOB() + "', " + user.getSIN() + ", '" + user.getName() + ", '" + user.getHostCancelCount() + ", '" + user.getGuestCancels() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
            }
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
                    user.getAddress() + "', DOB = '" + user.getDOB() + "', SIN = " + user.getSIN() + "', name = '" + user.getName() + "', hostCancels = '" + user.getHostCancelCount() + "', guestCancels = '" + user.getGuestCancels() + " WHERE userId = " + user.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Rating> getAllRatings(int userId) {
    	List<Rating> ratings = new ArrayList<>();
    	try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rating WHERE renterId = " + userId);
            while (resultSet.next()) {
                ratings.add(new Rating(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5)));
            }
        } catch (SQLException e) {
            return null;
        }
    	return ratings;
    }
}
