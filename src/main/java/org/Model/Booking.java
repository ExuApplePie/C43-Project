package org.Model;

import java.text.SimpleDateFormat;

public class Booking {
    private int bookingId;
    private int listingId;
    private int guestId;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String startDate;
    private String endDate;
    private int score;
    private String comment;

    public Booking(int bookingId, int listingId, int guestId, String startDate, String endDate, int score, String comment) {
        this.bookingId = bookingId;
        this.listingId = listingId;
        this.guestId = guestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.score = score;
        this.comment = comment;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getListingId() {
        return listingId;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", listingId=" + listingId +
                ", guestId=" + guestId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
