package org.bottleProject.dao.impl;

import org.bottleProject.dao.SettingsDao;
import org.bottleProject.entity.MailConfiguration;
import org.bottleProject.entity.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SettingsDaoImpl extends AbstractDaoImpl<MailConfiguration> implements SettingsDao {
    protected SettingsDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<MailConfiguration> getAll(int size, int offset) {
        return null;
    }

    @Override
    public MailConfiguration create(MailConfiguration mailConfiguration) {
        return null;
    }

    @Override
    public MailConfiguration update(MailConfiguration mailConfiguration, Long id) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public MailConfiguration findById(Long id) {
        return null;
    }

    @Override
    public String getMailActiveConfiguration() {
        return getJdbcTemplate().queryForObject("select mail_configuration from mail_configuration \n" +
                        "Where active_configuration = 1 ",
                String.class);
    }

    @Override
    public void setNewMailConfiguration(String mailConfiguration) {
        String offMailConfig = getJdbcTemplate().queryForObject("SELECT mail_configuration FROM mail_configuration Where mail_configuration != ?;", String.class, mailConfiguration);
        getJdbcTemplate().update("UPDATE mail_configuration\n" +
                "SET active_configuration \n" +
                "= CASE mail_configuration\n" +
                "WHEN ? THEN 1\n" +
                "WHEN ? THEN 0\n" +
                "ELSE active_configuration\n" +
                "END\n" +
                "WHERE mail_configuration IN('"+mailConfiguration+"', '"+offMailConfig+"');", mailConfiguration, offMailConfig);
    }

    @Override
    public String getSaveActiveConfiguration() {
        return getJdbcTemplate().queryForObject("select drive_configuration from drive_configuration \n" +
                        "Where active_configuration = 'ACTIVE' ",
                String.class);
    }

    @Override
    public void setNewSaveConfiguration(String saveConfiguration) {
        String offDriveConfig = getJdbcTemplate().queryForObject("SELECT drive_configuration FROM drive_configuration Where active_configuration = 'INACTIVE';", String.class);
        getJdbcTemplate().update("UPDATE drive_configuration\n" +
                "SET drive_configuration \n" +
                "= CASE drive_configuration \n" +
                "WHEN ? THEN 'ACTIVE' \n" +
                "WHEN ? THEN 'INACTIVE '\n" +
                "ELSE active_configuration \n" +
                "END \n" +
                "WHERE drive_configuration IN('"+saveConfiguration+"', '"+offDriveConfig+"');", saveConfiguration, offDriveConfig);
    }
}
