package org.example.implementation;

import org.example.connectionDB.ConnectionDB;
import org.example.dao.impl.OrderDaoImpl;
import org.example.dto.BottleListWrapper;
import org.example.dto.InvoiceWrapper;
import org.example.entity.Order;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class OrderDaoImplTest {

    OrderDaoImpl orderImplementDao = new OrderDaoImpl(ConnectionDB.getInstance());

    Order order = new Order();

    @Test
    public void testCreateAndDelete() {
        order.setDeliveryAddress("str. Mesterul Manole 15");
        order.setLocalDateTime(LocalDateTime.now());
        order.setStatusID(1);
        long id = orderImplementDao.create(order);
        order.setOrderID(id);

        Order order1 = orderImplementDao.findById(id);
        assertNotNull(order1);
        assertEquals(order1, order);
        orderImplementDao.removeById(id);
        assertNull(orderImplementDao.findById(id));
    }

    @Test
    public void testGetAll() {
        List<Order> orders = orderImplementDao.getAll();

        assertNotNull(orders);
    }

    @Test
    public void testFindById() {
        order = orderImplementDao.findById(50L);

        assertNotNull(order);
    }

    @Test
    public void testUpdate() {
        order = orderImplementDao.findById(50L);
        String oldDeliveryAddress = order.getDeliveryAddress();
        order.setDeliveryAddress("str. Mihail Cogalniceanu 12");
        Order order1 = orderImplementDao.update(order, 50L);

        assertNotNull(order1);
        assertEquals(order, order1);
        order.setDeliveryAddress(oldDeliveryAddress);
        orderImplementDao.update(order, 50L);
    }

    @Test
    public void testFindOrderBottles() {
        long id = orderImplementDao.findOrderBottles(1L, 1L);
        assertEquals(1 , id);
    }

    @Test
    public void testSetOrderBottles() {
        String answer = orderImplementDao.setOrderBottles(2L, 2L);
        assertNotNull(answer);
        orderImplementDao.findOrderBottles(2L, 2L);
    }

    @Test
    public void testUpdateOrderBottles() {
        String answer = orderImplementDao.updateOrderBottles(1L);
        assertNotNull(answer);
    }

    @Test
    public void testGetFinalOrder() {
        List<BottleListWrapper> finalOrderDtoList = orderImplementDao.getFinalOrder(orderImplementDao.findById(1L));
        assertNotNull(finalOrderDtoList);
    }

    @Test
    public void testGetOrder() {
        InvoiceWrapper orderDto = orderImplementDao.getOrderInvoice(orderImplementDao.findById(1L));
        assertNotNull(orderDto);
    }

}
