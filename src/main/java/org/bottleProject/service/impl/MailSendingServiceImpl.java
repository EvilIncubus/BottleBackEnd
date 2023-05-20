package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dao.SettingsDao;
import org.bottleProject.dto.MailSendingDto;
import org.bottleProject.service.MailSendingService;
import org.bottleProject.service.MailService;
import org.bottleProject.service.MailServiceFactory;
import org.springframework.stereotype.Service;

@Service
public class MailSendingServiceImpl implements MailSendingService {

    private final MailServiceFactory mailServiceFactory;
    private final SettingsDao settingsDao;
    private final OrderDao orderDao;

    public MailSendingServiceImpl(MailServiceFactory mailServiceFactory, SettingsDao settingsDao, OrderDao orderDao) {
        this.mailServiceFactory = mailServiceFactory;
        this.settingsDao = settingsDao;
        this.orderDao = orderDao;
    }

    @Override
    public void submitCreatedOrderByCustomer(MailSendingDto mailSendingDto) {
        String configuration = settingsDao.getMailActiveConfiguration();
        String link = "http://localhost:8080/rest/api/customer/order/setOrderConfirmation/" + mailSendingDto.getOrderId();
        String confirmLink = link.concat("/ApprovedByCustomer");
        String rejectLink = link.concat("/RejectByCustomer");
        MailService mailService = mailServiceFactory.createConfiguration(configuration);
        mailService.sendOrderConfirmation(mailSendingDto.getEmail(), getHTMLBody(mailSendingDto.getFirstName(), mailSendingDto.getLastName(), confirmLink, rejectLink), getMessageHeader(mailSendingDto.getFirstName(), mailSendingDto.getLastName()));
        orderDao.updateOrderStatus(mailSendingDto.getOrderId(), mailSendingDto.getStatus());
    }

    private String getHTMLBody(String firstName, String lastName, String confirmLink, String rejectLink) {
        return "<h1>Hello dear " + firstName + " " + lastName + " </h1>" +
                "<p>here is confirm link for you  </p>" +
                "<form action=\"" + confirmLink + "\">" +
                "<input type=\"submit\" value=\"Confirm!\" />" +
                "</form>" +
                "<p>here is reject link for you </p>" +
                "<form action=\"" + rejectLink + "\">" +
                "<input type=\"submit\" value=\"Reject!\" />" +
                "</form>";
    }

    private String getMessageHeader(String firstName, String lastName) {
        return "Order confirmation for " + firstName + " " + lastName;
    }
}
