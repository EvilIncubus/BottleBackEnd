package org.bottleProject.service;

public interface MailService {
    String sendOrderConfirmation(String email, String messageBody, String subject);
}
