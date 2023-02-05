package org.bottleProject.service;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;

import java.util.List;

public interface BottleService {
    void createOrder(Bottle bottle);

    Bottle getBottleById(long bottleId);

    List<Bottle> getListOfBottleByCategory(BottleFilterDto bottleFilterDto);
}
