package org.Controller;

import org.Model.Availability;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class AvailabilityController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static Availability getAvailability(int listingId, String date) {
        Availability availability = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM availability WHERE listingId = " + listingId + " AND date = '" + date + "'");
            while (resultSet.next()) {
                availability = new Availability(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3), resultSet.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availability;
    }

    public static void addAvailability(Availability availability) {
        try {
            conn.createStatement().executeUpdate("INSERT IGNORE INTO availability VALUES (" + availability.getListingId() + ", '" + availability.getDate() + "', " + availability.isAvailable() + ", " + availability.getPrice() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAvailability(int listingId, String date) {
        try {
            conn.createStatement().executeUpdate("DELETE FROM availability WHERE listingId = " + listingId + " AND date = '" + date + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editAvailability(Availability availability) {
        try {
            conn.createStatement().executeUpdate("UPDATE availability SET available = " + availability.isAvailable() + ", price = " + availability.getPrice() + " WHERE listingId = " + availability.getListingId() + " AND date = '" + availability.getDate() + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
