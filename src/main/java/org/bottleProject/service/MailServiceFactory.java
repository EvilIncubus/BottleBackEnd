package org.bottleProject.service;

import org.bottleProject.service.mailSendFactory.MailService;

public interface MailServiceFactory {
    MailService createConfiguration(String configType);
}
