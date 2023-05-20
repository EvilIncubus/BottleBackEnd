package org.bottleProject.service;

import org.bottleProject.dto.AuthenticationRequest;

public interface AuthenticationService {
    String authenticateUser(AuthenticationRequest authenticationRequest);
}
