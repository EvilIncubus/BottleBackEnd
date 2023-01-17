package org.bottleProject.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao<User> {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<User> getAll() {
        return getJdbcTemplate().query("SELECT * FROM userprofile;", BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM userprofile WHERE UserID=?", id);
    }

    @Override
    public User findById(Long id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM userprofile WHERE UserID=?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long create(User entity) {
        getJdbcTemplate().update("INSERT INTO userprofile(FirstName, LastName, Email, CustomerID, RoleID, PhoneNumber) VALUES(?,?,?,?,?,?);",
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
            return getJdbcTemplate().queryForObject("SELECT max(UserID) FROM userprofile;",
                    Long.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public User update(User entity, Long id) {
        getJdbcTemplate().update("UPDATE userprofile SET PhoneNumber=? WHERE UserID=?;",
                entity.getPhoneNumber(), entity.getUserID());
        return findById(id);
    }

    @Override
    public User findByEmail(String userEmail) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM userprofile WHERE Email=?",
                    BeanPropertyRowMapper.newInstance(User.class), userEmail);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
