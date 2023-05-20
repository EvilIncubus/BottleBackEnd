package org.bottleProject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private long orderId;

    private int profileId;

    private int deliveryAddressId;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = ("dd:MM:yyyy hh:mm:ss"))
    private LocalDateTime createdDate;

    private int statusId;

    private String operatorEmail;


    public Order(long orderId, int profileId, int deliveryAddressId, LocalDateTime createdDate, int statusId, String operatorId) {
        this.orderId = orderId;
        this.profileId = profileId;
        this.deliveryAddressId = deliveryAddressId;
        this.createdDate = createdDate;
        this.statusId = statusId;
        this.operatorEmail = operatorId;
    }

    public  Order(){
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderID) {
        this.orderId = orderID;
    }

    public int getProfileId() {
        return profileId;
    }

    public int getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(int deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorId) {
        this.operatorEmail = operatorId;
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "orderId=" + orderId +
                ", customerId=" + profileId +
                ", deliveryAddress=" + deliveryAddressId +
                ", localDateTime=" + createdDate +
                ", statusId=" + statusId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (org.bottleProject.entity.Order) obj;

        boolean idMatches = Objects.equals(orderId,order.orderId);
        boolean allFieldsMach = profileId == order.profileId;

        return idMatches && allFieldsMach;
    }

}
