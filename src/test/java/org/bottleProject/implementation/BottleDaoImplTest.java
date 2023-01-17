package org.bottleProject.implementation;

import org.bottleProject.connectionDB.DBConfig;
import org.bottleProject.dao.impl.BottleDaoImpl;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.dto.enums.BottleSortBy;
import org.bottleProject.entity.Bottle;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class BottleDaoImplTest {

    BottleDaoImpl bottleImplementDao = new BottleDaoImpl(new DBConfig().dataSource());
    Bottle bottle = new Bottle();

    //Test to check if bottle is created successful
    @Test
    public void testCreateAndDelete() {
        bottle.setNameBottle("Peace");
        bottle.setVolume(4);
        bottle.setCO2(true);
        bottle.setPlastic(true);
        bottle.setPriceID(2);
        bottle.setCreateDate(LocalDateTime.now());
        bottle.setReserved(true);
        bottle.setProducer("Drink water SRL");
        bottle.setStorageID(4);
        long id = bottleImplementDao.create(bottle);

        System.out.println(bottle);
        Bottle bottle1 = bottleImplementDao.findById(id);
        System.out.println(bottle1);
        assertNotEquals(bottle1, bottle);
        bottleImplementDao.removeById(id);
        assertNull(bottleImplementDao.findById(id));
    }

    //Test to check if bottles is getting all in correct form
    @Test
    public void testGetAll() {
        List<Bottle> bottles = bottleImplementDao.getAll();
        System.out.println(bottles);
        assertNotNull(bottles);
    }

    //Test to check if function correct find bottle by id
    @Test
    public void testFindById() {
        bottle = bottleImplementDao.findById(1L);
        System.out.println(bottle);
        assertNotNull(bottle);
    }

    //Test to check in function make correct update for bottle price
    @Test
    public void testUpdate() {
        bottle = bottleImplementDao.findById(1L);
        int oldPrice = bottle.getPriceID();
        bottle.setPriceID(5);
        Bottle bottle1 = bottleImplementDao.update(bottle, 1L);

        assertNotNull(bottle1);
        assertEquals(bottle, bottle1);
        bottle1.setBottleID(oldPrice);
        bottleImplementDao.update(bottle, 1L);
    }

    @Test
    public void testFilter() {
        BottleFilterDto bottleFilterDto = new BottleFilterDto(1, 4, BottleSortBy.VolumeID);
        List<Bottle> bottles = bottleImplementDao.filterBy(bottleFilterDto);
        System.out.println(bottles);
        assertNotNull(bottles);
    }
}
