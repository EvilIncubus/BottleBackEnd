package org.bottleProject.service;

import org.bottleProject.dto.ListOrderDto;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;

import java.util.List;

public interface OrderService {

    public Page<Order> getAllOrder(ListOrderDto listOrderDto);

    Order createOrder(Order order);

    String addItemToOrder(OrderBottle orderBottle);

    Order getOrderById(long orderId);

    Page<Order> searchOrder(OrderSearchDto orderSearchDto);
}
