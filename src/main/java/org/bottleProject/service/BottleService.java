package org.bottleProject.service;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Page;

import java.util.List;

public interface BottleService {
    Bottle createBottle(Bottle bottle);

    Bottle getBottleById(long bottleId);

    Page<Bottle> getListOfBottleByCategory(BottleFilterDto bottleFilterDto);
}
