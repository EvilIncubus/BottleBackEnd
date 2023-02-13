package org.bottleProject.dao.impl;

import org.bottleProject.dao.CustomerDao;
import org.bottleProject.entity.Customer;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerDaoImpl extends AbstractDaoImpl<Customer> implements CustomerDao {

    public CustomerDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Customer> getAll() {
        return getJdbcTemplate().query("SELECT * FROM customer;", BeanPropertyRowMapper.newInstance(Customer.class));
    }

    @Override
    public Customer create(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO customer (name_company, email, address, phone_number) VALUES(?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,customer.getNameCompany());
            stmt.setString(2,customer.getEmail());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4,customer.getPhoneNumber());
            return stmt;
        }, keyHolder);
        return findById((keyHolder.getKey().longValue()));
    }

    @Override
    public Customer update(Customer customer, Long id) {
        getJdbcTemplate().update("UPDATE customer SET phone_number = ? WHERE customer_id = ? ;",
                customer.getPhoneNumber(), customer.getCustomerId());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM customer WHERE customer_id=?", id);
    }

    @Override
    public Customer findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM customer WHERE customer_id=?",
                    BeanPropertyRowMapper.newInstance(Customer.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer findByEmail(String email) {
        return getJdbcTemplate().queryForObject("SELECT * FROM customer WHERE email=?",
                BeanPropertyRowMapper.newInstance(Customer.class), email);
    }
}
