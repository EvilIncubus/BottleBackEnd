package org.bottleProject.entity;

import java.time.LocalDateTime;
import java.util.List;

public class User{

    private long userId;
    private String email;
    private String password;
    private String accountStatus;
    private LocalDateTime createdDate;
    private List<Role> roles;

    public User() {
    }

    public User(long userId, String email, String password, String accountStatus, LocalDateTime createdDate, List<Role> roles) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.createdDate = createdDate;
        this.roles = roles;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
