package org.bottleProject.service;

import org.bottleProject.dao.OrderDao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class InvoicingServiceTest {
    @MockBean
    private OrderDao orderDao;

    @Autowired
    private InvoicingService invoicingService;

    @Test
    public void shouldPrepareInvoice(){

    }

    @Test
    public void shouldGetInvoiceContent(){

    }

}
