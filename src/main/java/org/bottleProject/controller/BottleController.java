package org.bottleProject.controller;

import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.BottleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/bottles")
public class BottleController {

    private final BottleService bottleService;

    public BottleController(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping("/createBottle")
    public ResponseEntity<Bottle> createBottle(@RequestBody Bottle bottle) {
        try {
            Bottle bottleResponse = bottleService.createBottle(bottle);
            return new ResponseEntity<>(bottleResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @GetMapping("/getBottleById/{bottleId}")
    public ResponseEntity<Bottle> getBottleById(@PathVariable int bottleId) {
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


    @GetMapping("/getListOfBottles")
    public ResponseEntity<Page<Bottle>> getListOfBottles(@RequestParam int page,
                                                         @RequestParam int size) {
        try {
            Page<Bottle> bottles = bottleService.getListOfBottle(page, size);

            if (bottles == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bottles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("/getListOfFilterBottles")
    public ResponseEntity<Page<Bottle>> getListOfBottlesByCategory(@RequestBody BottleFilterDto bottleFilterDto) {
        try {
            Page<Bottle> bottles = bottleService.getListOfBottleByCategory(bottleFilterDto);

            if (bottles == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bottles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSearchBottleByBrand")
    public ResponseEntity<Page<Bottle>> getSearchBottleByBrand(@RequestParam String search,
                                                               @RequestParam int page,
                                                               @RequestParam int size) {
        try {
            Page<Bottle> bottles = bottleService.getSearchBottleByBrand(search, page, size);

            if (bottles == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bottles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
