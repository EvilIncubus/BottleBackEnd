package org.bottleProject.service;

import org.bottleProject.dto.CreateOrderDto;
import org.bottleProject.dto.FullOrderDto;
import org.bottleProject.dto.ListOrderDto;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;

public interface OrderService {

    Page<Order> getAllCustomerOrder(ListOrderDto listOrderDto);

    Page<FullOrderDto> getAllOrder(int page, int size);

    long createOrder(CreateOrderDto order);

    String addItemToOrder(OrderBottle orderBottle);

    Order getOrderById(long orderId);

    Page<Order> searchOrder(OrderSearchDto orderSearchDto);
}
