package org.bottleProject.dto;

public class OrderSearchAllDto {
    private int page;
    private int size;
    private String company;

    public OrderSearchAllDto(int page, int size, String company) {
        this.page = page;
        this.size = size;
        this.company = company;
    }

    public OrderSearchAllDto() {

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
