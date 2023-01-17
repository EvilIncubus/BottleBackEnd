package org.bottleProject.service;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;
import org.bottleProject.service.impl.OrderServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class OrderServiceTest {

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
        order.setBottles(bottles);
        Order order1 = orderService.addBottlesToOrder(order);
        assertEquals(order, order1);
    }
}