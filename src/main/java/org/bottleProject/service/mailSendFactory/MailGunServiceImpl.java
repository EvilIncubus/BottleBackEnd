package org.bottleProject.service.mailSendFactory;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public class MailGunServiceImpl extends MailService{

    private final String API_KEY = "872721eed80c876dff8a492918219e47-ca9eeb88-ee5a1d4c";

    private final String YOUR_DOMAIN_NAME = "sandboxc60c083999094943b5962df094f46180.mailgun.org";

    public MessageResponse sendSimpleMessage() {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
                .createApi(MailgunMessagesApi.class);

        Message message = Message.builder()
                .from("vladislav.odai@stefanini.com")
                .to("vladislav.odai@gmail.com")
                .subject("Hello")
                .text("Thank you for your order! For confirm your order please check the link below. ")
                .build();

        return mailgunMessagesApi.sendMessage(YOUR_DOMAIN_NAME, message);
    }

}
