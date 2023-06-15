package org.bottleProject.dao;

import org.bottleProject.entity.MailConfiguration;

public interface SettingsDao extends Dao<MailConfiguration> {

    String getMailActiveConfiguration();

    void setNewMailConfiguration(String mailConfiguration);

    String getSaveActiveConfiguration();

    void setNewSaveConfiguration(String saveConfiguration);

}
