package org.bottleProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OrderAllFilterDto {
    private int page;
    private int size;
    private Integer status;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = ("dd:MM:yyyy hh:mm:ss"))
    private LocalDateTime fromDate;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = ("dd:MM:yyyy hh:mm:ss"))
    private LocalDateTime toDate;
    private String deliveryAddress;

    public OrderAllFilterDto(int page, int size, Integer status, LocalDateTime fromDate, LocalDateTime toDate, String deliveryAddress) {
        this.page = page;
        this.size = size;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.deliveryAddress = deliveryAddress;
    }

    public OrderAllFilterDto() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getOffset() {
        return (page - 1) * size;
    }
}
