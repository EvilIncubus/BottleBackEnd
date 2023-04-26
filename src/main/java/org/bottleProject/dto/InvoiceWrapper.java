package org.bottleProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceWrapper {
    private long orderId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String company;
    private String profilePhotoPath;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = ("dd:MM:yyyy hh:mm:ss"))
    private LocalDateTime createdDate;
    private List<BottleListWrapper> bottleListDtoList;

    public InvoiceWrapper() {
    }

    public InvoiceWrapper(long orderId, String firstName, String lastName, String phoneNumber, String company, String profilePhotoPath, LocalDateTime createdDate, String producer, List<BottleListWrapper> bottleListDtoList) {
        this.orderId = orderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.profilePhotoPath = profilePhotoPath;
        this.createdDate = createdDate;
        this.bottleListDtoList = bottleListDtoList;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<BottleListWrapper> getBottleListDtoList() {
        return bottleListDtoList;
    }

    public void setBottleListDtoList(List<BottleListWrapper> bottleListDtoList) {
        this.bottleListDtoList = bottleListDtoList;
    }
}
