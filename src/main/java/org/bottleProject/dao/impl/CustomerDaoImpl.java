package org.bottleProject.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bottleProject.dao.CustomerDao;
import org.bottleProject.entity.Customer;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CustomerDaoImpl extends AbstractDaoImpl<Customer> implements CustomerDao<Customer> {

    public CustomerDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final Logger logger = LogManager.getLogger(CustomerDaoImpl.class);

    @Override
    public List<Customer> getAll() {
        return getJdbcTemplate().query("SELECT * FROM customer;", BeanPropertyRowMapper.newInstance(Customer.class));
    }

    @Override
    public Long create(Customer customer) {
        getJdbcTemplate().update(" INSERT INTO customer (NameCompany, Email, Address, PhoneNumber) VALUES(?,?,?,?);",
                customer.getNameCompany(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber()
        );
        return findMaxId();
    }

    private Long findMaxId() {
        try {
            return getJdbcTemplate().queryForObject("SELECT max(CustomerID) FROM customer;",
                    Long.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer update(Customer customer, Long id) {
        getJdbcTemplate().update("UPDATE customer SET PhoneNumber = ? WHERE CustomerID = ? ;",
                customer.getPhoneNumber(), customer.getCustomerID());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM customer WHERE CustomerID=?", id);
    }

    @Override
    public Customer findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM customer WHERE CustomerID=?",
                    BeanPropertyRowMapper.newInstance(Customer.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
