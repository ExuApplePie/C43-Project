package org.Model;

import static org.Model.DateParser.formatDate;

// join table for listing and availability - view only
public class Dates {
    private int listingId;
    private String date;
    private boolean available;
    private int price;
    private int hostId;
    private String type;
    private float longitude;
    private float latitude;
    private String postalCode;
    private String country;
    private String city;

    public Dates(int listingId, String date, boolean available, int price, int hostId, String type, float longitude, float latitude, String postalCode, String country, String city) {
        this.listingId = listingId;
        this.date = formatDate(date);
        this.available = available;
        this.price = price;
        this.hostId = hostId;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
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

    public int getPrice() {
        return price;
    }

    public int getHostId() {
        return hostId;
    }

    public String getType() {
        return type;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
