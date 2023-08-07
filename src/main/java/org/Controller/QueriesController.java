package org.Controller;

import org.Model.Listing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static void createDatesView() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE OR REPLACE VIEW dates AS " +
                    "SELECT Listing.*, Availability.date, Availability.available, Availability.price, Availability.listingId AS availability_listingId FROM Listing " +
                    "INNER JOIN Availability " +
                    "ON Listing.listingId = Availability.listingId";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateDateQuery(String startDate, String endDate) {
        String dateQuery = "";
        if (startDate != null && endDate != null) {
            dateQuery = "AND date BETWEEN '" + startDate + "' AND '" + endDate + "'";
        } else if (startDate != null) {
            dateQuery = "AND date = '" + startDate + "'";
        }
        return dateQuery;
    }

    // Returns all listings within a certain distance of a given longitude and latitude, in kilometers. Uses the Haversine formula.
    // If endDate is null, it will return all listings that are available on startDate. If both are null, it will return all listings.
    public static List<Listing> getListingWithinDistance(float longitude, float latitude, float distance, boolean orderByDistance, boolean orderByPriceAsc, String startDate, String endDate) {
        createDatesView();
        String orderBy = orderByDistance ? "DISTANCE" : (orderByPriceAsc ? "price ASC" : "price DESC");
        String dateQuery = generateDateQuery(startDate, endDate);
        String query = "SELECT listingId FROM dates WHERE (6371 * acos(cos(radians(" + latitude + ")) " +
                "* cos(radians(latitude)) * cos(radians(longitude) - radians(" + longitude + ")) + sin(radians(" + latitude + ")) * sin(radians(latitude))))" + " AS DISTANCE " +
                dateQuery + " FROM Listing HAVING DISTANCE < " + distance + " ORDER BY " + orderBy;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Listing> listings = new ArrayList<>();
            while (rs.next()) {
                listings.add(ListingController.getListing(rs.getInt(1)));
            }
            return listings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Listing> getListingByPostalCode(String postalCode, String startDate, String endDate) {
        createDatesView();
        String dateQuery = generateDateQuery(startDate, endDate);
        String query = "SELECT listingId FROM dates WHERE LEFT(postalCode, 5) = LEFT('" + postalCode + "', 5) " + dateQuery;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Listing> listings = new ArrayList<>();
            while(rs.next()) {
                listings.add(ListingController.getListing(rs.getInt(1)));
            }
            return listings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Listing> getListingByAddress(String address, String startDate, String endDate) {
        createDatesView();
        String dateQuery = generateDateQuery(startDate, endDate);
        String query = "SELECT listingId FROM dates WHERE address = '" + address + "' " + dateQuery;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Listing> listings = new ArrayList<>();
            while(rs.next()) {
                listings.add(ListingController.getListing(rs.getInt(1)));
            }
            return listings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
