package org.bottleProject.service.mailSendFactory;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.bottleProject.service.MailService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridServiceImpl implements MailService{

    private final String API_KEY = "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";

    public String sendOrderConfirmation(String email, String messageBody, String subject){
        Email from = new Email("vladislav.odai@stefanini.com");
        Email to = new Email(email);
        Content content = new Content("text/html", messageBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            return String.valueOf(sg.api(request));
        } catch (IOException ex) {
            try {
                throw ex;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
