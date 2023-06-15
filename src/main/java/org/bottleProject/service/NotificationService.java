package org.bottleProject.service;

import org.bottleProject.entity.Notification;
import org.bottleProject.entity.Page;

public interface NotificationService {
    void createNotification(int orderId,String status, String description);
    Integer countActiveNotification(String email);
    Page<Notification> getActiveNotification(String email, int page, int size);

    Page<Notification> getAllNotification(String email, int page, int size);

    void updateNotificationReadStatus(String email, int orderId);
}
