package org.bottleProject.controller;

import org.bottleProject.entity.Customer;
import org.bottleProject.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<Long> createBottle(@RequestBody Customer customer) {
        try {
            long customerId = customerService.addCustomer(customer);
            return new ResponseEntity<>(customerId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomerByEmail/{email}")
    public ResponseEntity<Customer> getListOfOrders(@PathVariable String email) {
        try {
            Customer customer = customerService.getCustomerByEmail(email);

            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
