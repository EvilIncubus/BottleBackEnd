package org.example.service;

import org.example.dao.OrderDao;
import org.example.entity.Bottle;
import org.example.entity.Order;
import org.example.service.impl.OrderServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class OrderServiceTest {

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
    public void shouldCreateOrderAndAddBottles(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Bottle bottle = new Bottle(1L, "Peace", 4, true, true, localDateTime, "Drink water SRL", 13, 5);
        List<Bottle> bottles = new ArrayList<>();
        bottles.add(bottle);
        Order order =new Order();
        order.setOrderID(2L);
        order.setCustomerID(1);
        order.setDeliveryAddress("str. Alexei Mateevici 14");
        order.setLocalDateTime(localDateTime);
        order.setStatusID(3);
        order.setBottles(bottles);
        long id = 2L;

        given(orderDao.findById(anyLong())).willReturn(order);
        given(orderDao.create(order)).willReturn(id);
        given(orderDao.findOrderBottles(anyInt(), anyInt())).willReturn(id);
        given(orderDao.setOrderBottles(anyLong(), anyLong())).willReturn("Success Create");
        given(orderDao.updateOrderBottles(anyLong())).willReturn("Success Update");

        OrderService orderService = new OrderServiceImpl(orderDao);
        Order order1 = orderService.addBottlesToOrder(order, bottles);
        assertEquals(order, order1);
    }
}
