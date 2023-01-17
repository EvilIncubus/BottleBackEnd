package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.service.LogInService;

public class LogInServiceImpl implements LogInService {
    private final UserDao<User> userDao;

    public LogInServiceImpl (UserDao<User> userDao){
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email) {

        User user = userDao.findByEmail(email);

        if (user == null) {
            System.out.println("User with such login not found");
        }

        return user;
    }

}
