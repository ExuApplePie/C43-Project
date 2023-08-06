package org.Model;

public class Listing {
    private int listingId;
    private int hostId;
    private String type;
    private float longitude;
    private float latitude;
    private String postalCode;
    private String country;
    private String city;

    public Listing(int listingId, int hostId, String type, float longitude, float latitude, String postalCode, String country, String city) {
        this.listingId = listingId;
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

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public int getHostId() {
        return hostId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "listingId=" + listingId +
                ", hostId=" + hostId +
                ", type='" + type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
