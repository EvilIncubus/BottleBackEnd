package org.example.implementation;

import org.example.connectionDB.ConnectionDB;
import org.example.dao.impl.UserDaoImpl;
import org.example.entity.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class UserDaoImplTest {

    UserDaoImpl userDao = new UserDaoImpl(ConnectionDB.getInstance());

    User user = new User();

    @Test
    public void testCreateAndDelete() {
        user.setFirstName("Grigorii");
        user.setLastName("Afanasii");
        user.setEmail("ciorescu.a@gmail.com");
        user.setPhoneNumber("062011235");
        user.setRoleID(1);
        long id = userDao.create(user);

        User user1 = userDao.findById(id);
        assertNotEquals(user1, user);
        userDao.removeById(id);
        assertNull(userDao.findById(id));
    }

    //Test to check if bottles is getting all in correct form
    @Test
    public void testGetAll() {
        List<User> users = userDao.getAll();
        System.out.println(users);
        assertNotNull(users);
    }

    //Test to check if function correct find bottle by id
    @Test
    public void testFindById() {
        user = userDao.findById(1L);
        System.out.println(user);
        assertNotNull(user);
    }

    //Test to check in function make correct update for bottle price
    @Test
    public void testUpdate() {
        user = userDao.findById(1L);
        String oldNumber = user.getPhoneNumber();
        user.setPhoneNumber("065212684");
        User user1 = userDao.update(user, 1L);

        assertNotNull(user1);
        assertEquals(user, user1);
        user1.setPhoneNumber(oldNumber);
        userDao.update(user, 1L);
    }

    @Test
    public void testFindByEmail() {
        user = userDao.findByEmail("ciorescuvasilie@gmail.com");
        assertNotNull(user);
    }
}
