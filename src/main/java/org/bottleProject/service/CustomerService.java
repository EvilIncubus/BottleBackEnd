package org.bottleProject.service;

import org.bottleProject.entity.Customer;

public interface CustomerService {
    long addCustomer(Customer customer);

    Customer getCustomerByEmail(String email);
}
