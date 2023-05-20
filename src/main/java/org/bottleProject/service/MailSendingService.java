package org.bottleProject.service;

import org.bottleProject.dto.MailSendingDto;

public interface MailSendingService {

    void submitCreatedOrderByCustomer(MailSendingDto mailSendingDto);
}
