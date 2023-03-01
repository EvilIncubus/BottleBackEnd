package org.bottleProject.service;

import com.mailgun.model.message.MessageResponse;
import org.bottleProject.service.mailSendFactory.MailGunServiceImpl;
import org.bottleProject.service.mailSendFactory.SendGridServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("integration_test")
public class MailSendigTest {

    @Autowired
    MailGunServiceImpl mailGunService;

    @Autowired
    SendGridServiceImpl sendGridService;

    @Test
    void sendMailByMailGunTest(){
        MessageResponse messageResponse = mailGunService.sendSimpleMessage();
        System.out.println(messageResponse.getMessage());
    }

    @Test
    void sendMailBySendGridTest() throws IOException {
        sendGridService.sendEmail();

    }
}
