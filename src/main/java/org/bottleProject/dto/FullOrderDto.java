package org.bottleProject.dto;

import java.time.LocalDateTime;

public class FullOrderDto {

    private int orderId;
    private LocalDateTime createdDate;
    private String email;
    private String status;
    private String deliveryAddress;

    public  FullOrderDto(){}

    public FullOrderDto(int orderId, LocalDateTime createdDate, String email, String status, String deliveryAddress) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.email = email;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
