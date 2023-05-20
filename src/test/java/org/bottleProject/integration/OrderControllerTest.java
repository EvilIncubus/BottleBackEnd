package org.bottleProject.integration;

import org.bottleProject.controller.OrderController;
import org.bottleProject.dto.*;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration_test")
public class OrderControllerTest {
    @Autowired
    OrderController orderController;

    @Test
    public void createOrderTest(){
        Order order = new Order();
        order.setProfileId(1);
        order.setDeliveryAddressId(2);
        order.setCreatedDate(LocalDateTime.now());
        order.setStatusId(1);
        ResponseEntity<FullOrderDto> response = orderController.createOrderByCustomerId(new CreateOrderDto());
        assertNotNull(response.getBody());
        order.setOrderId(Objects.requireNonNull(response.getBody().getOrderId()));
        assertEquals(order.getOrderId(), response.getBody());
    }

    @Test
    public void addBottlesToOrderTest(){
        OrderBottle orderBottle = new OrderBottle();
        orderBottle.setAmountBottle(12);
        orderBottle.setBottleId(1);
        orderBottle.setOrderId(1);
        ResponseEntity<String> response = orderController.addItemToOrder(orderBottle);
        assertNotNull(response.getBody());
        assertEquals("Success Create", response.getBody());
    }

    @Test
    public void getOrderByIdTest(){
        ResponseEntity<InvoiceWrapper> response = orderController.getOrderById(1);
        assertNotNull(response.getBody());
    }

    @Test
    public void getListOfOrderTest(){
        ListOfCustomersOrdersDto listOrderDto = new ListOfCustomersOrdersDto();
        listOrderDto.setProfileId(1);
        listOrderDto.setPage(1);
        listOrderDto.setSize(1);
    }

    @Test
    public void searchOrderTest(){
        OrderFilterDto orderSearchDto = new OrderFilterDto();
        orderSearchDto.setFromDate(LocalDateTime.parse("2022-02-09T11:59:46"));
        orderSearchDto.setPage(1);
        orderSearchDto.setSize(2);
    }
}
