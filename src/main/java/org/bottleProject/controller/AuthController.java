package org.bottleProject.controller;

import io.jsonwebtoken.Jwt;
import org.bottleProject.dto.AuthenticationRequest;
import org.bottleProject.entity.User;
import org.bottleProject.service.AuthenticationService;
import org.bottleProject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //TODO hashmap with
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        String token = authenticationService.authenticateUser(request);
        return ResponseEntity.ok().body(token);
        //todo instead of token use JwtDto return new with constructor
    }

    @GetMapping("/tokenCheck")
    public ResponseEntity<String> tokenCheck(){
        return ResponseEntity.ok().body("Valid Token");
    }
}
