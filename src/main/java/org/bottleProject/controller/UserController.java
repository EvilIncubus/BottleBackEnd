package org.bottleProject.controller;

import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.entity.User;
import org.bottleProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/user")
public class UserController {
    //todo spring boot controller advices
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
            User userResponse = userService.addUser(createUserDto);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

}
