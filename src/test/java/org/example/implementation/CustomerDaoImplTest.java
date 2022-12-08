package org.example.implementation;

import org.example.connectionDB.ConnectionDB;

import org.example.dao.impl.CustomerDaoImpl;
import org.example.entity.Customer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerDaoImplTest {

    private final CustomerDaoImpl costumerImplementDao = new CustomerDaoImpl(ConnectionDB.getInstance());
    private Customer customer = new Customer();

    //Test to check if customer is created and deleted successful
    @Test
    public void testCreateAndDelete() {
        customer.setNameCompany("SushiMushi");
        customer.setEmail("celentanocontact@gmail.com");
        customer.setAddress("str. Mesterul Manole 51");
        customer.setPhoneNumber("060123456");
        long id = costumerImplementDao.create(customer);
        customer.setCustomerID(id);

        Customer customer1 = costumerImplementDao.findById(id);
        assertEquals(customer1, customer);
        costumerImplementDao.removeById(id);
        assertNull(costumerImplementDao.findById(id));
    }

    //Test to check if customers is getting all in correct form
    @Test
    public void testGetAll() {
        List<Customer> customers = costumerImplementDao.getAll();

        assertNotNull(customers);
    }

    //Test to check if function correct find customer by id
    @Test
    public void testFindById() {
        customer = costumerImplementDao.findById(1L);

        assertNotNull(customer);
    }

    //Test to check in function make correct update for customer phone number
    @Test
    public void testUpdate() {
        customer = costumerImplementDao.findById(1L);
        String oldPhone = customer.getPhoneNumber();
        customer.setPhoneNumber("06098765");
        Customer customer1 = costumerImplementDao.update(customer, 1L);

        assertNotNull(customer1);
        assertEquals(customer, customer1);
        customer1.setPhoneNumber(oldPhone);
        costumerImplementDao.update(customer, 1L);
    }
}
