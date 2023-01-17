package org.bottleProject.dao;

import org.bottleProject.dto.BottleFilterDto;

import java.util.List;

public interface BottleDao<Bottle> extends Dao<Bottle> {

    List<Bottle> filterBy(BottleFilterDto bottleFilterDto);

}
