package org.bottleProject.dto;

import org.bottleProject.dto.enums.BottleSortBy;

public class BottleFilterDto {
    private int page;
    private int volumeId;
    private BottleSortBy sortBy;

    public BottleFilterDto(int page, int volumeId, BottleSortBy sortBy) {
        this.page = page;
        this.volumeId = volumeId;
        this.sortBy = sortBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    public BottleSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(BottleSortBy sortBy) {
        this.sortBy = sortBy;
    }

    public int getOffset() {
        return (page - 1) * volumeId;
    }
}
