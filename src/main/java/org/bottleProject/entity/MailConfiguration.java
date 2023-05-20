package org.bottleProject.entity;

public class MailConfiguration {

    private long mailConfigurationId;
    private String mailConfiguration;
    private boolean activeConfiguration;

    public MailConfiguration(long mailConfigurationId, String mailConfiguration, boolean activeConfiguration) {
        this.mailConfigurationId = mailConfigurationId;
        this.mailConfiguration = mailConfiguration;
        this.activeConfiguration = activeConfiguration;
    }

    public MailConfiguration() {
    }

    public boolean isActiveConfiguration() {
        return activeConfiguration;
    }

    public void setActiveConfiguration(boolean activeConfiguration) {
        this.activeConfiguration = activeConfiguration;
    }

    public long getMailConfigurationId() {
        return mailConfigurationId;
    }

    public void setMailConfigurationId(long mailConfigurationId) {
        this.mailConfigurationId = mailConfigurationId;
    }

    public String getMailConfiguration() {
        return mailConfiguration;
    }

    public void setMailConfiguration(String mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }
}
