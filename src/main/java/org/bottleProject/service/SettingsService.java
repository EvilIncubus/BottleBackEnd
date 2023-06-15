package org.bottleProject.service;

public interface SettingsService {

    String getMailActiveConfiguration();

    void setNewMailConfiguration(String mailConfiguration);

    void setNewDriveConfiguration(String driveConfiguration);

    String getDriveActiveConfiguration();
}
