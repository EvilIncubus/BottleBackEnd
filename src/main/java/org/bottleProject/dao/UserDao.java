package org.bottleProject.dao;

import org.bottleProject.entity.User;

public interface UserDao extends Dao<User>{
    User findByEmail(String email);

    void setRoleForUser(int userId, String roleName);
}
