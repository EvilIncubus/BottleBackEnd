package org.bottleProject.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SearchOrderDto {

    private int customerId;
    private List<String> deliveryAddress;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public SearchOrderDto(int customerId, List<String> deliveryAddress, LocalDateTime startDate, LocalDateTime endDate) {
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<String> getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(List<String> deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
