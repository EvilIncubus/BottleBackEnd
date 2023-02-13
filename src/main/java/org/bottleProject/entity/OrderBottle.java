package org.bottleProject.entity;

public class OrderBottle {
    private long orderBottleId;
    private int orderId;
    private int bottleId;
    private int amountBottle;

    public OrderBottle(long orderBottleId, int orderId, int bottleId, int amountBottle) {
        this.orderBottleId = orderBottleId;
        this.orderId = orderId;
        this.bottleId = bottleId;
        this.amountBottle = amountBottle;
    }

    public OrderBottle() {

    }

    public long getOrderBottleId() {
        return orderBottleId;
    }

    public void setOrderBottleId(long orderBottleId) {
        this.orderBottleId = orderBottleId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBottleId() {
        return bottleId;
    }

    public void setBottleId(int bottleId) {
        this.bottleId = bottleId;
    }

    public int getAmountBottle() {
        return amountBottle;
    }

    public void setAmountBottle(int amountBottle) {
        this.amountBottle = amountBottle;
    }
}
