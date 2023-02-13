package org.bottleProject.service;

import org.bottleProject.dao.CustomerDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration_test")
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerDao customerDao;

    @Test
    public void shouldAddCustomer(){

    }

    @Test
    public void shouldGetCustomerByEmail(){

    }
}
