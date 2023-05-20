package org.bottleProject.service;

public interface MailServiceFactory {
    MailService createConfiguration(String configType);
}
