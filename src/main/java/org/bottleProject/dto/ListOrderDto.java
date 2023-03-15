package org.bottleProject.dto;

public class ListOrderDto {
    private int orderId;
    private int size;
    private int page;

    public ListOrderDto(int orderId, int size, int page) {
        this.orderId = orderId;
        this.size = size;
        this.page = page;
    }

    public ListOrderDto() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
