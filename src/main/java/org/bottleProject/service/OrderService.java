package org.bottleProject.service;

import org.bottleProject.dto.*;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;

public interface OrderService {

    Page<FullOrderDto> getAllCustomerOrder(ListOfCustomersOrdersDto listOrderDto);

    Page<FullOrderDto> getAllOrder(int page, int size);

    long createOrder(CreateOrderDto order);

    String addItemToOrder(OrderBottle orderBottle);

    InvoiceWrapper getOrderById(long orderId);

    Page<Order> searchOrder(OrderSearchDto orderSearchDto);

    Page<FullOrderDto> getListOfOperatorsOrders(CustomersQueryForOperatorDto customersQueryForOperator);

    Page<FullOrderDto> getAllFilterCustomerOrder(String search);
}
