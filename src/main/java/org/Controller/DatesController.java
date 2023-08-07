package org.Controller;

import org.Model.Amenity;
import org.Model.Dates;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatesController {
    private int listingId;
    private int hostId;
    private String type;
    private float longitude;
    private float latitude;
    private String postalCode;
    private String country;
    private String city;
    private String address;
    private String date;
    private boolean available;
    private int price;
    private String amenityName;
    private int quantity;
    private int amenityId;

    static Connection conn = JdbcSqlServerConnection.conn;

    public static Dates getDates(int listingId) {
        Dates dates = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dates WHERE listingId = " + listingId);
            while (resultSet.next()) {
                dates = (new Dates(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getBoolean(11), resultSet.getInt(12), resultSet.getInt(14), resultSet.getString(15), resultSet.getInt(16)));
            }
            return dates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}