package org.bottleProject.service.impl;

import org.bottleProject.dao.NotificationDao;
import org.bottleProject.dao.RoleDao;
import org.bottleProject.entity.Notification;
import org.bottleProject.entity.Page;
import org.bottleProject.entity.User;
import org.bottleProject.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;
    private final RoleDao roleDao;

    public NotificationServiceImpl(NotificationDao notificationDao, RoleDao roleDao) {
        this.notificationDao = notificationDao;
        this.roleDao = roleDao;
    }

    @Override
    public void createNotification(int orderId, String status, String description) {
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setOrderId(orderId);
        notification.setUserNotificationListJson(getJsonUserList(status));
        notificationDao.create(notification);
    }

    private List<String> getUserRolesForOrderStatus(String status) {
        List<String> roles = new ArrayList<>();
        switch (status) {
            case "ApprovedByCustomer":
                roles.add("OPERATOR");
                roles.add("STOREMAN");
                roles.add("MANAGER");
                break;
            case "OrderRejectedByWarehouse":
            case "RejectByCustomer":
                roles.add("OPERATOR");
                roles.add("MANAGER");
                break;
            default:
                System.out.println("Status is not Allowed");
        }
        return roles;
    }

    private String getJsonUserList(String status) {
        List<String> roles = getUserRolesForOrderStatus(status);
        List<User> userList = roleDao.getUsersByRoles(roles);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"userList\":[");
        int i = 0;
        for (User user : userList) {
            stringBuilder.append("{\"id\":");
            stringBuilder.append("\"");
            stringBuilder.append(++i);
            stringBuilder.append("\", ");
            stringBuilder.append("\"email\":");
            stringBuilder.append("\"");
            stringBuilder.append(user.getEmail());
            stringBuilder.append("\", ");
            stringBuilder.append("\"readStatus\":");
            stringBuilder.append("false},");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]}");
        return String.valueOf(stringBuilder);
    }

    @Override
    public Integer countActiveNotification(String email) {
        return notificationDao.countActiveNotificationForUser(email);
    }

    @Override
    public Page<Notification> getActiveNotification(String email, int page, int size) {
        return new Page<>(notificationDao.getActiveNotificationForUser(email, size, (page - 1) * size), notificationDao.countActiveNotificationForUser(email), page, size);
    }

    @Override
    public Page<Notification> getAllNotification(String email, int page, int size) {
        return new Page<>(notificationDao.getAllNotificationForUser(email, size, (page - 1) * size), notificationDao.countAllNotificationForUser(email), page, size);
    }

    @Override
    public void updateNotificationReadStatus(String email, int orderId) {
        notificationDao.updateUserNotificationReadStatus(email, orderId);
    }
}
