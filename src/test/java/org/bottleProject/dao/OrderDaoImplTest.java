package org.bottleProject.dao;

import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration_test")
public class OrderDaoImplTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testCreateAndDelete() {
        Order order = new Order();
//        order.setAddressId("str. Mesterul Manole 15");
        order.setCreatedDate(LocalDateTime.now());
        order.setStatusId(1);
        order = orderDao.create(order);
        Order order1 = orderDao.findById(order.getOrderId());
        order.setOrderId(order1.getOrderId());
        assertNotNull(order1);
        assertEquals(order1, order);
        System.out.println(order);
        System.out.println(order1);
        orderDao.removeById(order.getOrderId());
        assertNull(orderDao.findById(order.getOrderId()));
    }

    @Test
    public void testGetAll() {
        List<Order> orders = orderDao.getAll(1,1);

        assertNotNull(orders);
    }

    @Test
    public void testFindById() {
        Order order = orderDao.findById(50L);

        assertNotNull(order);
    }

    @Test
    public void testUpdate() {
//        Order order = orderDao.findById(50L);
//        String oldDeliveryAddress = order.getAddressId();
//        order.setAddressId("str. Mihail Cogalniceanu 12");
//        Order order1 = orderDao.update(order, 50L);
//
//        assertNotNull(order1);
//        assertEquals(order, order1);
//        order.setAddressId(oldDeliveryAddress);
//        orderDao.update(order, 50L);
    }

    @Test
    public void testFindOrderBottles() {
        long id = orderDao.findOrderBottles(1L, 1L);
        assertEquals(1L , id);
    }

    @Test
    public void testSetOrderBottles() {
        OrderBottle orderBottle = new OrderBottle();
        orderBottle.setBottleId(2);
        orderBottle.setOrderId(2);
        orderBottle.setAmountBottle(15);
        String answer = orderDao.setOrderBottles(orderBottle);
        assertNotNull(answer);
        orderDao.findOrderBottles(1L, 1L);
    }

    @Test
    public void testGetFinalOrder() {
        List<BottleListWrapper> finalOrderDtoList = orderDao.getFinalOrder(orderDao.findById(1L));
        assertNotNull(finalOrderDtoList);
    }

    @Test
    public void testGetOrderInvoice() {
        InvoiceWrapper orderDto = orderDao.getOrderInvoice(orderDao.findById(1L));
        assertNotNull(orderDto);
    }

    @Test
    public void testCountOrders() {
        int count = orderDao.countFilterCustomerOrders(1);
        assertNotEquals(0, count);
    }

    @Test
    public void testGetAllFilterOrder() {
        List<Order> orderList = orderDao.getAllFilterCustomerOrder(1, 1,0);
        System.out.println(orderList);
        assertNotNull(orderList);
    }
}
