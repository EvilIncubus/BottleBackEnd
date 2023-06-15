package org.bottleProject.dao;

import org.bottleProject.entity.Notification;

import java.util.List;

public interface NotificationDao extends Dao<Notification>{
    List<Notification> getActiveNotificationForUser(String email, int size, int offset);

    Integer countActiveNotificationForUser(String email);

    void updateUserNotificationReadStatus(String email, int orderId);

    List<Notification> getAllNotificationForUser(String email, int size, int offset);

    Integer countAllNotificationForUser(String email);
}
