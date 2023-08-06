package org.Model;

import java.text.SimpleDateFormat;
import static org.Model.DateParser.formatDate;

public class User {
    private int userId;
    private String occupation;
    private String address;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String DOB;
    private int SIN;
    private String name;
    // when manually creating a User object, userID is not known yet
    public User(int userId, String occupation, String address, String DOB, int SIN, String name) {
        this.userId = userId;
        this.occupation = occupation;
        this.address = address;
        this.DOB = formatDate(DOB);
        this.SIN = SIN;
        this.name = name;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", DOB='" + DOB + '\'' +
                ", SIN=" + SIN +
                ", name='" + name + '\'' +
                '}';
    }
}
