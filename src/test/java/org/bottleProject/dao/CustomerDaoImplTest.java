package org.bottleProject.dao;

import org.bottleProject.connectionDB.DBConfig;
import org.bottleProject.dao.impl.CustomerDaoImpl;
import org.bottleProject.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration_test")
public class CustomerDaoImplTest {

    @Autowired
    private CustomerDaoImpl costumerImplementDao;

    //Test to check if customer is created and deleted successful
    @Test
    public void testCreateAndDelete() {
        Customer customer = new Customer();
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
        Customer customer = costumerImplementDao.findById(1L);

        assertNotNull(customer);
    }

    //Test to check in function make correct update for customer phone number
    @Test
    public void testUpdate() {
        Customer customer = costumerImplementDao.findById(1L);
        String oldPhone = customer.getPhoneNumber();
        customer.setPhoneNumber("06098765");
        Customer customer1 = costumerImplementDao.update(customer, 1L);

        assertNotNull(customer1);
        assertEquals(customer, customer1);
        customer1.setPhoneNumber(oldPhone);
        costumerImplementDao.update(customer, 1L);
    }
}
