package org.example.service;

import org.example.dao.OrderDao;
import org.example.entity.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class InvoicingServiceTest {
    @Mock
    private OrderDao<Order> orderDao;

    private AutoCloseable autoCloseable;

    @Before
    public void init() {
         autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void shouldCreateAndSave(){


    }
}
