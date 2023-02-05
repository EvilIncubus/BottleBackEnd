package org.bottleProject.service.impl;

import org.bottleProject.dao.BottleDao;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
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
    public void createOrder(Bottle bottle) {
        bottleDao.create(bottle);
    }

    @Override
    public Bottle getBottleById(long bottleId) {
        return bottleDao.findById(bottleId);
    }

    @Override
    public List<Bottle> getListOfBottleByCategory(BottleFilterDto bottleFilterDto) {
        return bottleDao.filterBy(bottleFilterDto);
    }
}
