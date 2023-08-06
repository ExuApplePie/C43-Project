package org.Model;

public class ListingAmenity {
    private int listingId;
    private int amenityId;
    private int quantity;

    public ListingAmenity(int listingId, int amenityId, int quantity) {
        this.listingId = listingId;
        this.amenityId = amenityId;
        this.quantity = quantity;
    }

    public int getListingId() {
        return listingId;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
