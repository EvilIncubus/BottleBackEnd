package org.bottleProject.service.impl;

import org.apache.catalina.LifecycleState;
import org.bottleProject.dao.BottleDao;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.BottleService;
import org.springframework.stereotype.Service;

import java.util.List;


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
        List<Bottle> bottleList = bottleDao.filterBy(bottleFilterDto);
        return new Page<>(bottleList, bottleDao.countAllFilterBottle(bottleFilterDto), bottleFilterDto.getPage(), bottleFilterDto.getSize());
    }

    @Override
    public Page<Bottle> getListOfBottle(int page, int size) {
        return new Page<>(bottleDao.getAll(size, (page - 1) * size), bottleDao.countAllBottle(), page, size);
    }

    @Override
    public Page<Bottle> getSearchBottleByBrand(String search, int page, int size) {
        return new Page<>(bottleDao.searchBottle(search ,size, (page - 1) * size), bottleDao.countSearchBottle(search), page, size);
    }
}
