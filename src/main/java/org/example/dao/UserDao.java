package org.example.dao;

public interface UserDao<User> extends Dao<User> {
    User findByEmail(String userEmail);
}
