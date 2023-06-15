package org.bottleProject.service.impl;

import org.bottleProject.dao.SettingsDao;
import org.bottleProject.service.SettingsService;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsDao settingsDao;

    public SettingsServiceImpl(SettingsDao settingsDao) {
        this.settingsDao = settingsDao;
    }

    @Override
    public String getMailActiveConfiguration() {
        return settingsDao.getMailActiveConfiguration();
    }

    @Override
    public void setNewMailConfiguration(String mailConfiguration) {
        settingsDao.setNewMailConfiguration(mailConfiguration);
    }

    @Override
    public void setNewDriveConfiguration(String driveConfiguration) {
        settingsDao.setNewSaveConfiguration(driveConfiguration);
    }

    @Override
    public String getDriveActiveConfiguration() {
        return settingsDao.getSaveActiveConfiguration();
    }
}
