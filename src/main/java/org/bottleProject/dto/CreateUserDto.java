package org.bottleProject.dto;

import org.bottleProject.entity.Role;

import java.util.List;

public class CreateUserDto {
    private String email;
    private String password;
    private List<Role> roles;
    private long profileId;
    private String firstName;
    private String lastName;
    private int addressId;
    private String phoneNumber;
    private String company;

    public CreateUserDto(String email, String password, List<Role> roles, long profileId, String firstName, String lastName, int addressId, String phoneNumber, String company) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.phoneNumber = phoneNumber;
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public void setAddressId(int addressId) {
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
