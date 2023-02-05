package org.bottleProject.service.impl;

import org.bottleProject.dao.CustomerDao;
import org.bottleProject.entity.Customer;
import org.bottleProject.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao){
        this.customerDao = customerDao;
    }
    @Override
    public long addCustomer(Customer customer) {
        return customerDao.create(customer);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerDao.findByEmail(email);
    }
}
