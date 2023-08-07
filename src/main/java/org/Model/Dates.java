package org.Model;

import static org.Model.DateParser.formatDate;

// join table for listing and availability and amenities - view only
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
    private int amenityId;
    private String amenityName;
    private int quantity;

    public Dates(int listingId, String date, boolean available, int price, int hostId, String type, float longitude, float latitude, String postalCode, String country, String city, int amenityId, String amenityName, int quantity) {
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
        this.amenityId = amenityId;
        this.amenityName = amenityName;
        this.quantity = quantity;
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

    public int getAmenityId() {
        return amenityId;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public int getQuantity() {
        return quantity;
    }
}
