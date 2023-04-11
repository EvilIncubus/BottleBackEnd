package org.bottleProject.dto;

import org.bottleProject.entity.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateUserDto {
    private String email;
    private String password;
    private List<Role> roles;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String company;
    private String profilePhoto;

    public CreateUserDto() {
    }

    public CreateUserDto(String email, String password, List<Role> roles, String firstName, String lastName, String address, String phoneNumber, String company, String profilePhoto) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
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
