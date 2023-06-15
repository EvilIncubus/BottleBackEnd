package org.bottleProject.controller;

import org.bottleProject.entity.Notification;
import org.bottleProject.entity.Page;
import org.bottleProject.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/rest/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    private static final Logger logger = Logger.getLogger(NotificationController.class.getName());

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN') or hasAnyAuthority('MANAGER')")
    @GetMapping("/getNumberOfActiveNotification")
    public ResponseEntity<Integer> getNumberOfActiveNotification(@RequestParam String email) {
        try {
            Integer numberOfActiveNotification =  notificationService.countActiveNotification(email);
            return new ResponseEntity<>(numberOfActiveNotification,HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN') or hasAnyAuthority('MANAGER')")
    @GetMapping("/getAllActiveNotification")
    public ResponseEntity<Page<Notification>> getAllActiveNotification(@RequestParam String email,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size){
        try {
            Page<Notification> notification = notificationService.getActiveNotification(email, page, size);
            return new ResponseEntity<>(notification, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN') or hasAnyAuthority('MANAGER')")
    @GetMapping("/getAllNotification")
    public ResponseEntity<Page<Notification>> getAllNotification(@RequestParam String email,
                                                     @RequestParam int page,
                                                     @RequestParam int size){
        try {
            Page<Notification> notification = notificationService.getAllNotification(email, page, size);
            return new ResponseEntity<>(notification, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN') or hasAnyAuthority('MANAGER')")
    @GetMapping("/updateNotificationReadStatus")
    public ResponseEntity<String> getAllNotification(@RequestParam String email,
                                                     @RequestParam int orderId){
        try {
            notificationService.updateNotificationReadStatus(email, orderId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
