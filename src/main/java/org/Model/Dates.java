package org.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> amenities = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();



    public Dates(int listingId, int hostId, String type, float longitude, float latitude, String postalCode, String country, String city, String address, String date, boolean available, int price, String amenities, String quantities) {
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
        this.amenities = Arrays.asList(amenities.split(",")); // split string into list
        String[] quantityArray = quantities.split(",");
        for (String quantity : quantityArray) {
            this.quantities.add(Integer.parseInt(quantity));
        }
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

    public List<String> getAmenities() {
        return amenities;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "listingId=" + listingId +
                ", hostId=" + hostId +
                ", type='" + type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", available=" + available +
                ", price=" + price +
                ", amenities=" + amenities +
                ", quantities=" + quantities +
                '}';
    }
}