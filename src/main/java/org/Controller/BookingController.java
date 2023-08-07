package org.Controller;

import org.Model.Booking;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingController {
    static Connection conn = JdbcSqlServerConnection.conn;

    public static Booking getBooking(int bookingId) {
        Booking booking = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM booking WHERE bookingId = " + bookingId);
            while (resultSet.next()) {
                booking = new Booking(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    public static void addBooking(Booking booking) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT IGNORE INTO booking VALUES (" + "NULL, " + booking.getListingId() + ", " + booking.getGuestId() + ", '" + booking.getStartDate() + "', '" + booking.getEndDate() + "', " + booking.getScore() + ", '" + booking.getComment() + "')", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                booking.setBookingId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteBooking(int bookingId) {
        try {
            conn.createStatement().executeUpdate("DELETE FROM booking WHERE bookingId = " + bookingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editBooking(Booking booking) {
        try {
            conn.createStatement().executeUpdate("UPDATE booking SET startDate = " + booking.getStartDate() + "', endDate = '" + booking.getEndDate() + "', score = " + booking.getScore() + ", comment = '" + booking.getComment() + "' WHERE bookingId = " + booking.getBookingId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
