package org.bottleProject.dao;

import org.bottleProject.entity.Role;
import org.bottleProject.entity.User;

import java.util.List;

public interface RoleDao extends Dao<Role>{
    void setUserRole(User user);

    List<Role> getUserRole(User user);
}
