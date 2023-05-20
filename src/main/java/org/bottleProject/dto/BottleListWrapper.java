package org.bottleProject.dto;

public class BottleListWrapper {
    private long bottleId;
    private int amountBottle;
    private String nameBottle;

    private boolean sugar;

    private double bottleVolume;

    private int stock;

    private String producer;

    private String storageSection;

    private String category;

    private String packaging;

    private int price;

    private String bottlePhoto;

    public BottleListWrapper() {
    }

    public BottleListWrapper(long bottleId, int amountBottle, String nameBottle, boolean sugar, double bottleVolume, int stock, String producer, String storageSection, String category, String packaging, int price, String bottlePhoto) {
        this.bottleId = bottleId;
        this.amountBottle = amountBottle;
        this.nameBottle = nameBottle;
        this.sugar = sugar;
        this.bottleVolume = bottleVolume;
        this.stock = stock;
        this.producer = producer;
        this.storageSection = storageSection;
        this.category = category;
        this.packaging = packaging;
        this.price = price;
        this.bottlePhoto = bottlePhoto;
    }

    public long getBottleId() {
        return bottleId;
    }

    public void setBottleId(long bottleId) {
        this.bottleId = bottleId;
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

    public boolean isSugar() {
        return sugar;
    }

    public void setSugar(boolean sugar) {
        this.sugar = sugar;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getBottleVolume() {
        return bottleVolume;
    }

    public void setBottleVolume(double bottleVolume) {
        this.bottleVolume = bottleVolume;
    }

    public String getStorageSection() {
        return storageSection;
    }

    public void setStorageSection(String storageSection) {
        this.storageSection = storageSection;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getBottlePhoto() {
        return bottlePhoto;
    }

    public void setBottlePhoto(String bottlePhoto) {
        this.bottlePhoto = bottlePhoto;
    }

    @Override
    public String toString() {
        return "FinalOrderDto{" +
                ", BottleID=" + bottleId +
                ", AmountBottle=" + amountBottle +
                ", NameBottle=" + nameBottle +
                '}';
    }
}
