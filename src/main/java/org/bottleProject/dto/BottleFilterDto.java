package org.bottleProject.dto;

import org.bottleProject.dto.enums.BottleSortBy;

public class BottleFilterDto {
    private int page;
    private int size;
    private BottleSortBy sortBy;

    public BottleFilterDto(int page,int size, BottleSortBy sortBy) {
        this.page = page;
        this.sortBy = sortBy;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public BottleSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(BottleSortBy sortBy) {
        this.sortBy = sortBy;
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
