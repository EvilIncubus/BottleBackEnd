package org.bottleProject.dto;

public class ListOfCustomersOrdersDto {
    private int profileId;
    private String operatorEmail;
    private int size;
    private int page;

    public ListOfCustomersOrdersDto(int profileId, String operatorEmail, int size, int page) {
        this.profileId = profileId;
        this.operatorEmail = operatorEmail;
        this.size = size;
        this.page = page;
    }

    public ListOfCustomersOrdersDto() {

    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return (page - 1) * size;
    }
}
