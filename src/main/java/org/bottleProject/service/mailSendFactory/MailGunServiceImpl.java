package org.bottleProject.service.mailSendFactory;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.bottleProject.service.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailGunServiceImpl implements MailService {

    private final String API_KEY = "872721eed80c876dff8a492918219e47-ca9eeb88-ee5a1d4c";

    private final String YOUR_DOMAIN_NAME = "sandboxc60c083999094943b5962df094f46180.mailgun.org";

    @Override
    public String sendOrderConfirmation(String email, String messageBody, String subject) {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
                .createApi(MailgunMessagesApi.class);

        Email from = new Email("vladislav.odai@stefanini.com");
        Email to = new Email(email);
        Content content = new Content("text/html", messageBody);
        Mail mail = new Mail(from, subject, to, content);

        Message message = Message.builder()
                .from("vladislav.odai@stefanini.com")
                .to(email)
                .subject(subject)
                .html(messageBody)
                .build();

        return String.valueOf(mailgunMessagesApi.sendMessage(YOUR_DOMAIN_NAME, message));
    }

}
