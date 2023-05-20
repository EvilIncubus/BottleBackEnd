package org.bottleProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OrderFilterDto {
    private int page;
    private int size;
    private String operatorEmail;
    private List<String> status = new ArrayList<>();
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fromDate = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime toDate = LocalDateTime.now();

    public OrderFilterDto(int page, int size, String operatorEmail, List<String> status, LocalDateTime fromDate, LocalDateTime toDate) {
        this.page = page;
        this.size = size;
        this.operatorEmail = operatorEmail;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public OrderFilterDto() {

    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
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

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
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

    public int getOffset() {
        return (page - 1) * size;
    }
}
