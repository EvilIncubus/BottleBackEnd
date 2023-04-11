package org.bottleProject.dto;

public class CreateOrderDto {

    private int profileId;
    private String address;
    private String status;

    public CreateOrderDto() {
    }

    public CreateOrderDto(int profileId, String address, String status) {
        this.profileId = profileId;
        this.address = address;
        this.status = status;
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

    public void setAddressId(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusId(String status) {
        this.status = status;
    }

}
