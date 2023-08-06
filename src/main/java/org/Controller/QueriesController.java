package org.Controller;

import org.Model.Listing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueriesController {
    static Connection conn = JdbcSqlServerConnection.conn;
    // Returns all listings within a certain distance of a given longitude and latitude, in kilometers. Uses the Haversine formula.
    public static Listing[] getListingWithinDistance(float longitude, float latitude, float distance) {
        String query = "SELECT listingId FROM Listing WHERE (6371 * acos(cos(radians(" + latitude + ")) " +
                "* cos(radians(latitude)) * cos(radians(longitude) - radians(" + longitude + ")) + sin(radians(" + latitude + ")) * sin(radians(latitude)))) AS DISTANCE " +
                "FROM Listing HAVING DISTANCE < " + distance + " ORDER BY DISTANCE";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Listing[] listings = new Listing[100];
            for (int i = 0; rs.next(); i++) {
                listings[i] = ListingController.getListing(rs.getInt(1));
            }
            return listings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
