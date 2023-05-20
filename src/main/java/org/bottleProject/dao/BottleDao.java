package org.bottleProject.dao;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;

import java.util.List;

public interface BottleDao extends Dao<Bottle> {

    List<Bottle> filterBy(BottleFilterDto bottleFilterDto);

    Integer countAllFilterBottle(BottleFilterDto bottleFilterDto);

    Integer countAllBottle();

    List<Bottle> searchBottle(String search, int size, int page);

    Integer countSearchBottle(String search);
}
