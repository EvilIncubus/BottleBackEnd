package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.Objects;

//Entity
//This is entity to create an instance of bottle of water
public class Bottle {

        private long bottleId;

        private String nameBottle;

        private LocalDateTime createDate;

        private int volumeId;

        private boolean soda;

        private boolean plastic;

        private boolean reserved;

        private String producer;

        private int storageId;



    public Bottle() {

    }

    public Bottle(long bottleId, String nameBottle, int volumeId, boolean soda, boolean plastic, LocalDateTime createDate, String producer, int storageId) {
        this.bottleId = bottleId;
        this.nameBottle = nameBottle;
        this.volumeId = volumeId;
        this.soda = soda;
        this.plastic = plastic;
        this.createDate = createDate;
        this.producer = producer;
        this.storageId = storageId;
    }

    public String getNameBottle() {
                return nameBottle;
        }

        public void setNameBottle(String nameBottle) {
                this.nameBottle = nameBottle;
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

        public boolean isSoda() {
                return soda;
        }

        public void setSoda(boolean soda) {
                this.soda = soda;
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

        public int getStorageId() {
                return storageId;
        }

        public void setStorageId(int storageId) {
                this.storageId = storageId;
        }

        @Override
        public String toString() {
                return "Bottle{" +
                        "bottleId=" + bottleId +
                        ", name=" + nameBottle +
                        ", createDate=" + createDate +
                        ", volumeId=" + volumeId +
                        ", soda=" + soda +
                        ", plastic=" + plastic +
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
