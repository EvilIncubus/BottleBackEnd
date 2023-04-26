package org.bottleProject.dto;

public class CreateOrderDto {

    private int profileId;
    private String address;
    private String status;

    private String operatorEmail;

    public CreateOrderDto() {
    }

    public CreateOrderDto(int profileId, String address, String status, String operatorEmail) {
        this.profileId = profileId;
        this.address = address;
        this.status = status;
        this.operatorEmail = operatorEmail;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusId(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }
}
