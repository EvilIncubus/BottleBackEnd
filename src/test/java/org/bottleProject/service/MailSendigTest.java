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
        String messageResponse = mailGunService.sendOrderConfirmation("vladislav.odai@gmail.com", "localhost:3000/confirmOrder", "hi customer");
        System.out.println(messageResponse);
    }

    @Test
    void sendMailBySendGridTest() throws IOException {
        sendGridService.sendOrderConfirmation("vladislav.odai@gmail.com", "localhost:3000/confirmOrder", "hi customer");

    }
}
