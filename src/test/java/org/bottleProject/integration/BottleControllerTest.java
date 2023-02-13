package org.bottleProject.integration;

import org.bottleProject.controller.BottleController;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.dto.enums.BottleSortBy;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("integration_test")
public class BottleControllerTest {
    @Autowired
    BottleController bottleController;

    @Test
    public void createBottleTest(){
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
        ResponseEntity<Bottle> bottle1 = bottleController.createBottle(bottle);
        assertNotNull(bottle1.getBody());
        bottle.setBottleId(Objects.requireNonNull(bottle1.getBody()).getBottleId());
        assertEquals(bottle, bottle1.getBody());
    }

    @Test
    public void getBottleByIdTest(){
        assertNotNull(bottleController.getBottleById(1));
    }

    @Test
    public void getAllFilterBottleTest(){
        BottleFilterDto bottleFilterDto = new BottleFilterDto(1, 1, BottleSortBy.SODA);
        ResponseEntity<Page<Bottle>> response = bottleController.getListOfBottles(bottleFilterDto);
        assertNotNull((response.getBody()).getContent());
    }
}
