package org.bottleProject.service.mailSendFactory;

import org.bottleProject.service.MailService;
import org.bottleProject.service.MailServiceFactory;
import org.springframework.stereotype.Service;

@Service
public class MailServiceFactoryImpl implements MailServiceFactory {

    @Override
    public MailService createConfiguration(String configType){
        if(configType == null){
            return null;
        } else if (configType.equalsIgnoreCase("MAILGUN")) {
            return new MailGunServiceImpl();
        } else if (configType.equalsIgnoreCase("SENDGRID")) {
            return new SendGridServiceImpl();
        }
        return null;
    }
}
