package org.bottleProject.entity;

public class Profile {
    private long profileId;
    private String firstName;
    private String lastName;
    private int addressId;
    private String phoneNumber;
    private String company;

    public Profile(long profileId, String firstName, String lastName, int addressId, String phoneNumber, String company) {
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.phoneNumber = phoneNumber;
        this.company = company;
    }

    public Profile() {

    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int address) {
        this.addressId = addressId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
