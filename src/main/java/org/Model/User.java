package org.Model;

import java.text.SimpleDateFormat;

public class User {
    private int userId;
    private String occupation;
    private String address;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String DOB;
    private int SIN;
    public User(int userId, String occupation, String address, String DOB, int SIN) {
        this.userId = userId;
        this.occupation = occupation;
        this.address = address;
        this.DOB = simpleDateFormat.format(DOB);
        this.SIN = SIN;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", DOB='" + DOB + '\'' +
                ", SIN=" + SIN +
                '}';
    }

    public int getUserId() {
        return userId;
    }

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

    public void setDOB(String DOB) {
        this.DOB = simpleDateFormat.format(DOB);
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }
}
