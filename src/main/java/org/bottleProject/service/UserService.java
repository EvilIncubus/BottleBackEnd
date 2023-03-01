package org.bottleProject.service;

import org.bottleProject.dto.AuthenticationRequest;
import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User addUser(CreateUserDto createUserDto);

    User getUserByEmail(String email);

}
