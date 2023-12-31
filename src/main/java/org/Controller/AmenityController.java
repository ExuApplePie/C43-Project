package org.Controller;

import org.Model.Amenity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AmenityController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static Amenity getAmenity(int amenityId) {
        Amenity amenity = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM amenity WHERE amenityId = " + amenityId);
            while (resultSet.next()) {
                amenity = new Amenity(resultSet.getInt(1), resultSet.getString(2));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            return null;
        }
        return amenity;
    }
    
    public static Amenity getAmenity(String amenityName) {
        Amenity amenity = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM amenity WHERE name = " + amenityName);
            while (resultSet.next()) {
                amenity = new Amenity(resultSet.getInt(1), resultSet.getString(2));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            return null;
        }
        return amenity;
    }

    public static void addAmenity(Amenity amenity) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT IGNORE INTO amenity VALUES (" + "NULL, '" + amenity.getName() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                amenity.setAmenityId(resultSet.getInt(1));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAmenity(int amenityId) {
        try {
            conn.createStatement().executeUpdate("DELETE FROM amenity WHERE amenityId = " + amenityId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editAmenity(Amenity amenity) {
        try {
            conn.createStatement().executeUpdate("UPDATE amenity SET name = '" + amenity.getName() + "' WHERE amenityId = " + amenity.getAmenityId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
