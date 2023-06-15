package org.bottleProject.controller;

import org.bottleProject.service.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/rest/api/settings")
public class SettingsController {
    private final SettingsService settingsService;

    private final static Logger logger = Logger.getLogger(SettingsController.class.getName());

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/changeMailSender")
    public ResponseEntity<String> changeMailSender(@RequestParam String mailConfiguration) {
        try {
            settingsService.setNewMailConfiguration(mailConfiguration);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getMailSender")
    public ResponseEntity<String> getMailSender(){
        try {
            String mailSetting = settingsService.getMailActiveConfiguration();
            return new ResponseEntity<>(mailSetting, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/changeActiveDrive")
    public ResponseEntity<String> changeActiveDrive(@RequestParam String driveConfiguration) {
        try {
            settingsService.setNewDriveConfiguration(driveConfiguration);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getActiveDrive")
    public ResponseEntity<String> getActiveDrive(){
        try {
            String mailSetting = settingsService.getDriveActiveConfiguration();
            return new ResponseEntity<>(mailSetting, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
