package org.bottleProject.service.impl;

import org.bottleProject.configuration.security.JwtProvider;
import org.bottleProject.dto.AuthenticationRequest;
import org.bottleProject.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String authenticateUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        return jwtUtils.generateToken(authentication);
    }
}
