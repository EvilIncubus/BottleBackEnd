package org.bottleProject.service.mailSendFactory;

import org.bottleProject.service.MailServiceFactory;

public class MailServiceFactoryImpl implements MailServiceFactory {

    private MailService mailService;

    @Override
    public MailService createConfiguration(String configType){
        if(configType == null){
            return null;
        } else if (configType.equalsIgnoreCase("MAILGUN")) {
            return mailService = new MailGunServiceImpl();
        } else if (configType.equalsIgnoreCase("SENDGRID")) {
            return mailService = new SendGridServiceImpl();
        }
        return null;
    }
}
