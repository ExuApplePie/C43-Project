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
            System.out.println(warnCommercialHost(getCommercialHosts(country, city)));
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
            System.out.println(warnCommercialHost(getCommercialHosts(country, city)));
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
            System.out.println(warnCommercialHost(getCommercialHosts(country)));
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

    // Returns an array of host IDs who have more than 10% of the total listings in a region
    public static int[] getCommercialHosts(String country, String city) {
        try {
            String sql = "SELECT hostId, COUNT(*) as num_listings " +
                    "FROM listing " +
                    "WHERE city = '" + city + "' AND country = '" + country + "' " +
                    "GROUP BY hostId " +
                    "HAVING num_listings > ( " +
                    "SELECT COUNT(*) * 0.1 " +
                    "FROM listing l2 " +
                    "WHERE l2.city = '" + city + "' AND l2.country = '" + country + "'" +
                    ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int[] hosts = new int[rs.getFetchSize()];
            for (int i = 0; rs.next(); i++) {
                hosts[i] = rs.getInt(1);
            }
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getCommercialHosts(String country) {
        try {
            String sql = "SELECT hostId, COUNT(*) as num_listings " +
                    "FROM listing " +
                    "WHERE country = '" + country + "' " +
                    "GROUP BY hostId " +
                    "HAVING num_listings > ( " +
                    "SELECT COUNT(*) * 0.1 " +
                    "FROM listing l2 " +
                    "WHERE l2.country = '" + country + "'" +
                    ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int[] hosts = new int[rs.getFetchSize()];
            for (int i = 0; rs.next(); i++) {
                hosts[i] = rs.getInt(1);
            }
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Still needs to prohibit???
    public static String warnCommercialHost(int[] hosts) {
        if (hosts.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("The following hosts have more than 10% of the total listings in their region:\n");
            for (int host : hosts) {
                sb.append(host).append("\n");
            }
            return sb.toString();
        } else {
            return "No hosts have more than 10% of the total listings in their region.";
        }
    }

    // first element is the guestId, second element is the number of bookings
    public static List<int[]> getRenterRanking(String startDate, String endDate) {
        String sql = "SELECT guestId, COUNT(*) as num_bookings " +
                "FROM booking " +
                "WHERE startDate BETWEEN '"+ startDate + "' AND '" + endDate + "' " +
                "GROUP BY guestId " +
                "ORDER BY num_bookings DESC";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<int[]> renters = new ArrayList<>();
            while (rs.next()) {
                renters.add(new int[]{rs.getInt(1), rs.getInt(2)});
            }
            return renters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<int[]> getRenterRanking(String startDate, String endDate, String city) {
        String sql = "SELECT guestId, COUNT(*) as num_bookings " +
                "FROM booking b, listing l " +
                "WHERE b.startDate BETWEEN '"+ startDate + "' AND '" + endDate + "' " +
                "AND b.listingId = l.listingId " +
                "AND l.city = '" + city + "' " +
                "GROUP BY guestId " +
                "HAVING num_bookings >= 2 " +
                "ORDER BY num_bookings DESC";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<int[]> renters = new ArrayList<>();
            while (rs.next()) {
                renters.add(new int[]{rs.getInt(1), rs.getInt(2)});
            }
            return renters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
