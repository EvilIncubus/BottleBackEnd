package org.bottleProject.dao.impl;

import org.bottleProject.dao.ConfigurationDao;
import org.bottleProject.entity.Configuration;

import javax.sql.DataSource;
import java.util.List;

public class ConfigurationDaoImpl extends AbstractDaoImpl<Configuration> implements ConfigurationDao {

    protected ConfigurationDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Configuration> getAll() {
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
        return null;
    }

    @Override
    public Configuration getConfigurationByRequestType(String saveType) {
        return null;
    }
}
