package org.bottleProject.service.impl;

import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.User;
import org.bottleProject.service.LogInService;
import org.springframework.stereotype.Service;

@Service
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
