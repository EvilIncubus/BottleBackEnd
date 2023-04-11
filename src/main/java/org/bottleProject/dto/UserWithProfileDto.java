package org.bottleProject.dto;

public class UserWithProfileDto {

    private int profileId;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String company;
    private String accountStatus;
    private String profilePhotoPath;

    public UserWithProfileDto(){};

    public UserWithProfileDto(int profileId, String email, String firstName, String lastName, String address, String phoneNumber, String company, String accountStatus, String profilePhotoPath) {
        this.profileId = profileId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.accountStatus = accountStatus;
        this.profilePhotoPath = profilePhotoPath;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
