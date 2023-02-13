package org.bottleProject.dao;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.dto.enums.BottleSortBy;
import org.bottleProject.entity.Bottle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
        bottle.setSoda(true);
        bottle.setPlastic(true);
        bottle.setPriceId(2);
        bottle.setCreateDate(LocalDateTime.now().withNano(0));
        bottle.setReserved(true);
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
        List<Bottle> bottles = bottleImplementDao.getAll();
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
        int oldPrice = bottle.getPriceId();
        bottle.setPriceId(5);
        Bottle bottle1 = bottleImplementDao.update(bottle, 1L);

        assertNotNull(bottle1);
        assertEquals(bottle, bottle1);
        bottle.setBottleId(oldPrice);
        bottleImplementDao.update(bottle, 1L);
    }

    @Test
    public void testFilter() {
        BottleFilterDto bottleFilterDto = new BottleFilterDto(1, 5, BottleSortBy.VOLUME_ID);
        List<Bottle> bottles = bottleImplementDao.filterBy(bottleFilterDto);
        System.out.println(bottles);
        assertNotNull(bottles);
    }
}
