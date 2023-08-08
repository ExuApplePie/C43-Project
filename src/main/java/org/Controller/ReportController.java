package org.Controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opennlp.tools.chunker.*;
import opennlp.tools.cmdline.parser.*;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;

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
            resultSet.close();
            statement.close();
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
            resultSet.close();
            statement.close();
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
            resultSet.close();
            statement.close();
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
            resultSet.close();
            statement.close();
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
            resultSet.close();
            statement.close();
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Returns an array of host IDs who have more than 10% of the total listings in a region
    public static int[] getCommercialHosts(String country, String city) {
        try {
            String query = "SELECT hostId, COUNT(*) as num_listings " +
                    "FROM listing " +
                    "WHERE city = '" + city + "' AND country = '" + country + "' " +
                    "GROUP BY hostId " +
                    "HAVING num_listings > ( " +
                    "SELECT COUNT(*) * 0.1 " +
                    "FROM listing l2 " +
                    "WHERE l2.city = '" + city + "' AND l2.country = '" + country + "'" +
                    ")";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int[] hosts = new int[resultSet.getFetchSize()];
            for (int i = 0; resultSet.next(); i++) {
                hosts[i] = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getCommercialHosts(String country) {
        try {
            String query = "SELECT hostId, COUNT(*) as num_listings " +
                    "FROM listing " +
                    "WHERE country = '" + country + "' " +
                    "GROUP BY hostId " +
                    "HAVING num_listings > ( " +
                    "SELECT COUNT(*) * 0.1 " +
                    "FROM listing l2 " +
                    "WHERE l2.country = '" + country + "'" +
                    ")";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int[] hosts = new int[resultSet.getFetchSize()];
            for (int i = 0; resultSet.next(); i++) {
                hosts[i] = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
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
        String query = "SELECT guestId, COUNT(*) as num_bookings " +
                "FROM booking " +
                "WHERE startDate BETWEEN '"+ startDate + "' AND '" + endDate + "' " +
                "GROUP BY guestId " +
                "ORDER BY num_bookings DESC";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<int[]> renters = new ArrayList<>();
            while (resultSet.next()) {
                renters.add(new int[]{resultSet.getInt(1), resultSet.getInt(2)});
            }
            resultSet.close();
            statement.close();
            return renters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<int[]> getRenterRanking(String startDate, String endDate, String city) {
        String query = "SELECT guestId, COUNT(*) as num_bookings " +
                "FROM booking b, listing l " +
                "WHERE b.startDate BETWEEN '"+ startDate + "' AND '" + endDate + "' " +
                "AND b.listingId = l.listingId " +
                "AND l.city = '" + city + "' " +
                "GROUP BY guestId " +
                "HAVING num_bookings >= 2 " +
                "ORDER BY num_bookings DESC";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<int[]> renters = new ArrayList<>();
            while (resultSet.next()) {
                renters.add(new int[]{resultSet.getInt(1), resultSet.getInt(2)});
            }
            resultSet.close();
            statement.close();
            return renters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // gets the top 3 hosts with the most cancellations
    public static int[] getMostHostCancellations(String startDate, String endDate) {
        String query = "SELECT cancelledBy, COUNT(*) as num_cancellations " +
                "FROM booking " +
                "WHERE startDate BETWEEN " + startDate + " AND " + endDate + " " +
                "AND cancelledBy != guestId " +
                "GROUP BY cancelledBy " +
                "ORDER BY num_cancellations DESC " +
                "LIMIT 3";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int[] hosts = new int[resultSet.getFetchSize()];
            for (int i = 0; resultSet.next(); i++) {
                hosts[i] = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            return hosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // gets the top 3 guests with the most cancellations
    public static int[] getMostGuestCancellations(String startDate, String endDate) {
        String query = "SELECT userId FROM user ORDER BY hostCancels DESC LIMIT 3";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int[] guests = new int[resultSet.getFetchSize()];
            for (int i = 0; resultSet.next(); i++) {
                guests[i] = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            return guests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getNounPhrases(int listingId) {
        Tokenizer tokenizer = new SimpleTokenizer();
        Map<String, Integer> nounPhraseCounts = new HashMap<>();
        List<String> nounPhrases = new ArrayList<>();
        try {
            POSTaggerME posTagger = new POSTaggerME(new POSModel(new File("en-pos-maxent.bin")));
            ChunkerME chunker = new ChunkerME(new ChunkerModel(new File("en-chunker.bin")));
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT comment FROM booking WHERE listingId = " + listingId);
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String[] tokens = tokenizer.tokenize(comment);
                String[] tags = posTagger.tag(tokens);
                String[] chunks = chunker.chunk(tokens, tags);
                for (int i = 0; i < chunks.length; i++) {
                    if (chunks[i].equals("B-NP")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(tokens[i]);
                        i++;
                        while (i < chunks.length && chunks[i].equals("I-NP")) {
                            sb.append(" ").append(tokens[i]);
                            i++;
                        }
                        nounPhrases.add(sb.toString());
                    }
                }
                for (String np : nounPhrases) {
                    int count = nounPhraseCounts.getOrDefault(np, 0);
                    nounPhraseCounts.put(np, count + 1);
                }
            }
            resultSet.close();
            return nounPhrases;
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
