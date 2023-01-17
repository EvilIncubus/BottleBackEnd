package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {

    private long orderID;

     private int customerID;

    private String deliveryAddress;

    private LocalDateTime localDateTime;

    private int statusID;

    private List<Bottle> bottles;

    public Order(long orderID, int customerID, String deliveryAddress, LocalDateTime localDateTime, int statusID, List<Bottle> bottles) {
    }

    public  Order(){
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List<Bottle> getBottles() {
        return bottles;
    }

    public void setBottles(List<Bottle> bottles) {
        this.bottles = bottles;
    }

    public void extendBottles(List<Bottle> bottles) {
        this.bottles.addAll(bottles);
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "orderID=" + orderID +
                ", Customer=" + customerID +
                ", deliveryAddress=" + deliveryAddress +
                ", localDateTime=" + localDateTime +
                ", StatusID=" + statusID +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (org.bottleProject.entity.Order) obj;

        boolean idMatches = Objects.equals(orderID,order.orderID);
        boolean allFieldsMach = customerID == order.customerID;

        return idMatches && allFieldsMach;
    }

}
