package org.bottleProject.dto;

public class CustomersQueryForOperatorDto {

    private String email;
    private int size;
    private int page;

    public CustomersQueryForOperatorDto() {
    }

    public CustomersQueryForOperatorDto(String email, int size, int page) {
        this.email = email;
        this.size = size;
        this.page = page;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
