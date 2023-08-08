package org.Controller;

import org.Model.ListingAmenity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListingAmenityController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static ListingAmenity getListingAmenity(int listingId, int amenityId) {
        ListingAmenity listingAmenity = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM listingAmenity WHERE listingId = " + listingId + " AND amenityId = " + amenityId);
            while (resultSet.next()) {
                listingAmenity = new ListingAmenity(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listingAmenity;
    }

    public static void addListingAmenity(ListingAmenity listingAmenity) {
        try {
            conn.createStatement().executeUpdate("INSERT IGNORE INTO listingAmenity VALUES (" + listingAmenity.getListingId() + ", " + listingAmenity.getAmenityId() + ", " + listingAmenity.getQuantity() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteListingAmenity(int listingId, int amenityId) {
        try {
            conn.createStatement().executeUpdate("DELETE FROM listingAmenity WHERE listingId = " + listingId + " AND amenityId = " + amenityId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateListingAmenity(ListingAmenity listingAmenity) {
        try {
            conn.createStatement().executeUpdate("UPDATE listingAmenity SET quantity = " + listingAmenity.getQuantity() + " WHERE listingId = " + listingAmenity.getListingId() + " AND amenityId = " + listingAmenity.getAmenityId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
