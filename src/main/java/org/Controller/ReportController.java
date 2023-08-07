package org.Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    static Connection conn = JdbcSqlServerConnection.conn;
    public static int getTotalListings(String country, String city, String postalCode) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM listing WHERE country = '" + country + "' AND city = '" + city + "' AND postalCode = '" + postalCode + "'");
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static int getTotalListings(String country, String city) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM listing WHERE country = '" + country + "' AND city = '" + city + "'");
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int getTotalListings(String country) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM listing WHERE country = '" + country + "'");
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // Returns a list of hosts with their total listings, sorted by total listings
    // The first element of the array is the hostId, the second element is the total listings
    public static List<int []> getHostsByCountry(String country) {
        try {
            List<int []> hosts = new ArrayList<>();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT hostId, COUNT(*) AS total_listings FROM listing WHERE country = '" + country + "' GROUP BY hostId ORDER BY total_listings DESC");
            while (resultSet.next()) {
                hosts.add(new int[]{resultSet.getInt(1), resultSet.getInt(2)});
            }
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Returns a list of hosts with their total listings, sorted by total listings
    // The first element of the array is the hostId, the second element is the total listings
    public static List<int []> getHostsByCity(String country, String city) {
        try {
            List<int []> hosts = new ArrayList<>();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT hostId, COUNT(*) AS total_listings FROM listing WHERE country = '" + country + "' AND city = '" + city + "' GROUP BY hostId ORDER BY total_listings DESC");
            while (resultSet.next()) {
                hosts.add(new int[]{resultSet.getInt(1), resultSet.getInt(2)});
            }
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
