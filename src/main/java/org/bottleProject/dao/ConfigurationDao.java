package org.bottleProject.dao;

import org.bottleProject.entity.Configuration;

public interface ConfigurationDao extends Dao<Configuration> {

    Configuration getConfigurationByRequestType(String saveType);

}
