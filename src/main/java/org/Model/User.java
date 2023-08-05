package org.Model;

public class User {
    private int userId;
    private String occupation;
    private String address;
    private String DOB;
    private int SIN;
    public User(int userId, String occupation, String address, String DOB, int SIN) {
        this.userId = userId;
        this.occupation = occupation;
        this.address = address;
        this.DOB = DOB;
        this.SIN = SIN;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        this.DOB = DOB;
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }
}
