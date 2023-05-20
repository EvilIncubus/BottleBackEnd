//package org.bottleProject.dao;
//
//import org.bottleProject.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("integration_test")
//public class UserDaoImplTest {
//
//    @Autowired
//    private UserDao userDao;
//
//    //Test to check if customer is created and deleted successful
//    @Test
//    public void testCreateAndDelete() {
//        User user = new User();
//        user.setNameCompany("SushiMushi");
//        user.setEmail("celentanocontact@gmail.com");
//        Customer customer1 = userDao.create(user);
//        user.setCustomerId(customer1.getCustomerId());
//        assertEquals(user, customer1);
//        userDao.removeById(user.getCustomerId());
//        assertNull(userDao.findById(user.getCustomerId()));
//    }
//
//    //Test to check if customers is getting all in correct form
//    @Test
//    public void testGetAll() {
//        List<Customer> customers = userDao.getAll();
//
//        assertNotNull(customers);
//    }
//
//    //Test to check if function correct find customer by id
//    @Test
//    public void testFindById() {
//        Customer customer = userDao.findById(1L);
//
//        assertNotNull(customer);
//    }
//
//    //Test to check in function make correct update for customer phone number
//    @Test
//    public void testUpdate() {
//        Customer customer = userDao.findById(1L);
//        String oldPhone = customer.getPhoneNumber();
//        customer.setPhoneNumber("06098765");
//        Customer customer1 = userDao.update(customer, 1L);
//
//        assertNotNull(customer1);
//        assertEquals(customer, customer1);
//        customer.setPhoneNumber(oldPhone);
//        userDao.update(customer, 1L);
//    }
//
//    @Test
//    public void testFindByEmail() {
//        Customer customer = userDao.findByEmail("sushiline@yaponamama.md");
//        assertNotNull(customer);
//    }
//}
