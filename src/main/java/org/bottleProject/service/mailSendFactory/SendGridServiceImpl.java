package org.bottleProject.service.mailSendFactory;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridServiceImpl extends MailService{

    private final String API_KEY = "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";

    public void sendEmail() throws IOException {
        Email from = new Email("vladislav.odai@stefanini.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("vladislav.odai@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
