package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private long orderId;

    private int profileId;

    private int addressId;

    private LocalDateTime createdDate;

    private int statusId;


    public Order(long orderId, int profileId, int addressId, LocalDateTime createdDate, int statusId) {
        this.orderId = orderId;
        this.profileId = profileId;
        this.addressId = addressId;
        this.createdDate = createdDate;
        this.statusId = statusId;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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

    @Override
    public String toString() {
        return "Bottle{" +
                "orderId=" + orderId +
                ", customerId=" + profileId +
                ", deliveryAddress=" + addressId +
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
