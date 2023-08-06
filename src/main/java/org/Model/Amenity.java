package org.Model;

public class Amenity {
    private int amenityId;
    private String name;

    public Amenity(int amenityId, String name) {
        this.amenityId = amenityId;
        this.name = name;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
