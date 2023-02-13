package org.bottleProject.dto;

public class ListOrderDto {
    private String nameCompany;
    private int size;
    private int page;

    public ListOrderDto(String nameCompany, int size, int page) {
        this.nameCompany = nameCompany;
        this.size = size;
        this.page = page;
    }

    public ListOrderDto() {

    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return (page - 1) * size;
    }
}
