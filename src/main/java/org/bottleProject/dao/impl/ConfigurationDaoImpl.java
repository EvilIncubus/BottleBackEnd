package org.bottleProject.dao.impl;

import org.bottleProject.dao.ConfigurationDao;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Configuration;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ConfigurationDaoImpl extends AbstractDaoImpl<Configuration> implements ConfigurationDao {

    protected ConfigurationDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Configuration> getAll(int size, int offset) {
        return null;
    }

    @Override
    public Configuration create(Configuration configuration) {
        return null;
    }

    @Override
    public Configuration update(Configuration configuration, Long id) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public Configuration findById(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM configuration WHERE configuration_id = ?",
                BeanPropertyRowMapper.newInstance(Configuration.class), id);
    }

    @Override
    public String getEnableStatus() {
        try {
            return getJdbcTemplate().queryForObject("SELECT request_type FROM request_type WHERE request_status = 'Enable'",
                    BeanPropertyRowMapper.newInstance(String.class));
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Configuration findConfigByType(String configType) {
        return getJdbcTemplate().queryForObject("SELECT * FROM configuration WHERE configuration_type = ?",
                BeanPropertyRowMapper.newInstance(Configuration.class), configType);
    }
}
