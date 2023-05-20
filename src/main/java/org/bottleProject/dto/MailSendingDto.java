package org.bottleProject.dto;

public class MailSendingDto {
    private int orderId;
    private String email;
    private String status;
    private String firstName;
    private String lastName;

    public MailSendingDto(int orderId, String email, String status, String firstName, String lastName) {
        this.orderId = orderId;
        this.email = email;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public MailSendingDto() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
