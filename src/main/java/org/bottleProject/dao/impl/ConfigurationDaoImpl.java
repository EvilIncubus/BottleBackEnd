package org.bottleProject.dao.impl;

import org.bottleProject.dao.ConfigurationDao;
import org.bottleProject.entity.DriveConfiguration;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ConfigurationDaoImpl extends AbstractDaoImpl<DriveConfiguration> implements ConfigurationDao {

    protected ConfigurationDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<DriveConfiguration> getAll(int size, int offset) {
        return null;
    }

    @Override
    public DriveConfiguration create(DriveConfiguration configuration) {
        return null;
    }

    @Override
    public DriveConfiguration update(DriveConfiguration configuration, Long id) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public DriveConfiguration findById(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM configuration WHERE configuration_id = ?",
                BeanPropertyRowMapper.newInstance(DriveConfiguration.class), id);
    }

    @Override
    public String getEnableStatus() {
        try {
            return getJdbcTemplate().queryForObject("SELECT drive_configuration FROM drive_configuration WHERE active_configuration = 'ACTIVE'",
                    String.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public DriveConfiguration findConfigByType(String configType) {
        return getJdbcTemplate().queryForObject("SELECT * FROM configuration WHERE configuration_type = ?",
                BeanPropertyRowMapper.newInstance(DriveConfiguration.class), configType);
    }
}
