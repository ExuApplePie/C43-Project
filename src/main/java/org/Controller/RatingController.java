package org.Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.Model.Rating;

public class RatingController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static Rating getRating(int renterId, int hostId) {
        Rating rating = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rating WHERE renterId = " + renterId + " AND hostId = " + hostId);
            while (resultSet.next()) {
                rating = new Rating(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getString(4), resultSet.getString(5));
            }
        } catch (SQLException e) {
            return null;
        }
        return rating;
    }

    public static void addRating(Rating rating) {
        try {
            conn.createStatement().executeUpdate("INSERT IGNORE INTO rating VALUES (" + rating.getRenterId() + ", " + rating.getHostId() + ", " + rating.getScore() + ", '" + rating.getDate() + "', '" + rating.getComment() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteRating(int renterId, int hostId) {
        try {
            conn.createStatement().executeUpdate("DELETE FROM rating WHERE renterId = " + renterId + " AND hostId = " + hostId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editRating(Rating rating) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE rating SET score = " + rating.getScore() + ", date = '" + rating.getDate() + "', comment = '" + rating.getComment() + "' WHERE renterId = " + rating.getRenterId() + " AND hostId = " + rating.getHostId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
