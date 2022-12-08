package org.example.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.connectionDB.JdbcConnection;
import org.example.dao.CustomerDao;
import org.example.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl extends AbstractDaoImpl<Customer> implements CustomerDao<Customer> {

    public CustomerDaoImpl(JdbcConnection jdbcConnection) {
        super(jdbcConnection);
    }

    private static final Logger logger = LogManager.getLogger(CustomerDaoImpl.class);

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer;";
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getLong("CustomerID"));
                customer.setNameCompany(rs.getString("NameCompany"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            logger.info("Couldn't get customer from BD");
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public long create(Customer customer) {
        String sql = "INSERT INTO customer (NameCompany, Email, Address, PhoneNumber) VALUES(?,?,?,?);";
        String sql1 = "SELECT max(CustomerID) FROM customer;";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setString(1, customer.getNameCompany());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't create customer in BD");
            throw new RuntimeException(e);
        }
        long customerID = 0L;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql1);
            while (rs.next()) {
                customerID = rs.getLong("max(CustomerID)");
            }
        } catch (SQLException e) {
            logger.info("Couldn't find customer from BD");
            throw new RuntimeException(e);
        }
        return customerID;
    }

    @Override
    public Customer update(Customer customer, Long id) {
        String sql = "UPDATE customer SET PhoneNumber = ? WHERE CustomerID =" + id + ";";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setString(1, customer.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't update phone number in BD");
            throw new RuntimeException(e);
        }
        return findById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = "DELETE FROM customer WHERE CustomerID=" + id + ";";
        try {
            getConnectionStatement().executeUpdate(sql);
        } catch (SQLException e) {
            logger.info("Couldn't remove customer by id in BD");
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Customer findById(Long id) {
        String sql = "SELECT * \n" +
                "FROM customer\n" +
                "WHERE CustomerID =" + id + ";";
        Customer customer;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            customer = null;
            while (rs.next()) {
                customer = new Customer();
                customer.setCustomerID(rs.getLong("CustomerID"));
                customer.setEmail(rs.getString("NameCompany"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
            }
        } catch (SQLException e) {
            logger.info("Couldn't order find by id in BD");
            throw new RuntimeException(e);
        }
        return customer;
    }
}
