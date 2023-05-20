package org.bottleProject.dto;

import java.time.LocalDateTime;

public class OrderSearchDto {
    private int page;
    private int size;
    private String operatorEmail;
    private String company;

    public OrderSearchDto(int page, int size, String operatorEmail, String company) {
        this.page = page;
        this.size = size;
        this.company = company;
        this.operatorEmail = operatorEmail;
    }

    public OrderSearchDto() {

    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public int getOffset() {
        return (page - 1) * size;
    }
}
