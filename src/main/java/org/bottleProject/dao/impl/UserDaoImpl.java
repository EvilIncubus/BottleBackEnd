package org.bottleProject.dao.impl;

import org.bottleProject.dao.UserDao;
import org.bottleProject.dto.FullOrderDto;
import org.bottleProject.dto.UserWithProfileDto;
import org.bottleProject.entity.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<User> getAll(int size, int offset) {
        return getJdbcTemplate().query("SELECT * FROM user;", BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO user (email, password, account_status, created_date) VALUES(?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAccountStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(user.getCreatedDate()));
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

    @Override
    public List<UserWithProfileDto> getListOfUsersWithProfile(int page, int size) {
        return getJdbcTemplate().query("select p.profile_id, u.email, p.first_name, p.last_name, a.address, p.phone_number, p.profile_photo_path, p.company, u.account_status from `user` as u \n" +
                "inner join profile as p on p.user_id = u.user_id \n" +
                "inner join address as a on a.profile_id = p.profile_id limit ? offset ?", BeanPropertyRowMapper.newInstance(UserWithProfileDto.class), size, (page - 1) * size);
    }

    @Override
    public Integer countListOfUsersWithProfile() {
        return getJdbcTemplate().queryForObject("select count(*) from `user` as u \n" +
                "inner join profile as p on p.user_id = u.user_id \n" +
                "inner join address as a on a.profile_id = p.profile_id ", Integer.class);
    }

    @Override
    public void setNewUserAccountStatus(String email, String accountStatus) {
        getJdbcTemplate().update("UPDATE user SET account_status = ? WHERE email = ? ;",
                accountStatus, email);
    }

    @Override
    public UserWithProfileDto findUserByEmail(String email) {
        return getJdbcTemplate().queryForObject("SELECT * FROM user WHERE email=?",
                BeanPropertyRowMapper.newInstance(UserWithProfileDto.class), email);
    }

    @Override
    public User updateUsers(User user) {
        getJdbcTemplate().update("UPDATE user SET email = ?, password = ? WHERE user_id = ? ;",
                user.getEmail(), user.getUserId());
        return findById(user.getUserId());
    }

    @Override
    public List<UserWithProfileDto> getListOfCustomersForOperator() {
        return getJdbcTemplate().query("select p.profile_id, u.email, p.first_name, p.last_name, a.address, p.phone_number, p.profile_photo_path, p.company, u.account_status from `user` as u \n" +
                "inner join profile as p on p.user_id = u.user_id \n" +
                "inner join address as a on a.profile_id = p.profile_id \n" +
                "inner join user_role ur on ur.user_id = u.user_id \n" +
                "inner join role as r on r.role_id = ur.role_id where r.role_name = 'CUSTOMER' limit "+5+"", BeanPropertyRowMapper.newInstance(UserWithProfileDto.class));
    }

    @Override
    public List<UserWithProfileDto> getSearchListOfCustomersForOperator(String search) {
        return getJdbcTemplate().query("select p.profile_id, u.email, p.first_name, p.last_name, a.address, p.phone_number, p.profile_photo_path, p.company, u.account_status from `user` as u \n" +
                "inner join profile as p on p.user_id = u.user_id \n" +
                "inner join address as a on a.profile_id = p.profile_id \n" +
                "inner join user_role ur on ur.user_id = u.user_id \n" +
                "inner join role as r on r.role_id = ur.role_id where r.role_name = 'CUSTOMER' and p.company like '%"+ search +"%' limit "+5+" offset "+0+";", BeanPropertyRowMapper.newInstance(UserWithProfileDto.class));
    }
}
