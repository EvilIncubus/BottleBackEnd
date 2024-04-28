package org.bottleProject.dao;

import org.bottleProject.dto.*;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends Dao<Order> {
    public List<Order> allCustomerOrder(long id);

    String setOrderBottles(OrderBottle orderBottle);

    Long findOrderBottles(long order, long bottleID);

    List<BottleListWrapper> getFinalOrder(Order order);

    InvoiceWrapper getOrderInvoice(Order order);

    Integer countFilterCustomerOrders(int profileId);

    List<FullOrderDto> getAllFilterCustomerOrder(int profileId, String operatorEmail, int page, int size);

    List<FullOrderDto> filterOrders(OrderFilterDto orderSearchDto);

    Integer countFilterCustomerOrder(OrderFilterDto orderSearchDto);

    List<FullOrderDto> getAllFilterOrder(int page, int size);

    Integer countFilterOrders();


    Integer getOrderStatus(String status);

    Integer getOrderAddress(String address);

    List<FullOrderDto> getAllOperatorOrders(String email, int offset, int size);

    Integer countOperatorOrders(String email);

    List<FullOrderDto> searchCustomer(String search);

    FullOrderDto getFullOrder(long orderId);

    void removeBottlesFromStore(int amountBottle, int bottleId);

    List<FullOrderDto> searchOrders(OrderSearchDto searchOrderDto);

    Integer countSearchCustomerOrder(OrderSearchDto searchOrderDto);

    List<FullOrderDto> filterAllOrders(OrderAllFilterDto filterOrderDto);

    Integer countFilterAllOrder(OrderAllFilterDto filterOrderDto);

    List<FullOrderDto> searchAllOrders(OrderSearchAllDto searchOrderDto);

    Integer countSearchAllOrder(OrderSearchAllDto searchOrderDto);

    List<String> getDeliveryAddress();

    List<String> getSearchDeliveryAddress(String search);

    List<Double> countYesterdayAmount(String operatorEmail, LocalDateTime start, LocalDateTime end);

    List<Double> countAllYesterdayAmount(LocalDateTime start, LocalDateTime end);

    String setBottleStatus(OrderBottle orderBottle);

    void updateOrderStatus(int orderId, String status);

    List<FullOrderDto> searchOrdersById(List<Integer> orderIdList);
}
