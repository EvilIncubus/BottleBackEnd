package org.example.entity;

import org.example.entity.enums.Volume;

import java.time.LocalDateTime;
import java.util.Objects;

//Entity
//This is entity to create an instance of bottle of water
public class Bottle {

        private long bottleID;

        private String name;

        private LocalDateTime localDateTime;

        private int volume;

        private boolean CO2;

        private boolean plastic;

        private boolean reserved;

        private String producer;

        private int storageID;

        private int priceID;


    public Bottle() {

    }

    public Bottle(long bottleID, String name, int volume, boolean CO2, boolean plastic, LocalDateTime localDateTime, String producer, int storageID, int priceID) {
        this.bottleID = bottleID;
        this.name = name;
        this.volume = volume;
        this.CO2 = CO2;
        this.plastic = plastic;
        this.localDateTime = localDateTime;
        this.producer = producer;
        this.storageID = storageID;
        this.priceID = priceID;
    }

    public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
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

        public LocalDateTime getLocalDateTime() {
                return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
                this.localDateTime = localDateTime;
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
                        ", name=" + name +
                        ", createDate=" + localDateTime +
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
                Bottle bottle = (org.example.entity.Bottle) obj;

                boolean idMatches = Objects.equals(bottleID, bottle.bottleID);
                boolean allFieldsMach = name.equals(bottle.name) && localDateTime.equals(bottle.localDateTime);

                return idMatches && allFieldsMach;
        }
}
