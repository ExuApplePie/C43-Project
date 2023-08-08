package org.Model;

import static org.Model.DateParser.formatDate;

public class Availability {
    private int listingId;
    private String date;
    private boolean available;
    private int price;

    public Availability(int listingId, String date, boolean available, int price) {
        this.listingId = listingId;
        this.date = formatDate(date);
        this.available = available;
        this.price = price;
    }

    public int getListingId() {
        return listingId;
    }

    public String getDate() {
        return date;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "listingId=" + listingId +
                ", date='" + date + '\'' +
                ", available=" + available +
                ", price=" + price +
                '}';
    }
}
