package org.bottleProject.dao;

import org.bottleProject.entity.Configuration;

public interface ConfigurationDao extends Dao<Configuration> {

    String getEnableStatus();

    Configuration findConfigByType(String configType);
}
