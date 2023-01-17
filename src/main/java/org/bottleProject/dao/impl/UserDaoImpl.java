package org.bottleProject.dao.impl;

import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<User> getAll() {
        return getJdbcTemplate().query("SELECT * FROM user_profile;", BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM user_profile WHERE user_id=?", id);
    }

    @Override
    public User findById(Long id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM user_profile WHERE user_id=?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long create(User entity) {
        getJdbcTemplate().update("INSERT INTO user_profile(first_name, last_name, email, customer_id, role_id, phone_number) VALUES(?,?,?,?,?,?);",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getCostumerID(),
                entity.getRoleID(),
                entity.getPhoneNumber()
        );
        return findMaxId();
    }

    private Long findMaxId() {
        try {
            return getJdbcTemplate().queryForObject("SELECT max(user_id) FROM user_profile;",
                    Long.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public User update(User entity, Long id) {
        getJdbcTemplate().update("UPDATE user_profile SET phone_number=? WHERE user_id=?;",
                entity.getPhoneNumber(), entity.getUserID());
        return findById(id);
    }

    @Override
    public User findByEmail(String userEmail) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM user_profile WHERE email=?",
                    BeanPropertyRowMapper.newInstance(User.class), userEmail);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
