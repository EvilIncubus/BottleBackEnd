package org.bottleProject.dto;

import java.util.ArrayList;
import java.util.List;

public class BottleFilterDto {
    private int page;
    private int size;
    private List<String> categories = new ArrayList<>();
    private int minPrice = 0;
    private int maxPrice = 0;
    private List<String> packaging = new ArrayList<>();
    private List<Double> volume = new ArrayList<>();
    private Boolean sugar;

    public BottleFilterDto() {
    }

    public BottleFilterDto(int page, int size, List<String> categories, int minPrice, int maxPrice, List<String> packaging, List<Double> volume, Boolean sugar) {
        this.page = page;
        this.size = size;
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.packaging = packaging;
        this.volume = volume;
        this.sugar = sugar;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<String> getPackaging() {
        return packaging;
    }

    public void setPackaging(List<String> packaging) {
        this.packaging = packaging;
    }

    public List<Double> getVolume() {
        return volume;
    }

    public void setVolume(List<Double> volume) {
        this.volume = volume;
    }

    public Boolean getSugar() {
        return sugar;
    }

    public void setSugar(Boolean sugar) {
        this.sugar = sugar;
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
