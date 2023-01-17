package org.example.dto;

import java.time.LocalDateTime;
public class BottleListWrapper {
    private long bottleID;
    private int amountBottle;
    private String nameBottle;
    private double size;
    private double price;
    private boolean CO2;
    private boolean plastic;
    private LocalDateTime dateTime;


    public long getBottleID() {
        return bottleID;
    }

    public void setBottleID(long bottleID) {
        this.bottleID = bottleID;
    }

    public int getAmountBottle() {
        return amountBottle;
    }

    public void setAmountBottle(int amountBottle) {
        this.amountBottle = amountBottle;
    }

    public String getNameBottle() {
        return nameBottle;
    }

    public void setNameBottle(String nameBottle) {
        this.nameBottle = nameBottle;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCO2() {
        return CO2;
    }

    public void setCO2(boolean CO2) {
        this.CO2 = CO2;
    }

    public boolean isPlastic() {
        return plastic;
    }

    public void setPlastic(boolean plastic) {
        this.plastic = plastic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public String toString() {
        return "FinalOrderDto{" +
                ", BottleID=" + bottleID +
                ", AmountBottle=" + amountBottle +
                ", NameBottle=" + nameBottle +
                ", Size=" + size +
                ", Price=" + price +
                ", CO2=" + CO2 +
                ", Plastic=" + plastic +
                ", Data/Time=" + dateTime +
                '}';
    }
}
