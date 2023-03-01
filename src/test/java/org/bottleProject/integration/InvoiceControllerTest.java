package org.bottleProject.integration;

import org.bottleProject.controller.InvoiceController;
import org.bottleProject.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration_test")
public class InvoiceControllerTest {
    @Autowired
    InvoiceController invoiceController;

    @Test
    public void createInvoiceTest(){
        Order order = new Order();
        order.setOrderId(1);
//        order.setAddressId("str. Mesterul Manole 15");
        order.setCurentDate(LocalDateTime.now());
        order.setStatusId(1);
        ResponseEntity<String> response = invoiceController.createInvoiceByOrder(order);
        assertEquals(response.getBody(), "invoice was created successfully.");
    }

    @Test
    public void saveInvoiceTest(){

    }

    @Test
    public void downloadInvoiceTest(){
        try {
            ResponseEntity<Resource> response = invoiceController.download(1L, 1L);
            assertNotNull(response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
