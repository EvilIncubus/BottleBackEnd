package org.bottleProject.service;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Page;

public interface BottleService {
    Bottle createBottle(Bottle bottle);

    Bottle getBottleById(long bottleId);

    Page<Bottle> getListOfBottleByCategory(BottleFilterDto bottleFilterDto);

    Page<Bottle> getListOfBottle(int page, int size);

    Page<Bottle> getSearchBottleByBrand(String search, int page, int size);
}
