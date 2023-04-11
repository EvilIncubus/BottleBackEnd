package org.bottleProject.controller;

import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.dto.UserWithProfileDto;
import org.bottleProject.entity.Page;
import org.bottleProject.entity.User;
import org.bottleProject.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/user")
public class UserController {
    //todo spring boot controller advices
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        System.out.println(createUserDto.getEmail() + createUserDto.getProfilePhoto() + createUserDto.getPassword());
        User userResponse = userService.addUser(createUserDto);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/photos")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String filename = UUID.randomUUID() + "-" + originalFilename;

            Path path = Paths.get("C:\\resources\\" + filename);
            InputStream is = file.getInputStream();

            try {
                Files.copy(is, path);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            is.close();
            return ResponseEntity.ok().body("http://localhost:8080/photos/" + filename);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getUsersList")
    public ResponseEntity<Page<UserWithProfileDto>> getListOfUsers(@RequestParam int page,
                                                                   @RequestParam int size) {
        Page<UserWithProfileDto> userList = userService.getAllUsersWithProfile(page, size);
        return new ResponseEntity<>(userList, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'OPERATOR')")
    @GetMapping("/getCustomersList")
    public ResponseEntity<Page<UserWithProfileDto>> getListOfCustomers(@RequestParam int page,
                                                                   @RequestParam int size) {
        Page<UserWithProfileDto> userList = userService.getAllUsersWithProfile(page, size);
        return new ResponseEntity<>(userList, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/UpdateUser")
    public ResponseEntity<User> getUserByEmail(@RequestBody CreateUserDto createUserDto) {
        User user = userService.updateUser(createUserDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/setNewAccountStatus")
    public ResponseEntity<String> setNewAccountStatus(@RequestParam String email,
                                                      @RequestParam String accountStatus) {
        userService.setUserAccountStatus(email, accountStatus);
        return new ResponseEntity<>("Update Successful", HttpStatus.CREATED);
    }

}
