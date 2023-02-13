package org.bottleProject.service.impl;

import org.bottleProject.dao.BottleDao;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.BottleService;
import org.springframework.stereotype.Service;


@Service
public class BottleServiceImpl implements BottleService {

    private final BottleDao bottleDao;

    public BottleServiceImpl(BottleDao bottleDao) {
        this.bottleDao = bottleDao;
    }

    @Override
    public Bottle createBottle(Bottle bottle) {
        return bottleDao.create(bottle);
    }

    @Override
    public Bottle getBottleById(long bottleId) {
        return bottleDao.findById(bottleId);
    }

    @Override
    public Page<Bottle> getListOfBottleByCategory(BottleFilterDto bottleFilterDto) {
        return new Page<>(bottleDao.filterBy(bottleFilterDto), bottleDao.countAllFilterBottle(bottleFilterDto), bottleFilterDto.getPage(), bottleFilterDto.getSize());
    }
}
