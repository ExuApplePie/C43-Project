package org.Model;

import static org.Model.DateParser.formatDate;

// join table for listing and availability and amenities - view only
public class Dates {
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
    private int quantity;
    private String amenityName;
    private int amenityId;


    public Dates(int listingId, int hostId, String type, float longitude, float latitude, String postalCode, String country, String city, String address, String date, boolean available, int price, int quantity, String amenityName, int amenityId) {
        this.listingId = listingId;
        this.hostId = hostId;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.address = address;
        this.date = date;
        this.available = available;
        this.price = price;
        this.quantity = quantity;
        this.amenityName = amenityName;
        this.amenityId = amenityId;
    }

    public int getListingId() {
        return listingId;
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

    public String getAddress() {
        return address;
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

    public int getQuantity() {
        return quantity;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public int getAmenityId() {
        return amenityId;
    }
}
