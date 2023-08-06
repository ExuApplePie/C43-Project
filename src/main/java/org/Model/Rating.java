package org.Model;

import java.text.SimpleDateFormat;

public class Rating {
    private int renterId;
    private int hostId;
    private int score;
    private String date;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String comment;

    public Rating(int renterId, int hostId, int score, String date, String comment) {
        this.renterId = renterId;
        this.hostId = hostId;
        this.score = score;
        this.date = simpleDateFormat.format(date);
        this.comment = comment;
    }

    public int getRenterId() {
        return renterId;
    }

    public int getHostId() {
        return hostId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = simpleDateFormat.format(date);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
