package org.bottleProject.service;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

public class OrderServiceTest {

    @MockBean
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldCreateOrder(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Order order =new Order();
        order.setOrderId(2L);
        order.setCustomerId(1);
        order.setDeliveryAddress("str. Alexei Mateevici 14");
        order.setCurentDate(localDateTime);
        order.setStatusId(3);
        long id = 2L;

//        given(orderDao.findById(anyLong())).willReturn(order);
//        given(orderDao.create(order)).willReturn(id);
//        given(orderDao.findOrderBottles(anyInt(), anyInt())).willReturn(id);
//        given(orderDao.setOrderBottles(anyLong(), anyLong())).willReturn("Success Create");
//        given(orderDao.updateOrderBottles(anyLong())).willReturn("Success Update");

//        Order order1 = orderService.createOrder(order);
//        assertEquals(order, order1);
    }

    @Test
    public void shouldAddBottlesToOrder(){

    }

    @Test
    public void shouldGetAllOrders(){

    }
    @Test
    public void shouldGetOrderById(){

    }

    @Test
    public void shouldSearchOrder(){

    }
}
