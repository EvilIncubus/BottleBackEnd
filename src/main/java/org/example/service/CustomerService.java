package org.example.service;

import org.example.entity.Customer;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer editCustomer(Customer customer);
}
