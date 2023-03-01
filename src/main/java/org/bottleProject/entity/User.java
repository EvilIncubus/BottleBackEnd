package org.bottleProject.entity;

import java.util.List;

public class User{

    private long userId;
    private String email;
    private String password;
    private List<Role> roles;

    public User() {
    }

    public User(long userId, String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.userId = userId;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
