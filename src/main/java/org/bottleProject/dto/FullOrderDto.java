package org.bottleProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class FullOrderDto {

    private int orderId;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = ("dd:MM:yyyy hh:mm:ss"))
    private LocalDateTime createdDate;
    private String email;
    private String company;
    private String status;
    private String address;
    private int profileId;

    public  FullOrderDto(){}

    public FullOrderDto(int orderId, LocalDateTime createdDate, String email, String status, String address, String company, int profileId) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.email = email;
        this.status = status;
        this.address = address;
        this.company = company;
        this.profileId = profileId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
