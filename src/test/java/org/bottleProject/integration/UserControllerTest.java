//package org.bottleProject.integration;
//
//import org.bottleProject.controller.UserController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("integration_test")
//public class UserControllerTest {
//    @Autowired
//    UserController customerController;
//
//    @Test
//    public void createCustomerTest(){
//        Customer customer = new Customer();
//        customer.setNameCompany("SushiMushi");
//        customer.setEmail("celentanocontact@gmail.com");
//        customer.setAddress_id(1);
//        customer.setPhoneNumber("060123456");
//        ResponseEntity<Customer> response = customerController.createCustomer(customer);
//        assertNotNull(response.getBody());
//        customer.setCustomerId(response.getBody().getCustomerId());
//        assertEquals(customer, response.getBody());
//    }
//
//    @Test
//    public void getCustomerByIdTest(){
//       assertNotNull(customerController.getListOfOrders("celentanocontact@gmail.com"));
//    }
//
//}
