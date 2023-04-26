package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.Objects;

//Entity
//This is entity to create an instance of bottle of water
public class Bottle {

    private long bottleId;

    private String nameBottle;

    private boolean sugar;

    private LocalDateTime createDate;

    private int volumeId;

    private int stock;

    private String producer;

    private int storageId;

    private int bottleCategoryId;

    private int bottlePackagingId;

    private String bottlePhoto;


    public Bottle() {
    }

    public Bottle(long bottleId, String nameBottle, boolean sugar, LocalDateTime createDate, int volumeId, int stock, String producer, int storageId, int bottleCategoryId, int bottlePackagingId, String bottlePhoto) {
        this.bottleId = bottleId;
        this.nameBottle = nameBottle;
        this.sugar = sugar;
        this.createDate = createDate;
        this.volumeId = volumeId;
        this.stock = stock;
        this.producer = producer;
        this.storageId = storageId;
        this.bottleCategoryId = bottleCategoryId;
        this.bottlePackagingId = bottlePackagingId;
        this.bottlePhoto = bottlePhoto;
    }

    public String getBottlePhoto() {
        return bottlePhoto;
    }

    public void setBottlePhoto(String bottlePhoto) {
        this.bottlePhoto = bottlePhoto;
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

    public long getBottleId() {
        return bottleId;
    }

    public void setBottleId(long bottleId) {
        this.bottleId = bottleId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
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

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public int getBottleCategoryId() {
        return bottleCategoryId;
    }

    public void setBottleCategoryId(int bottleCategoryId) {
        this.bottleCategoryId = bottleCategoryId;
    }

    public int getBottlePackagingId() {
        return bottlePackagingId;
    }

    public void setBottlePackagingId(int bottlePackagingId) {
        this.bottlePackagingId = bottlePackagingId;
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "bottleId=" + bottleId +
                ", name=" + nameBottle +
                ", createDate=" + createDate +
                ", volumeId=" + volumeId +
                ", producer=" + producer +
                ", storage=" + storageId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bottle bottle = (org.bottleProject.entity.Bottle) obj;

        boolean idMatches = Objects.equals(bottleId, bottle.bottleId);
        boolean allFieldsMach = nameBottle.equals(bottle.nameBottle) && createDate.equals(bottle.createDate);

        return idMatches && allFieldsMach;
    }
}
