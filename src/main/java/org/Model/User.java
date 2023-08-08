package org.Model;

import static org.Model.DateParser.formatDate;

public class User {
    private int userId;
    private String occupation;
    private String address;
    private String DOB;
    private int SIN;
    private String name;
    // when manually creating a User object, userID is not known yet
    private int hostCancelCount = 0;
    private int guestCancels = 0;
    public User(int userId, String occupation, String address, String DOB, int SIN, String name, int hostCancelCount, int guestCancels) {
        this.userId = userId;
        this.occupation = occupation;
        this.address = address;
        this.DOB = formatDate(DOB);
        this.SIN = SIN;
        this.name = name;
        this.hostCancelCount = hostCancelCount;
        this.guestCancels = guestCancels;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {this.userId = userId;}

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {this.DOB = formatDate(DOB);}

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHostCancelCount() {
        return hostCancelCount;
    }

    public void setHostCancelCount(int hostCancelCount) {
        this.hostCancelCount = hostCancelCount;
    }

    public int getGuestCancels() {
        return guestCancels;
    }

    public void setGuestCancels(int guestCancels) {
        this.guestCancels = guestCancels;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", DOB='" + DOB + '\'' +
                ", SIN=" + SIN +
                ", name='" + name + '\'' +
                ", hostCancelCount=" + hostCancelCount +
                ", renterCancelCount=" + guestCancels +
                '}';
    }
}
