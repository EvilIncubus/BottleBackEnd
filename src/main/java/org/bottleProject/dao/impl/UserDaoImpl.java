package org.bottleProject.dao.impl;

import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<User> getAll() {
        return getJdbcTemplate().query("SELECT * FROM user;", BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO user (email, password) VALUES(?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            return stmt;
        }, keyHolder);

        return findById((Objects.requireNonNull(keyHolder.getKey()).longValue()));
    }

    @Override
    public User update(User user, Long id) {
        getJdbcTemplate().update("UPDATE user SET email = ? WHERE user_id = ? ;",
                user.getEmail(), user.getUserId());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM user WHERE user_id=?", id);
    }

    @Override
    public User findById(Long id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM user WHERE user_id=?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        return getJdbcTemplate().queryForObject("SELECT * FROM user WHERE email=?",
                BeanPropertyRowMapper.newInstance(User.class), email);
    }

    @Override
    public void setRoleForUser(int userId, String roleName) {

    }
}
