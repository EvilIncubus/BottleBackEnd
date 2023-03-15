package org.bottleProject.controller;

import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.dto.UserWithProfileDto;
import org.bottleProject.entity.Page;
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
        System.out.println(createUserDto.getPassword() + createUserDto.getEmail() + createUserDto.getFirstName() + createUserDto.getLastName() + createUserDto.getRoles());
            User userResponse = userService.addUser(createUserDto);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getUsersList")
    public ResponseEntity<Page<UserWithProfileDto>> getListOfUsers(@RequestParam int page,
                                                                   @RequestParam int size) {
        Page<UserWithProfileDto> userList = userService.getAllUsersWithProfile(page, size);
        return new ResponseEntity<>(userList, HttpStatus.CREATED);
    }


}
