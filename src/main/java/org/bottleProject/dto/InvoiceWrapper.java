package org.bottleProject.dto;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceWrapper {
    private long orderID;
    private String email;
    private String deliveryAddress;
    private LocalDateTime curentDate;
    private String producer;
    private List<BottleListWrapper> bottleListDtoList;

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String nameCompany) {
        this.email = nameCompany;
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<BottleListWrapper> getBottleListDtoList() {
        return bottleListDtoList;
    }

    public void setBottleListDtoList(List<BottleListWrapper> bottleListDtoList) {
        this.bottleListDtoList = bottleListDtoList;
    }
}
