package org.Controller;

import org.Model.Dates;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static void createDatesWithAmenitiesView() {
        try {
            Statement stmt = conn.createStatement();
            String dropView = "DROP VIEW IF EXISTS dates";
            stmt.executeUpdate(dropView);
            String sql = "CREATE VIEW dates AS " +
                    "SELECT Listing.*, Availability.date, Availability.available, Availability.price, Availability.listingId AS availability_listingId, GROUP_CONCAT(DISTINCT amenity.name) AS amenities, GROUP_CONCAT(DISTINCT ListingAmenity.quantity) AS quantities " +
                    "FROM Listing " +
                    "INNER JOIN Availability " +
                    "ON Listing.listingId = Availability.listingId " +
                    "INNER JOIN ListingAmenity ON Listing.listingId = ListingAmenity.listingId " +
                    "INNER JOIN Amenity ON ListingAmenity.amenityId = Amenity.amenityId " +
                    "GROUP BY Listing.listingId, Availability.date";
            stmt.executeUpdate(sql);
            stmt.close();
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

    private static String generatePriceQuery(int lowestPrice, int highestPrice) {
        String priceQuery = "";
        if (lowestPrice != 0 && highestPrice != 0) {
            priceQuery = "AND price BETWEEN " + lowestPrice + " AND " + highestPrice;
        } else if (lowestPrice != 0) {
            priceQuery = "AND price >= " + lowestPrice;
        } else if (highestPrice != 0) {
            priceQuery = "AND price <= " + highestPrice;
        }
        return priceQuery;
    }

    private static String generateAmenitiesQuery(List<String> amenities) {
        String amenitiesQuery = "";
        if (amenities != null && amenities.size() > 0) {
            amenitiesQuery = "AND amenities IN (";
            for (int i = 0; i < amenities.size(); i++) {
                amenitiesQuery += "'" + amenities.get(i) + "'";
                if (i != amenities.size() - 1) {
                    amenitiesQuery += ", ";
                }
            }
            amenitiesQuery += ")";
        }
        return amenitiesQuery;
    }

    private static String generateAllQueries(String startDate, String endDate, int lowestPrice, int highestPrice, List<String> amenities) {
        String dateQuery = generateDateQuery(startDate, endDate);
        String priceQuery = generatePriceQuery(lowestPrice, highestPrice);
        String amenitiesQuery = generateAmenitiesQuery(amenities);
        return dateQuery + " " + priceQuery + " " + amenitiesQuery;
    }

    // Returns all listings within a certain distance of a given longitude and latitude, in kilometers. Uses the Haversine formula.
    // If endDate is null, it will return all listings that are available on startDate. If both are null, it will return all listings.
    // If lowestPrice and highestPrice are both 0, it will return all listings.
    // If amenities is null, it will return all listings.
    // If orderByDistance is true, it will order the listings by distance. If false, it will order by price.
    // If orderByPriceAsc is true, it will order the listings by price ascending. If false, it will order by price descending.
    public static List<Dates> getListingWithinDistance(float longitude, float latitude, float distance, boolean orderByDistance, boolean orderByPriceAsc, String startDate, String endDate, int lowestPrice, int highestPrice, List<String> amenities) {
        createDatesWithAmenitiesView();
        String orderBy = orderByDistance ? "DISTANCE" : (orderByPriceAsc ? "price ASC" : "price DESC");
        String queryFilter = generateAllQueries(startDate, endDate, lowestPrice, highestPrice, amenities);
        String query = "SELECT listingId, (6371 * acos(cos(radians(" + latitude + ")) " +
                "* cos(radians(latitude)) * cos(radians(longitude) - radians(" + longitude + ")) + sin(radians(" + latitude + ")) * sin(radians(latitude)))) AS DISTANCE " +
                "FROM Dates WHERE available = 1 " + queryFilter + " HAVING DISTANCE < " + distance + " ORDER BY " + orderBy;
        return getDates(query);
    }


    public static List<Dates> getListingByPostalCode(String postalCode, boolean orderByPriceAsc, String startDate, String endDate, int lowestPrice, int highestPrice, List<String> amenities) {
        createDatesWithAmenitiesView();
        String orderBy = (orderByPriceAsc ? "price ASC" : "price DESC");
        String queryFilter = generateAllQueries(startDate, endDate, lowestPrice, highestPrice, amenities);
        String query = "SELECT listingId FROM dates WHERE available = 1 AND LEFT(postalCode, 5) = LEFT('" + postalCode + "', 5) " + queryFilter + " ORDER BY " + orderBy;
        return getDates(query);
    }

    public static List<Dates> getListingByAddress(String address, boolean orderByPriceAsc, String startDate, String endDate, int lowestPrice, int highestPrice, List<String> amenities) {
        createDatesWithAmenitiesView();
        String orderBy = (orderByPriceAsc ? "price ASC" : "price DESC");
        String queryFilter = generateAllQueries(startDate, endDate, lowestPrice, highestPrice, amenities);
        String query = "SELECT listingId FROM dates WHERE available = 1 AND address = '" + address + "' " + queryFilter + " ORDER BY " + orderBy;
        return getDates(query);
    }

    private static List<Dates> getDates(String query) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Dates> dates = new ArrayList<>();
            Dates prev = null;
            while (resultSet.next()) {
                Dates temp = DatesController.getDates(resultSet.getInt(1));
                if (temp != null && (prev == null || temp.getListingId() != prev.getListingId())) {
                    dates.add(temp);
                    prev = temp;
                }
            }
            resultSet.close();
            statement.close();
            return dates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}