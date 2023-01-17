package org.bottleProject.entity;

import java.util.Objects;

public class User {
    private long userID;
    private String firstName;
    private String lastName;
    private String email;
    private int costumerID;
    private int roleID;
    private String phoneNumber;

    public User(long userID, String firstName, String lastName, String email, int costumerID, int roleID, String phoneNumber) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.costumerID = costumerID;
        this.roleID = roleID;
        this.phoneNumber = phoneNumber;
    }

    public User(){}

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "bottleID=" + userID +
                ", name=" + firstName +
                ", createDate=" + lastName +
                ", volumeID=" + email +
                ", CO2=" + costumerID +
                ", plastic=" + roleID +
                ", producer=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (org.bottleProject.entity.User) obj;

        boolean idMatches = Objects.equals(userID, user.userID);
        boolean allFieldsMach = email.equals(user.email) && phoneNumber.equals(user.phoneNumber);

        return idMatches && allFieldsMach;
    }
}
