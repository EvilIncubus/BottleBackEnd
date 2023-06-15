package org.bottleProject.entity;

public class Notification {
    private long notificationId;
    private String description;
    private int orderId;
    private String userNotificationListJson;

    public Notification(long notificationId, String description, int orderId, String userNotificationListJson) {
        this.notificationId = notificationId;
        this.description = description;
        this.orderId = orderId;
        this.userNotificationListJson = userNotificationListJson;
    }

    public Notification() {
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserNotificationListJson() {
        return userNotificationListJson;
    }

    public void setUserNotificationListJson(String userNotificationListJson) {
        this.userNotificationListJson = userNotificationListJson;
    }
}
