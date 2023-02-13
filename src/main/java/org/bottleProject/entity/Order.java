package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {

    private long orderId;

    private int customerId;

    private String deliveryAddress;

    private LocalDateTime curentDate;

    private int statusId;


    public Order(long orderId, int customerId, String deliveryAddress, LocalDateTime curentDate, int statusId, List<Bottle> bottles) {
    }

    public  Order(){
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderID) {
        this.orderId = orderID;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getCurentDate() {
        return curentDate;
    }

    public void setCurentDate(LocalDateTime curentDate) {
        this.curentDate = curentDate;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
                ", customerId=" + customerId +
                ", deliveryAddress=" + deliveryAddress +
                ", localDateTime=" + curentDate +
                ", statusId=" + statusId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (org.bottleProject.entity.Order) obj;

        boolean idMatches = Objects.equals(orderId,order.orderId);
        boolean allFieldsMach = customerId == order.customerId;

        return idMatches && allFieldsMach;
    }

}
