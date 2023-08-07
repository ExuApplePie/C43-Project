package org.Controller;

import org.Model.Listing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListingController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static Listing getListing(int listingId) {
        Listing listing = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM listing WHERE listingId = " + listingId);
            while (resultSet.next()) {
                listing = new Listing(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
            }
        } catch (SQLException e) {
            return null;
        }
        return listing;
    }

    public static void addListing(Listing listing) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT IGNORE INTO listing VALUES (" + "NULL, " + listing.getHostId() + ", '" + listing.getType() + "', " + listing.getLongitude() + ", " + listing.getLatitude() + ", '" + listing.getPostalCode() + "', '" + listing.getCountry() + "', '" + listing.getCity() + "', '" + listing.getAddress() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                listing.setListingId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteListing(int listingId) {
        try {
            conn.createStatement().executeUpdate("DELETE FROM listing WHERE listingId = " + listingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editListing(Listing listing) {
        try {
            conn.createStatement().executeUpdate("UPDATE listing SET type = '" + listing.getType() + "', longitude = " + listing.getLongitude() + ", latitude = " + listing.getLatitude() + ", postalCode = '" + listing.getPostalCode() + "', country = '" + listing.getCountry() + "', city = '" + listing.getCity() + "', address = '" + listing.getAddress() + "' WHERE listingId = " + listing.getListingId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}