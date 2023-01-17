package org.bottleProject.service;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.entity.Order;
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
