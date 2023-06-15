package org.bottleProject.dao;

import org.bottleProject.entity.DriveConfiguration;

public interface ConfigurationDao extends Dao<DriveConfiguration> {

    String getEnableStatus();

    DriveConfiguration findConfigByType(String configType);
}
