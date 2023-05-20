package org.bottleProject.dao;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("integration_test")
public class BottleDaoImplTest {

    @Autowired
    private BottleDao bottleImplementDao;

    //Test to check if bottle is created successful
    @Test
    public void testCreateAndDelete() {

        Bottle bottle =new Bottle();
        bottle.setNameBottle("Peace");
        bottle.setVolumeId(4);
        bottle.setPlastic(true);
        bottle.setCreateDate(LocalDateTime.now().withNano(0));
        bottle.setStock(15);
        bottle.setProducer("Drink water SRL");
        bottle.setStorageId(4);
        Bottle bottle1 = bottleImplementDao.create(bottle);
        bottle.setBottleId(bottle1.getBottleId());
        assertEquals(bottle1, bottle);
        bottleImplementDao.removeById(bottle.getBottleId());
        assertNull(bottleImplementDao.findById(bottle.getBottleId()));
    }

    //Test to check if bottles is getting all in correct form
    @Test
    public void testGetAll() {
        List<Bottle> bottles = bottleImplementDao.getAll(1,1);
        System.out.println(bottles);
        assertNotNull(bottles);
    }

    //Test to check if function correct find bottle by id
    @Test
    public void testFindById() {
        Bottle bottle = bottleImplementDao.findById(1L);
        System.out.println(bottle);
        assertNotNull(bottle);
    }

    //Test to check in function make correct update for bottle price
    @Test
    public void testUpdate() {
        Bottle bottle = bottleImplementDao.findById(1L);
        Bottle bottle1 = bottleImplementDao.update(bottle, 1L);

        assertNotNull(bottle1);
        assertEquals(bottle, bottle1);
        bottleImplementDao.update(bottle, 1L);
    }

    @Test
    public void testFilter() {
        List<String> categories = new ArrayList<>();
        categories.add("Coca Cola");
        categories.add("Sprite");
        BottleFilterDto bottleFilterDto = new BottleFilterDto();
        List<Bottle> bottles = bottleImplementDao.filterBy(bottleFilterDto);
        System.out.println(bottles);
        assertNotNull(bottles);
    }

    @Test
    public void testCountFilter(){
        List<String> categories = new ArrayList<>();
        categories.add("Coca Cola");
        categories.add("Sprite");
        BottleFilterDto bottleFilterDto = new BottleFilterDto();
        int count = bottleImplementDao.countAllFilterBottle(bottleFilterDto);
        System.out.println(count);
    }
}
