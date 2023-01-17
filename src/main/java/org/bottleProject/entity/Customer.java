package org.bottleProject.entity;

import java.util.Objects;

//This class represent entity of customer who want tu buy bottles with water
//Entity
public class Customer {

    private long customerID;

    private String nameCompany;

    private String email;

    private String address;

    private String phoneNumber;

    public Customer(long customerID, String contactPerson, String email, String address, String phoneNumber, int userID) {
    }

    public Customer() {

    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", email=" + email +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (org.bottleProject.entity.Customer) obj;

        boolean idMatches = Objects.equals(customerID,customer.customerID);
        boolean allFieldsMach = phoneNumber.equals(customer.phoneNumber);

        return idMatches && allFieldsMach;
    }
}
