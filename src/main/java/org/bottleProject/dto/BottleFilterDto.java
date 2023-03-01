package org.bottleProject.dto;

import java.util.List;

public class BottleFilterDto {
    private int page;
    private int size;
    private List<String> categories;

    public BottleFilterDto(int page,int size, List<String> categories) {
        this.page = page;
        this.categories = categories;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getListOfCategories() {
        return categories;
    }

    public void setListOfCategories(List<String> categories) {
        this.categories = categories;
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
