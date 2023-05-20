package org.bottleProject.dao;

import org.bottleProject.dto.UserWithProfileDto;
import org.bottleProject.entity.User;

import java.util.List;

public interface UserDao extends Dao<User>{
    User findByEmail(String email);

    void setRoleForUser(int userId, String roleName);

    List<UserWithProfileDto> getListOfUsersWithProfile(int page, int size);

    Integer countListOfUsersWithProfile();

    void setNewUserAccountStatus(String email, String accountStatus);

    UserWithProfileDto findUserByEmail(String email);

    User updateUsers(User user);

    List<UserWithProfileDto> getListOfCustomersForOperator();

    List<UserWithProfileDto> getSearchListOfCustomersForOperator(String search);
}
