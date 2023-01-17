package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.Objects;

//Entity
//This is entity to create an instance of bottle of water
public class Bottle {

        private long bottleID;

        private String nameBottle;

        private LocalDateTime createDate;

        private int volume;

        private boolean CO2;

        private boolean plastic;

        private boolean reserved;

        private String producer;

        private int storageID;

        private int priceID;


    public Bottle() {

    }

    public Bottle(long bottleID, String nameBottle, int volume, boolean CO2, boolean plastic, LocalDateTime createDate, String producer, int storageID, int priceID) {
        this.bottleID = bottleID;
        this.nameBottle = nameBottle;
        this.volume = volume;
        this.CO2 = CO2;
        this.plastic = plastic;
        this.createDate = createDate;
        this.producer = producer;
        this.storageID = storageID;
        this.priceID = priceID;
    }

    public String getNameBottle() {
                return nameBottle;
        }

        public void setNameBottle(String nameBottle) {
                this.nameBottle = nameBottle;
        }

        public int getPriceID() {
                return priceID;
        }

        public void setPriceID(int priceID) {
                this.priceID = priceID;
        }

        public long getBottleID() {
                return bottleID;
        }

        public void setBottleID(long bottleID) {
                this.bottleID = bottleID;
        }

        public LocalDateTime getCreateDate() {
                return createDate;
        }

        public void setCreateDate(LocalDateTime createDate) {
                this.createDate = createDate;
        }

        public int getVolume() {
                return volume;
        }

        public void setVolume(int volume) {
                this.volume = volume;
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

        public boolean isReserved() {
                return reserved;
        }

        public void setReserved(boolean reserved) {
                this.reserved = reserved;
        }

        public String getProducer() {
                return producer;
        }

        public void setProducer(String producer) {
                this.producer = producer;
        }

        public int getStorageID() {
                return storageID;
        }

        public void setStorageID(int storageID) {
                this.storageID = storageID;
        }

        @Override
        public String toString() {
                return "Bottle{" +
                        "bottleID=" + bottleID +
                        ", name=" + nameBottle +
                        ", createDate=" + createDate +
                        ", volumeID=" + volume +
                        ", CO2=" + CO2 +
                        ", plastic=" + plastic +
                        ", producer=" + producer +
                        ", storage=" + storageID +
                        ", priceID=" + priceID +
                        '}';
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                Bottle bottle = (org.bottleProject.entity.Bottle) obj;

                boolean idMatches = Objects.equals(bottleID, bottle.bottleID);
                boolean allFieldsMach = nameBottle.equals(bottle.nameBottle) && createDate.equals(bottle.createDate);

                return idMatches && allFieldsMach;
        }
}
