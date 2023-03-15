package org.bottleProject.integration;

import org.bottleProject.controller.InvoiceController;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("integration_test")
public class createUserTest {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Test
    public void createUser() {
        System.out.println(bCryptPasswordEncoder.encode("password"));
    }
}
