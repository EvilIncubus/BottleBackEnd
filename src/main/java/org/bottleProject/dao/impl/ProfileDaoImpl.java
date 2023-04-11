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
    public List<Profile> getAll(int size, int offset) {
        return null;
    }

    @Override
    public Profile create(Profile profile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO profile (first_name, last_name, phone_number, company, user_id, profile_photo_path) VALUES(?,?,?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, profile.getFirstName());
            stmt.setString(2, profile.getLastName());
            stmt.setString(3, profile.getPhoneNumber());
            stmt.setString(4, profile.getCompany());
            stmt.setInt(5, profile.getUserId());
            stmt.setString(6, profile.getProfilePhotoPath());
            return stmt;
        }, keyHolder);

        getJdbcTemplate().update("INSERT INTO address (profile_id, address) VALUES(?,?);",Objects.requireNonNull(keyHolder.getKey()).longValue(), profile.getAddress());


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
            return getJdbcTemplate().queryForObject("SELECT address FROM address WHERE profile_id = ?",
                    String.class, profile.getProfileId());
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Profile getProfileByUserId(long id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM profile WHERE user_id=?",
                    BeanPropertyRowMapper.newInstance(Profile.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateProfile(Profile profile) {
        getJdbcTemplate().update("UPDATE profile SET first_name = ?, last_name = ?, phone_number = ?, company = ?, profile_photo_path = ? WHERE user_id = ? ;",
                profile.getFirstName(), profile.getLastName(), profile.getPhoneNumber(), profile.getCompany(), profile.getProfilePhotoPath());
    }
}
