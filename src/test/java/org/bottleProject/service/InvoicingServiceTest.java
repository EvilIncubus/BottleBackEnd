package org.bottleProject.service;

import org.bottleProject.dao.OrderDao;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class InvoicingServiceTest {
    @Mock
    private OrderDao orderDao;

    private AutoCloseable autoCloseable;


    public void init() {
         autoCloseable = MockitoAnnotations.openMocks(this);
    }


    public void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void shouldCreateAndSave(){


    }
}
