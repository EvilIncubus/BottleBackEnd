package org.bottleProject.dao.impl;

import org.bottleProject.dao.ProfileDao;
import org.bottleProject.entity.Profile;
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
public class ProfileDaoImpl extends AbstractDaoImpl<Profile> implements ProfileDao {

    public ProfileDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Profile> getAll() {
        return null;
    }

    @Override
    public Profile create(Profile profile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO user (first_name, last_name, address_id, phone_number, company) VALUES(?,?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, profile.getFirstName());
            stmt.setString(2, profile.getLastName());
            stmt.setInt(3, profile.getAddressId());
            stmt.setString(4, profile.getPhoneNumber());
            stmt.setString(5, profile.getCompany());
            return stmt;
        }, keyHolder);

        return findById((Objects.requireNonNull(keyHolder.getKey()).longValue()));
    }

    @Override
    public Profile update(Profile profile, Long id) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public Profile findById(Long id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM profile WHERE profile_id=?",
                    BeanPropertyRowMapper.newInstance(Profile.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getProfileAddress(Profile profile) {
        try {
            return getJdbcTemplate().queryForObject("SELECT address FROM profile WHERE profile_id= ? and address_id = ?",
                    String.class, profile.getProfileId(), profile.getAddressId());
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
