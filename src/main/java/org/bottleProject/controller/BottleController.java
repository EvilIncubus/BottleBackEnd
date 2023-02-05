package org.bottleProject.controller;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.service.BottleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/bottles")
public class BottleController {

    private final BottleService bottleService;

    public BottleController(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    @PostMapping("/createBottle")
    public ResponseEntity<String> createBottle(@RequestBody Bottle bottle) {
        try {
            bottleService.createOrder(bottle);
            return new ResponseEntity<>("Bottle was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBottleById/{bottleID}")
    public ResponseEntity<Bottle> getListOfOrders(@PathVariable int bottleId) {
        try {
            Bottle bottle = bottleService.getBottleById(bottleId);

            if (bottle == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bottle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListOfBottlesByCategory/{customerId}")
    public ResponseEntity<List<Bottle>> getListOfOrders(@PathVariable BottleFilterDto bottleFilterDto) {
        try {
            List<Bottle> bottles = bottleService.getListOfBottleByCategory(bottleFilterDto);

            if (bottles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bottles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
