package org.bottleProject.dao;

import org.bottleProject.entity.Customer;

public interface CustomerDao extends Dao<Customer>{
    Customer findByEmail(String email);
}
