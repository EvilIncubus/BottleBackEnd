package org.example.dao;

import org.example.dto.BottleFilterDto;

import java.util.List;

public interface BottleDao<Bottle> extends Dao<Bottle> {

    List<Bottle> filterBy(BottleFilterDto bottleFilterDto);

}
