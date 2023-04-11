package org.bottleProject.service;

import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.dto.UserWithProfileDto;
import org.bottleProject.entity.Page;
import org.bottleProject.entity.User;

public interface UserService {
    User addUser(CreateUserDto createUserDto);

    Page<UserWithProfileDto> getAllUsersWithProfile(int page, int size);

    void setUserAccountStatus(String email, String accountStatus);

    User updateUser(CreateUserDto createUserDto);
}
