package org.bottleProject.dao;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;

import java.util.List;

public interface BottleDao extends Dao<Bottle> {

    List<Bottle> filterBy(BottleFilterDto bottleFilterDto);

    Integer countAllFilterBottle(BottleFilterDto bottleFilterDto);
}
