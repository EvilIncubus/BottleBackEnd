package org.bottleProject.service;

import org.bottleProject.dto.*;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;

import java.util.List;

public interface OrderService {

    Page<FullOrderDto> getAllCustomerOrder(ListOfCustomersOrdersDto listOrderDto);

    Page<FullOrderDto> getAllOrder(int page, int size);

    FullOrderDto createOrder(CreateOrderDto order);

    String addItemToOrder(OrderBottle orderBottle);

    InvoiceWrapper getOrderById(long orderId);

    Page<FullOrderDto> filterOrder(OrderFilterDto orderSearchDto);

    Page<FullOrderDto> getListOfOperatorsOrders(CustomersQueryForOperatorDto customersQueryForOperator);

    Page<FullOrderDto> searchOrder(OrderSearchDto searchOrderDto);

    Page<FullOrderDto> filterAllOrder(OrderAllFilterDto filterOrderDto);

    Page<FullOrderDto> searchAllOrder(OrderSearchAllDto searchOrderDto);

    List<String> getDeliveryAddress();

    List<String> getSearchDeliveryAddress(String search);

    Double countYesterdayAmount(String operatorEmail);

    Double countAllYesterdayAmount();

    String setBottleStatus(OrderBottle orderBottle);

    String submitCreatedOrderByCustomer(int orderId, String status);
}

